package com.example.mindsafe.Api;

import com.example.mindsafe.requestModel.LoginRequestModel;
import com.example.mindsafe.responseModels.LoginResponseModel;
import com.example.mindsafe.responseModels.RegisterResponseModel;
import com.example.mindsafe.requestModel.RegisterRequestModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {




    String url="http://10.0.2.2:8080/api/auth/";
//    @Headers("Content-Type: application/x-www-form-urlencoded")
//    @FormUrlEncoded
  @POST("register")
    Call<RegisterResponseModel>Register(
            @Body RegisterRequestModel model);



    @POST("login")
    Call<LoginResponseModel>Login(
            @Body LoginRequestModel model);
}






