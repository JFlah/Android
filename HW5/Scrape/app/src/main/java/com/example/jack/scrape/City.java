package com.example.jack.scrape;

/**
 * Created by Jack on 4/9/2016.
 */
public class City {
    private String name;
    private String oldPop, newPop;

    public City(String name, String oldPop, String newPop) {
        this.name = name;
        this.oldPop = oldPop;
        this.newPop = newPop;
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

    @Override
    public String toString() {
        return this.getName();
    }
}
