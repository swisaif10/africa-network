package com.mobiblanc.amdie.africa.network.models.search.init_montoring;

import com.google.gson.annotations.SerializedName;

public class Header{

	@SerializedName("search")
	private int search;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setSearch(int search){
		this.search = search;
	}

	public int getSearch(){
		return search;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}