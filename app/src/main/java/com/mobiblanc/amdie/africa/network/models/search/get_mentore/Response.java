package com.mobiblanc.amdie.africa.network.models.search.get_mentore;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("request")
	private Request request;

	@SerializedName("response")
	private List<Object> response;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public void setRequest(Request request){
		this.request = request;
	}

	public Request getRequest(){
		return request;
	}

	public void setResponse(List<Object> response){
		this.response = response;
	}

	public List<Object> getResponse(){
		return response;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}
}