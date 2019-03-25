package com.example.egor.smokenet.Models.Network;

import android.util.Log;
import com.example.egor.smokenet.Config.AppConfig;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/** Кдасс создает объекты сокета

 * */
public class NetworkController {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private Thread rxThread;
    private NetworkListener listener;

    public NetworkController()  {


    }
    public void connect() throws IOException {
        socket = new Socket("192.168.0.104", AppConfig.SERVER_PORT);
        Log.d ("NetworkController", "Сокет создан " + InetAddress.getLocalHost());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String str;
        while(true)
        {
            if((str= in.readLine()) != null)
            {
                Log.d("NetworkSocket", str);
            }
            out.write("Привет, тест программы!");
        }

    }

}
