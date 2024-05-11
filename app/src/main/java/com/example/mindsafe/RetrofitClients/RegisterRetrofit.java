package com.example.mindsafe.RetrofitClients;


import com.example.mindsafe.Api.AuthApi;
import com.example.mindsafe.Api.KeyVaultApi;
import com.example.mindsafe.Api.UserApi;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterRetrofit {

    public static RegisterRetrofit registerRetrofitInstance;
    public static Retrofit retrofit;

    public RegisterRetrofit() {

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

    public static synchronized RegisterRetrofit getInstance() {
        if (registerRetrofitInstance == null) {
            registerRetrofitInstance = new RegisterRetrofit();
        }
        return registerRetrofitInstance;
    }

    public AuthApi getApi() {
        return retrofit.create(AuthApi.class);
    }

    public KeyVaultApi getKeyVaultApi(){
        return  retrofit.create(KeyVaultApi.class);
    }

    public UserApi getUserApi(){
        return  retrofit.create(UserApi.class);
    }


}
