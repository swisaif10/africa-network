package com.mobiblanc.amdie.africa.network.models.search.profile;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("profile")
	private Profile profile;

	public void setProfile(Profile profile){
		this.profile = profile;
	}

	public Profile getProfile(){
		return profile;
	}
}