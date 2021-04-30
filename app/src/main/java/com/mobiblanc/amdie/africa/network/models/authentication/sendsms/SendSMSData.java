package com.mobiblanc.amdie.africa.network.models.authentication.sendsms;

import com.google.gson.annotations.Expose;
import com.mobiblanc.amdie.africa.network.models.common.Header;

public class SendSMSData {

    @Expose
    private Header header;
    @Expose
    private SendSMSResponse results;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public SendSMSResponse getResults() {
        return results;
    }

    public void setResults(SendSMSResponse results) {
        this.results = results;
    }
}