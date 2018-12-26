package com.example.egor.smokenet.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.egor.smokenet.Fragments.RegisterFragment;
import com.example.egor.smokenet.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = new RegisterFragment();
        fragmentManager.beginTransaction().add(R.id.login_frame, currentFragment).commit();
    }
}
