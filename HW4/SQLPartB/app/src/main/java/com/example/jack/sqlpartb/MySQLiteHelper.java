package com.example.jack.sqlpartb;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";

    private String tableName;

    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "create table " + TodoEntry.TABLE_TODO + " (" +
                    TodoEntry.COLUMN_ID + " integer primary key autoincrement, " +
                    TodoEntry.COLUMN_TITLE  + " text not null, " +
                    TodoEntry.COLUMN_DESC + " text not null, " +
                    TodoEntry.COLUMN_DATE + " text not null)";

    public MySQLiteHelper(Context context, String tableName) {
        super(context, tableName, null, DATABASE_VERSION);
        this.tableName=tableName;
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
        public static final String TABLE_TODO = "todo";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESC = "desc";
        public static final String COLUMN_DATE = "date";
    }

}


