package com.example.developersimualtor.gameClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.developersimualtor.MainActivity;
import com.example.developersimualtor.R;
import com.example.developersimualtor.forCompany.Company;


public class Tender extends FragmentActivity {

    TextView tender;
    Button close,yes,no;
    Company in, out;
    double myproc;
    double myproc_2;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tender_activity);

        tender = findViewById(R.id.tender);
        close = findViewById(R.id.close);
        yes = findViewById(R.id.buttonYES);
        no = findViewById(R.id.buttonNO);

        in = (Company) getIntent().getSerializableExtra("company");
        out = (Company) getIntent().getSerializableExtra("out");
        myproc = in.getPopular() / 100.0;
        myproc_2 = out.getPopular() / myproc;
        myproc_2 = myproc_2 > 100 ? 100 : myproc_2;
        close.setVisibility(View.INVISIBLE);
        yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getPlayer().setCompany(in);
                MainActivity.getPlayer().setCountTask(0);//Если перешли обнуляем наш "бонус"
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



            new Thread() {
                @Override
                public void run() {
                    super.run();
                    for (int i = 0; i < 500; i++) {
                        if (i != 499) {
                            tender.setText(String.valueOf(Math.random() * 100));
                        }else {
                            if (Math.round(Math.random() * 100) <= myproc_2 + MainActivity.getPlayer().getXp() / 1000 ) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tender.setText(String.format("Поздравляю!\nВы выиграли в конкурсе!\nВаш шанс: %f\nТеперь ваша возможная компания:\n%s\n Вы хотите перейти?", myproc_2, in.toString()));

                                        yes.setVisibility(View.VISIBLE);
                                        no.setVisibility(View.VISIBLE);
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tender.setText(String.format("Увы!\n Вы проиграли в конкурсе!\nВаш шанс: %f", myproc_2));
                                        close.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
    }
}
