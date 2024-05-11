package com.example.mindsafe.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.mindsafe.R;
import com.example.mindsafe.RetrofitClients.RegisterRetrofit;
import com.example.mindsafe.requestModel.LoginRequestModel;
import com.example.mindsafe.responseModels.LoginResponseModel;
import com.example.mindsafe.responseModels.RegisterResponseModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView txt_create_account, ForgotPassword;
    TextInputEditText LEmail, LPassword;


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
        btnLogin.setOnClickListener(v -> {
            login();

        });
        txt_create_account.setOnClickListener(v -> {
            Intent iRegister = new Intent(this, RegisterActivity.class);
            startActivity(iRegister);
        });


    }

    public void login() {
        String lemail = Objects.requireNonNull(LEmail.getText()).toString();
        String lpassword = Objects.requireNonNull(LPassword.getText()).toString();
        if (lemail.isEmpty() || lpassword.isEmpty()) {
            LEmail.setError("Email and Password is required");
            LEmail.requestFocus();
            return;
        }
        else {
            LoginRequestModel model=new LoginRequestModel(lemail,lpassword);
            Call<LoginResponseModel> call = RegisterRetrofit.getInstance().getApi().Login(model);
            call.enqueue(new Callback<LoginResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponseModel> call, @NonNull Response<LoginResponseModel> response) {


                    assert response.body() != null;
                    //TODO: Add JWT Token in Shared Preference
                    if(0==0){
                        Toast.makeText(LoginActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponseModel> call, @NonNull Throwable throwable) {
                    Log.e("RegisterActivity", "Login failed", throwable);
                    if (throwable instanceof IOException) {
                        // This is a network error, handle accordingly
                        Toast.makeText(LoginActivity.this, "Network error occurred", Toast.LENGTH_SHORT).show();
                    } else {
                        // This is a non-network error, handle accordingly
                        Toast.makeText(LoginActivity.this, "Login failed: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }}
            });


            Intent iHome = new Intent(this, MainActivity.class);
            startActivity(iHome);
        }

    }
}
