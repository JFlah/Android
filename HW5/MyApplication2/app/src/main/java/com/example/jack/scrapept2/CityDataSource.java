package com.example.jack.scrapept2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class CityDataSource {
    public SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    public String[] allColumns = {
            MySQLiteHelper.CityEntry.COLUMN_ID,
            MySQLiteHelper.CityEntry.COLUMN_NAME,
            MySQLiteHelper.CityEntry.COLUMN_OLDPOP,
            MySQLiteHelper.CityEntry.COLUMN_NEWPOP,
            MySQLiteHelper.CityEntry.COLUMN_POPCHANGE
    };
    public CityDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void delete() {
        database.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.CityEntry.TABLE_CITY);
    }

    public void create() {
        database.execSQL("create table if not exists " + MySQLiteHelper.CityEntry.TABLE_CITY + " (" +
                MySQLiteHelper.CityEntry.COLUMN_ID + " integer primary key autoincrement, " +
                MySQLiteHelper.CityEntry.COLUMN_NAME  + " text not null, " +
                MySQLiteHelper.CityEntry.COLUMN_OLDPOP + " text not null, " +
                MySQLiteHelper.CityEntry.COLUMN_NEWPOP + " text not null, " +
                MySQLiteHelper.CityEntry.COLUMN_POPCHANGE + " text not null)");
    }

    public long createCity(String name, String oldPop, String newPop, String popChange) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.CityEntry.COLUMN_NAME, name);
        values.put(MySQLiteHelper.CityEntry.COLUMN_OLDPOP, oldPop);
        values.put(MySQLiteHelper.CityEntry.COLUMN_NEWPOP, newPop);
        values.put(MySQLiteHelper.CityEntry.COLUMN_POPCHANGE, popChange);
        return database.insert(MySQLiteHelper.CityEntry.TABLE_CITY, null, values);
    }

//    public void updateCity(City item){
//        ContentValues values = new ContentValues();
//        values.put(MySQLiteHelper.CityEntry.COLUMN_TITLE, item.title);
//        values.put(MySQLiteHelper.CityEntry.COLUMN_DESC, item.desc);
//    }

//    public void deleteItem(City item) {
//        long id = item.getId();
//        System.out.println("Item deleted with id: " + id);
//        database.delete(MySQLiteHelper.TodoEntry.TABLE_TODO, MySQLiteHelper.TodoEntry.COLUMN_ID
//                + " = " + id, null);
//    }

    public ArrayList<City> getAllItems() {
        ArrayList<City> items = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.CityEntry.TABLE_CITY,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            City detail = cursorToItem(cursor);
            items.add(detail);
            cursor.moveToNext();
        }
        // closing
        cursor.close();
        return items;
    }

    public City cursorToItem(Cursor cursor) {
        long id = cursor.getLong(0);
        String name = cursor.getString(1);
        String oldPop = cursor.getString(2);
        String newPop = cursor.getString(3);
        String popChange = cursor.getString(4);
        return new City(id, name, oldPop, newPop, popChange);
    }

}
