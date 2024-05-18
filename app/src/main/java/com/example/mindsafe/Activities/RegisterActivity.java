package com.example.mindsafe.Activities;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.mindsafe.responseModels.APIResponseModel;
import com.example.mindsafe.R;
import com.example.mindsafe.RetrofitClients.PublicRetrofit;
import com.example.mindsafe.requestModel.RegisterRequestModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText RPassword;
    private TextInputEditText REmail;
   TextInputEditText RName;
   ProgressBar progressBar;
   TextView goBack;






    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        RPassword=findViewById(R.id.RPassword);
        REmail=findViewById(R.id.REmail);
        RName = findViewById(R.id.RName);
        goBack=findViewById(R.id.Rgoback);
        progressBar=findViewById(R.id.RProgressBar);

        Button btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(v -> {
            RegisterUser();
        });

        goBack.setOnClickListener(v -> {
            Intent iRegister = new Intent(this, LoginActivity.class);
            startActivity(iRegister);
        });


    }
    public void RegisterUser(){
        progressBar.setVisibility(View.VISIBLE);
        String UserPassword= Objects.requireNonNull(RPassword.getText()).toString();
        String UserEmail= Objects.requireNonNull(REmail.getText()).toString();
        String UserName= Objects.requireNonNull(RName.getText()).toString();



        if(UserEmail.isEmpty()){
            REmail.requestFocus();
            REmail.setError("Please Enter Your Email");
            return;
        }
        if(UserPassword.isEmpty()){
            RPassword.requestFocus();
            RPassword.setError("Please Enter Your Password");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()){
            REmail.requestFocus();
            REmail.setError("Please Enter Your Valid Email");
            return;
            
        }
        else {
            RegisterRequestModel model=new RegisterRequestModel(UserEmail,UserPassword,UserName);

            Call<APIResponseModel> call = PublicRetrofit.getInstance().getApi().Register(model);
            call.enqueue(new Callback<APIResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<APIResponseModel> call, @NonNull Response<APIResponseModel> response) {

                    assert response.body() != null;
                    if(response.body().success){
                           progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, response.body().msg, Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),OtpActivity.class);
                        i.putExtra("email",UserEmail);
                        startActivity(i);
                        finish();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Fragment fragment = fragmentManager.findFragmentById(R.id.container);
                        assert fragment != null;
                        fragmentManager.beginTransaction()
                                .detach(fragment)
                                .attach(fragment)
                                .commit();
                        } else {
                        progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();

                        }
                }

                @Override
                public void onFailure(@NonNull Call<APIResponseModel> call, @NonNull Throwable throwable) {
                    progressBar.setVisibility(View.GONE);
                    Log.e("RegisterActivity", "Registration failed", throwable);
                    if (throwable instanceof IOException) {
                        // This is a network error, handle accordingly
                        Toast.makeText(RegisterActivity.this, "Network error occurred", Toast.LENGTH_SHORT).show();
                    } else {
                        // This is a non-network error, handle accordingly
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }}
            });
        }
    }
}
