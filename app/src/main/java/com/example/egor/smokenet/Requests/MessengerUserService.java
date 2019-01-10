package com.example.egor.smokenet.Requests;

import com.example.egor.smokenet.POJO.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MessengerUserService {
    @GET("get_user.php?name")
    Call<User> getUser(@Query("name") String name);
}
