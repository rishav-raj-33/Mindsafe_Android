package com.example.mindsafe.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mindsafe.responseModels.RegisterResponseModel;
import com.example.mindsafe.R;
import com.example.mindsafe.RetrofitClients.RegisterRetrofit;
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






    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        RPassword=findViewById(R.id.RPassword);
        REmail=findViewById(R.id.REmail);
        RName = findViewById(R.id.RName);

        Button btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(v -> {
            RegisterUser();
        });

    }
    public void RegisterUser(){
        String UserPassword= Objects.requireNonNull(RPassword.getText()).toString();
        String UserEmail= Objects.requireNonNull(REmail.getText()).toString();
        String UserName= Objects.requireNonNull(RName.getText()).toString();

        RegisterRequestModel model=new RegisterRequestModel(UserEmail,UserPassword,UserName);

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

            Call<RegisterResponseModel> call = RegisterRetrofit.getInstance().getApi().Register(model);
            call.enqueue(new Callback<RegisterResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<RegisterResponseModel> call, @NonNull Response<RegisterResponseModel> response) {

                    assert response.body() != null;
                    if(response.body().success){
                            Toast.makeText(RegisterActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();

                        }
                }

                @Override
                public void onFailure(@NonNull Call<RegisterResponseModel> call, @NonNull Throwable throwable) {
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
