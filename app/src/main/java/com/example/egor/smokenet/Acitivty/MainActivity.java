package com.example.egor.smokenet.Acitivty;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.egor.smokenet.Database.DBHelper;
import com.example.egor.smokenet.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.tx);
        dbHelper = new DBHelper(this);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                double money = (int)(Math.random()*(100-1)+1);
                cv.put("name", "Nikita");
                cv.put("money", money);

                long rowID = db.insert("purse", null, cv);
                dbHelper.close();
            }
        });

    }
}
