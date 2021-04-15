package com.mobiblanc.amdie.africa.network.models.search.get_mentore;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Body{

	@SerializedName("mode")
	private String mode;

	@SerializedName("formdata")
	private List<FormdataItem> formdata;

	public void setMode(String mode){
		this.mode = mode;
	}

	public String getMode(){
		return mode;
	}

	public void setFormdata(List<FormdataItem> formdata){
		this.formdata = formdata;
	}

	public List<FormdataItem> getFormdata(){
		return formdata;
	}
}