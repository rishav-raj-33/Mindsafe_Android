package com.example.mindsafe.Activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mindsafe.Fragments.HomeFragment;
import com.example.mindsafe.Fragments.ProfileFragment;
import com.example.mindsafe.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab_btn;
    private ImageView profile_image;
    private LinearLayout ll_heading;
    private View view_line;
    private MaterialButton btn_save,btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
         loadFragment(new HomeFragment());
         Dialog dialog=new Dialog(this);
        fab_btn=findViewById(R.id.fab_btn);
        profile_image=findViewById(R.id.profile_image);
        ll_heading=findViewById(R.id.ll_heading);
        view_line=findViewById(R.id.view_line);


        profile_image.setOnClickListener(view->{
            loadFragment(new ProfileFragment());
            fab_btn.setVisibility(GONE);
            ll_heading.setVisibility(GONE);
            view_line.setVisibility(GONE);
        });
        fab_btn.setOnClickListener(view->{
          dialog.setContentView(R.layout.add_password_dialogbox);
          dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.getWindow().getAttributes();
            btn_save=dialog.findViewById(R.id.btn_save);
            btn_cancel=dialog.findViewById(R.id.btn_cancel);
            btn_cancel.setOnClickListener(v->{
               dialog.dismiss();
            });
  dialog.show();
  

        });

    }
    public void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container,fragment)
                .addToBackStack(null)
                .commit();

    }
    public void loadedFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container,fragment)
                .commit();

    }
    public void onBackPressed(){
        fab_btn.setVisibility(VISIBLE);
        ll_heading.setVisibility(VISIBLE);
        view_line.setVisibility(VISIBLE);
        super.onBackPressed();
    }
}
