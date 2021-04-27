package com.mobiblanc.amdie.africa.network.models.search.init_montoring;

import com.google.gson.annotations.SerializedName;

public class ExperiencesItem{

	@SerializedName("ordre")
	private int ordre;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public void setOrdre(int ordre){
		this.ordre = ordre;
	}

	public int getOrdre(){
		return ordre;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}