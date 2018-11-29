package com.example.egor.smokenet.Acitivty;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.egor.smokenet.Database.DBHelper;
import com.example.egor.smokenet.R;

import java.util.Random;

public class    MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button button;
    private Button button1;
    private EditText editText;
    private DBHelper dbHelper;
    public static final String  TAG = "mainactivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.tx);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        button1 = findViewById(R.id.button2);
        dbHelper = new DBHelper(this);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, editText.getText().toString());
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();


                double money = (int)(Math.random()*(100-1)+1);
                cv.put("name", editText.getText().toString());
                cv.put("money", money);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                long rowID = db.insert("purse", null, cv);
                dbHelper.close();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "--- Rows in mytable: ---");
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("purse", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int name = c.getColumnIndex("name");
                    int money = c.getColumnIndex("money");


                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(TAG,
                                "name = " + c.getString(name) +
                                        ", money = " + c.getInt(money));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(TAG, "0 rows");
                c.close();
            }
        });

    }
}
