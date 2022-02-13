package com.example.lab4;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "todos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TXT = "txt";
    public static final String COLUMN_ISURGENT = "isUrgent";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todos_db";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TXT + " TEXT,"
                    + COLUMN_ISURGENT + " INTEGER"
                    + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }
    public void insertTodo (String txt, int isUrgent, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TXT, txt);
        values.put(COLUMN_ISURGENT, isUrgent);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public int geTodoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        Log.d("count is", String.valueOf(count));
        return count;
    }
    @SuppressLint("Range")
    public List<Todo> getAllTodos () {
        List <Todo> todos = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                COLUMN_ID + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("my ids", String.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                @SuppressLint("Range") Todo todo = new Todo(cursor.getString(cursor.getColumnIndex(COLUMN_TXT)), cursor.getInt(cursor.getColumnIndex(COLUMN_ISURGENT)) == 1 ? true : false);
                todos.add(todo);
            } while (cursor.moveToNext());
        }
        db.close();
        return todos;
    }

    public Todo getLastToDo () {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID +  " DESC LIMIT 1";
        String newQ = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                COLUMN_ID + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(newQ, null);
        cursor.moveToLast();
        @SuppressLint("Range") Todo todo = new Todo(cursor.getString(cursor.getColumnIndex(COLUMN_TXT)), cursor.getInt(cursor.getColumnIndex(COLUMN_ISURGENT)) == 1 ? true : false);
        return todo;
    }
    public void delTodo (String txt) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_TXT + " = ?", new String[] {txt});
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
