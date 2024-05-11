package com.example.mindsafe.RetrofitClients;

import com.example.mindsafe.Api.AuthApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRetrofit {
    public static LoginRetrofit loginRetrofitInstance;
    public void setLoginRetrofitInstance(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthApi api=retrofit.create(AuthApi.class);
    }
    public static LoginRetrofit getInstance(){
        if (loginRetrofitInstance==null){
            loginRetrofitInstance=new LoginRetrofit();
        }
        return loginRetrofitInstance;
    }

}
