package com.mobiblanc.amdie.africa.network.models.share;

import com.google.gson.annotations.Expose;

public class Results{
    @Expose
    private String message;
    @Expose
    private String url;

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
