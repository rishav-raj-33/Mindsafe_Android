package com.example.mindsafe.responseModels;

public class LoginResponseModel {

    public String jwtToken,userName;

    public LoginResponseModel(String jwtToken, String userName) {
        this.jwtToken = jwtToken;
        this.userName = userName;
    }

    public LoginResponseModel() {
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
