package com.mobiblanc.amdie.africa.network.models.checkversion;

import com.google.gson.annotations.Expose;

public class CheckVersionResponse {
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
