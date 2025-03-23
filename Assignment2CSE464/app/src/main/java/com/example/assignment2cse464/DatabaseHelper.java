package com.example.assignment2cse464;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todoDB";
    private static final String TABLE_NAME = "tasks";
    private static final String COL_ID = "id";
    private static final String COL_TASK = "task";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TASK + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addTask(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TASK, task);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean updateTask(int id, String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TASK, task);
        int result = db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public Map<Integer, String> getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        Map<Integer, String> tasks = new HashMap<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String task = cursor.getString(cursor.getColumnIndexOrThrow(COL_TASK));
                tasks.put(id, task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return tasks;
    }
}

