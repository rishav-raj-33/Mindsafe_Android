package com.example.mindsafe.Api;

import com.example.mindsafe.helper.ImageRequestBody;
import com.example.mindsafe.requestModel.LoginRequestModel;
import com.example.mindsafe.requestModel.keyVaultRequestModel;
import com.example.mindsafe.responseModels.APIResponseModel;
import com.example.mindsafe.responseModels.KeyPageResponse;
import com.example.mindsafe.responseModels.KeyVaultResponseModel;
import com.example.mindsafe.responseModels.LoginResponseModel;
import com.example.mindsafe.responseModels.UserResponseModel;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SecureApi {

    String url="http://192.168.172.23:8080/";


    @GET("api/auth/current-user/")
    Call<UserResponseModel> getLogdinUser();

@POST("api/user/dp/upload/{id}")
@Multipart
    Call<APIResponseModel> upload(@Path("id") int id, @Part("image\"; filename=\"image.jpg\"") RequestBody image);


@DELETE("api/user/{id}")
    Call<APIResponseModel> deleteUser(@Path("id") int id);




    @POST("api/key/user/{id}")
    Call<KeyVaultResponseModel> addKey(@Path("id") int id,@Body keyVaultRequestModel model);

@DELETE("api/key/{id}")
    Call<APIResponseModel> deleteKey(@Path("id") int id);

@GET("api/key/{id}")
    Call<KeyVaultResponseModel> viewKey(@Path("id") int id);

@PUT("api/key/{id}")
    Call<KeyVaultResponseModel> updateKey(@Path("id") int id,@Body keyVaultRequestModel model);

@GET("api/key/user/{id}")
    Call<KeyPageResponse>  getAllKeys(@Path("id") int id);


}










