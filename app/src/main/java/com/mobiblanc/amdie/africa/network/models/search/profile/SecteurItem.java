package com.mobiblanc.amdie.africa.network.models.search.profile;

import com.google.gson.annotations.SerializedName;

public class SecteurItem {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}