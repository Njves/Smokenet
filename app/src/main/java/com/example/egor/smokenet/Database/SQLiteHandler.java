package com.example.egor.smokenet.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {
    public static final String TAG = SQLiteHandler.class.getSimpleName();
    public static final String NAME = "LiteMessengerDatabase";
    public static final int VERSION = 1;
    public static final String TABLE_USER = "users";
    public static final String TABLE_ID = "_id";
    public static final String USER_LOGIN = "login";
    public static final String USER_EMAIL = "email";
    public static final String USER_UID = "uid";
    public static final String USER_CREATED_AT = "created_at";
    public SQLiteHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE = "CREATE TABLE " + TABLE_USER + "("
                    + TABLE_ID + " INTEGER PRIMARY KEY," + USER_LOGIN + " TEXT,"
                    + USER_EMAIL + " TEXT UNIQUE," + USER_UID + " TEXT,"
                    + USER_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addUser(String login,String uid, String email, String date)
    {
        SQLiteDatabase sqlite = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_LOGIN,login);
        values.put(USER_EMAIL,email);
        values.put(USER_CREATED_AT,date);
        values.put(USER_UID,uid);
        long id = sqlite.insert(TABLE_USER, null, values);
        sqlite.close();
        Log.d(TAG, "id" + id);

    }
    public HashMap<String, String> getUserDetails()
    {
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("login", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        return user;
    }
}
