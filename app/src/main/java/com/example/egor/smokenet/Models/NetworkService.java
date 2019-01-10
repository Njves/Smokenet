package com.example.egor.smokenet.Models;

import com.example.egor.smokenet.Config.AppConfig;
import com.example.egor.smokenet.Requests.MessengerCreateUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService instance;
    private Retrofit retrofit;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static NetworkService getInstance()
    {
        if(instance==null)
        {
            instance = new NetworkService();

        }
        return instance;
    }
    public NetworkService()
    {
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl("http://litemessenger.ru/").build();

    }
    public Retrofit getRetrofit()
    {
        return retrofit;
    }
}
