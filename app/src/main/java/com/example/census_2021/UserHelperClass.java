package com.example.census_2021;


public class UserHelperClass {
    String Name = "", Mobile_No = "";
    public UserHelperClass() {
    }

    public UserHelperClass(String NameT, String MobileNo) {
        this.Name = NameT;
        this.Mobile_No = MobileNo;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile_No() {
        return Mobile_No;
    }

    public void setMobile_No(String mobile_No) {
        Mobile_No = mobile_No;
    }
}