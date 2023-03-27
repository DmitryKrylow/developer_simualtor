package com.example.developersimualtor.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.developersimualtor.MainActivity;

import java.util.concurrent.TimeUnit;

public class PassivMoneyService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        getPassivMoney();

    }
    public void getPassivMoney(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        MainActivity.getPlayer().setMoney(MainActivity.getPlayer().getMoney() + MainActivity.getPlayer().getLvl() / 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
