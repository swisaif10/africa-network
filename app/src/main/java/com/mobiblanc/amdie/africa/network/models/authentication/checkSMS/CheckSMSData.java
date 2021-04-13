package com.mobiblanc.amdie.africa.network.models.authentication.checkSMS;

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
    @Expose
    private List<Form> forms;

    public void setHeader(Header header) {
        this.header = header;
    }

    public Header getHeader() {
        return header;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public Results getResults() {
        return results;
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

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public List<Form> getForms() {
        return forms;
    }

    public List<String> getCountriesNames() {
        List<String> names = new ArrayList<>();
        for (Country country : countries) {
            names.add(country.getName());
        }
        return names;
    }
}