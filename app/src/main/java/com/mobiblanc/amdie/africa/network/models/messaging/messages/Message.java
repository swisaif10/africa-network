package com.mobiblanc.amdie.africa.network.models.messaging.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Message implements Serializable {
    @Expose
    private String date;
    @SerializedName("id_client")
    private Integer idClient;
    @Expose
    private String message;
    @Expose
    private String username;

    public Message(String date, Integer idClient, String message, String username) {
        this.date = date;
        this.idClient = idClient;
        this.message = message;
        this.username = username;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
