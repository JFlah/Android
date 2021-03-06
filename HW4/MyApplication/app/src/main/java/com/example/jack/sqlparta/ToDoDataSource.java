package com.example.jack.sqlparta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ToDoDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.TodoEntry.COLUMN_ID,
            MySQLiteHelper.TodoEntry.COLUMN_TITLE,
            MySQLiteHelper.TodoEntry.COLUMN_DESC,
            MySQLiteHelper.TodoEntry.COLUMN_DATE
    };
    public ToDoDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createTodo(String title, String desc, String date) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TodoEntry.COLUMN_TITLE, title);
        values.put(MySQLiteHelper.TodoEntry.COLUMN_DESC, desc);
        values.put(MySQLiteHelper.TodoEntry.COLUMN_DATE, date);
        return database.insert(MySQLiteHelper.TodoEntry.TABLE_TODO, null, values);
    }

    public void updateTodo(ToDo item){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TodoEntry.COLUMN_TITLE, item.title);
        values.put(MySQLiteHelper.TodoEntry.COLUMN_DESC, item.desc);
    }

    public void deleteItem(ToDo item) {
        long id = item.getId();
        database.delete(MySQLiteHelper.TodoEntry.TABLE_TODO, MySQLiteHelper.TodoEntry.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<ToDo> getAllItems() {
        ArrayList<ToDo> items = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TodoEntry.TABLE_TODO,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ToDo detail = cursorToItem(cursor);
            items.add(detail);
            cursor.moveToNext();
        }
        // closing
        cursor.close();
        return items;
    }

    private ToDo cursorToItem(Cursor cursor) {
        long id = cursor.getLong(0);
        String title = cursor.getString(1);
        String desc = cursor.getString(2);
        String date = cursor.getString(3);
        return new ToDo(id,title,desc,date);
    }

}
