package com.mobiblanc.amdie.africa.network.models.common;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Header implements Serializable {

    @Expose
    private String status;
    @Expose
    private String message;
    @Expose
    private String search;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
