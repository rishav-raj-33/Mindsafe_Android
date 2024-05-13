package com.example.mindsafe.RetrofitClients;

import com.example.mindsafe.Api.SecureApi;
import com.example.mindsafe.helper.GetJwt;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecureRetrofit {
    public static PublicRetrofit registerRetrofitInstance;
    public static Retrofit retrofit;

    public SecureRetrofit() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = clientBuilder
                .addInterceptor(new AuthInterceptor(new GetJwt().getJwt()))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(SecureApi.url)
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

    public SecureApi getApi() {
        return retrofit.create(SecureApi.class);
    }






}

