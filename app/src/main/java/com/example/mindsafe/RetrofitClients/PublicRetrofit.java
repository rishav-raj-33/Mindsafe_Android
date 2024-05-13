package com.example.mindsafe.RetrofitClients;


import com.example.mindsafe.Api.AuthApi;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PublicRetrofit {

    public static PublicRetrofit registerRetrofitInstance;
    public static Retrofit retrofit;

    public PublicRetrofit() {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = clientBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(AuthApi.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static synchronized PublicRetrofit getInstance() {
        if (registerRetrofitInstance == null) {
            registerRetrofitInstance = new PublicRetrofit();
        }
        return registerRetrofitInstance;
    }

    public AuthApi getApi() {
        return retrofit.create(AuthApi.class);
    }




}
