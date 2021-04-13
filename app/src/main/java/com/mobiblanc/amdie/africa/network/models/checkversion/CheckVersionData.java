package com.mobiblanc.amdie.africa.network.models.checkversion;

import com.google.gson.annotations.Expose;
import com.mobiblanc.amdie.africa.network.models.common.Header;

public class CheckVersionData {

    @Expose
    private Header header;
    @Expose
    private CheckVersionResponse response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public CheckVersionResponse getResponse() {
        return response;
    }

    public void setResponse(CheckVersionResponse response) {
        this.response = response;
    }
}