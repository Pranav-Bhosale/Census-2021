package com.example.census_2021;


public class UserHelperClass {
    String mail = " ", pass = " ";
    public UserHelperClass() {
    }

    public UserHelperClass(String mail, String pass) {
        this.mail = mail;
        this.pass = pass;

    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}