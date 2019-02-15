package com.example.egor.smokenet.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.egor.smokenet.Adapters.DialogListAdapter;
import com.example.egor.smokenet.Database.SQLiteHandler;
import com.example.egor.smokenet.Fragments.DialogFragment;
import com.example.egor.smokenet.Fragments.DialogsFragment;
import com.example.egor.smokenet.POJO.Client;
import com.example.egor.smokenet.Models.SessionManager;
import com.example.egor.smokenet.R;

import java.util.HashMap;

public class MenuActivity extends AppCompatActivity implements DialogListAdapter.DialogHolderListener {
    Button buttonLogout;
    TextView textViewUserDetails;
    SQLiteHandler sqliteHandler;
    SessionManager sessionManager;
    Intent intent;
    Fragment dialogFragment;
    FragmentManager fragmentManager;
    public final static String TAG = MenuActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sessionManager = new SessionManager(getApplicationContext());
        if(dialogFragment==null) {
            fragmentManager = getSupportFragmentManager();
            dialogFragment = new DialogsFragment();
            fragmentManager.beginTransaction().add(R.id.frame, dialogFragment).commit();
        }
        /*buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setLogin(false);
                intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
                sqliteHandler.deleteUsers();
            }
        });*/


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.app_logout:
            {
                menuClickLogout();
                return true;
            }
            case R.id.about_dev:
            {
                menuClickAboutDev();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void menuClickLogout()
    {
        sqliteHandler = new SQLiteHandler(this);
        sqliteHandler.deleteUsers();
        sessionManager.setLogin(false);
        intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);


    }
    public void menuClickAboutDev()
    {
        Dialog dialogAboutDev = new Dialog(MenuActivity.this);
        dialogAboutDev.setTitle("О разработчках");
        dialogAboutDev.setContentView(R.layout.dialog_layout_about_dev);
        TextView text = dialogAboutDev.findViewById(R.id.about_dev_text);
        text.setText("Разработчики: Егор Луговкин," + "\n" + "Последня версия: 1.0.1dev");
        dialogAboutDev.show();

    }

    @Override
    public void onBackPressed() {
        
    }


    @Override
    public void getClient(Client client) {
        Log.d(TAG, client.toString());
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().detach(dialogFragment).commit();
        dialogFragment = DialogFragment.newInstance(client.getLogin(), client.getEmail());
        fragmentManager.beginTransaction().add(R.id.frame,dialogFragment).addToBackStack(null).commit();
    }

}

