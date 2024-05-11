package com.example.mindsafe.responseModels;


public class RegisterResponseModel {



    public String msg;
    public boolean success;


    public RegisterResponseModel() {
    }

    public RegisterResponseModel(String msg, boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
