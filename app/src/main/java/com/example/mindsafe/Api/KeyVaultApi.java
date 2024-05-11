package com.example.mindsafe.Api;

import com.example.mindsafe.requestModel.RegisterRequestModel;
import com.example.mindsafe.responseModels.RegisterResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface KeyVaultApi {


    String url="http://10.0.2.2:8080/api/key";


    @POST("/user/{id}")
    Call<RegisterResponseModel> Register(
            @Body RegisterRequestModel model);
}
