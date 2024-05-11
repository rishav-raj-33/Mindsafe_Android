package com.example.mindsafe.RetrofitClients;

import com.example.mindsafe.Api.AuthApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterRetrofit {
    public static RegisterRetrofit registerRetrofitInstance;
    public static  Retrofit retrofit;

    public RegisterRetrofit() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ContentTypeInterceptor())
                .build();
        retrofit=new Retrofit.Builder()
                .baseUrl(AuthApi.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }


    public static synchronized RegisterRetrofit getInstance(){
        if(registerRetrofitInstance==null){
            registerRetrofitInstance=new RegisterRetrofit();
        }
        return registerRetrofitInstance;
    }
   public AuthApi getApi(){
       return  retrofit.create(AuthApi.class);
    }
}
