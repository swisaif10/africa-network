package com.mobiblanc.amdie.africa.network.models.authentication.checksms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobiblanc.amdie.africa.network.models.authentication.Results;
import com.mobiblanc.amdie.africa.network.models.common.Header;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckSMSData implements Serializable {
    @Expose
    private Header header;
    @SerializedName("lieux")
    private List<place> places;
    @Expose
    private Results results;
    @SerializedName("Liste_countries")
    private List<Country> countries;
    private List<Form> forms;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public List<place> getPlaces() {
        return places;
    }

    public void setPlaces(List<place> places) {
        this.places = places;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<String> getCountriesNames() {
        List<String> names = new ArrayList<>();
        for (Country country : countries) {
            names.add(country.getName());
        }
        return names;
    }

    public String getCountryNameById(int id) {
        for (Country country : countries) {
            if (country.getId() == id)
                return country.getName();
        }
        return "";
    }
}