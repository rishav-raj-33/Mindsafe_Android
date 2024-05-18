package com.example.mindsafe.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {







    public HomeItemAdapter() {




    }


    @NonNull
    @Override
    public HomeItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemAdapter.ViewHolder holder, int position) {
        KeyVaultResponseModel keyVaultResponseModel = GlobalData.getGlobalList().get(position);
        holder.Note.setText("Notes: "+keyVaultResponseModel.getNotes());
        holder.UserId.setText("User Name: "+keyVaultResponseModel.getUserName());
        holder.Password.setText("Password: "+keyVaultResponseModel.getPassword());
        holder.keyId.setText(String.valueOf(keyVaultResponseModel.getId()));

    }

    @Override
    public int getItemCount() {
        return GlobalData.getGlobalList().size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView UserId, Password, Note, keyId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UserId = itemView.findViewById(R.id.UserId);
            Password = itemView.findViewById(R.id.Password);
            Note = itemView.findViewById(R.id.Note);
            keyId = itemView.findViewById(R.id.keyid);
        }
    }


    // Delete Password Feature (Logic Completed Integration pending....)
    //TODO: Refresh Fragment after successful API hit
//    public void deleteKey() {
//
//        // String getId= Objects.requireNonNull(?.getText()).toString();
//
//        //  progressBar.setVisibility(View.VISIBLE);
//
//        SharedPreferences sp = activity.getSharedPreferences("token", MODE_PRIVATE);
//        Call<APIResponseModel> call = SecureRetrofit.getInstance(sp.getString("jwt", null)).getApi().deleteKey(1); //change (getId)
//        call.enqueue(new Callback<APIResponseModel>() {
//            @Override
//            public void onResponse(@NonNull Call<APIResponseModel> call, @NonNull Response<APIResponseModel> response) {
//
//                assert response.body() != null;
//                if (response.body().success) {
//                    //  progressBar.setVisibility(View.GONE);
//                    Toast.makeText(activity, response.body().msg, Toast.LENGTH_SHORT).show();
//                } else {
//                    //   progressBar.setVisibility(View.GONE);
//                    Toast.makeText(activity, "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<APIResponseModel> call, @NonNull Throwable throwable) {
//                //  progressBar.setVisibility(View.GONE);
//                Log.e("MainActivity", "MainActivity failed", throwable);
//                if (throwable instanceof IOException) {
//                    // This is a network error, handle accordingly
//                    Toast.makeText(activity, "Network error occurred", Toast.LENGTH_SHORT).show();
//                } else {
//                    // This is a non-network error, handle accordingly
//                    Toast.makeText(activity, "MainActivity Issue: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//    // Logic completed Integration pendng.....
//    //TODO: Refresh Fragment after successful API hit
//
//    public void updateKey() {
//        //   progressBar.setVisibility(View.VISIBLE);
//        // String getId= Objects.requireNonNull(?.getText()).toString();
//        // String keyUserName = Objects.requireNonNull(?.getText()).toString();
//        //  String keyPassword = Objects.requireNonNull(?.getText()).toString();
//        // String keyNotes = Objects.requireNonNull(?.getText()).toString();
//
//        //  keyVaultRequestModel model=new keyVaultRequestModel(keyUserName,keyPassword,keyNotes);
//
//        SharedPreferences sp = activity.getSharedPreferences("token", MODE_PRIVATE);
//        Call<KeyVaultResponseModel> call = SecureRetrofit.getInstance(sp.getString("jwt", null)).getApi().updateKey(1, new keyVaultRequestModel());  //change id and model
//        call.enqueue(new Callback<KeyVaultResponseModel>() {
//            @Override
//            public void onResponse(@NonNull Call<KeyVaultResponseModel> call, @NonNull Response<KeyVaultResponseModel> response) {
//
//                assert response.body() != null;
//                int statusCode = response.code();
//                if (statusCode == 202) {
//                    //   progressBar.setVisibility(View.GONE);
//                    Toast.makeText(activity, "updated....", Toast.LENGTH_SHORT).show();
//                } else {
//                    //  progressBar.setVisibility(View.GONE);
//                    Toast.makeText(activity, "Unexpected Problem Occurred", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<KeyVaultResponseModel> call, @NonNull Throwable throwable) {
//                // progressBar.setVisibility(View.GONE);
//                Log.e("MainActivity", "MainActivity failed", throwable);
//                if (throwable instanceof IOException) {
//                    // This is a network error, handle accordingly
//                    Toast.makeText(activity, "Network error occurred", Toast.LENGTH_SHORT).show();
//                } else {
//                    // This is a non-network error, handle accordingly
//                    Toast.makeText(activity, "MainActivity Issue: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//    }


}
