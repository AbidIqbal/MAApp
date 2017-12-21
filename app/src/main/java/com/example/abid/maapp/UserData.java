package com.example.abid.maapp;

public class UserData {

    private String firstName;
    private String secondName;
    private String userName;
    private String email;
    private String contact;
    private String address;
    private String password;
    private String service;


    public UserData(String fname, String sname, String uname, String mail, String contactnum, String adress, String pass, String servicename) {
        firstName = fname;
        secondName = sname;
        userName = uname;
        email = mail;
        contact = contactnum;
        address = adress;
        password = pass;
        service = servicename;
    }

}
