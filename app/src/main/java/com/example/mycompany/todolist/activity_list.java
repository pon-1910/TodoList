package com.example.mycompany.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.mycompany.todolist.db.TodoOpenHelper;

public class activity_list extends ListActivity {

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TodoOpenHelper todoOpenHelper = new TodoOpenHelper(this);
        SQLiteDatabase db = null;
        try {
            db = todoOpenHelper.getReadableDatabase();
            cursor = db.query("TODO", null, null, null,
                    null, null, null);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_row, cursor,
                    new String[]{"title"}, new int[]{R.id.list_title},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            setListAdapter(adapter);

        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "DBエラー", e);
        } finally {
            if(db != null) {
                db.close();
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}