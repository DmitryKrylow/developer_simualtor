package com.example.developersimualtor.gameClass;

import com.example.developersimualtor.forCompany.Company;
import com.example.developersimualtor.fortask.TaskObject;
import com.example.developersimualtor.person.Person;

import java.io.Serializable;

public class SaveGame implements Serializable {
    private static final long serialVersionUID = 1L;

    private Person person;
    private Company company;
    private Integer[] timer;
    private TaskObject taskObject;


    public SaveGame(Person person, Company company,TaskObject taskObject, Integer... val){
        timer = val;
        this.company = company;
        this.person = person;
        this.taskObject = taskObject;
    }

    public Company getCompany() {
        return company;
    }


    public Person getPerson() {
        return person;
    }

    public Integer[] getTimer() {
        return timer;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setTimer(Integer[] timer) {
        this.timer = timer;
    }

    public TaskObject getTaskObject() {
        return taskObject;
    }
}
