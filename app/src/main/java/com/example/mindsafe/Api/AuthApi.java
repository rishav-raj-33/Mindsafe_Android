package com.example.mindsafe.Api;

import com.example.mindsafe.requestModel.LoginRequestModel;
import com.example.mindsafe.responseModels.LoginResponseModel;
import com.example.mindsafe.responseModels.APIResponseModel;
import com.example.mindsafe.requestModel.RegisterRequestModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AuthApi {




    String url="http://192.168.172.23:8080/";
//    @Headers("Content-Type: application/x-www-form-urlencoded")
//    @FormUrlEncoded
  @POST("api/auth/register")
    Call<APIResponseModel>Register(
            @Body RegisterRequestModel model);



    @POST("api/auth/login")
    Call<LoginResponseModel>Login(
            @Body LoginRequestModel model);

    @POST("api/auth/confirm")
    Call<APIResponseModel> confirmToken(
            @Query("confirmationToken") String otp
    );

    @POST("api/auth/token/resend")
    Call<APIResponseModel> reSend(
            @Query("email") String email
    );


}








