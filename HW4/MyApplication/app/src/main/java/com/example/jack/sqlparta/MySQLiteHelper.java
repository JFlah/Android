package com.example.jack.sqlparta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + TodoEntry.TABLE_TODO + " (" +
                    TodoEntry.COLUMN_ID + " integer primary key autoincrement, " +
                    TodoEntry.COLUMN_TITLE  + " text not null, " +
                    TodoEntry.COLUMN_DESC + " text not null, " +
                    TodoEntry.COLUMN_DATE + " text not null)";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TodoEntry.TABLE_TODO);
        onCreate(db);
    }

    public static abstract class TodoEntry implements BaseColumns {
        public static final String COLUMN_ID = "_id";
        public static final String TABLE_TODO = "todo";
        public static final String COLUMN_DESC = "desc";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TITLE = "title";
    }
}


