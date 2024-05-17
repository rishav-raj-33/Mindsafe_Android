package com.example.mindsafe.responseModels;

public class KeyVaultResponseModel {

    private Integer id;
    private String userName;
    private String password;
    private String notes;

    public KeyVaultResponseModel(Integer id, String userName, String password, String notes) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public KeyVaultResponseModel() {
    }

    public void setId(Integer id) {
        this.id = id;
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
