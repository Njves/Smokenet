package com.example.egor.smokenet.Requests;

import com.example.egor.smokenet.POJO.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DialogsUser {
    @GET("getListClients.php")
    Call<List<Client>> getUsersLogin();
}
