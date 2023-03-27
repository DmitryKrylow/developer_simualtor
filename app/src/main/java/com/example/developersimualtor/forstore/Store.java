package com.example.developersimualtor.forstore;

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
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developersimualtor.MainActivity;
import com.example.developersimualtor.R;
import com.example.developersimualtor.service.MediaPlayerService;

import java.util.ArrayList;
import java.util.HashMap;

public class Store extends ListActivity {

    private ArrayList<HashMap<String, String>> storeArray = new ArrayList<>();
    private HashMap<String, String> map;
    private SimpleAdapter adapter;
    ListView listView;
    Button button_back;
    String[] productName = {"Печенье","Пирожок","Булочка с маком", "Картошка фри", "Бургер", "Ланч", "Борщ в столовой", "Бизнес-ланч", "Комплексный обед"};
    String[] skill = {"Blueprint","C++","C#","JAVA","SQLite","Spring"};

    ProgressBar progressHunger;
    TextView hunger_TV,money_TV;
    Intent musik;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        //ИНИЦИАЛИЗАЦИЯ ПЕРЕМЕННЫХ
        StoreSQL storeSQL = new StoreSQL(this);
        SQLiteDatabase sqLiteDatabase = storeSQL.getWritableDatabase();
        progressHunger = findViewById(R.id.hunger);
        hunger_TV = findViewById(R.id.hunger_TXT);
        money_TV = findViewById(R.id.moneyText);
        progressHunger.setProgress(MainActivity.getPlayer().getHunger());
        hunger_TV.setText(String.valueOf(MainActivity.getPlayer().getHunger()));
        money_TV.setText(String.valueOf(MainActivity.getPlayer().getMoney()));
        listView = findViewById(android.R.id.list);

        musik = new Intent(this, com.example.developersimualtor.service.MediaPlayerService.class);
        musik.putExtra("musikID",R.raw.forall);

        button_back = findViewById(R.id.buttonBack);
        //БД
        try{
            ContentValues cv = new ContentValues();

            cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM " + storeSQL.TABLE_NAME + "",null);

            if(cursor != null){
                cursor.moveToFirst();
                if(cursor.getInt(0) == 0){
                    for(int i = 0; i < 9; i++){
                        cv.put(storeSQL.HUNGER,i * 10 + 5);
                        cv.put(storeSQL.MONEY,i * 25 + 17);
                        cv.put(storeSQL.NAME,productName[i]);
                        sqLiteDatabase.insert(storeSQL.TABLE_NAME,null,cv);
                        cv.clear();
                    }
                    for(int i = 0; i < 6; i++){
                        cv.put(storeSQL.HUNGER,0);
                        cv.put(storeSQL.MONEY, (i + 2) * 1000);
                        cv.put(storeSQL.NAME, skill[i]);
                        sqLiteDatabase.insert(storeSQL.TABLE_NAME,null,cv);
                        cv.clear();
                    }
                }
            }
            cursor = sqLiteDatabase.query(storeSQL.TABLE_NAME,null,null,null,null,null,null);


        } catch (Exception e){
            Log.d("ERCURSOR",e.getMessage());
            e.printStackTrace();
        }

        //Настройка элементов ListView
        try {
            cursor.moveToLast();
            int a = cursor.getInt(storeSQL.NUM_COLUMN_ID);
            cursor.moveToFirst();
            for(int i = 0; i < 9; i++){
                map = new HashMap<>();
                map.put("product",cursor.getString(storeSQL.NUM_COLUMN_NAME));
                map.put("info_product",String.format("Восстановишь голода: %d \nСтоимость %d $.", cursor.getInt(storeSQL.NUM_COLUMN_HUNGER),cursor.getInt(storeSQL.NUM_COLUMN_MONEY)));
                storeArray.add(map);
                cursor.moveToNext();
            }
            for(int i = 0; i < 6; i++){
                map = new HashMap<>();
                map.put("product",cursor.getString(storeSQL.NUM_COLUMN_NAME));
                map.put("info_product",String.format("Откроешь задания, требующие: %s \nСтоимость %d $.", cursor.getString(storeSQL.NUM_COLUMN_NAME), cursor.getInt(storeSQL.NUM_COLUMN_MONEY)));
                storeArray.add(map);
                cursor.moveToNext();
            }
            adapter = new SimpleAdapter(this,storeArray, R.layout.maket,
                    new String[]{"product","info_product"},
                    new int[]{android.R.id.text1,android.R.id.text2});
            listView.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }

        cursor.moveToFirst();
        Cursor finalCursor = cursor;

        //НАСТРОЙКА ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finalCursor.move(position);
                if(MainActivity.getPlayer().getMoney() >= finalCursor.getInt(storeSQL.NUM_COLUMN_MONEY) && MainActivity.getPlayer().getHunger() < 100 && position < 9){
                    MainActivity.getPlayer().setMoney(MainActivity.getPlayer().getMoney() - finalCursor.getInt(storeSQL.NUM_COLUMN_MONEY));
                    MainActivity.getPlayer().eat(finalCursor.getInt(storeSQL.NUM_COLUMN_HUNGER));
                    hunger_TV.setText(String.valueOf(MainActivity.getPlayer().getHunger()));
                    progressHunger.setProgress(MainActivity.getPlayer().getHunger());
                    money_TV.setText(String.valueOf(MainActivity.getPlayer().getMoney()));
                }else if(position >= 9 &&
                        MainActivity.getPlayer().getMoney() >= finalCursor.getInt(storeSQL.NUM_COLUMN_MONEY) &&
                        !MainActivity.getPlayer().getSkils().get(finalCursor.getString(storeSQL.NUM_COLUMN_NAME)))
                {
                    MainActivity.getPlayer().setMoney(MainActivity.getPlayer().getMoney() - finalCursor.getInt(storeSQL.NUM_COLUMN_MONEY));
                    MainActivity.getPlayer().getSkils().put(finalCursor.getString(storeSQL.NUM_COLUMN_NAME),true);
                    money_TV.setText(String.valueOf(MainActivity.getPlayer().getMoney()));
                    MainActivity.getPlayer().addSkill(finalCursor.getString(storeSQL.NUM_COLUMN_NAME));
                }else if(position >= 9 && MainActivity.getPlayer().getSkils().get(finalCursor.getString(storeSQL.NUM_COLUMN_NAME))){
                    new Toast(getApplicationContext()).makeText(getApplicationContext(),"У вас уже есть данное умение", Toast.LENGTH_SHORT).show();
                } else if (MainActivity.getPlayer().getMoney() < finalCursor.getInt(storeSQL.NUM_COLUMN_MONEY)){
                    new Toast(getApplicationContext()).makeText(getApplicationContext(),"У вас недостаточно денег!",Toast.LENGTH_SHORT).show();
                }else if(position < 9 && MainActivity.getPlayer().getHunger() == 100) {
                    new Toast(getApplicationContext()).makeText(getApplicationContext(), "Вы не голодны!", Toast.LENGTH_SHORT).show();
                }
                finalCursor.moveToFirst();
            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                stopService(musik);
                setResult(2);
                finish();
            }
        });
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
