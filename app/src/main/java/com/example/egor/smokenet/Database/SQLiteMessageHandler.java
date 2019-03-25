package com.example.egor.smokenet.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.IntentSender;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.egor.smokenet.POJO.Message;

import java.util.Arrays;
import java.util.HashMap;
/** База данных для хранения сообщений
 * */
public class SQLiteMessageHandler extends SQLiteOpenHelper {
    public static final String TAG = "SQLiteMessageDB";
    private static final String TABLE = "messages";
    private static final String DB_NAME = "messageDb";
    private static int VERSION = 1;
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SENDER = "user_sender";
    private static final String COLUMN_RECIVER = "user_reciver";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_FLAGS = "flags";
    public SQLiteMessageHandler(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SENDER + " TEXT, " + COLUMN_RECIVER + " TEXT, " +
                     COLUMN_TEXT + " TEXT, " + COLUMN_FLAGS + " REAL, " + COLUMN_DATE + " INTEGER );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void writeMessage(Message message)
    {
        HashMap<String, String> dataMsgMap = Message.messageConverter(message);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_SENDER, dataMsgMap.get("user_sender"));
        contentValues.put(COLUMN_RECIVER, dataMsgMap.get("user_reciver"));
        contentValues.put(COLUMN_TEXT, dataMsgMap.get("text"));
        contentValues.put(COLUMN_FLAGS, String.valueOf(dataMsgMap.get("flags")));
        contentValues.put(COLUMN_DATE, String.valueOf(dataMsgMap.get("date")));

        long id = db.insert(TABLE, null,contentValues);


        db.close();
        getMessages();
    }
    public void getMessages()
    {
        SQLiteDatabase sql = this.getReadableDatabase();
        Cursor cursor = sql.rawQuery("SELECT * FROM " + TABLE, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String user_sender = cursor.getString(cursor.getColumnIndex(COLUMN_SENDER));
            String user_reciver = cursor.getString(cursor.getColumnIndex(COLUMN_RECIVER));
            String text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT));
          int flags = cursor.getInt(cursor.getColumnIndex(COLUMN_FLAGS));
          String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

            Log.d(TAG, "Sender: " + user_sender + " Reciver: " + user_reciver + " Text: " + text + " Flags: " + flags + " Date: "+ date);
            sql.close();
        }
    }
}
