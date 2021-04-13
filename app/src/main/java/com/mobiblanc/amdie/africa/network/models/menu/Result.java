package com.mobiblanc.amdie.africa.network.models.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("subtags")
    private List<MenuItem> menuItems;
    @Expose
    private String name;

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}