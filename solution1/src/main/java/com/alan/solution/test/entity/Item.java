package com.alan.solution.test.entity;

import java.util.List;

public class Item {
    // item entity
    private String id;
    private String title;
    private List<Item> items;

    // getter/setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
