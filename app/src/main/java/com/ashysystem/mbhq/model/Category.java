package com.ashysystem.mbhq.model;

/**
 * Created by AQB Solutions PVT. LTD. on 7/9/18.
 */
public class Category {
    int id;
    String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
