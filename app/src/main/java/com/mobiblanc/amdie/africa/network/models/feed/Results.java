package com.mobiblanc.amdie.africa.network.models.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {
    @SerializedName("feed")
    private List<Feed> feeds;
    @Expose
    private List<Type> types;
    @SerializedName("secteurs")
    private List<Sector> sectors;
    @Expose
    private int offset;
    @SerializedName("nbr_pages")
    private int totalPages;

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(List<Sector> Sectors) {
        this.sectors = Sectors;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}