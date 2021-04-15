package com.mobiblanc.amdie.africa.network.models.search.get_mentore;

import com.google.gson.annotations.SerializedName;

public class FormdataItem{

	@SerializedName("src")
	private String src;

	@SerializedName("type")
	private String type;

	@SerializedName("key")
	private String key;

	@SerializedName("value")
	private String value;

	@SerializedName("disabled")
	private boolean disabled;

	public void setSrc(String src){
		this.src = src;
	}

	public String getSrc(){
		return src;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setKey(String key){
		this.key = key;
	}

	public String getKey(){
		return key;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}

	public void setDisabled(boolean disabled){
		this.disabled = disabled;
	}

	public boolean isDisabled(){
		return disabled;
	}
}