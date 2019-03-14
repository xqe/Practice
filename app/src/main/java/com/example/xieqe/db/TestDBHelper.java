package com.example.practice.vivopractice.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by XieQiEn(11083243) on 2019/3/14
 */
public class TestDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Test.db";
    static final String DB_TABLE_NAME = "testTable";
    private static final String KEY_ID = "_id";
    private static final int DB_VERSION = 1;
    static final String DB_KEY1 = "key1";
    static final String DB_KEY2 = "key2";


    public TestDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableCmd = "CREATE TABLE " + DB_TABLE_NAME
                + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DB_KEY1 + " TEXT,"
                + DB_KEY2 + " TEXT);";
        db.execSQL(createTableCmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
