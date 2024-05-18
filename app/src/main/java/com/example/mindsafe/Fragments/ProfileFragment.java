package com.example.mindsafe.Fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mindsafe.Activities.LoginActivity;
import com.example.mindsafe.Activities.MainActivity;
import com.example.mindsafe.R;
import com.example.mindsafe.RetrofitClients.SecureRetrofit;
import com.example.mindsafe.helper.GetProfile;
import com.example.mindsafe.helper.ImageRequestBody;
import com.example.mindsafe.helper.RealPathUtil;
import com.example.mindsafe.responseModels.APIResponseModel;
import com.example.mindsafe.responseModels.UserResponseModel;
import com.google.android.material.internal.ToolbarUtils;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    private CircleImageView UProfile;

    private ProgressBar progressBar;

    private TextView Uname,Uemail;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    androidx.appcompat.widget.Toolbar toolbar;


MainActivity mainActivity;


    public ProfileFragment(MainActivity activity) {
        // Required empty public constructor
        this.mainActivity = activity;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_profile, container, false);
toolbar=view.findViewById(R.id.toolbar);
toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.logout){
            logout();
        }
        else if (id==R.id.disable){
            disableUser();

        }
        return true;
    }
});





        UProfile=view.findViewById(R.id.UProfile);
        progressBar=view.findViewById(R.id.RProgressBar);
      Uname=view.findViewById(R.id.UName);
      Uemail=view.findViewById(R.id.UEmail);
        String url="http://192.168.172.23:8080/api/auth/dp/"+ GetProfile.getProfile();
        Glide.with(this).load(url).into(UProfile);
        Uname.setText(GetProfile.getName());
        Uemail.setText(GetProfile.getEmail());
        UProfile.setOnClickListener(View->{
            open_galary();
        });


            return view;
    }




    public void open_galary(){
        Intent intentGalary=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentGalary,REQUEST_IMAGE_CAPTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Log.e("error","failed or reached");
            // Get the URI of the selected image
            Uri selectedImageUri = data.getData();
            String realPath = RealPathUtil.getRealPath(requireContext(), selectedImageUri);

            assert realPath != null;
            File image=new File(realPath);
                   upload(image);

        }
    }




    public void upload(File image){
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sp= requireContext().getSharedPreferences("token",Context.MODE_PRIVATE);
        RequestBody imageRequestBody=ImageRequestBody.createImageRequestBody(image);
        Call<APIResponseModel> call = SecureRetrofit.getInstance(sp.getString("jwt",null)).getApi().upload(sp.getInt("userId",0),imageRequestBody);
        Log.e("reach","message");
        call.enqueue(new Callback<APIResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<APIResponseModel> call, @NonNull Response<APIResponseModel> response) {

                assert response.body() != null;
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    GetProfile.setProfile(response.body().msg);

                    Toast.makeText(requireContext(),"File Uploaded.....", Toast.LENGTH_SHORT).show();
                    String url="http://192.168.172.23:8080/api/auth/dp/"+ GetProfile.getProfile();
                    Glide.with(requireContext()).load(url).into(UProfile);
                    Glide.with(requireContext()).load(url).into(mainActivity.profile_image);
                    Uname.setText(GetProfile.getName());

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResponseModel> call, @NonNull Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Log.e("Profile Fragment", "Profile Fragment failed", throwable);
                if (throwable instanceof IOException) {
                    // This is a network error, handle accordingly
                    Toast.makeText(requireContext(), "Network error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    // This is a non-network error, handle accordingly
                    Toast.makeText(requireContext(), "Profile Fragment Issue: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }}
        });
    }


    public void logout(){
        SharedPreferences sp= requireContext().getSharedPreferences("token",Context.MODE_PRIVATE);
        sp.edit().remove("jwt").commit();
        sp.edit().remove("timeLimit").commit();
        sp.edit().remove("id").commit();
        sp.edit().apply();
        startActivity(new Intent(requireContext(), LoginActivity.class));
    }

    public void disableUser(){

        SharedPreferences sp= requireContext().getSharedPreferences("token",Context.MODE_PRIVATE);
        Call<APIResponseModel> call = SecureRetrofit.getInstance(sp.getString("jwt",null)).getApi().deleteUser(sp.getInt("userId",0));
        call.enqueue(new Callback<APIResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<APIResponseModel> call, @NonNull Response<APIResponseModel> response) {

                assert response.body() != null;
                if(response.body().success){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), response.body().msg, Toast.LENGTH_SHORT).show();
                    logout();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResponseModel> call, @NonNull Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Log.e("Profile Fragment", "Profile Fragment failed", throwable);
                if (throwable instanceof IOException) {
                    // This is a network error, handle accordingly
                    Toast.makeText(requireContext(), "Network error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    // This is a non-network error, handle accordingly
                    Toast.makeText(requireContext(), "Profile Fragment Issue: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }}
        });
    }

}


