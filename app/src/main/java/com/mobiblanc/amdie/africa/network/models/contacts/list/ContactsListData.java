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

    public int getNbrPages() {
        return nbrPages;
    }

    public void setNbrPages(int nbrPages) {
        this.nbrPages = nbrPages;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Contact> getResults() {
        return results;
    }

    public void setResults(List<Contact> results) {
        this.results = results;
    }
}