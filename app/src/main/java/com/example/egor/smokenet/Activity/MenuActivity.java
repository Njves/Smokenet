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
import android.widget.TextView;
import com.example.egor.smokenet.Adapters.DialogListAdapter;
import com.example.egor.smokenet.BuildConfig;
import com.example.egor.smokenet.Database.SQLiteHandler;
import com.example.egor.smokenet.Fragments.DialogFragment;
import com.example.egor.smokenet.Fragments.DialogsFragment;
import com.example.egor.smokenet.Models.SessionManager;
import com.example.egor.smokenet.Models.TCPConnection;
import com.example.egor.smokenet.POJO.Client;
import com.example.egor.smokenet.R;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity implements DialogListAdapter.DialogHolderListener {

    TextView textViewUserDetails;
    SQLiteHandler sqliteHandler;
    SessionManager sessionManager;
    Intent intent;
    Fragment dialogFragment;
    Fragment dialogsFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    Dialog dialogAboutDev;

    public final static String TAG = MenuActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sessionManager = new SessionManager(getApplicationContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                TCPConnection tcp = null;
                try {
                    tcp = new TCPConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tcp.startTalk();
            }
        }).start();
        if (dialogFragment == null) {

            dialogsFragment = new DialogsFragment();

            fragmentManager.beginTransaction().replace(R.id.frame, dialogsFragment, "dialog").commit();

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_menu, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_logout: {
                menuClickLogout();
                return true;
            }
            case R.id.about_dev: {
                menuClickAboutDev();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void menuClickLogout() {
        sqliteHandler = new SQLiteHandler(this);
        sqliteHandler.deleteUsers();
        sessionManager.setLogin(false);
        intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);


    }

    public void menuClickAboutDev() {
        dialogAboutDev = new Dialog(MenuActivity.this);
        dialogAboutDev.setTitle("О разработчках");
        dialogAboutDev.setContentView(R.layout.dialog_layout_about_dev);
        TextView text = dialogAboutDev.findViewById(R.id.about_dev_text);
        text.setText("Разработчики: Егор Луговкин," + "\n" + "Последня версия: 1.0.1dev" + BuildConfig.VERSION_NAME);
        dialogAboutDev.show();

    }


    @Override
    public void getClient(Client client) {
        Log.d(TAG, client.toString());


        dialogFragment = DialogFragment.newInstance(client.getLogin(), client.getEmail());

        fragmentManager.beginTransaction().replace(R.id.frame, dialogFragment, "dialog").addToBackStack("dialogs").commitAllowingStateLoss();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textViewUserDetails = null;
        sqliteHandler = null;
        sessionManager = null;
        intent = null;
        dialogFragment = null;
        fragmentManager = null;
        dialogAboutDev = null;
        dialogsFragment = null;

    }



}


