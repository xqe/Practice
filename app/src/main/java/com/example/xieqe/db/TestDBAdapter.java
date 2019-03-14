package com.example.practice.vivopractice.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.example.practice.vivopractice.db.TestDBHelper.DB_KEY1;
import static com.example.practice.vivopractice.db.TestDBHelper.DB_KEY2;
import static com.example.practice.vivopractice.db.TestDBHelper.DB_TABLE_NAME;

/**
 * Created by XieQiEn(11083243) on 2019/3/14
 */
public class TestDBAdapter {

    private TestDBHelper testDBHelper;
    private static final String TAG = TestDBAdapter.class.getSimpleName();

    public TestDBAdapter(Context context) {
        testDBHelper = new TestDBHelper(context);
    }

    public void insert(String value) {
        SQLiteDatabase db = testDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_KEY1,value + "1");
        values.put(DB_KEY2,value + "2");
        db.insert(DB_TABLE_NAME,null,values);
        Log.i(TAG, "insert success:" + value);
    }
}
