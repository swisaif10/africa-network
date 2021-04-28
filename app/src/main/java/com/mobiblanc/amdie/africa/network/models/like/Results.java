package com.mobiblanc.amdie.africa.network.models.like;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results {
    @SerializedName("nbr_likes")
    private int nbrLikes;
    @Expose
    private int liked;

    public int getNbrLikes() {
        return nbrLikes;
    }

    public void setNbrLikes(int nbrLikes) {
        this.nbrLikes = nbrLikes;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }
}
