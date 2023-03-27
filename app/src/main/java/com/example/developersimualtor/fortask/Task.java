package com.example.developersimualtor.fortask;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.developersimualtor.MainActivity;
import com.example.developersimualtor.R;
import com.example.developersimualtor.asyncclasses.Timer;
import com.example.developersimualtor.service.MediaPlayerService;

import java.util.ArrayList;
import java.util.HashMap;

public class Task extends ListActivity {
    private ArrayList<HashMap<String, String>> taskArray = new ArrayList<>();
    private HashMap<String, String> map;
    private SimpleAdapter adapter;
    Intent musik;
    ListView listView;
    Button button_back;
    String[] task_name = {
            "Написать программу Hello World",
            "Написать калькулятор",
            "Написать инженерный калькулятор",
            "Создать форму авторизации для сайта",
            "Сделать простую игру 2D на Unity",
            "Сделать 3D игру на Unity",
            "Написать игру на Unreal Engine 4",
            "Создать веб-сервер",
            "Создать свой защищенный мессенджер",
            "Создать свою социальную сеть"};
    Integer[] money = {10, 20, 50, 153, 300, 750, 1000, 2500, 7000, 100000};
    String[] skills = {"Pascal","Blueprint","C++","C#","JAVA","SQLite","Spring"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        listView = findViewById(android.R.id.list);
        TaskSQL taskSQL = new TaskSQL(this);
        button_back = findViewById(R.id.button);
        SQLiteDatabase sql = taskSQL.getWritableDatabase();
        musik = new Intent(this, com.example.developersimualtor.service.MediaPlayerService.class);
        musik.putExtra("musikID",R.raw.forall);
        Cursor cursor = new Cursor() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public int getPosition() {
                return 0;
            }

            @Override
            public boolean move(int offset) {
                return false;
            }

            @Override
            public boolean moveToPosition(int position) {
                return false;
            }

            @Override
            public boolean moveToFirst() {
                return false;
            }

            @Override
            public boolean moveToLast() {
                return false;
            }

            @Override
            public boolean moveToNext() {
                return false;
            }

            @Override
            public boolean moveToPrevious() {
                return false;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean isBeforeFirst() {
                return false;
            }

            @Override
            public boolean isAfterLast() {
                return false;
            }

            @Override
            public int getColumnIndex(String columnName) {
                return 0;
            }

            @Override
            public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
                return 0;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return null;
            }

            @Override
            public String[] getColumnNames() {
                return new String[0];
            }

            @Override
            public int getColumnCount() {
                return 0;
            }

            @Override
            public byte[] getBlob(int columnIndex) {
                return new byte[0];
            }

            @Override
            public String getString(int columnIndex) {
                return null;
            }

            @Override
            public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {

            }

            @Override
            public short getShort(int columnIndex) {
                return 0;
            }

            @Override
            public int getInt(int columnIndex) {
                return 0;
            }

            @Override
            public long getLong(int columnIndex) {
                return 0;
            }

            @Override
            public float getFloat(int columnIndex) {
                return 0;
            }

            @Override
            public double getDouble(int columnIndex) {
                return 0;
            }

            @Override
            public int getType(int columnIndex) {
                return 0;
            }

            @Override
            public boolean isNull(int columnIndex) {
                return false;
            }

            @Override
            public void deactivate() {

            }

            @Override
            public boolean requery() {
                return false;
            }

            @Override
            public void close() {

            }

            @Override
            public boolean isClosed() {
                return false;
            }

            @Override
            public void registerContentObserver(ContentObserver observer) {

            }

            @Override
            public void unregisterContentObserver(ContentObserver observer) {

            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void setNotificationUri(ContentResolver cr, Uri uri) {

            }

            @Override
            public Uri getNotificationUri() {
                return null;
            }

            @Override
            public boolean getWantsAllOnMoveCalls() {
                return false;
            }

            @Override
            public void setExtras(Bundle extras) {

            }

            @Override
            public Bundle getExtras() {
                return null;
            }

            @Override
            public Bundle respond(Bundle extras) {
                return null;
            }
        };
        //РАБОТА С БД
        try {
            ContentValues cv = new ContentValues();

            cursor = sql.rawQuery("SELECT COUNT(*) FROM " + taskSQL.TABLE_NAME + "",null);

            if(cursor != null){
                cursor.moveToFirst();
                if(cursor.getInt(0) == 0){
                    for(int i = 0; i < 10; i++){
                        cv.put(taskSQL.XP, 6 * i + 12);
                        cv.put(taskSQL.MONEY,money[i]);
                        cv.put(taskSQL.NAME_TASK, task_name[i]);
                        cv.put(taskSQL.STAMINA,i + 30);
                        cv.put(taskSQL.TIME,(i + 1) * 10 - 5);
                        String temp = "";
                        int j = 0;
                        while(j <= i && j < skills.length){
                            temp += "\n" + skills[j];
                            j++;
                        }
                        cv.put(taskSQL.SKILL, temp);
                        sql.insert(taskSQL.TABLE_NAME,null,cv);
                        cv.clear();
                    }
                }
            }
            cursor = sql.query(taskSQL.TABLE_NAME,null,null,null,null,null,null);
        }catch (Exception e){
            Log.d("task", e.getMessage());
        }
        cursor.moveToLast();
        int a = cursor.getInt(0);

        cursor.moveToFirst();
        try {
            for (int i = 0; i < a; i++) {
                map = new HashMap<>();
                map.put("task",cursor.getString(taskSQL.NUM_COLUMN_NAME_TASK));
                map.put("info_task",String.format("Получишь опыта: %d XP\nПолучишь денег: %d $\nЗатратишь сил: %d\nЗатратишь времени: %d ч.\nНужно знать: %s", cursor.getInt(taskSQL.NUM_COLUMN_XP),cursor.getInt(taskSQL.NUM_COLUMN_MONEY), cursor.getInt(taskSQL.NUM_COLUMN_STAMINA), cursor.getInt(taskSQL.NUM_COLUNM_TIME), cursor.getString(taskSQL.NUM_COLUMN_SKILL)));
                taskArray.add(map);
                cursor.moveToNext();
            }
            adapter = new SimpleAdapter(this,taskArray, R.layout.maket,
                    new String[]{"task","info_task"},
                    new int[]{android.R.id.text1,android.R.id.text2});
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ERBD",e.getMessage());
        }
        cursor.moveToFirst();
        Cursor finalCursor = cursor;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finalCursor.move(position);
                if(MainActivity.getPlayer().getStamina() > finalCursor.getInt(taskSQL.NUM_COLUMN_STAMINA) && TaskObject.isEnd()){
                    String temp = finalCursor.getString(taskSQL.NUM_COLUMN_SKILL);
                    String[] skill_array = temp.split("\n");
                    TaskObject taskObject = new TaskObject(finalCursor.getString(taskSQL.NUM_COLUMN_NAME_TASK),
                            finalCursor.getInt(taskSQL.NUM_COLUMN_XP),
                            finalCursor.getInt(taskSQL.NUM_COLUNM_TIME) + Timer.getHour(),finalCursor.getInt(taskSQL.NUM_COLUMN_MONEY),
                            finalCursor.getInt(taskSQL.NUM_COLUNM_TIME),skill_array);
                    if(checkSlils(taskObject)){
                        MainActivity.getPlayer().setStamina(MainActivity.getPlayer().getStamina() - finalCursor.getInt(taskSQL.NUM_COLUMN_STAMINA));
                        Log.d("STAMINA",String.valueOf(MainActivity.getPlayer().getStamina()));
                        Intent i = new Intent();
                        i.putExtra("task",taskObject);
                        setResult(1,i);
                        finish();
                    }else{
                        new Toast(getApplicationContext()).makeText(getApplicationContext(),"У вас недостаточно навыков!",Toast.LENGTH_SHORT).show();
                    }
                }else if(!TaskObject.isEnd()){
                    new Toast(getApplicationContext()).makeText(getApplicationContext(),"У вас уже выполняется задание!",Toast.LENGTH_SHORT).show();
                    setResult(-2);
                    finish();
                }
                else{
                    new Toast(getApplicationContext()).makeText(getApplicationContext(),"Вы слишком устали!",Toast.LENGTH_SHORT).show();
                    setResult(-1);
                    finish();
                }
                finalCursor.moveToFirst();
            }
        });


        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopService(musik);
                setResult(0);
                finish();
            }
        });
    }

    public boolean checkSlils(TaskObject taskObject){
        HashMap<String, Boolean> skils_person = MainActivity.getPlayer().getSkils();
        for(int i = 0; i < taskObject.getSkills().size(); i++){
            if(!skils_person.get(taskObject.getSkills().keySet().toArray()[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!MediaPlayerService.isPlay) {
            startService(musik);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(musik);
    }
}