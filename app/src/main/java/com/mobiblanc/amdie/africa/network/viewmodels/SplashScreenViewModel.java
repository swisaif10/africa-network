package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.models.checkversion.CheckVersionData;
import com.mobiblanc.amdie.africa.network.repository.SplashScreenRepository;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class SplashScreenViewModel extends AndroidViewModel {

    private final SplashScreenRepository repository;
    private final MutableLiveData<Resource<CheckVersionData>> checkVersionLiveData;

    public SplashScreenViewModel(@NonNull Application application) {
        super(application);

        this.repository = new SplashScreenRepository();
        checkVersionLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<CheckVersionData>> getCheckVersionLiveData() {
        return checkVersionLiveData;
    }

    public void checkVersion() {
        repository.checkVersion(checkVersionLiveData);
    }
}
