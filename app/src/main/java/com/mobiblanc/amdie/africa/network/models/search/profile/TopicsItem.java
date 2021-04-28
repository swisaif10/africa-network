package com.mobiblanc.amdie.africa.network.models.search.profile;

import com.google.gson.annotations.SerializedName;

public class TopicsItem {

    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}