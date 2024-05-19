package com.example.mindsafe.Activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.example.mindsafe.Fragments.HomeFragment;
import com.example.mindsafe.Fragments.ProfileFragment;
import com.example.mindsafe.R;
import com.example.mindsafe.RetrofitClients.SecureRetrofit;
import com.example.mindsafe.helper.GetProfile;
import com.example.mindsafe.helper.GlobalData;
import com.example.mindsafe.helper.Parser;
import com.example.mindsafe.requestModel.keyVaultRequestModel;
import com.example.mindsafe.responseModels.KeyVaultResponseModel;
import com.example.mindsafe.responseModels.UserResponseModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab_btn;
    public ImageView profile_image;
    private LinearLayout ll_heading;
    private View view_line;
    private MaterialButton btn_save, btn_cancel;

    public ProgressBar progressBar;

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    RelativeLayout relativeLayout;


    private EditText editText, passwordKey, userIdkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.RProgressBar);
        Dialog dialog = new Dialog(this);
        fab_btn = findViewById(R.id.fab_btn);
        profile_image = findViewById(R.id.profile_image);
        ll_heading = findViewById(R.id.ll_heading);
        view_line = findViewById(R.id.view_line);
        relativeLayout = findViewById(R.id.main);
        userExist();
        getUser();
        getKeyList();
        profile_image.setOnClickListener(view -> {
            loadFragment(new ProfileFragment(MainActivity.this));
            fab_btn.setVisibility(GONE);
            ll_heading.setVisibility(GONE);
            view_line.setVisibility(GONE);
        });
        fab_btn.setOnClickListener(view -> {
            dialog.setContentView(R.layout.add_password_dialogbox);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.getWindow().getAttributes();
            btn_save = dialog.findViewById(R.id.btn_save);
            btn_cancel = dialog.findViewById(R.id.btn_cancel);
            btn_cancel.setOnClickListener(v -> {
                dialog.dismiss();
            });
            btn_save.setOnClickListener(v -> {

                editText = dialog.findViewById(R.id.note);
                passwordKey = dialog.findViewById(R.id.keypassword);
                userIdkey = dialog.findViewById(R.id.userName);
                savePassword();
            });

            dialog.show();


        });

    }


    public void restart() {
        onRestart();
    }


    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();


    }


    public void onBackPressed() {
        fab_btn.setVisibility(VISIBLE);
        ll_heading.setVisibility(VISIBLE);
        view_line.setVisibility(VISIBLE);
        super.onBackPressed();
    }


    private void userExist() {
        SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);

        if (!sp.contains("jwt")) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else {
            fingerPrint(true);
        }
        // If Time Limit is Up
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (LocalDateTime.now().isAfter(Parser.dateTimeParser(sp.getString("timeLimit", null)))) {
                sp.edit().remove("jwt").commit();
                sp.edit().remove("timeLimit").commit();
                sp.edit().remove("id").commit();
                sp.edit().apply();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }

    }

    public void getUser() {

        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);
        Call<UserResponseModel> call = SecureRetrofit.getInstance(sp.getString("jwt", null)).getApi().getLogdinUser();
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<UserResponseModel> call, @NonNull Response<UserResponseModel> response) {

                assert response.body() != null;
                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.GONE);
                    GetProfile.setEmail(response.body().getEmail());
                    GetProfile.setName(response.body().getName());
                    GetProfile.setId(response.body().getId());
                    GetProfile.setProfile(response.body().getProfilePhoto());
                    setProfile_image(GetProfile.getProfile());

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponseModel> call, @NonNull Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Log.e("MainActivity", "MainActivity failed", throwable);
                if (throwable instanceof IOException) {
                    // This is a network error, handle accordingly
                    Toast.makeText(MainActivity.this, "Network error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    // This is a non-network error, handle accordingly
                    Toast.makeText(MainActivity.this, "MainActivity Issue: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void setProfile_image(String img) {

        String url = "http://192.168.172.23:8080/api/auth/dp/" + img;
        Glide.with(this).load(url).into(profile_image);
        GetProfile.setProfile(img);

    }


    public void savePassword() {


        String keyUserName = Objects.requireNonNull(userIdkey.getText()).toString();

        String keyPassword = Objects.requireNonNull(passwordKey.getText()).toString();
        String keyNotes = Objects.requireNonNull(editText.getText()).toString();
        if (keyUserName.isEmpty()) {
            userIdkey.requestFocus();
            userIdkey.setError("user name is required");
            return;
        }

        if (keyPassword.isEmpty()) {
            passwordKey.requestFocus();
            passwordKey.setError("password is required");
            return;
        }
        if (keyNotes.isEmpty()) {
            editText.requestFocus();   //change
            editText.setError("Key Note is required");
            return;
        }

        Log.e("testing", "pass2");
        if (keyUserName.isEmpty() || keyPassword.isEmpty() || keyNotes.isEmpty()) {
            userIdkey.setError("Email Password and Id are required");
            userIdkey.requestFocus();

        } else {
            progressBar.setVisibility(View.VISIBLE);
            keyVaultRequestModel model = new keyVaultRequestModel(keyUserName, keyPassword, keyNotes);
            SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);

            Call<KeyVaultResponseModel> call = SecureRetrofit.getInstance(sp.getString("jwt", null)).getApi().addKey(sp.getInt("userId", 0), model);
            call.enqueue(new Callback<KeyVaultResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<KeyVaultResponseModel> call, @NonNull Response<KeyVaultResponseModel> response) {

                    assert response.body() != null;
                    int statusCode = response.code();
                    if (statusCode == 201) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Key Added.....", Toast.LENGTH_SHORT).show();
                       refresh();

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(@NonNull Call<KeyVaultResponseModel> call, @NonNull Throwable throwable) {
                    progressBar.setVisibility(View.GONE);
                    Log.e("MainActivity", "MainActivity failed", throwable);
                    if (throwable instanceof IOException) {
                        // This is a network error, handle accordingly
                        Toast.makeText(MainActivity.this, "Network error occurred", Toast.LENGTH_SHORT).show();
                    } else {
                        // This is a non-network error, handle accordingly
                        Toast.makeText(MainActivity.this, "MainActivity Issue: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }


    public void getKeyList() {


        SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);
        Call<List<KeyVaultResponseModel>> call = SecureRetrofit.getInstance(sp.getString("jwt", null)).getApi().getAllKeys(sp.getInt("userId", 0));


        call.enqueue(new Callback<List<KeyVaultResponseModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<KeyVaultResponseModel>> call, @NonNull Response<List<KeyVaultResponseModel>> response) {
                assert response.body() != null;
                int statusCode = response.code();
                loadFragment(new HomeFragment(MainActivity.this));
                if (statusCode == 200) {
                    List<KeyVaultResponseModel> list = response.body();
                    //  progressBar.setVisibility(View.GONE);

                    GlobalData.setGlobalList(list);
                    Toast.makeText(MainActivity.this, "Fetching.....", Toast.LENGTH_SHORT).show();


                } else {
                    //   progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Fetching Failed....", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<KeyVaultResponseModel>> call, @NonNull Throwable throwable) {
                //  progressBar.setVisibility(View.GONE);
                Log.e("MainActivity", "MainActivity failed", throwable);
                if (throwable instanceof IOException) {
                    // This is a network error, handle accordingly
                    Toast.makeText(MainActivity.this, "Network error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    // This is a non-network error, handle accordingly
                    Toast.makeText(MainActivity.this, "MainActivity Issue: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void fingerPrint(boolean flag) {




        if (flag) {
         //   relativeLayout.setVisibility(View.INVISIBLE);

            BiometricManager biometricManager = BiometricManager.from(this);
            switch (biometricManager.canAuthenticate()) {

                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    Toast.makeText(MainActivity.this, "Biometric Not Available, Device is Unsecure", Toast.LENGTH_SHORT).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    Toast.makeText(MainActivity.this, "Biometric  not Working, Device is Unsecure", Toast.LENGTH_SHORT).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    Toast.makeText(MainActivity.this, "Biometric is not Set, Device is Unsecure", Toast.LENGTH_SHORT).show();
                    break;

            }

            Executor executor = ContextCompat.getMainExecutor(this);

            biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);

                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    relativeLayout.setVisibility(VISIBLE);
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }
            });
               promptInfo= new BiometricPrompt.PromptInfo.Builder().setTitle("Mind Safe").setDescription("Use fingerprint to verify")
                       .setDeviceCredentialAllowed(true).build();

               biometricPrompt.authenticate(promptInfo);


        }


    }

    public void refresh(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}





