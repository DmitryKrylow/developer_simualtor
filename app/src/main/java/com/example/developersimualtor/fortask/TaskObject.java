package com.example.developersimualtor.fortask;


import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;

public class TaskObject implements Serializable {
    private String name;
    private int xp;
    protected int hour;
    protected int day;
    private int hourToTV;
    private int money;
    private static boolean end = true;
    private int timeComplite;
    private HashMap<String, Boolean> skills = new HashMap<>();

    public TaskObject(String name, int xp, int hour, int money,int timeComplite, String[] skill){
        this.name = name;
        this.xp = xp;
        this.money = money;
        this.hour = hour;
        day = timeComplite /  24;
        hourToTV = timeComplite - (day * 24);
        this.timeComplite  = timeComplite;
        for(int i = 1; i < skill.length; i++){
            skills.put(skill[i],true);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s\nПолучишь денег: %d\nПолучишь опыта: %d\nБудет выполнено через:\n%d д.%d ч.",name,money,xp,day,hourToTV);
    }
    public void updateTV(){
        day = timeComplite /  24;
        hourToTV = timeComplite - (day * 24);
    }

    public int getHour() {
        return hour;
    }

    public int getXp() {
        return xp;
    }

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public static boolean isEnd() {
        return end;
    }

    public static void setEnd(boolean end) {
        TaskObject.end = end;
    }

    public int getTimeComplite() {
        return timeComplite;
    }

    public void setTimeComplite(int timeComplite) {
        this.timeComplite = timeComplite;
    }

    public HashMap<String, Boolean> getSkills() {
        return skills;
    }
}
