package com.example.developersimualtor.forCompany;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;


public class CompanySQL extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "COMPANY";
    private static final int VERSION = 1;
    protected static final String TABLE_NAME = "COMPANY_TABLE";

    protected static final String ID = "id";
    protected static final String MONEY = "money";
    protected static final String POPULAR = "popular";
    protected static final String NAME_COMPANY = "name_company";
    protected static final String FATIGUE = "fatigue";

    protected static final int NUM_COLUMN_ID = 0;
    protected static final int NUM_COLUMN_MONEY = 1;
    protected static final int NUM_COLUMN_POPULAR = 2;
    protected static final int NUM_COLUMN_NAME_COMPANY  = 3;
    protected static final int NUM_COLUMN_FATIGUE = 4;

    String[] company_name = {"Montana", "ParSer", "Crystal Digital", "1S", "Bukva", "SAMSONG", "Pail.RU","RobyCODE","Youndex.ru","Moogle"};


    public CompanySQL(Context context) {
        super(context, DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                MONEY + " INTEGER, "+
                POPULAR + " INTEGER, "+
                NAME_COMPANY + " TEXT, " + FATIGUE + " INTEGER )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static int getNumColumnFatigue() {
        return NUM_COLUMN_FATIGUE;
    }


    public static int getNumColumnMoney() {
        return NUM_COLUMN_MONEY;
    }

    public static int getNumColumnNameCompany() {
        return NUM_COLUMN_NAME_COMPANY;
    }

    public static int getNumColumnPopular() {
        return NUM_COLUMN_POPULAR;
    }

    public static String getNameCompany() {
        return NAME_COMPANY;
    }

    public static String getMONEY() {
        return MONEY;
    }

    public static String getPOPULAR() {
        return POPULAR;
    }

    public static String getFATIGUE() {
        return FATIGUE;
    }

    public String[] getCompany_name() {
        return company_name;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }
}
