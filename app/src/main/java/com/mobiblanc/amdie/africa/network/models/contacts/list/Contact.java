package com.mobiblanc.amdie.africa.network.models.contacts.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Contact {
    @SerializedName("produits")
    private String products;
    @SerializedName("id_client")
    private int idClient;
    @SerializedName("demande_id")
    private int requestId;
    @Expose
    private String commune;
    @Expose
    private List<Item> topics;
    @SerializedName(("pictureEntreprise"))
    private String companyPicture;
    @SerializedName("siege")
    private String headquarters;
    @Expose
    private int active;
    @SerializedName("secteurs")
    private List<Item> sectors;
    @Expose
    private String experiences;
    @Expose
    private List<Item> devise;
    @Expose
    private String presentation;
    @SerializedName("effectif")
    private String companySize;
    @SerializedName("pictureProfil")
    private String ProfilePicture;
    @Expose
    private String province;
    @Expose
    private List<Item> speaks;
    @SerializedName("chiffredaffaire")
    private String revenue;
    @SerializedName("fonction")
    private String function;
    @Expose
    private int id;
    @SerializedName("nomentreprise")
    private String companyName;
    @Expose
    private int age;
    @Expose
    private String username;
    @Expose
    private String status;
    @SerializedName("favoris")
    private Boolean isFavourite;

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public List<Item> getTopics() {
        return topics;
    }

    public void setTopics(List<Item> topics) {
        this.topics = topics;
    }

    public String getCompanyPicture() {
        return companyPicture;
    }

    public void setCompanyPicture(String companyPicture) {
        this.companyPicture = companyPicture;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public List<Item> getSectors() {
        return sectors;
    }

    public void setSectors(List<Item> sectors) {
        this.sectors = sectors;
    }

    public String getExperiences() {
        return experiences;
    }

    public void setExperiences(String experiences) {
        this.experiences = experiences;
    }

    public List<Item> getDevise() {
        return devise;
    }

    public void setDevise(List<Item> devise) {
        this.devise = devise;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<Item> getSpeaks() {
        return speaks;
    }

    public void setSpeaks(List<Item> speaks) {
        this.speaks = speaks;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}