package com.example.egor.smokenet.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {


        @SerializedName("login")
        @Expose
        private String login;
        @SerializedName("email")
        @Expose
        private String email;

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

    @Override
    public String toString() {
        return "Client{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
