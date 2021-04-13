package com.mobiblanc.amdie.africa.network.models.common;

import com.google.gson.annotations.Expose;

public class Header {

    @Expose
    private String status;
    @Expose
    private String message;

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
}
