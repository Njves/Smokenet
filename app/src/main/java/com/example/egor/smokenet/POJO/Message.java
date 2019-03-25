package com.example.egor.smokenet.POJO;

import android.util.Log;
import com.example.egor.smokenet.Models.NetworkService;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Message {

    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("user_sender")
    @Expose
    private String userSender;
    @SerializedName("user_reciver")
    @Expose
    private String userReciver;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("flags")
    @Expose
    private Integer flags;
    @SerializedName("time")
    @Expose
    private Integer time;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUserSender() {
        return userSender;
    }

    public void setUserSender(String userSender) {
        this.userSender = userSender;
    }

    public String getUserReciver() {
        return userReciver;
    }

    public void setUserReciver(String userReciver) {
        this.userReciver = userReciver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "uniqueId='" + uniqueId + '\'' +
                ", userSender='" + userSender + '\'' +
                ", userReciver='" + userReciver + '\'' +
                ", text='" + text + '\'' +
                ", flags=" + flags +
                ", time=" + time +
                '}';
    }
    public static HashMap<String, String> messageConverter(Message message)
    {
        String JSON = NetworkService.getInstance().getGson().toJson(message);
        HashMap<String, String> map = NetworkService.getInstance().getGson().fromJson(JSON, HashMap.class);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Log.d("MessageConverter", "Ключ: " + key + " ," + " Значения: " + value);
        }
            

        return map;
    }

    public Message(String userSender, String userReciver, String text, Integer flags, Integer time) {
        this.userSender = userSender;
        this.userReciver = userReciver;
        this.text = text;
        this.flags = flags;
        this.time = time;
    }
}
