package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;

import com.mobiblanc.amdie.africa.network.models.search.init_montoring.InitMontoringData;
import com.mobiblanc.amdie.africa.network.models.search.profile.Profile;
import com.mobiblanc.amdie.africa.network.models.search.update_mentore.UpdateMentoreData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ContactsRepository {
    public void updateMentore(RequestBody token,
                              MultipartBody.Part pictureProfil,
                              MultipartBody.Part pictureEntreprise,
                              RequestBody lang,
                              RequestBody canal,
                              RequestBody presentation,
                              RequestBody siege,
                              RequestBody secteur,
                              RequestBody chiffredaffaire,
                              RequestBody effectif,
                              RequestBody topics,
                              RequestBody devise,
                              RequestBody produit,
                              MutableLiveData<Resource<UpdateMentoreData>> mutableLiveData) {
        new ApiManager().updateMentore( token, pictureProfil, pictureEntreprise, lang, canal,   presentation,   siege,    secteur,  chiffredaffaire,
         effectif,     topics,devise,produit,  mutableLiveData);}

    public void getInitMontoring(String token,
                        String lang,
                        MutableLiveData<Resource<InitMontoringData>> mutableLiveData) {
        new ApiManager().getInitMontoring(token, lang, mutableLiveData);
    }
    public void getProfile(String token,
                                 String lang,
                                 MutableLiveData<Resource<Profile>> mutableLiveData) {
        new ApiManager().getProfile(token, lang, mutableLiveData);
    }

}
