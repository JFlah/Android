package com.example.jack.sqlpartb;


import java.io.Serializable;
import java.util.Date;


public class ToDo implements Serializable {
    String title = null;
    String desc = null;
    String time = null;
    long id;


    public ToDo(String title, String desc) {
        this.title = title;
        this.desc = desc;
        this.time = new Date().toString();
    }


    public ToDo(long id, String title, String desc, String date) {
        this.title = title;
        this.desc = desc;
        this.time = date;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }
    public String getDesc() {
        return desc;
    }
    public String getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toString(){
        String niceOutput = "Title: " + getTitle() + ". Description: " + getDesc() + ". DateCreated: " + getTime();
        return niceOutput;
    }
}



