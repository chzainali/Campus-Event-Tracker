package com.example.campuseventtracker.model;

import java.io.Serializable;

public class UsersData implements Serializable {
    int id;
    String userName, email, password, phoneNum;


    public UsersData(String userName, String email, String password,String phoneNum) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
    }

    public UsersData() {

    }

    public UsersData(int id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
