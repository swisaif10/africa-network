package com.mobiblanc.amdie.africa.network.models.authentication.checkSMS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Form implements Serializable {
    @SerializedName("libelleParent")
    private String parentWording;
    @SerializedName("ordre")
    private int order;
    @Expose
    private int max;
    @SerializedName("libelle")
    private String wording;
    @Expose
    private String name;
    @Expose
    private String type;
    @SerializedName("objetReference")
    private String objectReference;

    public String getParentWording() {
        return parentWording;
    }

    public void setParentWording(String parentWording) {
        this.parentWording = parentWording;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(String objectReference) {
        this.objectReference = objectReference;
    }
}
