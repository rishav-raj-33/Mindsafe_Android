package com.example.mindsafe.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.mindsafe.R;
import com.example.mindsafe.helper.GetProfile;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    private CircleImageView UProfile;

    private TextView Uname,Uemail;
    Toolbar toolbar;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        toolbar=view.findViewById(R.id.toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        UProfile=view.findViewById(R.id.UProfile);
      Uname=view.findViewById(R.id.UName);
      Uemail=view.findViewById(R.id.UEmail);
        String url="http://10.0.2.2:8080/api/auth/dp/"+ GetProfile.getProfile();
        Glide.with(this).load(url).into(UProfile);
        Uname.setText(GetProfile.getName());
        Uemail.setText(GetProfile.getEmail());
     return view;
    }


}
