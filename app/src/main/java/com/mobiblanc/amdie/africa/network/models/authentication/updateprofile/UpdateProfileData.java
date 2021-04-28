package com.mobiblanc.amdie.africa.network.models.authentication.updateprofile;

import com.google.gson.annotations.Expose;
import com.mobiblanc.amdie.africa.network.models.authentication.Results;
import com.mobiblanc.amdie.africa.network.models.common.Header;

public class UpdateProfileData {
    @Expose
    private Header header;
    @Expose
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
