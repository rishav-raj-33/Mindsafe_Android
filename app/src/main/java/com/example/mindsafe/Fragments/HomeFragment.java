package com.example.mindsafe.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mindsafe.Activities.MainActivity;
import com.example.mindsafe.Adapter.HomeItemAdapter;
import com.example.mindsafe.R;
import com.example.mindsafe.RetrofitClients.SecureRetrofit;
import com.example.mindsafe.helper.GlobalData;
import com.example.mindsafe.responseModels.KeyVaultResponseModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {



  MainActivity activity;


    public HomeFragment(MainActivity activity) {
        this.activity=activity;

    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
         RecyclerView recyclerView;
        recyclerView=view.findViewById(R.id.recycle);
        HomeItemAdapter homeItemAdapter=new HomeItemAdapter(activity);
        recyclerView.setAdapter(homeItemAdapter);
        return view;
    }









}
