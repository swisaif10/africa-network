package com.mobiblanc.amdie.africa.network.models.search.init_montoring;

import com.google.gson.annotations.SerializedName;

public class InitMontoringData {

    @SerializedName("header")
    private Header header;

    @SerializedName("results")
    private Results results;

    @SerializedName("forms")
    private Forms forms;

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

    public Forms getForms() {
        return forms;
    }

    public void setForms(Forms forms) {
        this.forms = forms;
    }
}