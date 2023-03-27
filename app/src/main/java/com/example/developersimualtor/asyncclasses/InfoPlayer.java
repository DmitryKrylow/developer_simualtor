package com.example.developersimualtor.asyncclasses;

import android.os.AsyncTask;
import android.widget.TextView;

import com.example.developersimualtor.person.Person;

public class InfoPlayer extends AsyncTask<Void, Integer,Void> {


    public InfoPlayer(Person player,TextView infoPlayer,TextView money){

    }


    @Override
    protected Void doInBackground(Void... voids) {

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);


    }
}
