package com.example.mindsafe.responseModels;

public class UserResponseModel {
    public UserResponseModel() {
    }

    public UserResponseModel(int id, String email, String name, String profilePhoto) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profilePhoto = profilePhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    private int id;
    private String email;
    private String name;
    private String profilePhoto;
}
