package com.mobiblanc.amdie.africa.network.repository;

import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.datamanager.ApiManager;
import com.mobiblanc.amdie.africa.network.models.authentication.completeregistraion.CompleteRegistrationData;
import com.mobiblanc.amdie.africa.network.models.profile.countries.CountriesListData;
import com.mobiblanc.amdie.africa.network.models.profile.details.ProfileDetailsData;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class PersonalInformationRepository {

    public void getPersonalInformation(String token,
                                       String lang,
                                       MutableLiveData<Resource<ProfileDetailsData>> mutableLiveData) {
        new ApiManager().getProfile(token, lang, mutableLiveData);
    }

    public void getCountries(String token,
                             String lang,
                             MutableLiveData<Resource<CountriesListData>> mutableLiveData) {
        new ApiManager().getCountries(token, lang, mutableLiveData);
    }

    public void updatePersonalInformation(String token,
                                          String gender,
                                          String lastName,
                                          String company,
                                          String job,
                                          String email,
                                          String firstName,
                                          int country,
                                          int city,
                                          int nationality,
                                          String firebaseToken,
                                          String code,
                                          String phoneNumber,
                                          String otherJob,
                                          MutableLiveData<Resource<CompleteRegistrationData>> mutableLiveData) {
        new ApiManager().completeRegistration(token, gender, lastName, company, job, email, firstName, country, city, nationality, firebaseToken, code, phoneNumber, otherJob, mutableLiveData);
    }
}
