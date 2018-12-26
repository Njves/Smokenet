package com.example.egor.smokenet.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.egor.smokenet.R;

public abstract class FragmentLauncher extends AppCompatActivity {
    public abstract Fragment createFragment();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = createFragment();
        fragmentManager.beginTransaction().add(R.id.login_frame, currentFragment).commit();


    }
}
