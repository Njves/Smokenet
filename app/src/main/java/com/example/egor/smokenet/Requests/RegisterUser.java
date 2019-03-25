package com.example.egor.smokenet.Requests;

import com.example.egor.smokenet.Config.AppConfig;
import com.example.egor.smokenet.POJO.ServerInformation;
import com.example.egor.smokenet.POJO.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterUser {
    @FormUrlEncoded
    @POST(AppConfig.USERS_API +"register.php")
    Call<ServerInformation> createUser(@FieldMap HashMap<String, String> postDataParams);
}
