package com.mobiblanc.amdie.africa.network.models.messaging.discussions;

import com.google.gson.annotations.Expose;
import com.mobiblanc.amdie.africa.network.models.common.Header;

import java.util.List;

public class DiscussionsListData {

    @Expose
    private Header header;
    @Expose
    private List<Discussion> results;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<Discussion> getResults() {
        return results;
    }

    public void setResults(List<Discussion> results) {
        this.results = results;
    }
}