package com.example.developersimualtor.fortask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskSQL extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TASK";
    private static final int VERSION = 1;
    protected static final String TABLE_NAME = "TASK_TABLE";

    protected static final String ID = "id";
    protected static final String MONEY = "money";
    protected static final String XP = "xp";
    protected static final String NAME_TASK = "name_task";
    protected static final String STAMINA = "stamina";
    protected static final String TIME = "time";
    protected static final String SKILL = "skills";

    protected static final int NUM_COLUMN_ID = 0;
    protected static final int NUM_COLUMN_MONEY = 1;
    protected static final int NUM_COLUMN_XP = 2;
    protected static final int NUM_COLUMN_NAME_TASK  = 3;
    protected static final int NUM_COLUMN_STAMINA = 4;
    protected static final int NUM_COLUNM_TIME = 5;
    protected static final int NUM_COLUMN_SKILL = 6;



    public TaskSQL(Context context) {
        super(context, DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                MONEY + " INTEGER, "+
                XP + " INTEGER, "+
                NAME_TASK + " TEXT, " + STAMINA + " INTEGER, " + TIME + " INTEGER, "+ SKILL + " TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static int getNumColumnId() {
        return NUM_COLUMN_ID;
    }

    public static int getNumColumnMoney() {
        return NUM_COLUMN_MONEY;
    }


    public static String getDBName() {
        return DATABASE_NAME;
    }

    public static int getNumColumnXp() {
        return NUM_COLUMN_XP;
    }

    public static String getID() {
        return ID;
    }

    public static String getMONEY() {
        return MONEY;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getNameTask() {
        return NAME_TASK;
    }

    public static int getVERSION() {
        return VERSION;
    }

    public static String getXP() {
        return XP;
    }
}
