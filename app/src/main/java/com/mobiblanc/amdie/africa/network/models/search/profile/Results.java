package com.mobiblanc.amdie.africa.network.models.search.profile;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Results{

	@SerializedName("birthday")
	private String birthday;

	@SerializedName("produits")
	private String produits;

	@SerializedName("city")
	private String city;

	@SerializedName("commune")
	private String commune;

	@SerializedName("pictureEntreprise")
	private String pictureEntreprise;

	@SerializedName("nom")
	private String nom;

	@SerializedName("presentation")
	private String presentation;

	@SerializedName("pictureProfil")
	private String pictureProfil;

	@SerializedName("mode_emploi")
	private ModeEmploi modeEmploi;

	@SerializedName("province")
	private String province;

	@SerializedName("notif_email")
	private boolean notifEmail;

	@SerializedName("id")
	private int id;

	@SerializedName("prenom")
	private String prenom;

	@SerializedName("email")
	private String email;

	@SerializedName("notif_sms")
	private boolean notifSms;

	@SerializedName("civilite")
	private String civilite;

	@SerializedName("notif_push")
	private boolean notifPush;

	@SerializedName("topics")
	private List<TopicsItem> topics;

	@SerializedName("siege")
	private String siege;

	@SerializedName("monitoring")
	private int monitoring;

	@SerializedName("devise")
	private List<DeviseItem> devise;

	@SerializedName("token")
	private String token;

	@SerializedName("secteur")
	private List<SecteurItem> secteur;

	@SerializedName("effectif")
	private String effectif;

	@SerializedName("chiffredaffaire")
	private String chiffredaffaire;

	@SerializedName("fonction")
	private String fonction;

	@SerializedName("share_app")
	private String shareApp;

	@SerializedName("nomentreprise")
	private String nomentreprise;

	@SerializedName("username")
	private String username;

	@SerializedName("mentions_legales")
	private MentionsLegales mentionsLegales;

	public void setBirthday(String birthday){
		this.birthday = birthday;
	}

	public String getBirthday(){
		return birthday;
	}

	public void setProduits(String produits){
		this.produits = produits;
	}

	public String getProduits(){
		return produits;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setCommune(String commune){
		this.commune = commune;
	}

	public String getCommune(){
		return commune;
	}

	public void setPictureEntreprise(String pictureEntreprise){
		this.pictureEntreprise = pictureEntreprise;
	}

	public String getPictureEntreprise(){
		return pictureEntreprise;
	}

	public void setNom(String nom){
		this.nom = nom;
	}

	public String getNom(){
		return nom;
	}

	public void setPresentation(String presentation){
		this.presentation = presentation;
	}

	public String getPresentation(){
		return presentation;
	}

	public void setPictureProfil(String pictureProfil){
		this.pictureProfil = pictureProfil;
	}

	public String getPictureProfil(){
		return pictureProfil;
	}

	public void setModeEmploi(ModeEmploi modeEmploi){
		this.modeEmploi = modeEmploi;
	}

	public ModeEmploi getModeEmploi(){
		return modeEmploi;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setNotifEmail(boolean notifEmail){
		this.notifEmail = notifEmail;
	}

	public boolean isNotifEmail(){
		return notifEmail;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPrenom(String prenom){
		this.prenom = prenom;
	}

	public String getPrenom(){
		return prenom;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setNotifSms(boolean notifSms){
		this.notifSms = notifSms;
	}

	public boolean isNotifSms(){
		return notifSms;
	}

	public void setCivilite(String civilite){
		this.civilite = civilite;
	}

	public String getCivilite(){
		return civilite;
	}

	public void setNotifPush(boolean notifPush){
		this.notifPush = notifPush;
	}

	public boolean isNotifPush(){
		return notifPush;
	}

	public void setTopics(List<TopicsItem> topics){
		this.topics = topics;
	}

	public List<TopicsItem> getTopics(){
		return topics;
	}

	public void setSiege(String siege){
		this.siege = siege;
	}

	public String getSiege(){
		return siege;
	}

	public void setMonitoring(int monitoring){
		this.monitoring = monitoring;
	}

	public int getMonitoring(){
		return monitoring;
	}

	public void setDevise(List<DeviseItem> devise){
		this.devise = devise;
	}

	public List<DeviseItem> getDevise(){
		return devise;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setSecteur(List<SecteurItem> secteur){
		this.secteur = secteur;
	}

	public List<SecteurItem> getSecteur(){
		return secteur;
	}

	public void setEffectif(String effectif){
		this.effectif = effectif;
	}

	public String getEffectif(){
		return effectif;
	}

	public void setChiffredaffaire(String chiffredaffaire){
		this.chiffredaffaire = chiffredaffaire;
	}

	public String getChiffredaffaire(){
		return chiffredaffaire;
	}

	public void setFonction(String fonction){
		this.fonction = fonction;
	}

	public String getFonction(){
		return fonction;
	}

	public void setShareApp(String shareApp){
		this.shareApp = shareApp;
	}

	public String getShareApp(){
		return shareApp;
	}

	public void setNomentreprise(String nomentreprise){
		this.nomentreprise = nomentreprise;
	}

	public String getNomentreprise(){
		return nomentreprise;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setMentionsLegales(MentionsLegales mentionsLegales){
		this.mentionsLegales = mentionsLegales;
	}

	public MentionsLegales getMentionsLegales(){
		return mentionsLegales;
	}
}