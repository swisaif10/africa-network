package com.mobiblanc.amdie.africa.network.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {

    @Expose
    private int id;
    @SerializedName(value = "name", alternate = {"value"})
    private String name;
    @SerializedName(value = "order", alternate = {"ordre"})
    private int order;
    @Expose
    private String icon;
    @Expose
    private Boolean selected;
    private Boolean isEnabled = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Boolean getSelected() {
        return selected != null ? selected : false;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}