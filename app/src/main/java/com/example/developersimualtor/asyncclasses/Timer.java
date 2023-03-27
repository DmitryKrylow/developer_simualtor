package com.example.developersimualtor.asyncclasses;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.developersimualtor.MainActivity;
import com.example.developersimualtor.fortask.TaskObject;

import java.io.Serializable;

public class Timer extends AsyncTask<Integer, Integer, String> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static boolean isStart = false;
    private TextView txt;
    private static Integer min,hour,DD,MM,YYYY;
    private static Integer[] intVal = null;
    private static TaskObject taskObject;
    private static boolean isEndTask = true;
    private static boolean startTender = false;
    private static int dayTender;
    static Context context;


    public Timer(TextView text,Integer... txt) {
        this.txt = text;
        if(intVal == null) {
            intVal = new Integer[6];
            min = txt[0];
            hour = txt[1];
            DD = txt[2];
            MM = txt[3];
            YYYY = txt[4];
            for (int i = 0; i < txt.length; i++) {
                intVal[i] = txt[i];
            }
            intVal[5] = 1;
        }else{
            min = intVal[0];
            hour = intVal[1];
            DD = intVal[2];
            MM = intVal[3];
            YYYY = intVal[4];
            dayTender = intVal[5];
        }
    }

    @Override
    protected String doInBackground(Integer... txt) {
        isStart = true;
        //таймер
            for (; ; ) {
                if (!isCancelled()) {
                    try {
                        if (min >= 59) {
                            hour++;
                            min = min - 59;
                            if (hour >= 24) {
                                DD++;
                                hour = hour - 24;
                                if (DD == 30 && (YYYY % 100 == 0 && YYYY % 400 == 0 && YYYY % 4 == 0) && MM == 2) {
                                    MM++;
                                    DD = 1;
                                } else if (DD == 29 && !(YYYY % 100 == 0 && YYYY % 400 == 0 && YYYY % 4 == 0) && MM == 2) {
                                    MM++;
                                    DD = 1;
                                } else if (DD == 31 && (MM == 4 || MM == 6 || MM == 9 || MM == 11)) {
                                    MM++;
                                    DD = 1;
                                } else if (DD == 32) {
                                    MM++;
                                    DD = 1;
                                }
                                if (MM == 13) {
                                    MM = 1;
                                    YYYY++;
                                }
                            }
                        } else {
                            min++;
                        }
                        publishProgress(min, hour, DD, MM, YYYY);
                        //задержка
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("timer", e.getMessage());
                    }
                }
            }
        }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        String mm = String.valueOf(values[0]);
        String hh = String.valueOf(values[1]);
        String DD = String.valueOf(values[2]);
        String MM = String.valueOf(values[3]);
        String YYYY = String.valueOf(values[4]);

        hh = hh.length() == 2 ? hh : "0"+hh;
        mm = mm.length() == 2 ? mm : "0"+mm;
        DD = DD.length() == 2 ? DD : "0"+DD;
        MM = MM.length() == 2 ? MM : "0"+MM;

        String str = String.format("%s:%s\n%s.%s.%s", hh, mm,DD,MM,YYYY);
        //Вывод строки таймера
        txt.setText(str);
        //Каждый день снимается энергия
        if(!Integer.valueOf(DD).equals(intVal[2])){
            MainActivity.getPlayer().work();
            MainActivity.getPlayer().setDay_work(MainActivity.getPlayer().getDayWork() + 1);
            dayTender++;
        }
        //голод снимается каждый час
        if(!Integer.valueOf(hh).equals(intVal[1]) ){
            if(MainActivity.getPlayer().getHunger() > 0){
                MainActivity.getPlayer().hunger();
            }
        }
        //проверка выполнения задания
        if(taskObject != null){
           TaskObject.setEnd(false);
            if(!Integer.valueOf(hh).equals(intVal[1])) {
                if (taskObject.getTimeComplite() > 1) {
                    taskObject.setTimeComplite(taskObject.getTimeComplite() - 1);
                    taskObject.updateTV();
                }else {
                    TaskObject.setEnd(true);
                    taskObject = null;
                    MainActivity.taskComplited();
                }
            }
        }
        //TENDER каждую неделю
        if(dayTender == 7){
            dayTender = 1;
            startTender = true;
        }
        //запоминание времени для сохранения
        intVal[0] = min;
        intVal[1] = hour;
        intVal[2] = this.DD;
        intVal[3] = this.MM;
        intVal[4] = this.YYYY;
        intVal[5] = dayTender;
    }


    public static Integer getHour() {
        return hour;
    }

    public static void setHour(Integer hour_new) {
        hour = hour_new;
        if(hour >= 24){
            hour = hour - 24;
            DD++;
        }
    }
    public static boolean getStart(){
        return isStart;
    }

    public static Integer[] getIntVal() {
        return intVal;
    }

    public static void setIntVal(Integer[] intVal) {
        Timer.intVal = intVal;
    }

    @Override
    protected void onPostExecute(String time) {
        super.onPostExecute(time);
    }

    public static void setTaskObject(TaskObject taskObject) {
        Timer.taskObject = taskObject;
    }

    public static TaskObject getTaskObject() {
        return taskObject;
    }

    public static boolean isEndTask() {
        return isEndTask;
    }

    public static void setContext(Context context) {
        Timer.context = context;
    }

    public static boolean isStartTender() {
        return startTender;
    }

    public static void setStartTender(boolean startTender) {
        Timer.startTender = startTender;
    }

    public static int getDayTender() {
        return dayTender;
    }

    public static void setDayTender(int dayTender) {
        Timer.dayTender = dayTender;
    }
}
