package com.example.mindsafe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mindsafe.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent iLogin=new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(iLogin);
           finish();
        }
    },2000);
    }
}
