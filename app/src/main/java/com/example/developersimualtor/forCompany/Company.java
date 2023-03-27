package com.example.developersimualtor.forCompany;

import java.io.Serializable;

public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int money;
    private int popular;
    private int fatigue;


    public Company(String name, int money, int popular, int fatigue){
        this.name = name;
        this.money = money;
        this.popular = popular;
        this.fatigue = fatigue;
    }

    @Override
    public String toString(){
        return String.format("Название: %s\n" +
                "Заработная плата: %d $\n" +
                "Требуется сил: %d\n" +
                "Популярность: %d",this.name,this.money,this.fatigue,this.popular);
    }

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public int getPopular() {
        return popular;
    }

    public int getFatigue() {
        return fatigue;
    }
    
    public int checkTime(){
        return 0;
    }
}
