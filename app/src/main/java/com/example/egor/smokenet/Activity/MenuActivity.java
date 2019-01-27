package com.example.egor.smokenet.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.egor.smokenet.Database.SQLiteHandler;
import com.example.egor.smokenet.Fragments.DialogFragment;
import com.example.egor.smokenet.Fragments.DialogsFragment;
import com.example.egor.smokenet.Models.SessionManager;
import com.example.egor.smokenet.R;

import java.util.HashMap;

public class MenuActivity extends AppCompatActivity implements DialogFragment.OnFragmentInteractionListener, DialogsFragment.OnFragmentInteractionListener {
    Button buttonLogout;
    TextView textViewUserDetails;
    SQLiteHandler sqliteHandler;
    SessionManager sessionManager;
    Intent intent;
    public final static String TAG = MenuActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        buttonLogout = findViewById(R.id.buttonLogout);
        textViewUserDetails = findViewById(R.id.textViewUserDetails);
        sessionManager = new SessionManager(getApplicationContext());
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment dialogFragment = new DialogsFragment();
        fragmentManager.beginTransaction().add(R.id.frame, dialogFragment).commit();
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setLogin(false);
                intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
                sqliteHandler.deleteUsers();
            }
        });
        fillUserDetails();

    }
    public void fillUserDetails()
    {
        sqliteHandler = new SQLiteHandler(this);
        HashMap<String,String> userDetails = sqliteHandler.getUserDetails();
        String data = "Логин - " + userDetails.get("login") + "\nПочта - " + userDetails.get("email") + "\n" + " Уникальный идентификатор " + userDetails.get("uid");

        textViewUserDetails.setTextSize(24);
        textViewUserDetails.setText(data);

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
