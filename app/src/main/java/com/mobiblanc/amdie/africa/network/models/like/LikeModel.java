package com.mobiblanc.amdie.africa.network.models.like;

import com.google.gson.annotations.SerializedName;

public class LikeModel {

    @SerializedName("header")
    private Header header;

    @SerializedName("results")
    private Results results;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
}