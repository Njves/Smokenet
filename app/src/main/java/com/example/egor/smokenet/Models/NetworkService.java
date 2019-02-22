package com.example.egor.smokenet.Models;

import com.example.egor.smokenet.Config.AppConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).client(client.build()).baseUrl(AppConfig.BASE_URL).build();

    }
    public Retrofit getRetrofit()
    {
        return retrofit;
    }
}
