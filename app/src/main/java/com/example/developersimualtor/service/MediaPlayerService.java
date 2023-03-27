package com.example.developersimualtor.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MediaPlayerService extends Service{

    private MediaPlayer mediaPlayer;
    private final int DEFAULT_ID_MUSIK = 1800000;
    public static boolean isPlay = false;

    public MediaPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mediaPlayer = MediaPlayer.create(this, intent.getIntExtra("musikID",DEFAULT_ID_MUSIK));
        mediaPlayer.setLooping(true);
        if (!isPlay) {
            mediaPlayer.selectTrack(intent.getIntExtra("musikID",DEFAULT_ID_MUSIK));
            mediaPlayer.start();
        }

        isPlay = true;
    }


    @Override
    public void onDestroy() {
        mediaPlayer.pause();
        isPlay = false;
        super.onDestroy();
    }



}