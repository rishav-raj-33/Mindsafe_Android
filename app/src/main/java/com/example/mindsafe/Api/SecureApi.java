package com.example.mindsafe.Api;

import com.example.mindsafe.requestModel.LoginRequestModel;
import com.example.mindsafe.responseModels.APIResponseModel;
import com.example.mindsafe.responseModels.LoginResponseModel;
import com.example.mindsafe.responseModels.UserResponseModel;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SecureApi {

    String url="http://192.168.172.23:8080/";


    @GET("api/auth/current-user/")
    Call<UserResponseModel> getLogdinUser();

@POST("api/user/dp/upload/{id}")
    Call<APIResponseModel> upload(@Path("id") int id, @Query("image")File file);


@DELETE("api/user/{id}")
    Call<APIResponseModel> deleteUser(@Path("id") int id);
}






