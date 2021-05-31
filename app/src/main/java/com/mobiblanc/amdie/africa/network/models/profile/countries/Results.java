package com.mobiblanc.amdie.africa.network.models.profile.countries;

import com.mobiblanc.amdie.africa.network.models.common.Country;

import java.util.ArrayList;
import java.util.List;

public class Results {

    private List<Country> countries;

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
}