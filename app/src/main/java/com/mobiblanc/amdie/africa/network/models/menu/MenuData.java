package com.mobiblanc.amdie.africa.network.models.menu;

import com.google.gson.annotations.Expose;
import com.mobiblanc.amdie.africa.network.models.common.Header;

import java.util.List;

public class MenuData {

    @Expose
    private Header header;
    @Expose
    private List<Result> results;

    public void setHeader(Header header){
        this.header = header;
    }

    public Header getHeader(){
        return header;
    }

    public void setResults(List<Result> results){
        this.results = results;
    }

    public List<Result> getResults(){
        return results;
    }
}