package com.mobiblanc.amdie.africa.network.models.contacts.filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobiblanc.amdie.africa.network.models.common.Item;

import java.util.List;

public class Results {
    @SerializedName("secteurs")
    private List<Item> sectors;
    @Expose
    private List<Item> countries;

    public List<Item> getSectors() {
        return sectors;
    }

    public void setSectors(List<Item> sectors) {
        this.sectors = sectors;
    }

    public List<Item> getCountries() {
        return countries;
    }

    public void setCountries(List<Item> countries) {
        this.countries = countries;
    }
}