package com.mobiblanc.amdie.africa.network.models.search.init_montoring;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Topics{

	@SerializedName("libelle_parent")
	private String libelleParent;

	@SerializedName("ordre")
	private int ordre;

	@SerializedName("max")
	private int max;

	@SerializedName("libelle")
	private String libelle;

	@SerializedName("name")
	private String name;

	@SerializedName("objet_reference_values")
	private List<ObjetReferenceValuesItem> objetReferenceValues;

	@SerializedName("type")
	private String type;

	@SerializedName("objet_reference")
	private String objetReference;

	public void setLibelleParent(String libelleParent){
		this.libelleParent = libelleParent;
	}

	public String getLibelleParent(){
		return libelleParent;
	}

	public void setOrdre(int ordre){
		this.ordre = ordre;
	}

	public int getOrdre(){
		return ordre;
	}

	public void setMax(int max){
		this.max = max;
	}

	public int getMax(){
		return max;
	}

	public void setLibelle(String libelle){
		this.libelle = libelle;
	}

	public String getLibelle(){
		return libelle;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setObjetReferenceValues(List<ObjetReferenceValuesItem> objetReferenceValues){
		this.objetReferenceValues = objetReferenceValues;
	}

	public List<ObjetReferenceValuesItem> getObjetReferenceValues(){
		return objetReferenceValues;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setObjetReference(String objetReference){
		this.objetReference = objetReference;
	}

	public String getObjetReference(){
		return objetReference;
	}
}