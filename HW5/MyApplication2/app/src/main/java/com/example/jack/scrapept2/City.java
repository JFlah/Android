package com.example.jack.scrapept2;

/**
 * Created by Jack on 4/9/2016.
 */
public class City {
    private String name;
    private String oldPop, newPop, popChange;
    private long id;

    public City(String name, String oldPop, String newPop, String popChange) {
        this.name = name;
        this.oldPop = oldPop;
        this.newPop = newPop;
        this.popChange = popChange;
    }

    public City(long id, String name, String oldPop, String newPop, String popChange) {
        this.name = name;
        this.oldPop = oldPop;
        this.newPop = newPop;
        this.popChange = popChange;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getOldPop() {
        return oldPop;
    }

    public String getNewPop() {
        return newPop;
    }

    public String getPopChange() {
        return popChange;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOldPop(String oldPop) {
        this.oldPop = oldPop;
    }

    public void setNewPop(String newPop) {
        this.newPop = newPop;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}