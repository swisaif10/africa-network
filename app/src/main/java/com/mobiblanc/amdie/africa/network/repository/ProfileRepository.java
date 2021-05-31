package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;
import com.mobiblanc.amdie.africa.network.models.profile.details.ProfileDetailsData;
import com.mobiblanc.amdie.africa.network.models.profile.initform.InitProfileFormData;
import com.mobiblanc.amdie.africa.network.models.profile.update.ProfileDetailsForUpdateData;
import com.mobiblanc.amdie.africa.network.models.profile.update.UpdateProfileData;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileRepository {

    public void initProfileForm(String token,
                                String lang,
                                MutableLiveData<Resource<InitProfileFormData>> mutableLiveData) {
        new ApiManager().initProfileForm(token, lang, mutableLiveData);
    }

    public void updateProfile(RequestBody token,
                              MultipartBody.Part profilePicture,
                              MultipartBody.Part companyPicture,
                              RequestBody lang,
                              RequestBody canal,
                              RequestBody presentation,
                              RequestBody headquarter,
                              RequestBody sector,
                              RequestBody revenues,
                              RequestBody companySize,
                              RequestBody topics,
                              RequestBody currency,
                              RequestBody products,
                              MutableLiveData<Resource<UpdateProfileData>> mutableLiveData) {
        new ApiManager().updateProfile(token, profilePicture,
                companyPicture, lang, canal, presentation, headquarter, sector, revenues, companySize, topics, currency, products, mutableLiveData);
    }

    public void getProfile(String token,
                           String lang,
                           MutableLiveData<Resource<ProfileDetailsData>> mutableLiveData) {
        new ApiManager().getProfile(token, lang, mutableLiveData);
    }

    public void getProfileForUpdate(String token,
                                    String lang,
                                    MutableLiveData<Resource<ProfileDetailsForUpdateData>> mutableLiveData) {
        new ApiManager().getProfileForUpdate(token, lang, mutableLiveData);
    }
}
