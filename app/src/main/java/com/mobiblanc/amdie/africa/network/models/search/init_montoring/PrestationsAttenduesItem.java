package com.mobiblanc.amdie.africa.network.models.search.init_montoring;

import com.google.gson.annotations.SerializedName;

public class PrestationsAttenduesItem {

    @SerializedName("ordre")
    private int ordre;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}