package com.example.egor.smokenet.Requests;

import com.example.egor.smokenet.Models.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface DialogsUser {
    @GET("getListClients.php")
    Call<List<Client>> getUsersLogin();
}
