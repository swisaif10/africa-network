package com.mobiblanc.amdie.africa.network.models.search.init_montoring;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PictureProfil {

    @SerializedName("libelle_parent")
    private String libelleParent;

    @SerializedName("ordre")
    private int ordre;

    @SerializedName("max")
    private int max;

    @SerializedName("libelle")
    private String libelle;

    @SerializedName("name")
    private String name;

    @SerializedName("objet_reference_values")
    private List<Object> objetReferenceValues;

    @SerializedName("type")
    private String type;

    @SerializedName("objet_reference")
    private String objetReference;

    public String getLibelleParent() {
        return libelleParent;
    }

    public void setLibelleParent(String libelleParent) {
        this.libelleParent = libelleParent;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getObjetReferenceValues() {
        return objetReferenceValues;
    }

    public void setObjetReferenceValues(List<Object> objetReferenceValues) {
        this.objetReferenceValues = objetReferenceValues;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjetReference() {
        return objetReference;
    }

    public void setObjetReference(String objetReference) {
        this.objetReference = objetReference;
    }
}