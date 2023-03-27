package com.example.developersimualtor.forstore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoreSQL extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "STORE";
    private static final int VERSION = 1;
    protected static final String TABLE_NAME = "STORE_TABLE";

    protected static final String ID = "id";
    protected static final String MONEY = "price";
    protected static final String NAME = "name";
    protected static final String HUNGER ="hunger";

    protected static final int NUM_COLUMN_ID = 0;
    protected static final int NUM_COLUMN_MONEY = 1;
    protected static final int NUM_COLUMN_NAME = 2;
    protected static final int NUM_COLUMN_HUNGER = 3;

    public StoreSQL(Context context) {
        super(context, DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ MONEY +" INTEGER, " + NAME + " TEXT, "+ HUNGER +" INTEGER)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
