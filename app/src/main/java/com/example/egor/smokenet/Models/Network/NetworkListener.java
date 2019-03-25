package com.example.egor.smokenet.Models.Network;

public interface NetworkListener {
    void onConnectionReady();
    void onReciveMessage();
    void onException();
    void onDisconnect();
}
