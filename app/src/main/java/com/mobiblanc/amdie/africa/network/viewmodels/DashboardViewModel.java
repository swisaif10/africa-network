package com.mobiblanc.amdie.africa.network.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.models.logout.LogoutData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;
import com.mobiblanc.amdie.africa.network.models.share.ShareAppData;
import com.mobiblanc.amdie.africa.network.repository.DashboardRepository;
import com.mobiblanc.amdie.africa.network.utilities.Resource;

public class DashboardViewModel extends AndroidViewModel {

    private final DashboardRepository repository;
    private final MutableLiveData<Resource<MenuData>> menuLiveData;
    private final MutableLiveData<Resource<ShareAppData>> shareAppLiveData;
    private final MutableLiveData<Resource<LogoutData>> logoutLiveData;

    public DashboardViewModel(@NonNull Application application) {
        super(application);

        this.repository = new DashboardRepository();
        menuLiveData = new MutableLiveData<>();
        shareAppLiveData = new MutableLiveData<>();
        logoutLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<MenuData>> getMenuLiveData() {
        return menuLiveData;
    }

    public MutableLiveData<Resource<ShareAppData>> getShareAppLiveData() {
        return shareAppLiveData;
    }

    public MutableLiveData<Resource<LogoutData>> getLogoutLiveData() {
        return logoutLiveData;
    }

    public void getMenu(String token,
                        String lang) {
        repository.getMenu(token, lang, menuLiveData);
    }

    public void getShareLink(String token,
                             String lang) {
        repository.getShareLink(token, lang, shareAppLiveData);
    }

    public void logout(String token,
                       String lang) {
        repository.logout(token, lang, logoutLiveData);
    }
}
