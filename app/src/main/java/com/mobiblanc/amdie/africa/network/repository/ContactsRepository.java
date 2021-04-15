package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;
import com.mobiblanc.amdie.africa.network.models.authentication.updateprofile.UpdateProfileData;

public class ContactsRepository {
    public void updateProfile(String token,
                              String pictureProfil,
                              String pictureEntreprise,
                              String lang,
                              String canal,
                              String presentation,
                              int siege,
                              int secteur,
                              int chiffredaffaire,
                              int effectif,
                              int topics,
                              int experiences,
                              MutableLiveData<Resource<UpdateProfileData>> mutableLiveData) {
        new ApiManager().updateMentore( token, pictureProfil, pictureEntreprise, lang, canal,   presentation,   siege,    secteur,  chiffredaffaire,
         effectif,     topics,  experiences,  mutableLiveData);}

}
