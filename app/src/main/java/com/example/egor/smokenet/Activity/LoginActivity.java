package com.example.egor.smokenet.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.egor.smokenet.Config.AppConfig;
import com.example.egor.smokenet.Database.SQLiteHandler;
import com.example.egor.smokenet.Models.ErrorTranslator;
import com.example.egor.smokenet.Models.NetworkService;
import com.example.egor.smokenet.Models.SessionManager;
import com.example.egor.smokenet.POJO.ServerInformation;
import com.example.egor.smokenet.POJO.User;
import com.example.egor.smokenet.R;
import com.example.egor.smokenet.Requests.LoginUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private SessionManager session;
    private SQLiteHandler mSQLiteDatabase;
    private Intent intent;
    private EditText mEditTextLoginOrEmail;
    private EditText mEditTextPassword;
    private Button mButtonGoToRegister;
    private Button mButtonLoginSubmit;
    public static final String TAG = LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(getApplicationContext());
        mEditTextLoginOrEmail = findViewById(R.id.editTextLoginInput);
        mEditTextPassword = findViewById(R.id.editTextPasswordInput);
        mButtonGoToRegister = findViewById(R.id.buttonRegisterNow);
        mButtonLoginSubmit = findViewById(R.id.buttonSubmitLogin);
        if(savedInstanceState!=null)
        {
            mEditTextLoginOrEmail.setText(savedInstanceState.getString("loginOrEmail"));
        }
        mButtonLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditTextLoginOrEmail.getText().toString().isEmpty() && !mEditTextPassword.getText().toString().isEmpty()) {
                    userLogin();
                }
            }
        });
        mButtonGoToRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


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

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "Override bundle on Login");
        outState.putString("loginOrEmail", mEditTextLoginOrEmail.getText().toString());
    }

    @Override
    public void onBackPressed() {

    }
    public void userLogin() {
        if (!mEditTextLoginOrEmail.getText().toString().isEmpty()) {
            LoginUser loginUser = NetworkService.getInstance().getRetrofit().create(LoginUser.class);
            HashMap<String, String> postMap = new HashMap<>();
            postMap.put("login", mEditTextLoginOrEmail.getText().toString().trim());
            postMap.put("email", mEditTextLoginOrEmail.getText().toString().trim());
            postMap.put("password", mEditTextPassword.getText().toString().trim());
            Call<ServerInformation> call = loginUser.loginPostCall(postMap);
            call.enqueue(new Callback<ServerInformation>() {
                @Override
                public void onResponse(Call<ServerInformation> call, Response<ServerInformation> response) {
                    mSQLiteDatabase = new SQLiteHandler(getApplicationContext());
                    if (response.body().getError() <= 0) {
                        session.setLogin(true);
                        String login = response.body().getUser().getLogin();
                        String email = response.body().getUser().getEmail();
                        String uid = response.body().getUid();
                        String createdAt = response.body().getUser().getCreatedAt();
                        mSQLiteDatabase.addUser(login, email, uid, createdAt);
                        Log.d(TAG, "login - " + login + " " + response.toString() + "uid " + uid);
                        intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);

                    } else {
                        String errorMsg = response.body().getErrorMsg();
                        try {
                            Toast.makeText(LoginActivity.this, ErrorTranslator.translator(response.body().getErrorMsg()), Toast.LENGTH_SHORT).show();
                        }catch (Exception e)
                        {
                            Log.d(TAG, e.toString());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerInformation> call, Throwable t) {
                    Log.d(TAG, "Ошибка запроса " + t.toString());
                }
            });
        }
    }

}
