package com.example.mindsafe.helper;

public class GetProfile {

    private static String imgage;

    private static String name;

    private static String email;

    private static int id;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        GetProfile.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        GetProfile.email = email;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        GetProfile.id = id;
    }

    public static String getProfile(){
        return imgage;
    }
    public static void setProfile(String img){
        imgage=img;
    }
}
