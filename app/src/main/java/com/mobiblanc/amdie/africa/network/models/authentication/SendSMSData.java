package com.mobiblanc.amdie.africa.network.models.authentication;

import com.google.gson.annotations.Expose;
import com.mobiblanc.amdie.africa.network.models.checkversion.CheckVersionResponse;
import com.mobiblanc.amdie.africa.network.models.common.Header;

public class SendSMSData {

    @Expose
    private Header header;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

}