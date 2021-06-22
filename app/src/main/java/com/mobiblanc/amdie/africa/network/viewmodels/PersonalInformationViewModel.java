package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.models.authentication.completeregistraion.CompleteRegistrationData;
import com.mobiblanc.amdie.africa.network.models.profile.countries.CountriesListData;
import com.mobiblanc.amdie.africa.network.models.profile.details.ProfileDetailsData;
import com.mobiblanc.amdie.africa.network.repository.PersonalInformationRepository;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class PersonalInformationViewModel extends AndroidViewModel {

    private final PersonalInformationRepository repository;
    private final MutableLiveData<Resource<ProfileDetailsData>> personalInformationLiveData;
    private final MutableLiveData<Resource<CountriesListData>> countriesListLiveData;
    private final MutableLiveData<Resource<CompleteRegistrationData>> updatePersonalInformationLiveData;

    public PersonalInformationViewModel(@NonNull Application application) {
        super(application);

        this.repository = new PersonalInformationRepository();
        personalInformationLiveData = new MutableLiveData<>();
        countriesListLiveData = new MutableLiveData<>();
        updatePersonalInformationLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<ProfileDetailsData>> getPersonalInformationLiveData() {
        return personalInformationLiveData;
    }

    public MutableLiveData<Resource<CompleteRegistrationData>> getUpdatePersonalInformationLiveData() {
        return updatePersonalInformationLiveData;
    }

    public MutableLiveData<Resource<CountriesListData>> getCountriesListLiveData() {
        return countriesListLiveData;
    }

    public void getPersonaInformation(String token,
                                      String lang) {
        repository.getPersonalInformation(token, lang, personalInformationLiveData);
    }

    public void getCountries(String token,
                             String lang) {
        repository.getCountries(token, lang, countriesListLiveData);
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
                                          String otherJob) {
        repository.updatePersonalInformation(token, gender, lastName, company, job, email, firstName, country, city, nationality, firebaseToken, code, phoneNumber, otherJob, updatePersonalInformationLiveData);
    }
}
