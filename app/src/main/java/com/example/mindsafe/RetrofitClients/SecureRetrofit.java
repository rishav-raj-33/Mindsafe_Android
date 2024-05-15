package com.example.mindsafe.RetrofitClients;

import com.example.mindsafe.Activities.MainActivity;
import com.example.mindsafe.Api.SecureApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecureRetrofit {
    public static SecureRetrofit registerRetrofitInstance1;
    public static Retrofit secureRetrofit;
    public String token;

    public SecureRetrofit(String token) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = clientBuilder
                .addInterceptor(new AuthInterceptor(token))
                .build();

        secureRetrofit = new Retrofit.Builder()
                .baseUrl(SecureApi.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }



    public static synchronized SecureRetrofit getInstance(String token) {
        if (registerRetrofitInstance1 == null) {
            registerRetrofitInstance1 = new SecureRetrofit(token);
        }
        return registerRetrofitInstance1;
    }

    public SecureApi getApi() {
        return secureRetrofit.create(SecureApi.class);
    }











}

