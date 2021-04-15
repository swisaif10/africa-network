package com.mobiblanc.amdie.africa.network.models.search.get_mentore;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Request{

	@SerializedName("method")
	private String method;

	@SerializedName("header")
	private List<Object> header;

	@SerializedName("body")
	private Body body;

	@SerializedName("url")
	private String url;

	public void setMethod(String method){
		this.method = method;
	}

	public String getMethod(){
		return method;
	}

	public void setHeader(List<Object> header){
		this.header = header;
	}

	public List<Object> getHeader(){
		return header;
	}

	public void setBody(Body body){
		this.body = body;
	}

	public Body getBody(){
		return body;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}
}