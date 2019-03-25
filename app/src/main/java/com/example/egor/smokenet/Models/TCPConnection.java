package com.example.egor.smokenet.Models;

import android.util.Log;

import com.example.egor.smokenet.Config.AppConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPConnection {
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public TCPConnection()
    {
        try {
            socket = new Socket(AppConfig.BASE_URL,AppConfig.HTTP_POST);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            StringBuilder command = new StringBuilder("GET users/register.html HTTP/1.1");
            command.append("Host: litemessenger.ru");
            command.append("Connection:close");
            printWriter.print(command);

            String line = bufferedReader.readLine();
            Log.d("Socketffb", line);
            while(line!=null)
            {
                line = bufferedReader.readLine();
                Log.d("SOCKETSFFB",line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
