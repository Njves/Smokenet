package com.example.egor.smokenet.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.egor.smokenet.Database.SQLiteHandler;
import com.example.egor.smokenet.R;

import java.util.HashMap;

public class MenuActivity extends AppCompatActivity {
    Button buttonLogout;
    TextView textViewUserDetails;
    SQLiteHandler sqliteHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        buttonLogout = findViewById(R.id.buttonLogout);
        textViewUserDetails = findViewById(R.id.textViewUserDetails);
        fillUserDetails();

    }
    public void fillUserDetails()
    {
        sqliteHandler = new SQLiteHandler(this);
        HashMap<String,String> userDetails = sqliteHandler.getUserDetails();
        String data = "Логин - " + userDetails.get("login") + "\nПочта - " + userDetails.get("email") + "\n";
        textViewUserDetails.setTextSize(24);
        textViewUserDetails.setText(data);
    }
}
