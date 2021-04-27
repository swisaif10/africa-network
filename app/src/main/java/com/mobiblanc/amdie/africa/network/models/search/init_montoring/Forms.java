package com.mobiblanc.amdie.africa.network.models.search.init_montoring;

import com.google.gson.annotations.SerializedName;

public class Forms{

	@SerializedName("secteur")
	private Secteur secteur;

	@SerializedName("presentation")
	private Presentation presentation;

	@SerializedName("effectif")
	private Effectif effectif;

	@SerializedName("produits")
	private Produits produits;

	@SerializedName("pictureProfil")
	private PictureProfil pictureProfil;

	@SerializedName("topics")
	private Topics topics;

	@SerializedName("chiffredaffaire")
	private Chiffredaffaire chiffredaffaire;

	@SerializedName("siege")
	private Siege siege;

	@SerializedName("pictureEntreprise")
	private PictureEntreprise pictureEntreprise;

	@SerializedName("devise")
	private Devise devise;

	public void setSecteur(Secteur secteur){
		this.secteur = secteur;
	}

	public Secteur getSecteur(){
		return secteur;
	}

	public void setPresentation(Presentation presentation){
		this.presentation = presentation;
	}

	public Presentation getPresentation(){
		return presentation;
	}

	public void setEffectif(Effectif effectif){
		this.effectif = effectif;
	}

	public Effectif getEffectif(){
		return effectif;
	}

	public void setProduits(Produits produits){
		this.produits = produits;
	}

	public Produits getProduits(){
		return produits;
	}

	public void setPictureProfil(PictureProfil pictureProfil){
		this.pictureProfil = pictureProfil;
	}

	public PictureProfil getPictureProfil(){
		return pictureProfil;
	}

	public void setTopics(Topics topics){
		this.topics = topics;
	}

	public Topics getTopics(){
		return topics;
	}

	public void setChiffredaffaire(Chiffredaffaire chiffredaffaire){
		this.chiffredaffaire = chiffredaffaire;
	}

	public Chiffredaffaire getChiffredaffaire(){
		return chiffredaffaire;
	}

	public void setSiege(Siege siege){
		this.siege = siege;
	}

	public Siege getSiege(){
		return siege;
	}

	public void setPictureEntreprise(PictureEntreprise pictureEntreprise){
		this.pictureEntreprise = pictureEntreprise;
	}

	public PictureEntreprise getPictureEntreprise(){
		return pictureEntreprise;
	}

	public void setDevise(Devise devise){
		this.devise = devise;
	}

	public Devise getDevise(){
		return devise;
	}
}