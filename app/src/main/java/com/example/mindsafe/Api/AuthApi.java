package com.example.mindsafe.Api;

import com.example.mindsafe.requestModel.LoginRequestModel;
import com.example.mindsafe.responseModels.LoginResponseModel;
import com.example.mindsafe.responseModels.APIResponseModel;
import com.example.mindsafe.requestModel.RegisterRequestModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {




    String url="http://10.0.2.2:8080/";
//    @Headers("Content-Type: application/x-www-form-urlencoded")
//    @FormUrlEncoded
  @POST("api/auth/register")
    Call<APIResponseModel>Register(
            @Body RegisterRequestModel model);



    @POST("api/auth/login")
    Call<LoginResponseModel>Login(
            @Body LoginRequestModel model);
}






