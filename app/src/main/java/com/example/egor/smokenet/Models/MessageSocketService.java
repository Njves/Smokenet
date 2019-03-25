package com.example.egor.smokenet.Models;

import com.example.egor.smokenet.Config.AppConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import android.util.Log;

public class MessageSocketService {
    private static final String TAG = "MessageSocketService";
    private Socket socket;
    private int port = 80;
    private InputStream socketInputStream;
    private OutputStream socketOutputStream;
    public MessageSocketService()
    {
        try {
            socket = new Socket("litemessenger.ru", port);

            StringBuilder command = new StringBuilder("POST /users/register.php HTTP/1.1\r\n");
            command.append("Content-Type: application/x-www-form-urlencoded\r\n");



            socketInputStream = socket.getInputStream();
            socketOutputStream = socket.getOutputStream();

            socketOutputStream.write(command.toString().getBytes());

            BufferedReader br = new BufferedReader(new InputStreamReader(socketInputStream));

            String line = br.readLine();
            while(line!=null)
            {
                Log.d(TAG, line);
                try{
                    line = br.readLine();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socketInputStream.close();
            socketOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
