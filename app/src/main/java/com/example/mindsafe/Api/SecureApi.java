package com.example.mindsafe.Api;

import com.example.mindsafe.requestModel.LoginRequestModel;
import com.example.mindsafe.responseModels.LoginResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SecureApi {

    String url="http://10.0.2.2:8080/";


    @GET("api/auth/current-user/")
    Call<LoginResponseModel> getLogdinUser();




}






