package com.example.egor.smokenet.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteMessageHandler extends SQLiteOpenHelper {
    public SQLiteMessageHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table `messages`(\n" +
                "`_id` integer primary key autoincrement, \n" +
                "`text` text,\n" +
                "`sender` text,\n" +
                "`reciver` text,\n" +
                "`date` date\n" +
                ");";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
