package com.example.developersimualtor.sujet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.developersimualtor.R;
import com.example.developersimualtor.delayPrinter.DelayedPrinter;
import com.example.developersimualtor.service.MediaPlayerService;

import java.util.HashMap;
import java.util.Map;

public class StartSujete extends FragmentActivity {
    String[] words = {"Привет! Меня зовут Алиса! И я помогу освовиться тебе в этой игре!",
            "Вы - Junior разработчик, который всеми пытается попасть в крупную компанию.",
            "Для достижения этой цели, вы должны работать, нажимая на кнопку \"Работать\", за это вы будете получать деньги и очки опыта.",
            "С повышением уровня вы будете получать пассивный доход.",
            "Вам также нужно сделить за показателями усталости и голода!. Усталость восполняется кнопкой \"отдых\".",
            "Чтобы восставноить свою сытость нужно нажать на кнопку \"магазин\" и купить там еду.",
            "Вы также можете выполнять задания, чтобы получать дополнительный заработок и опыт, но имейте ввиду, что задания требуют вашей энергии!",
            "С начала игры вы не сможете выполнить все задания, вам нужно изучить опеределенные навыки, которые приобретаются в магазине",
            "Каждую неделю проводится конкурс, на котором у вас есть шанс попасть в другую компаю. Работайте! И ваш шанс на победу будет повышаться!",
            "Работайте лучше и вы окажетесь на вершине списка лидеров!",
            "На этом у меня все! Увидимся позже, удачи!"

    };

    HashMap<Integer, String> word_map = new HashMap<>();
    TextView tv;
    Button next;
    int count = 0;
    DelayedPrinter.Word word;
    Intent musik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_sujete);
        tv = findViewById(R.id.textSujete);
        next = findViewById(R.id.nextWord);
        musik = new Intent(this, com.example.developersimualtor.service.MediaPlayerService.class);
        musik.putExtra("musikID", R.raw.forall);

        for(int i =0; i < words.length; i++){
            word_map.put(i,words[i]);
        }

        word = new DelayedPrinter.Word(50, word_map.get(count));
        word.setOffset(50);
        DelayedPrinter.printText(word, tv);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(word != null && word.getCurrentCharacterIndex() == 0) {
                    count++;
                    try{
                        if(count == words.length){
                            word = null;
                            finish();
                        }else {
                            word = new DelayedPrinter.Word(50, word_map.get(count));
                            word.setOffset(50);
                            DelayedPrinter.printText(word, tv);
                        }
                    }catch (Exception e){
                        e.getStackTrace();
                        word = null;
                        finish();
                    }
                }else if(word == null){
                    finish();
                }else{
                    word.setStop(true);
                    word.setCurrentCharacterIndex(0);
                    tv.setText(word_map.get(count));
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