package com.mobiblanc.amdie.africa.network.datamanager;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.datamanager.retrofit.RetrofitClient;
import com.mobiblanc.amdie.africa.network.models.authentication.SendSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.checkSMS.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.updateprofile.UpdateProfileData;
import com.mobiblanc.amdie.africa.network.models.checkversion.CheckVersionData;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class ApiManager {

    private void HandleThrowableException(Throwable e, MutableLiveData mutableLiveData) {
        if (e instanceof UnknownHostException || e instanceof ConnectException || e instanceof SocketTimeoutException) {
            mutableLiveData.setValue(Resource.error("Connexion réseau indisponible. Assurez-vous que votre connexion réseau est active et réessayez.", null));
        } else {
            mutableLiveData.setValue(Resource.error(e.getMessage(), null));
        }
    }

    public void checkVersion(MutableLiveData<Resource<CheckVersionData>> mutableLiveData) {
        Call<CheckVersionData> call = RetrofitClient.getInstance().endpoint().checkVersion(BuildConfig.VERSION_NAME, "Android");
        call.enqueue(new Callback<CheckVersionData>() {
            @Override
            public void onResponse(@NonNull Call<CheckVersionData> call, @NonNull Response<CheckVersionData> response) {
                assert response.body() != null;
                if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                    mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                else
                    mutableLiveData.setValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<CheckVersionData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void sendSMS(String msisdn, String lang, String uid, MutableLiveData<Resource<SendSMSData>> mutableLiveData) {
        Call<SendSMSData> call = RetrofitClient.getInstance().endpoint().sendSMS(msisdn, lang, uid);
        call.enqueue(new Callback<SendSMSData>() {
            @Override
            public void onResponse(@NonNull Call<SendSMSData> call, @NonNull Response<SendSMSData> response) {
                assert response.body() != null;
                if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                    mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                else
                    mutableLiveData.setValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<SendSMSData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void checkSMS(String msisdn, String code, String lang, MutableLiveData<Resource<CheckSMSData>> mutableLiveData) {
        Call<CheckSMSData> call = RetrofitClient.getInstance().endpoint().checkSMS(msisdn, code, lang);
        call.enqueue(new Callback<CheckSMSData>() {
            @Override
            public void onResponse(@NonNull Call<CheckSMSData> call, @NonNull Response<CheckSMSData> response) {
                assert response.body() != null;
                if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                    mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                else
                    mutableLiveData.setValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<CheckSMSData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void updateProfile(String token,
                              String lastName,
                              String company,
                              String job,
                              String email,
                              String firstName,
                              int country,
                              int city,
                              int nationality,
                              MutableLiveData<Resource<UpdateProfileData>> mutableLiveData) {
        Call<UpdateProfileData> call = RetrofitClient.getInstance().endpoint().updateProfile(token, lastName, company, job, email, firstName, country, city, nationality);
        call.enqueue(new Callback<UpdateProfileData>() {
            @Override
            public void onResponse(@NonNull Call<UpdateProfileData> call, @NonNull Response<UpdateProfileData> response) {
                assert response.body() != null;
                if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                    mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                else
                    mutableLiveData.setValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<UpdateProfileData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void getFeeds(String token,
                         String sectors,
                         String type,
                         String date,
                         String lang,
                         MutableLiveData<Resource<GetFeedData>> mutableLiveData) {
        Call<GetFeedData> call = RetrofitClient.getInstance().endpoint().getFeeds(token, "mobile", sectors, type, date, lang);
        call.enqueue(new Callback<GetFeedData>() {
            @Override
            public void onResponse(@NonNull Call<GetFeedData> call, @NonNull Response<GetFeedData> response) {
                assert response.body() != null;
                if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                    mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                else
                    mutableLiveData.setValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<GetFeedData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void getMenu(String token,
                        String lang,
                        MutableLiveData<Resource<MenuData>> mutableLiveData) {
        Call<MenuData> call = RetrofitClient.getInstance().endpoint().getMenu(token, "mobile", lang);
        call.enqueue(new Callback<MenuData>() {
            @Override
            public void onResponse(@NonNull Call<MenuData> call, @NonNull Response<MenuData> response) {
                assert response.body() != null;
                if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                    mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                else
                    mutableLiveData.setValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<MenuData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }


    public void updateMentore(String token,
                              String pictureProfil,
                              String pictureEntreprise,
                              String lang,
                              String canal,
                              String presentation,
                              String siege,
                              String secteur,
                              String chiffredaffaire,
                              String effectif,
                              String topics,
                              String experiences,
                              MutableLiveData<Resource<UpdateProfileData>> mutableLiveData) {
        Call<UpdateProfileData> call = RetrofitClient.getInstance().endpoint().updatemMentore(token,  pictureProfil,
                pictureEntreprise, lang, canal, presentation, siege, secteur, chiffredaffaire, effectif,topics, experiences);
        call.enqueue(new Callback<UpdateProfileData>() {
            @Override
            public void onResponse(@NonNull Call<UpdateProfileData> call, @NonNull Response<UpdateProfileData> response) {
                assert response.body() != null;
                if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                    mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                else
                    mutableLiveData.setValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<UpdateProfileData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

}
