package com.mobiblanc.amdie.africa.network.models.profile.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobiblanc.amdie.africa.network.models.common.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Results implements Serializable {

    @Expose
    private String birthday;
    @SerializedName("produits")
    private String products;
    @Expose
    private String city;
    @Expose
    private String country;
    @SerializedName("pictureEntreprise")
    private String companyPicture;
    @SerializedName("nom")
    private String lastname;
    @Expose
    private String presentation;
    @SerializedName("pictureProfil")
    private String profilePicture;
    @Expose
    private int id;
    @SerializedName("prenom")
    private String firstname;
    @Expose
    private String email;
    @Expose
    private List<Item> topics;
    @SerializedName("siege")
    private String headquarter;
    @Expose
    private int monitoring;
    @SerializedName("devise")
    private List<Item> currency;
    @Expose
    private String token;
    @SerializedName("secteur")
    private List<Item> sectors;
    @SerializedName("effectif")
    private String companySize;
    @SerializedName("chiffredaffaire")
    private String revenues;
    @SerializedName("fonction")
    private String function;
    @SerializedName("nomentreprise")
    private String companyName;
    @Expose
    private String username;
    @SerializedName("telephone")
    private String phoneNumber;
    @Expose
    private String gender;
    @SerializedName("country_id")
    private int countryId;
    @SerializedName("city_id")
    private int cityId;
    @SerializedName("fonctions_list")
    private List<Item> jobs;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompanyPicture() {
        return companyPicture;
    }

    public void setCompanyPicture(String companyPicture) {
        this.companyPicture = companyPicture;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Item> getTopics() {
        return topics;
    }

    public void setTopics(List<Item> topics) {
        this.topics = topics;
    }

    public String getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(String headquarter) {
        this.headquarter = headquarter;
    }

    public int getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(int monitoring) {
        this.monitoring = monitoring;
    }

    public List<Item> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Item> currency) {
        this.currency = currency;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Item> getSectors() {
        return sectors;
    }

    public void setSectors(List<Item> sectors) {
        this.sectors = sectors;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getRevenues() {
        return revenues;
    }

    public void setRevenues(String revenues) {
        this.revenues = revenues;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public List<Item> getJobs() {
        return jobs;
    }

    public void setJobs(List<Item> jobs) {
        this.jobs = jobs;
    }

    public List<String> getJobsNames() {
        List<String> names = new ArrayList<>();
        for (Item item : jobs) {
            names.add(item.getName());
        }
        return names;
    }
}