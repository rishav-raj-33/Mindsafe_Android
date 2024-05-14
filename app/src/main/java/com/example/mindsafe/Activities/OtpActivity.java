package com.example.mindsafe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.mindsafe.R;
import com.example.mindsafe.RetrofitClients.PublicRetrofit;
import com.example.mindsafe.responseModels.APIResponseModel;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {


    EditText field1,field2,field3,field4;
     Button verify;
     TextView emailView,resendToken;
     ProgressBar progressBar;

    Intent iLogin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ot_pactivity);

         iLogin = new Intent(this, LoginActivity.class);
        field1=findViewById(R.id.otp_edit_box1);
        field2=findViewById(R.id.otp_edit_box2);
        field3=findViewById(R.id.otp_edit_box3);
        field4=findViewById(R.id.otp_edit_box4);
        emailView=findViewById(R.id.email);
        progressBar=findViewById(R.id.RProgressBar);
        resendToken=findViewById(R.id.resend);

        emailView.setText(getIntent().getStringExtra("email"));
        verify=findViewById(R.id.verify_otp_btn);
        verify.setOnClickListener(v -> {
            processOTP();
        });
        resendToken.setOnClickListener(v -> {
            reSendTokenEmail();
        });
        cursorMoveOtp();


    }


    public void processOTP(){
        progressBar.setVisibility(View.VISIBLE);
        String first= Objects.requireNonNull(field1.getText()).toString();
        String second= Objects.requireNonNull(field2.getText()).toString();
        String third= Objects.requireNonNull(field3.getText()).toString();
        String four= Objects.requireNonNull(field4.getText()).toString();

        if(first.isEmpty()){
            field1.requestFocus();
            field1.setError("Require Number");
            return;

        }
        if(second.isEmpty()){
            field2.requestFocus();
            field2.setError("Require Number");
            return;

        }
        if(third.isEmpty()){
            field3.requestFocus();
            field3.setError("Require Number");
            return;
        }
        if (four.isEmpty()){
            field4.requestFocus();
            field4.setError("Require Number");
            return;

        }

        String otp=first+second+third+four;

        Call<APIResponseModel> call = PublicRetrofit.getInstance().getApi().confirmToken(otp);
        call.enqueue(new Callback<APIResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<APIResponseModel> call, @NonNull Response<APIResponseModel> response) {

                assert response.body() != null;
                if(response.body().success){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(OtpActivity.this, response.body().msg, Toast.LENGTH_LONG).show();
                    startActivity(iLogin);

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(OtpActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResponseModel> call, @NonNull Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Log.e("OTP Activity", "failed", throwable);
                if (throwable instanceof IOException) {
                    // This is a network error, handle accordingly
                    Toast.makeText(OtpActivity.this, "Network error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    // This is a non-network error, handle accordingly
                    Toast.makeText(OtpActivity.this, "failed: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }}
        });





    }

    public void reSendTokenEmail(){
        progressBar.setVisibility(View.VISIBLE);
        Call<APIResponseModel> call = PublicRetrofit.getInstance().getApi().reSend(Objects.requireNonNull(emailView.getText()).toString());
        call.enqueue(new Callback<APIResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<APIResponseModel> call, @NonNull Response<APIResponseModel> response) {

                assert response.body() != null;
                if(response.body().success){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(OtpActivity.this, response.body().msg, Toast.LENGTH_LONG).show();

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(OtpActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResponseModel> call, @NonNull Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Log.e("OTP Activity", "send failed", throwable);
                if (throwable instanceof IOException) {
                    // This is a network error, handle accordingly
                    Toast.makeText(OtpActivity.this, "Network error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    // This is a non-network error, handle accordingly
                    Toast.makeText(OtpActivity.this, "failed: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }}
        });


    }


    public void cursorMoveOtp(){
        progressBar.setVisibility(View.GONE);
        field1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    field2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        field2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    field3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        field3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    field4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



}