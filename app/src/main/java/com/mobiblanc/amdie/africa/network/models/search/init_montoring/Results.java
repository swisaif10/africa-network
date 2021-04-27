package com.mobiblanc.amdie.africa.network.models.search.init_montoring;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Results{

	@SerializedName("speaks")
	private List<SpeaksItem> speaks;

	@SerializedName("tranches_age")
	private List<TranchesAgeItem> tranchesAge;

	@SerializedName("competence_technique")
	private List<CompetenceTechniqueItem> competenceTechnique;

	@SerializedName("secteurs")
	private List<SecteursItem> secteurs;

	@SerializedName("experiences")
	private List<ExperiencesItem> experiences;

	@SerializedName("prestations_attendues")
	private List<PrestationsAttenduesItem> prestationsAttendues;

	@SerializedName("Liste_countries")
	private List<ListeCountriesItem> listeCountries;

	public void setSpeaks(List<SpeaksItem> speaks){
		this.speaks = speaks;
	}

	public List<SpeaksItem> getSpeaks(){
		return speaks;
	}

	public void setTranchesAge(List<TranchesAgeItem> tranchesAge){
		this.tranchesAge = tranchesAge;
	}

	public List<TranchesAgeItem> getTranchesAge(){
		return tranchesAge;
	}

	public void setCompetenceTechnique(List<CompetenceTechniqueItem> competenceTechnique){
		this.competenceTechnique = competenceTechnique;
	}

	public List<CompetenceTechniqueItem> getCompetenceTechnique(){
		return competenceTechnique;
	}

	public void setSecteurs(List<SecteursItem> secteurs){
		this.secteurs = secteurs;
	}

	public List<SecteursItem> getSecteurs(){
		return secteurs;
	}

	public void setExperiences(List<ExperiencesItem> experiences){
		this.experiences = experiences;
	}

	public List<ExperiencesItem> getExperiences(){
		return experiences;
	}

	public void setPrestationsAttendues(List<PrestationsAttenduesItem> prestationsAttendues){
		this.prestationsAttendues = prestationsAttendues;
	}

	public List<PrestationsAttenduesItem> getPrestationsAttendues(){
		return prestationsAttendues;
	}

	public void setListeCountries(List<ListeCountriesItem> listeCountries){
		this.listeCountries = listeCountries;
	}

	public List<ListeCountriesItem> getListeCountries(){
		return listeCountries;
	}
}