package com.example.egor.smokenet.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteMessageHandler extends SQLiteOpenHelper {
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SENDER = "sender";
    private static final String COLUMN_RECIVER = "reciver";
    private static final String COLUMN_DATE = "date";
    public SQLiteMessageHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table `messages`(\n" +
                COLUMN_ID + " integer primary key autoincrement, \n" +
                COLUMN_TEXT+ " text,\n" +
                COLUMN_SENDER + "text,\n" +
                COLUMN_RECIVER + "text,\n" +
                COLUMN_DATE + " date\n" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
