package com.mobiblanc.amdie.africa.network.models.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Results implements Serializable {
    @Expose
    private String birthday;
    @Expose
    private int city;
    @SerializedName("commune")
    private int town;
    @SerializedName("notif_push")
    private boolean notifPush;
    @SerializedName("monitoring")
    private int monitoring;
    @SerializedName("nom")
    private String lastName;
    @Expose
    private String picture;
    @Expose
    private String token;
    @Expose
    private int search;
    @Expose
    private int province;
    @SerializedName("notif_email")
    private boolean notifEmail;
    @Expose
    private int id;
    @SerializedName("prenom")
    private String firstName;
    @Expose
    private String email;
    @SerializedName("notif_sms")
    private boolean notifSms;
    @Expose
    private String username;
    @SerializedName("civilite")
    private String civility;
    @Expose
    private int country;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getTown() {
        return town;
    }

    public void setTown(int town) {
        this.town = town;
    }

    public boolean isNotifPush() {
        return notifPush;
    }

    public void setNotifPush(boolean notifPush) {
        this.notifPush = notifPush;
    }

    public int getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(int monitoring) {
        this.monitoring = monitoring;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSearch() {
        return search;
    }

    public void setSearch(int search) {
        this.search = search;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public boolean isNotifEmail() {
        return notifEmail;
    }

    public void setNotifEmail(boolean notifEmail) {
        this.notifEmail = notifEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNotifSms() {
        return notifSms;
    }

    public void setNotifSms(boolean notifSms) {
        this.notifSms = notifSms;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCivility() {
        return civility;
    }

    public void setCivility(String civility) {
        this.civility = civility;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }
}
