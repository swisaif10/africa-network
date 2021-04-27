package com.mobiblanc.amdie.africa.network.models.share;

import com.google.gson.annotations.Expose;
import com.mobiblanc.amdie.africa.network.models.common.Header;

public class ShareAppData{
    @Expose
    private Header header;
    @Expose
    private Results results;

    public void setHeader(Header header){
        this.header = header;
    }

    public Header getHeader(){
        return header;
    }

    public void setResults(Results results){
        this.results = results;
    }

    public Results getResults(){
        return results;
    }
}