package com.example.egor.smokenet.Models;

import android.util.Log;

import com.example.egor.smokenet.Config.AppConfig;
import okhttp3.WebSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPConnection {
    Socket socket;

    BufferedReader bufferedReader;
    PrintWriter printWriter;


    public TCPConnection() throws IOException {
        socket = new Socket("192.168.1.65", AppConfig.SERVER_PORT);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);


    }
    public void startTalk()
    {
        printWriter.println("Привет всем!");
        try {
            String str = bufferedReader.readLine();
            while (str!=null)
            {
                Log.d("TCPConnecton", str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
