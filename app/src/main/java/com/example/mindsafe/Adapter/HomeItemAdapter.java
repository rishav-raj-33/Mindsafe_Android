package com.example.mindsafe.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindsafe.Activities.MainActivity;
import com.example.mindsafe.R;
import com.example.mindsafe.RetrofitClients.SecureRetrofit;
import com.example.mindsafe.helper.GetProfile;
import com.example.mindsafe.helper.GlobalData;
import com.example.mindsafe.requestModel.keyVaultRequestModel;
import com.example.mindsafe.responseModels.APIResponseModel;
import com.example.mindsafe.responseModels.KeyPageResponse;
import com.example.mindsafe.responseModels.KeyVaultResponseModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {



  private    List<KeyVaultResponseModel> list;

  private MainActivity mainActivity;







    public HomeItemAdapter(MainActivity activity) {

list=GlobalData.getGlobalList();
this.mainActivity=activity;

    }


    @NonNull
    @Override
    public HomeItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        KeyVaultResponseModel keyVaultResponseModel = list.get(position);
        holder.Note.setText(keyVaultResponseModel.getNotes());
        holder.UserId.setText(keyVaultResponseModel.getUserName());
        holder.Password.setText(keyVaultResponseModel.getPassword());
        holder.keyId.setText(String.valueOf(keyVaultResponseModel.getId()));




        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=Integer.parseInt(holder.keyId.getText().toString());
              deleteKey(id);
              mainActivity.refresh();
            }
        });

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyVaultRequestModel model=new keyVaultRequestModel(Objects.requireNonNull(holder.UserId.getText()).toString(), Objects.requireNonNull(holder.Password.getText()).toString(), Objects.requireNonNull(holder.Note.getText()).toString());
                updateKey(Integer.parseInt(holder.keyId.getText().toString()),model);
                mainActivity.refresh();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();

    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextInputEditText UserId, Password, Note;
        TextView keyId;
        ImageView imgDelete,imgUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UserId = itemView.findViewById(R.id.UserId);
            Password = itemView.findViewById(R.id.Password);
            Note = itemView.findViewById(R.id.Note);
            keyId = itemView.findViewById(R.id.keyid);
            Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imgDelete=itemView.findViewById(R.id.imgDelete);
            imgUpdate=itemView.findViewById(R.id.imgUpdate);
        }


    }






    public void deleteKey(int id) {


        mainActivity.progressBar.setVisibility(View.VISIBLE);

        SharedPreferences sp = mainActivity.getSharedPreferences("token", MODE_PRIVATE);
        Call<APIResponseModel> call = SecureRetrofit.getInstance(sp.getString("jwt", null)).getApi().deleteKey(id);
        call.enqueue(new Callback<APIResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<APIResponseModel> call, @NonNull Response<APIResponseModel> response) {

                assert response.body() != null;
                if (response.body().success) {
                    mainActivity.progressBar.setVisibility(View.GONE);
                    Toast.makeText(mainActivity, response.body().msg, Toast.LENGTH_SHORT).show();
                } else {
                    mainActivity.progressBar.setVisibility(View.GONE);
                    Toast.makeText(mainActivity, "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResponseModel> call, @NonNull Throwable throwable) {
                mainActivity.progressBar.setVisibility(View.GONE);
                Log.e("MainActivity", "MainActivity failed", throwable);
                if (throwable instanceof IOException) {
                    // This is a network error, handle accordingly
                    Toast.makeText(mainActivity, "Network error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    // This is a non-network error, handle accordingly
                    Toast.makeText(mainActivity, "MainActivity Issue: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public void updateKey(int id,keyVaultRequestModel model) {
        mainActivity.progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sp = mainActivity.getSharedPreferences("token", MODE_PRIVATE);
        Call<KeyVaultResponseModel> call = SecureRetrofit.getInstance(sp.getString("jwt", null)).getApi().updateKey(id, model);
        call.enqueue(new Callback<KeyVaultResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<KeyVaultResponseModel> call, @NonNull Response<KeyVaultResponseModel> response) {

                assert response.body() != null;
                int statusCode = response.code();
                if (statusCode == 202) {
                    mainActivity.progressBar.setVisibility(View.GONE);
                    Toast.makeText(mainActivity, "updated....", Toast.LENGTH_SHORT).show();
                } else {
                    mainActivity.progressBar.setVisibility(View.GONE);
                    Toast.makeText(mainActivity, "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<KeyVaultResponseModel> call, @NonNull Throwable throwable) {
                mainActivity.progressBar.setVisibility(View.GONE);
                Log.e("MainActivity", "MainActivity failed", throwable);
                if (throwable instanceof IOException) {
                    // This is a network error, handle accordingly
                    Toast.makeText(mainActivity, "Network error occurred", Toast.LENGTH_SHORT).show();
                } else {
                    // This is a non-network error, handle accordingly
                    Toast.makeText(mainActivity, "MainActivity Issue: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




    }






