package com.example.mycompany.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycompany.todolist.db.TodoOpenHelper;

public class DetailActivity extends AppCompatActivity {
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        long id = intent.getLongExtra("id",0);

        TodoOpenHelper todoOpenHelper = new TodoOpenHelper(this);
        SQLiteDatabase db = null;

        try {
            db = todoOpenHelper.getReadableDatabase();

            cursor = db.query("TODO", null, "_id=?",
                    new String[]{String.valueOf(id)}, null, null, null);
            if(!cursor.moveToFirst()){
                return;
            }

            String strTitele = cursor.getString(cursor.getColumnIndex("title"));
            String strcontent = cursor.getString(cursor.getColumnIndex("content"));

            EditText titleview = findViewById(R.id.title_text);
            EditText contentview = findViewById(R.id.content_text);

            titleview.setText(strTitele);
            contentview.setText(strcontent);

        }catch (Exception e){
            Log.e(this.getClass().getSimpleName(),"DBエラー",e);
        }finally {
            if (db != null){
                db.close();
            }
        }

        View btnDelete = findViewById(R.id.delete_btn);
        btnDelete.setOnClickListener((View)->{
            SQLiteDatabase deleteDatabase = null;
            SQLiteOpenHelper deleteHelper = null;
            try {
                deleteHelper = new TodoOpenHelper(this);
                deleteDatabase = deleteHelper.getWritableDatabase();

                int deleteNum = deleteDatabase.delete("TODO", "_id=?",
                        new String[]{String.valueOf(id)});

                if (deleteNum != 1){
                    Toast.makeText(this, R.string.delete_failed,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, R.string.delete_success,Toast.LENGTH_SHORT).show();
                }
                finish();

            }catch (Exception e){
                Log.e(this.getClass().getSimpleName(), "DBエラー",e);
            }finally {
                if (deleteDatabase != null){
                    deleteDatabase.close();
                }
            }
        });

        View btnUodate = findViewById(R.id.update_btn);
        btnUodate.setOnClickListener((View)->{
            SQLiteOpenHelper UpdateOpenHelper = null;
            SQLiteDatabase UpdateDatabase = null;

            String title = ((EditText) findViewById(R.id.title_text)).getText().toString();
            String content = ((EditText) findViewById(R.id.content_text)).getText().toString();

            try {
                UpdateOpenHelper =new TodoOpenHelper(this);
                UpdateDatabase = UpdateOpenHelper.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put("title", title);
                cv.put("content", content);

                int updateNum = UpdateDatabase.update("TODO", cv, "_id=?",
                        new String[]{String.valueOf(id)});

                if (updateNum != 1){
                    Toast.makeText(this,R.string.update_failed,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,R.string.update_success,Toast.LENGTH_SHORT).show();
                }

                finish();

            }catch (Exception e){
                Log.e(this.getClass().getSimpleName(), "DBエラー",e);
            }finally {
                if (UpdateDatabase != null){
                    UpdateDatabase.close();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cursor != null && !cursor.isClosed()){
            cursor.close();
        }
    }
}

