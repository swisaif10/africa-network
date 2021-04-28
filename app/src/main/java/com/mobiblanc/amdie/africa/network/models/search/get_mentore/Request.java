package com.mobiblanc.amdie.africa.network.models.search.get_mentore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Request {

    @SerializedName("method")
    private String method;

    @SerializedName("header")
    private List<Object> header;

    @SerializedName("body")
    private Body body;

    @SerializedName("url")
    private String url;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Object> getHeader() {
        return header;
    }

    public void setHeader(List<Object> header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}