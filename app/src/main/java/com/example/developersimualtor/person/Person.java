package com.example.developersimualtor.person;

import com.example.developersimualtor.asyncclasses.Timer;
import com.example.developersimualtor.forCompany.Company;

import java.io.Serializable;
import java.util.HashMap;

public class Person implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private Company company;
    private int stamina;
    private int countTask;
    private int money;
    private int lvl;
    private int xp;
    private int hunger;
    private int day_work = 0;
    private boolean startSujete = true;
    HashMap<String,Boolean> skils = new HashMap<>();
    private String skill = "Pascal";

    public Person(String name, Company company){
        this.name = name;
        this.company = company;
        stamina = 100;
        money = 1000;
        hunger = 100;
        this.xp = 0;
        this.lvl = 1;
        countTask = 0;
        skils.put("Pascal",true);
        skils.put("C++",false);
        skils.put("JAVA",false);
        skils.put("C#",false);
        skils.put("Blueprint",false);
        skils.put("SQLite",false);
        skils.put("Spring",false);
    }
    public void getMoneyClick(int moneyClick){
        this.money += moneyClick;
    }

    @Override
    public String toString(){
        return String.format("Имя персонажа: %s\nКомпания: %s\nЗаработная плата: %d$\nКоличество дней, отработанных в %s - %d\nУмения: %s",name,company.getName(),company.getMoney(),company.getName(),day_work, skill);
    }
    public void work(){
        if(stamina - company.getFatigue() < 0){
            stamina = 0;
        }else {
            stamina -= company.getFatigue();
        }
    }

    public void relax(){
        Timer.setHour(Timer.getHour() + 8);
        this.stamina += 100 - this.stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStamina() {
        return stamina;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getDayWork() {
        return countTask;
    }

    public int getHunger() {
        return hunger;
    }

    public int getLvl() {
        return lvl;
    }

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public boolean upLvl(int max){
        if(xp >= max){
            lvl++;
            startSujete = true;
            return true;
        }else {
            return false;
        }
    }

    public void hunger(){
        if(this.hunger - 10 < 0){
            this.hunger = 0;
        }else {
            this.hunger -= 10;
        }
    }

    public void eat(int resetHunger){
        if(hunger + resetHunger > 100){
            hunger = 100;
        }else{
            hunger += resetHunger;
        }
    }


    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getXp() {
        return xp;
    }

    public void setCountTask(int countTask) {
        this.countTask = countTask;
    }

    public int getCountTask() {
        return countTask;
    }

    public boolean isStartSujete() {
        return startSujete;
    }

    public void setStartSujete(boolean startSujete) {
        this.startSujete = startSujete;
    }

    public HashMap<String, Boolean> getSkils() {
        return skils;
    }

    public int getDay_work() {
        return day_work;
    }

    public void setDay_work(int day_work) {
        this.day_work = day_work;
    }

    public void addSkill(String skill) {
        this.skill += "\n" + skill;
    }
}
