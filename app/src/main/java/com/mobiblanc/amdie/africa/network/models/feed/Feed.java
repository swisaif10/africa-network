package com.mobiblanc.amdie.africa.network.models.feed;

import com.google.gson.annotations.Expose;

public class Feed {
    @Expose
    private String date;
    @Expose
    private String image;
    @Expose
    private String subTitle;
    @Expose
    private String logo;
    @Expose
    private int id;
    @Expose
    private int nbrLikes;
    @Expose
    private String title;
    @Expose
    private String body;
    @Expose
    private int liked;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbrLikes() {
        return nbrLikes;
    }

    public void setNbrLikes(int nbrLikes) {
        this.nbrLikes = nbrLikes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
}
