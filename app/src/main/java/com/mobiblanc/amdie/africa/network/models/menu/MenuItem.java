package com.mobiblanc.amdie.africa.network.models.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuItem {

    @SerializedName("subtagId")
    private int id;
    @SerializedName("ordre")
    private int order;
    @Expose
    private String name;
    @Expose
    private String action;
    @SerializedName("icone")
    private String icon;
    @SerializedName("iconeInactive")
    private String inactiveIcon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getInactiveIcon() {
        return inactiveIcon;
    }

    public void setInactiveIcon(String nactiveIcon) {
        this.inactiveIcon = nactiveIcon;
    }
}
