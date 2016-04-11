package com.example.jack.scrapept2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cities.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + CityEntry.TABLE_CITY + " (" +
                    CityEntry.COLUMN_ID + " integer primary key autoincrement, " +
                    CityEntry.COLUMN_NAME  + " text not null, " +
                    CityEntry.COLUMN_OLDPOP + " text not null, " +
                    CityEntry.COLUMN_NEWPOP + " text not null, " +
                    CityEntry.COLUMN_POPCHANGE + " text not null)";

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
        db.execSQL("DROP TABLE IF EXISTS " + CityEntry.TABLE_CITY);
        onCreate(db);
    }

    public static abstract class CityEntry implements BaseColumns {
        public static final String COLUMN_ID = "_id";
        public static final String TABLE_CITY = "city";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_OLDPOP = "oldpop";
        public static final String COLUMN_NEWPOP = "newpop";
        public static final String COLUMN_POPCHANGE = "popchange";
    }

}


