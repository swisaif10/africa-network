package com.mobiblanc.amdie.africa.network.models.like;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("like_model")
	private LikeModel likeModel;

	public void setLikeModel(LikeModel likeModel){
		this.likeModel = likeModel;
	}

	public LikeModel getLikeModel(){
		return likeModel;
	}
}