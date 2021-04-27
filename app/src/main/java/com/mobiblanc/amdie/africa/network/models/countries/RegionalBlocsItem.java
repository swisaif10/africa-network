package com.mobiblanc.amdie.africa.network.models.countries;

import java.util.List;

public class RegionalBlocsItem{
    private List<String> otherNames;
    private String acronym;
    private String name;
    private List<Object> otherAcronyms;

    public void setOtherNames(List<String> otherNames){
        this.otherNames = otherNames;
    }

    public List<String> getOtherNames(){
        return otherNames;
    }

    public void setAcronym(String acronym){
        this.acronym = acronym;
    }

    public String getAcronym(){
        return acronym;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setOtherAcronyms(List<Object> otherAcronyms){
        this.otherAcronyms = otherAcronyms;
    }

    public List<Object> getOtherAcronyms(){
        return otherAcronyms;
    }
}