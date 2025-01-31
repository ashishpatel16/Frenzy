package com.ashish.frenzy.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ashish.frenzy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private TextInputEditText mPhoneNumber,mVerificationCode;
    private MaterialButton mSubmitButton;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseUser mUser;
    private String mVerficationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        isUserLoggedIn();
        // If user is logged in already, we directly jump to HomeActivity.

        mPhoneNumber = findViewById(R.id.ph_number_edittext);
        mVerificationCode = findViewById(R.id.code_edittext);
        mSubmitButton = findViewById(R.id.submit_code);

        // Callback for Firebase Phone Auth
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredentials(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(LoginActivity.this, "Something went Wrong. PLease Retry!", Toast.LENGTH_SHORT).show();
                Log.i("debug",e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mVerficationID = s;
                mSubmitButton.setText("Log In");
            }
        };

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phNumber = "+91" + mPhoneNumber.getText().toString();
                if(mVerficationID != null) {
                    verifyPhoneNumberWithCode(mVerficationID);
                }else {
                    phoneAuthInit(phNumber);
                }
            }
        });

    }

    private void isUserLoggedIn() {
        mUser = mAuth.getCurrentUser();
        if(mUser != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    /** Handling Authentication*/
    private void signInWithPhoneAuthCredentials(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Hurray! Login Success", Toast.LENGTH_SHORT).show();
                    updateUserDatabase();
                    isUserLoggedIn();
                }else {
                    Toast.makeText(LoginActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUserDatabase() {
        final FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            final DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
            mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.exists()) {
                        Log.i(TAG, "onDataChange: Firebase User doesn't exist right now. Adding this user to Database.");

                        HashMap<String, Object> newUser = new HashMap<>();
                        newUser.put("name",user.getPhoneNumber());
                        newUser.put("phone",user.getPhoneNumber());
                        mDatabaseReference.updateChildren(newUser);

                        Log.i(TAG, "onDataChange: Added new User.");
                    }else {
                        Log.i(TAG, "onDataChange: User already exists. No database Changes required.");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.i(TAG, "onCancelled: Database Error : "+ error.getMessage());
                }
            });
        }
    }


    private void verifyPhoneNumberWithCode(String id) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, mVerificationCode.getText().toString());
        signInWithPhoneAuthCredentials(credential);
    }

    private void phoneAuthInit(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        mAuth.setLanguageCode("en");
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


}