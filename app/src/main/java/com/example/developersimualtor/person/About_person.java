package com.example.developersimualtor.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.developersimualtor.MainActivity;
import com.example.developersimualtor.R;

public class About_person extends FragmentActivity {
    TextView info_PLAYER;
    Button back, howPlay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_player);
        info_PLAYER = findViewById(R.id.about_person);
        info_PLAYER.setText(MainActivity.getPlayer().toString());
        back = findViewById(R.id.back);
        howPlay = findViewById(R.id.howPlay);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        howPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(About_person.this, com.example.developersimualtor.gameClass.HowToPlay.class);
                startActivity(intent);
            }
        });
    }
}
