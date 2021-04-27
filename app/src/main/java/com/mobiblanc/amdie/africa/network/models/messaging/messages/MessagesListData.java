package com.mobiblanc.amdie.africa.network.models.messaging.messages;

import com.google.gson.annotations.Expose;
import com.mobiblanc.amdie.africa.network.models.common.Header;

import java.io.Serializable;
import java.util.List;

public class MessagesListData implements Serializable {
    @Expose
    private Header header;
    @Expose
    private List<Message> results;

    public void setHeader(Header header){
        this.header = header;
    }

    public Header getHeader(){
        return header;
    }

    public void setResults(List<Message> results){
        this.results = results;
    }

    public List<Message> getResults(){
        return results;
    }
}