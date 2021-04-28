package com.mobiblanc.amdie.africa.network.models.contacts.list;

import com.google.gson.annotations.Expose;

public class Item {
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
