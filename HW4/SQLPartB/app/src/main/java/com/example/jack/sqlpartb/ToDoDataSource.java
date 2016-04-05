package com.example.jack.sqlpartb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    private static final String TAG = "TODODB";

    public ToDoDataSource(Context context, String tableName) {
        dbHelper = new MySQLiteHelper(context, tableName);
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

        int rowsAffected = database.update(MySQLiteHelper.TodoEntry.TABLE_TODO, values, MySQLiteHelper.TodoEntry.COLUMN_ID+"="+item.getId(),null);
    }

    public void deleteItem(ToDo item) {
        long id = item.getId();
        Log.d(TAG, "delete item = " + id);
        System.out.println("Item deleted with id: " + id);
        database.delete(MySQLiteHelper.TodoEntry.TABLE_TODO, MySQLiteHelper.TodoEntry.COLUMN_ID
                + " = " + id, null);
    }

    public void deleteAllItems() {
        System.out.println("Item deleted all");
        Log.d(TAG, "delete all = ");
        database.delete(MySQLiteHelper.TodoEntry.TABLE_TODO, null, null);
    }

    public ArrayList<ToDo> getAllItems() {
        ArrayList<ToDo> items = new ArrayList<ToDo>();

        Cursor cursor = database.query(MySQLiteHelper.TodoEntry.TABLE_TODO,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ToDo detail = cursorToItem(cursor);
            Log.d(TAG, "get detail = " + cursorToItem(cursor).toString());
            items.add(detail);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
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