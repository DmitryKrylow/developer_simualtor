package com.example.developersimualtor;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.developersimualtor.asyncclasses.Timer;
import com.example.developersimualtor.forCompany.Company;
import com.example.developersimualtor.forCompany.CompanySQL;
import com.example.developersimualtor.fortask.TaskObject;
import com.example.developersimualtor.gameClass.SaveGame;
import com.example.developersimualtor.person.Person;
import com.example.developersimualtor.service.MediaPlayerService;
import com.example.developersimualtor.sujet.StartSujete;
import com.google.android.gms.common.sqlite.CursorWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class CreatePerson extends FragmentActivity implements View.OnClickListener, Serializable {
    private static final long serialVersionUID = 1L;

    private SharedPreferences pref = null;
    private ImageView start_company1;
    private ImageView start_company2;
    private ImageView start_company3;
    private Button start_game;
    private EditText editText;
    private Company company = null;
    private Person person;
    private TextView WRONGNAME,viewInfo;
    private Intent i,intentForMusik;
    private SaveGame saveGame;
    private Cursor cursor = new Cursor() {
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
    private CompanySQL companySQL;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_person);

        pref = getSharedPreferences("com.example.developersimualtor", MODE_PRIVATE);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        if(pref.getBoolean("first",true)){
            Intent firstTime = new Intent(this, StartSujete.class);
            startActivity(firstTime);
            pref.edit().putBoolean("first",false).apply();
        }
        companySQL = new CompanySQL(this);
        db = companySQL.getWritableDatabase();
        fillCompany();

        intentForMusik = new Intent(this, com.example.developersimualtor.service.MediaPlayerService.class);
        intentForMusik.putExtra("musikID",R.raw.forall);


        try {
            FileInputStream fileInputStream = new FileInputStream(new File(this.getFilesDir().getPath() + "save.out"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            saveGame = (SaveGame) objectInputStream.readObject();

        } catch (Exception e) {e.printStackTrace(); }

        if (saveGame != null) {
            person = saveGame.getPerson();
            TaskObject taskObject = saveGame.getTaskObject();
            i = new Intent(this, MainActivity.class);
            i.putExtra("PERSON", person);
            Integer[] arr = saveGame.getTimer();
            i.putExtra("intval",arr);
            i.putExtra("task", taskObject);
            Timer.setIntVal(arr);
            stopService(intentForMusik);
            startActivity(i);
            finish();
        } else {
            start_company1 = findViewById(R.id.startcompany1);
            start_company2 = findViewById(R.id.startcompany2);
            start_company3 = findViewById(R.id.startcompany3);
            start_company1.setClipToOutline(true);
            start_company2.setClipToOutline(true);
            start_company3.setClipToOutline(true);
            start_game = findViewById(R.id.startGame);
            viewInfo = findViewById(R.id.viewInfo);
            editText = findViewById(R.id.name);
            WRONGNAME = findViewById(R.id.WRONGMANE);

            i = new Intent(this, MainActivity.class);

            if (MainActivity.getPlayer() == null) {
                start_company1.setOnClickListener(this);
                start_company2.setOnClickListener(this);
                start_company3.setOnClickListener(this);
                start_game.setOnClickListener(this);
            }

        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startcompany1:
                cursor.moveToPosition(0);
                    company = new Company(cursor.getString(CompanySQL.getNumColumnNameCompany()),
                            cursor.getInt(CompanySQL.getNumColumnMoney()),
                            cursor.getInt(CompanySQL.getNumColumnPopular()),
                            cursor.getInt(CompanySQL.getNumColumnFatigue()));
                    viewInfo.setText(company.toString());
                    break;
            case R.id.startcompany2:
                cursor.moveToPosition(1);
                company = new Company(cursor.getString(CompanySQL.getNumColumnNameCompany()),
                        cursor.getInt(CompanySQL.getNumColumnMoney()),
                        cursor.getInt(CompanySQL.getNumColumnPopular()),
                        cursor.getInt(CompanySQL.getNumColumnFatigue()));
                viewInfo.setText(company.toString());
                viewInfo.setText(company.toString());
                break;
            case R.id.startcompany3:
                cursor.moveToPosition(2);
                company = new Company(cursor.getString(CompanySQL.getNumColumnNameCompany()),
                        cursor.getInt(CompanySQL.getNumColumnMoney()),
                        cursor.getInt(CompanySQL.getNumColumnPopular()),
                        cursor.getInt(CompanySQL.getNumColumnFatigue()));
                viewInfo.setText(company.toString());
                viewInfo.setText(company.toString());
                break;
            case R.id.startGame:
                if(editText.getText().toString().length() < 2 || company == null){
                    WRONGNAME.setTextColor(Color.RED);
                    WRONGNAME.setText("Имя вашего персонажа меньше 2 символов или вы не выбрали вашу компанию!");
                    break;
                }else {
                    person = new Person(editText.getText().toString(), company);
                    i.putExtra("PERSON", person);
                    stopService(intentForMusik);
                    startActivity(i);
                    finish();
                    break;
                }
        }
    }

    private void fillCompany(){
        ContentValues cv = new ContentValues();

        try {

            cursor = db.rawQuery("SELECT COUNT(*) FROM " + CompanySQL.getTableName() + "",null);

            if(cursor != null){
                cursor.moveToFirst();
                if(cursor.getInt(0) == 0){
                    for(int i = 0; i < 10; i++){
                        cv.put(CompanySQL.getNameCompany(), companySQL.getCompany_name()[i]);
                        cv.put(CompanySQL.getMONEY(), i * 3 + 2);
                        cv.put(CompanySQL.getPOPULAR(), i * 10 + 5);
                        cv.put(CompanySQL.getFATIGUE(), i * 5 + 10);
                        db.insert(CompanySQL.getTableName(),null,cv);
                        cv.clear();
                    }
                }
            }
            cursor = db.query(CompanySQL.getTableName(),null,null,null,null,null,null);
        }catch (Exception e){
            Log.d("company", e.getMessage());
        }
    }



    public static SQLiteDatabase getDb() {
        return db;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!MediaPlayerService.isPlay) {
            startService(intentForMusik);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(intentForMusik);
    }
}