package com.mobiblanc.amdie.africa.network.models.messaging.discussions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Discussion {

    @Expose
    private String date;
    @SerializedName("messenger_name")
    private String messengerName;
    @Expose
    private int id;
    @SerializedName("id_receiver")
    private int idReceiver;
    @Expose
    private String picture;
    @Expose
    private String country;
    @SerializedName("entreprise")
    private String company;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessengerName() {
        return messengerName;
    }

    public void setMessengerName(String messengerName) {
        this.messengerName = messengerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(int idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
