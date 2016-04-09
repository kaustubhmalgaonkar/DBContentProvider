package com.examples.dbcontentproviderdemo.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DemoDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Demo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TYPE_TEXT = " TEXT";
    private static final String COMMA_SEP = ",";

    public DemoDatabase(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.Demo +
                "( " + BaseColumns._ID + " INTEGER PRIMARY KEY," +
                DemoContract.ColumnsDemo.NAME + TYPE_TEXT + COMMA_SEP +
                DemoContract.ColumnsDemo.MOBILE + TYPE_TEXT + COMMA_SEP +
                DemoContract.ColumnsDemo.EMAIL + TYPE_TEXT + " )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.Demo);
    }

    interface Tables{
        String Demo = "Demo";
    }
}
