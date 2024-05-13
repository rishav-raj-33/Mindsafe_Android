package com.example.mindsafe.helper;

import android.content.SharedPreferences;

public class GetJwt {

    private SharedPreferences sp;

    public SharedPreferences getSp() {
        return sp;
    }

    public void setSp(SharedPreferences sp) {
        this.sp = sp;
    }

    public GetJwt() {
    }

    public GetJwt(SharedPreferences sp) {
        this.sp = sp;
    }

    public  String getJwt(){
        return sp.getString("jwt",null);
    }


}
