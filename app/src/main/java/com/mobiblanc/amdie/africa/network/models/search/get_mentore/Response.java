package com.mobiblanc.amdie.africa.network.models.search.get_mentore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("request")
    private Request request;

    @SerializedName("response")
    private List<Object> response;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public List<Object> getResponse() {
        return response;
    }

    public void setResponse(List<Object> response) {
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}