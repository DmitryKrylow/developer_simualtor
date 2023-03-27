package com.example.developersimualtor.sujet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.developersimualtor.R;
import com.example.developersimualtor.delayPrinter.DelayedPrinter;
import com.example.developersimualtor.service.MediaPlayerService;

import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Hacker extends FragmentActivity {

    Intent musik;

    String[] sujet = {
            "Начало беседы с ........",
            "ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА",
            "Ты действительно думал, что станешь лучшим так просто?",
            "Ты глубоко ОШИБАЛСЯ. ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА-ХА",
            "Кто я? Это интересный вопрос, тебе стоит над этим подумать. Я скажу лишь одно. Я тот - кто знает о тебе даже то, что ты и сам не знаешь",
            "Мне? От тебя? ХА-ХА-ХА насмешил. Если бы мне от тебя что-то нужно было, я бы это уже давно получил и без тебя",
            "Я хочу, чтобы ты, наконец, стал тем, кем ты мечтаешь стать очень давно. И я могу помочь тебе в этом",
            "Пользователь вышел из чата........."
    };
    String[] question_sujet = {
            "Что? Кто ты?",
            "Что тебе от меня нужно?",
            "Но как ты мне в этом поможешь?",
    };
    HashMap<Integer, String> word_map = new HashMap<>();
    TextView tv;
    Button next;
    ImageView hacker;
    int count_hacker = 0;
    int count_player = 0;
    DelayedPrinter.Word word;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacker);
        tv = findViewById(R.id.viewSujet);
        next = findViewById(R.id.nextWord);
        hacker = findViewById(R.id.hacker);
        musik = new Intent(this, com.example.developersimualtor.service.MediaPlayerService.class);
        musik.putExtra("musikID",R.raw.hacker_musik);
        word = null;


        for(int i = 0; i < sujet.length; i++){
            word_map.put(i,sujet[i]);
        }

            Animation anim_hacker = AnimationUtils.loadAnimation(this, R.anim.anim_hacker);

            hacker.startAnimation(anim_hacker);

            DelayedPrinter.Word word_temp = new DelayedPrinter.Word(50, word_map.get(count_hacker));
            word_temp.setOffset(50);

            word = new DelayedPrinter.Word(50, word_map.get(count_hacker));
            word.setOffset(50);
            DelayedPrinter.printText(word, tv);

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(word.getCurrentCharacterIndex() == 0) {
                        count_hacker++;
                        if (count_hacker == 3 || count_hacker == 4 || count_hacker == 6) {
                            next.setText(question_sujet[count_player]);
                            count_player++;
                        }else{

                            next.setText("ПРОДОЛЖИТЬ");

                        }
                        try{
                            if(count_hacker == sujet.length){
                                finish();
                            }else {
                                word = new DelayedPrinter.Word(50, word_map.get(count_hacker));
                                word.setOffset(50);
                                DelayedPrinter.printText(word, tv);
                            }
                        }catch (Exception e){
                            e.getStackTrace();
                            finish();
                        }
                    }else{
                        word.setStop(true);
                        word.setCurrentCharacterIndex(0);
                        tv.setText(word_map.get(count_hacker));
                    }
                }
            });
    }
    @Override
    protected void onResume() {
        if (!MediaPlayerService.isPlay) {
            startService(musik);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        stopService(musik);
        super.onPause();
    }
}