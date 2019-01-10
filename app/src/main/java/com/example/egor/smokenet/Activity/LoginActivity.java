package com.example.egor.smokenet.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.egor.smokenet.Database.SQLiteHandler;
import com.example.egor.smokenet.Fragments.RegisterFragment;
import com.example.egor.smokenet.Models.SessionManager;
import com.example.egor.smokenet.R;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private SessionManager session;
    private SQLiteHandler mSQLiteDatabase;
    private Intent intent;
    public static final String TAG = LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn())
        {
            intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        else
        {
            mSQLiteDatabase = new SQLiteHandler(getApplicationContext());
            HashMap<String, String> map = mSQLiteDatabase.getUserDetails();
            String login = map.get("login");
            String email = map.get("email");
            String password = map.get("password");
            Log.d(TAG, login + " " + email + " " + password);
            Fragment fragment = new RegisterFragment();
            FragmentManager fr = getSupportFragmentManager();
            FragmentTransaction ft = fr.beginTransaction().add(R.id.login_frame, fragment);
            ft.commit();
        }

    }
}
