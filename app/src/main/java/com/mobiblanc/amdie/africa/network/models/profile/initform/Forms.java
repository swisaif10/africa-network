package com.mobiblanc.amdie.africa.network.models.profile.initform;

import com.google.gson.annotations.SerializedName;

public class Forms {

    @SerializedName("secteur")
    private FormItem sectors;
    @SerializedName("presentation")
    private FormItem presentation;
    @SerializedName("effectif")
    private FormItem companySize;
    @SerializedName("produits")
    private FormItem products;
    @SerializedName("pictureProfil")
    private FormItem profilePicture;
    @SerializedName("topics")
    private FormItem topics;
    @SerializedName("chiffredaffaire")
    private FormItem revenues;
    @SerializedName("siege")
    private FormItem headquarters;
    @SerializedName("pictureEntreprise")
    private FormItem companyPicture;
    @SerializedName("devise")
    private FormItem currency;

    public FormItem getSectors() {
        return sectors;
    }

    public void setSectors(FormItem sectors) {
        this.sectors = sectors;
    }

    public FormItem getPresentation() {
        return presentation;
    }

    public void setPresentation(FormItem presentation) {
        this.presentation = presentation;
    }

    public FormItem getCompanySize() {
        return companySize;
    }

    public void setCompanySize(FormItem companySize) {
        this.companySize = companySize;
    }

    public FormItem getProducts() {
        return products;
    }

    public void setProducts(FormItem products) {
        this.products = products;
    }

    public FormItem getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(FormItem profilePicture) {
        this.profilePicture = profilePicture;
    }

    public FormItem getTopics() {
        return topics;
    }

    public void setTopics(FormItem topics) {
        this.topics = topics;
    }

    public FormItem getRevenues() {
        return revenues;
    }

    public void setRevenues(FormItem revenues) {
        this.revenues = revenues;
    }

    public FormItem getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(FormItem headquarters) {
        this.headquarters = headquarters;
    }

    public FormItem getCompanyPicture() {
        return companyPicture;
    }

    public void setCompanyPicture(FormItem companyPicture) {
        this.companyPicture = companyPicture;
    }

    public FormItem getCurrency() {
        return currency;
    }

    public void setCurrency(FormItem currency) {
        this.currency = currency;
    }
}