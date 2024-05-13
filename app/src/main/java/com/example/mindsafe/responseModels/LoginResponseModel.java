package com.example.mindsafe.responseModels;

import java.time.LocalDateTime;

public class LoginResponseModel {

    public String jwtToken,userName;
    public boolean success;

    public LocalDateTime expireDateTime;

    public LocalDateTime getExpireDateTime() {
        return expireDateTime;
    }

    public void setExpireDateTime(LocalDateTime expireDateTime) {
        this.expireDateTime = expireDateTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LoginResponseModel(String jwtToken, String userName, boolean success,LocalDateTime expireDateTime) {
        this.jwtToken = jwtToken;
        this.userName = userName;
        this.success=success;
        this.expireDateTime=expireDateTime;
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
