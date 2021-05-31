package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.models.profile.details.ProfileDetailsData;
import com.mobiblanc.amdie.africa.network.models.profile.initform.InitProfileFormData;
import com.mobiblanc.amdie.africa.network.models.profile.update.ProfileDetailsForUpdateData;
import com.mobiblanc.amdie.africa.network.models.profile.update.UpdateProfileData;
import com.mobiblanc.amdie.africa.network.repository.ProfileRepository;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileViewModel extends AndroidViewModel {

    private final ProfileRepository repository;
    private final MutableLiveData<Resource<InitProfileFormData>> InitProfileFormLiveData;
    private final MutableLiveData<Resource<UpdateProfileData>> updateProfileLiveData;
    private final MutableLiveData<Resource<ProfileDetailsData>> profileDetailsLiveData;
    private final MutableLiveData<Resource<ProfileDetailsForUpdateData>> profileDetailsForUpdateLiveData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);

        this.repository = new ProfileRepository();
        InitProfileFormLiveData = new MutableLiveData<>();
        updateProfileLiveData = new MutableLiveData<>();
        profileDetailsLiveData = new MutableLiveData<>();
        profileDetailsForUpdateLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<InitProfileFormData>> getInitProfileFormLiveData() {
        return InitProfileFormLiveData;
    }

    public MutableLiveData<Resource<UpdateProfileData>> getUpdateProfileLiveData() {
        return updateProfileLiveData;
    }

    public MutableLiveData<Resource<ProfileDetailsData>> getProfileDetailsLiveData() {
        return profileDetailsLiveData;
    }

    public MutableLiveData<Resource<ProfileDetailsForUpdateData>> getProfileDetailsForUpdateLiveData() {
        return profileDetailsForUpdateLiveData;
    }

    public void initProfileForm(String token,
                                String lang) {
        repository.initProfileForm(token, lang, InitProfileFormLiveData);
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
                              RequestBody products) {
        repository.updateProfile(token, profilePicture,
                companyPicture, lang, canal, presentation, headquarter, sector, revenues, companySize, topics, currency, products, updateProfileLiveData);
    }

    public void getProfile(String token,
                           String lang) {
        repository.getProfile(token, lang, profileDetailsLiveData);
    }

    public void getProfileForUpdate(String token,
                                    String lang) {
        repository.getProfileForUpdate(token, lang, profileDetailsForUpdateLiveData);
    }
}
