package com.example.mycompany.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycompany.todolist.db.TodoOpenHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        View list_button = findViewById(R.id.list_button);
        list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_list.class);
                startActivity(intent);
            }
        });

        View input_button = findViewById(R.id.input_button);
        input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title_edit = findViewById(R.id.title_edit);
                EditText content_edit = findViewById(R.id.content_edit);

                SQLiteOpenHelper helper = null;
                SQLiteDatabase db = null;
                try {
                    helper = new TodoOpenHelper(getApplicationContext());
                    db = helper.getWritableDatabase();

                    ContentValues cv = new ContentValues();
                    cv.put("title", title_edit.getText().toString());
                    cv.put("content", content_edit.getText().toString());
                    db.insert("TODO", null, cv);

                    Toast.makeText(MainActivity.this, R.string.input_success_massage,
                            Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.e(this.getClass().getSimpleName(), "DBエラー", e);
                } finally {
                    db.close();
                }
                
            }
        });
    }
}