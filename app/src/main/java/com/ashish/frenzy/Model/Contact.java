package com.ashish.frenzy.Model;

public class Contact {

    private String name;
    private String ph_number;
    private int display_picture;

    public Contact(String name, String ph_number) {
        this.name = name;
        this.ph_number = ph_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPh_number() {
        return ph_number;
    }

    public void setPh_number(String ph_number) {
        this.ph_number = ph_number;
    }

    public int getDisplay_picture() {
        return display_picture;
    }

    public void setDisplay_picture(int display_picture) {
        this.display_picture = display_picture;
    }
}