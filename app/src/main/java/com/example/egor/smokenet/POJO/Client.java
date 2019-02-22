package com.example.egor.smokenet.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Client implements Comparator<Client> {


    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("email")
    @Expose
    private String email;

    private int priority;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Client{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public int compare(Client o1, Client o2) {
        if(o1.getPriority()>o2.getPriority())
        {
            return 1;

        }
        else
        {
            return 0;
        }
    }
}
