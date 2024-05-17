package com.example.mindsafe.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.mindsafe.R;
import com.example.mindsafe.RetrofitClients.PublicRetrofit;
import com.example.mindsafe.requestModel.LoginRequestModel;
import com.example.mindsafe.responseModels.LoginResponseModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView txt_create_account, ForgotPassword;
    TextInputEditText LEmail, LPassword;

    ProgressBar progressBar;
    Intent iHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        AppCompatButton btnLogin = findViewById(R.id.btnLogin);
        LEmail = findViewById(R.id.LEmail);
        LPassword = findViewById(R.id.LPassword);
        ForgotPassword = findViewById(R.id.ForgotPassword);
        txt_create_account = findViewById(R.id.txt_create_account);
        progressBar=findViewById(R.id.RProgressBar);
        userExist();
         iHome = new Intent(this, MainActivity.class);
        btnLogin.setOnClickListener(v -> {
            login();

        });
        txt_create_account.setOnClickListener(v -> {
            Intent iRegister = new Intent(this, RegisterActivity.class);
            startActivity(iRegister);
        });

        ForgotPassword.setOnClickListener(v-> {
            forgetPassword();
            Toast.makeText(LoginActivity.this, "Forget Password coming soon.......", Toast.LENGTH_SHORT).show();
        });


    }



    public void login() {
        progressBar.setVisibility(View.VISIBLE);
        String lemail = Objects.requireNonNull(LEmail.getText()).toString();
        String lpassword = Objects.requireNonNull(LPassword.getText()).toString();
        if (lemail.isEmpty() || lpassword.isEmpty()) {
            LEmail.setError("Email and Password is required");
            LEmail.requestFocus();

        } else {
            LoginRequestModel model = new LoginRequestModel(lemail, lpassword);
            Call<LoginResponseModel> call = PublicRetrofit.getInstance().getApi().Login(model);
            call.enqueue(new Callback<LoginResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponseModel> call, @NonNull Response<LoginResponseModel> response) {
                    assert response.body() != null;
                    if (response.body().success) {
                        progressBar.setVisibility(View.GONE);
                        SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("jwt", response.body().jwtToken);
                        editor.putString("timeLimit",response.body().expireDateTime.toString());
                        editor.commit();
                        editor.apply();
                        startActivity(iHome);
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponseModel> call, @NonNull Throwable throwable) {
                    progressBar.setVisibility(View.GONE);
                    Log.e("RegisterActivity", "Login failed", throwable);
                    if (throwable instanceof IOException) {
                        // This is a network error, handle accordingly
                        Toast.makeText(LoginActivity.this, "Network error occurred", Toast.LENGTH_SHORT).show();
                    } else {
                        // This is a non-network error, handle accordingly
                        Toast.makeText(LoginActivity.this, "Login failed: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }

    }
    private void userExist() {
        SharedPreferences sp=getSharedPreferences("token",MODE_PRIVATE);
        if(sp.contains("jwt")){
            iHome=new Intent(this,MainActivity.class);
            startActivity(iHome);
            finish();
        }
    }



    //TODO: Backend Feature need to be added
    public void forgetPassword(){

    }




}
