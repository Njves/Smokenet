package com.example.egor.smokenet.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.egor.smokenet.Database.SQLiteHandler;
import com.example.egor.smokenet.Models.SessionManager;
import com.example.egor.smokenet.R;

import java.util.HashMap;

public class MenuActivity extends AppCompatActivity {
    Button buttonLogout;
    TextView textViewUserDetails;
    SQLiteHandler sqliteHandler;
    SessionManager sessionManager;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        buttonLogout = findViewById(R.id.buttonLogout);
        textViewUserDetails = findViewById(R.id.textViewUserDetails);
        fillUserDetails();
        sessionManager = new SessionManager(getApplicationContext());
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setLogin(false);
                intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    public void fillUserDetails()
    {
        sqliteHandler = new SQLiteHandler(this);
        HashMap<String,String> userDetails = sqliteHandler.getUserDetails();
        String data = "Логин - " + userDetails.get("login") + "\nПочта - " + userDetails.get("email") + "\n";
        textViewUserDetails.setTextSize(24);
        textViewUserDetails.setText(data);
    }

    @Override
    public void onBackPressed() {

    }
}
