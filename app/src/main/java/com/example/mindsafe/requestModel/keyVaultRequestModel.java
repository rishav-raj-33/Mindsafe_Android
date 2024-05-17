package com.example.mindsafe.requestModel;

public class keyVaultRequestModel {

    private String userName;
    private String password;
    private String notes;

    public keyVaultRequestModel() {
    }

    public keyVaultRequestModel(String userName, String password, String notes) {
        this.userName = userName;
        this.password = password;
        this.notes = notes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
