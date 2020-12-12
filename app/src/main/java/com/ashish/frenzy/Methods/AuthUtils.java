package com.ashish.frenzy.Methods;

import com.ashish.frenzy.Model.Contact;

public class AuthUtils {
    public static String validatePhone(Contact contact) {
        String phone = contact.getPh_number().replaceAll("\\s", "");
        if(phone.length() == 10) {
            phone = "+91" + contact.getPh_number();
            return phone.replaceAll("\\s", "");
        }
        return phone ;
    }
}
