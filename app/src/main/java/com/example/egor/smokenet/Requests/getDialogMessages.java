package com.example.egor.smokenet.Requests;

import com.example.egor.smokenet.POJO.Message;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface getDialogMessages {
    @GET("api.php")
    Call<List<Message>> messageCall(@Query("action") String action,@Query("sender") String sender, @Query("reciver") String reciver);
}
