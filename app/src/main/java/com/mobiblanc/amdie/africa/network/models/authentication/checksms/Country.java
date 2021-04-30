package com.mobiblanc.amdie.africa.network.models.authentication.checksms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Country implements Serializable {
    @SerializedName("ordre")
    private int order;
    @Expose
    private List<City> cities;
    @Expose
    private String name;
    @Expose
    private int id;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
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

    public List<String> getCitiesNames() {
        List<String> names = new ArrayList<>();
        for (City city : cities) {
            names.add(city.getName());
        }
        return names;
    }
}