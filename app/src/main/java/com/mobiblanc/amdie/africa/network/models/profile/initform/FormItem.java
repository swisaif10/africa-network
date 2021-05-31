package com.mobiblanc.amdie.africa.network.models.profile.initform;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobiblanc.amdie.africa.network.models.common.Item;

import java.util.List;

public class FormItem {
    @SerializedName("libelle_parent")
    private String parentWording;
    @SerializedName("ordre")
    private int order;
    @Expose
    private int max;
    @SerializedName("libelle")
    private String wording;
    @Expose
    private String name;
    @SerializedName("objet_reference")
    private String referenceObject;
    @Expose
    private String type;
    @SerializedName("objet_reference_values")
    private List<Item> referenceObjectValues;

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

    public String getReferenceObject() {
        return referenceObject;
    }

    public void setReferenceObject(String referenceObject) {
        this.referenceObject = referenceObject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Item> getReferenceObjectValues() {
        return referenceObjectValues;
    }

    public void setReferenceObjectValues(List<Item> referenceObjectValues) {
        this.referenceObjectValues = referenceObjectValues;
    }
}
