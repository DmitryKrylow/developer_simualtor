package com.example.developersimualtor;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.developersimualtor.asyncclasses.Timer;
import com.example.developersimualtor.forCompany.Company;
import com.example.developersimualtor.forCompany.CompanySQL;
import com.example.developersimualtor.fortask.Task;
import com.example.developersimualtor.fortask.TaskObject;
import com.example.developersimualtor.gameClass.SaveGame;
import com.example.developersimualtor.person.Person;
import com.example.developersimualtor.service.MediaPlayerService;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class MainActivity extends FragmentActivity {

    //TODO АНИМАЦИИ TextSwitcher Fade_in fade_out


    private static Person player;
    private static ProgressBar progressLvl;
    private ProgressBar stamina;
    private ProgressBar hunger;

    private TextView timer;
    private Timer timerClass;

    private TextView TV_InfoPlayer;
    private static TextView money;
    private TextView level_TV;
    private TextView hunger_TV;
    private TextView stamina_TV;
    private static TextView infoTask_TV;
    private Button task,relax,store,about;
    private Intent taskIntent,intentService, intentPassivMoney, leaderboard_intent;
    private Button work, leaderBoards;

    static TaskObject taskObject;

    Handler handler = new Handler();

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
        setContentView(R.layout.activity_main);
        intentService = new Intent(this, com.example.developersimualtor.service.MediaPlayerService.class);
        intentPassivMoney = new Intent(this,com.example.developersimualtor.service.PassivMoneyService.class);

        intentService.putExtra("musikID",R.raw.background);
        startService(intentPassivMoney);

        //Создание намерения
        CompanySQL companySQL = new CompanySQL(this);
        SQLiteDatabase sqLiteDatabase = companySQL.getWritableDatabase();
        cursor = sqLiteDatabase.query(CompanySQL.getTableName(),null,null,null,null,null,null);
        taskIntent = new Intent(this, Task.class);

        //Инициализация переменных и запуск таймера
        timer = (TextView) findViewById(R.id.timer);
        timerClass = new Timer(timer, 0, 7, 1, 5, 2021);
        timerClass.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        taskObject = (TaskObject) getIntent().getSerializableExtra("task");
        infoTask_TV = findViewById(R.id.infoTask_TV);
        hunger_TV = findViewById(R.id.hunger_TXT);
        stamina_TV = findViewById(R.id.stamina_TXT);
        relax = findViewById(R.id.relax);
        hunger = findViewById(R.id.hunger);
        stamina = findViewById(R.id.stamina);
        level_TV = findViewById(R.id.infoLevel);
        progressLvl = findViewById(R.id.Level);
        work = findViewById(R.id.work);
        task = findViewById(R.id.openTask);
        money = findViewById(R.id.moneyText);
        store = findViewById(R.id.openStore);
        about  = findViewById(R.id.about_person);
        player = (Person) getIntent().getSerializableExtra("PERSON");
        TV_InfoPlayer = findViewById(R.id.infoPlayer);
        Timer.setContext(this);

        leaderBoards = findViewById(R.id.leaderBoards);
        leaderboard_intent = new Intent(this, com.example.developersimualtor.gameClass.TableLeader.class);

        //НАСТРОЙКА АНИМАЦИИ
        Animation anim_work = AnimationUtils.loadAnimation(this, R.anim.anim_click);
        Animation anim_button = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_scale);


        //настройки TV
        if(taskObject == null) {
            infoTask_TV.setText("Задание не выбрано");
        }else {
            infoTask_TV.setText(taskObject.toString());
            timerClass.setTaskObject(taskObject);
        }

        money.setText(String.valueOf(player.getMoney()));
        work.setClipToOutline(true);
        level_TV.setText(String.format("Ваш уровень: %d",player.getLvl()));
        progressLvl.setMax(player.getLvl() * 100 + 1);
        progressLvl.setProgress(player.getXp());



        //КНОПКА РАБОТЫ
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.getHunger() > 0) {
                    v.startAnimation(anim_work);
                    player.getMoneyClick(player.getCompany().getMoney());
                    money.setText(String.valueOf(player.getMoney()));
                    player.setXp(player.getXp() + player.getCompany().getFatigue());
                    if (player.upLvl(progressLvl.getMax())) {
                        player.setXp(player.getXp() - progressLvl.getMax());
                        progressLvl.setMax(player.getLvl() * 100 + 1);
                    }
                    progressLvl.setProgress(player.getXp());
                    level_TV.setText(String.format("Ваш уровень: %d", player.getLvl()));
                }else{
                    new Toast(getApplicationContext()).makeText(getApplicationContext(),
                            "Вам нужно срочно поесть! Зайдите в магазин и купите еды!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //КНОПКА ЗАДАЧИ
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    stopService(intentService);
                    startActivityForResult(taskIntent, 1);
            }
        });

        //Кнопка отдых
        relax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(anim_button);
                if(player.getStamina() == 100){
                    new Toast(getApplicationContext()).makeText(getApplicationContext(),"Вы полны сил!",Toast.LENGTH_SHORT).show();
                }else if(!TaskObject.isEnd()){
                    new Toast(getApplicationContext()).makeText(getApplicationContext(),"Вы не можете отдыхать во время выполнения задания!",Toast.LENGTH_SHORT).show();
                }else{
                player.relax();
                }
            }
        });

        //МАГАЗИН
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStore = new Intent(getApplicationContext(), com.example.developersimualtor.forstore.Store.class);

                startActivityForResult(intentStore,1);
            }
        });


        //ЛИДЕРЫ
        leaderBoards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(leaderboard_intent);
            }
        });
        //КНОПКА ОБ ИГРОКЕ
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), com.example.developersimualtor.person.About_person.class);
                startActivity(i);
            }
        });
        
        //ИНФА О ПОКАЗАТЕЛЯХ ИГРОКА
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;){
                    if(player.getLvl() == 5 && player.isStartSujete()) {
                        player.setStartSujete(false);
                        Intent intent = new Intent(MainActivity.this, com.example.developersimualtor.sujet.Hacker.class);
                        startActivity(intent);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(player.getStamina() <= 0){
                                player.relax();
                            }
                            money.setText(String.valueOf(MainActivity.getPlayer().getMoney()));
                            hunger.setProgress(player.getHunger());
                            stamina.setProgress(player.getStamina());
                            stamina_TV.setText(String.valueOf(player.getStamina()));
                            hunger_TV.setText(String.valueOf(player.getHunger()));
                            if(taskObject == null) {
                                infoTask_TV.setText("Задание не выбрано");
                            }else {
                                infoTask_TV.setText(taskObject.toString());
                            }
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Intent intentForTender = new Intent(this, com.example.developersimualtor.gameClass.Tender.class);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(Timer.isStartTender()) {
                        try{
                            int a = (int) (Math.random() * 10);
                            cursor.moveToPosition(a);
                            Company in = new Company(cursor.getString(CompanySQL.getNumColumnNameCompany()), cursor.getInt(CompanySQL.getNumColumnMoney()), cursor.getInt(CompanySQL.getNumColumnPopular()), cursor.getInt(CompanySQL.getNumColumnFatigue()));
                            intentForTender.putExtra("company", in);
                            intentForTender.putExtra("out", getPlayer().getCompany());
                            startActivity(intentForTender);
                            Timer.setStartTender(false);
                        }catch (Exception e){
                            Log.d("tender",e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(timerClass.isCancelled()){
            timerClass = new Timer(timer,0);
            timerClass.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            if(taskObject != null){
                timerClass.setTaskObject(taskObject);
            }
        }
        if (!MediaPlayerService.isPlay) {
            startService(intentService);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //СОХРАНЕНИЕ
        Integer[] a = Timer.getIntVal();
        SaveGame saveGame = new SaveGame(player,player.getCompany(),taskObject,a);
        stopService(intentService);
        timerClass.cancel(true);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.getFilesDir().getPath() + "save.out");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(saveGame);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 1){
            taskObject = (TaskObject) data.getSerializableExtra("task");
            infoTask_TV.setText(taskObject.toString());
            Timer.setTaskObject(taskObject);

        }else if(resultCode == 2){
            hunger_TV.setText(String.valueOf(MainActivity.getPlayer().getHunger()));
            hunger.setProgress(MainActivity.getPlayer().getHunger());
            money.setText(String.valueOf(MainActivity.getPlayer().getMoney()));
        }
    }

    public static Person getPlayer() {
        return player;
    }
    public static void taskComplited(){
        player.setMoney(player.getMoney() + taskObject.getMoney());
        player.setXp(player.getXp() + taskObject.getXp());
        money.setText(String.valueOf(player.getMoney()));
        progressLvl.setProgress(player.getXp());
        taskObject = null;
        infoTask_TV.setText("Задание не выбрано");
        player.setCountTask(player.getCountTask() + 1);
    }
    public static void setPlayer(Person player) {
        MainActivity.player = player;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intentService);
        stopService(intentPassivMoney);
    }
}