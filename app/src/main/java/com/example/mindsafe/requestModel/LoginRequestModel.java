package com.example.mindsafe.requestModel;

public class LoginRequestModel {

    public String email;
    public String password;




    public LoginRequestModel() {
    }

    public LoginRequestModel(String email, String password) {
        this.email = email;
        this.password = password;

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
}
