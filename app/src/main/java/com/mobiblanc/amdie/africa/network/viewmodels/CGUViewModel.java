package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.models.cgu.CGUData;
import com.mobiblanc.amdie.africa.network.repository.CGURepository;

public class CGUViewModel extends AndroidViewModel {

    private final CGURepository repository;
    private final MutableLiveData<Resource<CGUData>> cguLiveData;

    public CGUViewModel(@NonNull Application application) {
        super(application);

        this.repository = new CGURepository();
        cguLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<CGUData>> getCguLiveData() {
        return cguLiveData;
    }

    public void getCGU(String token, String lang) {
        repository.getCGU(token, lang, cguLiveData);
    }
}
