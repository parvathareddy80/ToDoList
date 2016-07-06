package com.codepath.todolist;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;

public class ToDoItemDbHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "taskerManager";

    private static final String TABLE_NAME = "tasks";

    private static final String KEY_ID = "_id";
    private static final String KEY_TASKNAME = "taskName";
    private static final String KEY_DUEDATE = "taskDueDate";
    private static final String KEY_STATUS = "status";

    public ToDoItemDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + " ( "
        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + KEY_TASKNAME+ " TEXT,"
        + KEY_DUEDATE + " TEXT,"
        + KEY_STATUS + " TEXT )";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void addTask(ToDoItem task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASKNAME, task.getTask()); // task name
        Date date = task.getDueDate();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(date);

        values.put(KEY_DUEDATE, dateString);
// status of task- can be 0 for not done and 1 for done
        values.put(KEY_STATUS, task.getStatus());

// Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<ToDoItem> getAllTasks() {
        ArrayList<ToDoItem> toDoItems = new ArrayList<ToDoItem>();
// Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setId(cursor.getInt(0));

                toDoItem.setTask(cursor.getString(1));

                String dateString = cursor.getString(2);
                ParsePosition pos = new ParsePosition(0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
                Date date = simpleDateFormat.parse(dateString,pos);
                toDoItem.setDueDate(date);

                toDoItem.setStatus(cursor.getString(3));
// Adding contact to list
                toDoItems.add(toDoItem);
            } while (cursor.moveToNext());
        }
// return task list
        return toDoItems;
    }

    public void deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] {String.valueOf(id)});
    }

    public void updateTask(ToDoItem task) {
// updating row
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASKNAME, task.getTask());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(task.getDueDate());

        values.put(KEY_DUEDATE, dateString);

        values.put(KEY_STATUS, task.getStatus());

        db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }
}