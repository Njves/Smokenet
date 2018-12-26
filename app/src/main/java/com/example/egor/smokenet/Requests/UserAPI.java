package com.example.egor.smokenet.Requests;

import com.example.egor.smokenet.Models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPI {
    @GET("get_user.php?name")
    Call<User> getUser(@Query("name") String name);
}
