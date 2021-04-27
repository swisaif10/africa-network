package com.mobiblanc.amdie.africa.network.models.search.init_montoring;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListeCountriesItem{

	@SerializedName("ordre")
	private int ordre;

	@SerializedName("prefectures")
	private List<PrefecturesItem> prefectures;

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

	public void setPrefectures(List<PrefecturesItem> prefectures){
		this.prefectures = prefectures;
	}

	public List<PrefecturesItem> getPrefectures(){
		return prefectures;
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