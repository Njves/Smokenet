package com.example.egor.smokenet.Models;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.egor.smokenet.Requests.UserAPI;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller implements Callback<User> {
    public static final String URL = "http://litemessenger.ru/users/";
    private Context mContext;

    public Controller(Context context) {
        mContext = context;


    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if(response.isSuccessful())
        {
            String login = response.body().getLogin();


            Toast.makeText(mContext.getApplicationContext(), response.body().getSession(), Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Log.i("InternetConn", "Fail " + t.toString());
    }


}
