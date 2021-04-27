package com.mobiblanc.amdie.africa.network.models.search.init_montoring;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("initmontoringdata")
	private InitMontoringData initmontoringdata;

	public void setInitmontoringdata(InitMontoringData initmontoringdata){
		this.initmontoringdata = initmontoringdata;
	}

	public InitMontoringData getInitmontoringdata(){
		return initmontoringdata;
	}
}