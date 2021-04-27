package com.mobiblanc.amdie.africa.network.models.contacts.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobiblanc.amdie.africa.network.models.common.Header;

import java.util.List;

public class ContactsListData {

    @SerializedName("nbr_pages")
    private int nbrPages;
    @Expose
    private Header header;
    @Expose
    private int page;
    @Expose
    private List<Contact> results;

    public void setNbrPages(int nbrPages) {
        this.nbrPages = nbrPages;
    }

    public int getNbrPages() {
        return nbrPages;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Header getHeader() {
        return header;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setResults(List<Contact> results) {
        this.results = results;
    }

    public List<Contact> getResults() {
        return results;
    }
}