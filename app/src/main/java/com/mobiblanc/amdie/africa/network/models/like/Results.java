package com.mobiblanc.amdie.africa.network.models.like;

import com.google.gson.annotations.SerializedName;

public class Results{

	@SerializedName("nbr_likes")
	private int nbrLikes;

	@SerializedName("liked")
	private int liked;

	public void setNbrLikes(int nbrLikes){
		this.nbrLikes = nbrLikes;
	}

	public int getNbrLikes(){
		return nbrLikes;
	}

	public void setLiked(int liked){
		this.liked = liked;
	}

	public int getLiked(){
		return liked;
	}
}