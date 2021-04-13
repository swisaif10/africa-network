package com.mobiblanc.amdie.africa.network.models.feed;

import com.google.gson.annotations.Expose;

public class Type {
    @Expose
    private String id;
    @Expose
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
