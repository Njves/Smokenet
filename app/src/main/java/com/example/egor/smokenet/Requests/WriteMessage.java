package com.example.egor.smokenet.Requests;

import com.example.egor.smokenet.Config.AppConfig;
import com.example.egor.smokenet.POJO.Message;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WriteMessage {
    @FormUrlEncoded
    @POST("api.php")
    Call<Message> writeMessage(@FieldMap HashMap<String, String> postMap);
}
