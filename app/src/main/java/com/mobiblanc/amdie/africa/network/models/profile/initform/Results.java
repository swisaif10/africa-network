package com.mobiblanc.amdie.africa.network.models.profile.initform;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobiblanc.amdie.africa.network.models.common.Item;

import java.util.List;

public class Results {

    @Expose
    private List<Item> speaks;
    @SerializedName("tranches_age")
    private List<Item> ageGroups;
    @SerializedName("competence_technique")
    private List<Item> technicalSkills;
    @SerializedName("secteurs")
    private List<Item> sctors;
    @SerializedName("experiences")
    private List<Item> experiences;
    @SerializedName("prestations_attendues")
    private List<Item> expectedBenefits;
    @SerializedName("Liste_countries")
    private List<Item> countries;

    public List<Item> getSpeaks() {
        return speaks;
    }

    public void setSpeaks(List<Item> speaks) {
        this.speaks = speaks;
    }

    public List<Item> getAgeGroups() {
        return ageGroups;
    }

    public void setAgeGroups(List<Item> ageGroups) {
        this.ageGroups = ageGroups;
    }

    public List<Item> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<Item> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    public List<Item> getSctors() {
        return sctors;
    }

    public void setSctors(List<Item> sctors) {
        this.sctors = sctors;
    }

    public List<Item> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Item> experiences) {
        this.experiences = experiences;
    }

    public List<Item> getExpectedBenefits() {
        return expectedBenefits;
    }

    public void setExpectedBenefits(List<Item> expectedBenefits) {
        this.expectedBenefits = expectedBenefits;
    }

    public List<Item> getCountries() {
        return countries;
    }

    public void setCountries(List<Item> countries) {
        this.countries = countries;
    }
}