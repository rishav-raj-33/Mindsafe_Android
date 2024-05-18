package com.example.mindsafe.responseModels;

import java.time.LocalDateTime;

public class LoginResponseModel {

    public String jwtToken,userName;
    public boolean success;

    public int id;

    public LoginResponseModel(String jwtToken, String userName, boolean success, int id, String expireDateTime) {
        this.jwtToken = jwtToken;
        this.userName = userName;
        this.success = success;
        this.id = id;
        this.expireDateTime = expireDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String expireDateTime;

    public String  getExpireDateTime() {
        return expireDateTime;
    }

    public void setExpireDateTime(String expireDateTime) {
        this.expireDateTime = expireDateTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
