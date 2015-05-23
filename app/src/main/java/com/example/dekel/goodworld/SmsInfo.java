package com.example.dekel.goodworld;

/**
 * Created by Dekel on 5/19/2015.
 */
public class SmsInfo {

    private String message = "";
    private String username = "";
    private String userNumber;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }
}
