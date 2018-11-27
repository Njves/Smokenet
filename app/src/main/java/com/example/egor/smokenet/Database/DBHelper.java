package com.example.egor.smokenet.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG = "database";
    private final String table = "purse";
    private final String name = "name";
    private final String money = "money";
    public DBHelper(@Nullable Context context) {
        super(context, "smokedb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "Create database");
        sqLiteDatabase.execSQL("create table " + table + "( id integer primary key autoincrement,  " + name + " text, " + money + " real );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
