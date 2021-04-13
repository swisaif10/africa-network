package com.mobiblanc.amdie.africa.network.models.feed;

import com.google.gson.annotations.Expose;

public class sector {
    @Expose
    private int id;
    @Expose
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
