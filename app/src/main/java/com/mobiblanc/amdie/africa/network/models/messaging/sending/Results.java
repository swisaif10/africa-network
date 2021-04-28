package com.mobiblanc.amdie.africa.network.models.messaging.sending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results {
    @Expose
    private String date;
    @Expose
    private String message;
    @SerializedName("id_sender")
    private Integer idSender;
    @Expose
    private String username;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIdSender() {
        return idSender;
    }

    public void setIdSender(Integer idSender) {
        this.idSender = idSender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
