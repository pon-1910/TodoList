package com.example.mycompany.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TodoOpenHelper extends SQLiteOpenHelper {

    private static final String FILE_NAME = "data";
    private static final int VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE 'TODO' ("+
            "'_id' INTEGER PRIMARY KEY AUTOINCREMENT,"+"'title' TEXT,'content' TEXT"+");";

    public TodoOpenHelper(@Nullable Context context){
        super(context, FILE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
