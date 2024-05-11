package com.example.mindsafe.RetrofitClients;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ContentTypeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request newRequest = originalRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        return chain.proceed(newRequest);
    }
}
