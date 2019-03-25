package com.example.egor.smokenet.Models;

import android.content.Context;
import com.example.egor.smokenet.Database.SQLiteMessageHandler;
import com.example.egor.smokenet.POJO.Message;

public class MessageManager {
    Message message;
    SQLiteMessageHandler dbMessage;
    Context context;

    public MessageManager(Context context)
    {
        this.context = context;
        dbMessage = new SQLiteMessageHandler(context);

    }


}
