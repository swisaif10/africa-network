package com.mobiblanc.amdie.africa.network.models.search.init_montoring;

import com.google.gson.annotations.SerializedName;

public class Forms {

    @SerializedName("secteur")
    private Secteur secteur;

    @SerializedName("presentation")
    private Presentation presentation;

    @SerializedName("effectif")
    private Effectif effectif;

    @SerializedName("produits")
    private Produits produits;

    @SerializedName("pictureProfil")
    private PictureProfil pictureProfil;

    @SerializedName("topics")
    private Topics topics;

    @SerializedName("chiffredaffaire")
    private Chiffredaffaire chiffredaffaire;

    @SerializedName("siege")
    private Siege siege;

    @SerializedName("pictureEntreprise")
    private PictureEntreprise pictureEntreprise;

    @SerializedName("devise")
    private Devise devise;

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public Effectif getEffectif() {
        return effectif;
    }

    public void setEffectif(Effectif effectif) {
        this.effectif = effectif;
    }

    public Produits getProduits() {
        return produits;
    }

    public void setProduits(Produits produits) {
        this.produits = produits;
    }

    public PictureProfil getPictureProfil() {
        return pictureProfil;
    }

    public void setPictureProfil(PictureProfil pictureProfil) {
        this.pictureProfil = pictureProfil;
    }

    public Topics getTopics() {
        return topics;
    }

    public void setTopics(Topics topics) {
        this.topics = topics;
    }

    public Chiffredaffaire getChiffredaffaire() {
        return chiffredaffaire;
    }

    public void setChiffredaffaire(Chiffredaffaire chiffredaffaire) {
        this.chiffredaffaire = chiffredaffaire;
    }

    public Siege getSiege() {
        return siege;
    }

    public void setSiege(Siege siege) {
        this.siege = siege;
    }

    public PictureEntreprise getPictureEntreprise() {
        return pictureEntreprise;
    }

    public void setPictureEntreprise(PictureEntreprise pictureEntreprise) {
        this.pictureEntreprise = pictureEntreprise;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }
}