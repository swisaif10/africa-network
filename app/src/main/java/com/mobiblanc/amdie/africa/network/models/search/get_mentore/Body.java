package com.mobiblanc.amdie.africa.network.models.search.get_mentore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Body {

    @SerializedName("mode")
    private String mode;

    @SerializedName("formdata")
    private List<FormdataItem> formdata;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<FormdataItem> getFormdata() {
        return formdata;
    }

    public void setFormdata(List<FormdataItem> formdata) {
        this.formdata = formdata;
    }
}