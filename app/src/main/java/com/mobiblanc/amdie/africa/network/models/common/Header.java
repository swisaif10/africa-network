package com.mobiblanc.amdie.africa.network.models.common;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Header implements Serializable {

    @Expose
    private String status;
    @Expose
    private String message;
    @Expose
    private int search;

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

    public int getSearch() {
        return search;
    }

    public void setSearch(int search) {
        this.search = search;
    }
}
