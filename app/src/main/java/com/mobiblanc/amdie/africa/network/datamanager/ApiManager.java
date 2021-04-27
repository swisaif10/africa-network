package com.mobiblanc.amdie.africa.network.datamanager;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.mobiblanc.amdie.africa.network.BuildConfig;
import com.mobiblanc.amdie.africa.network.Utilities.Resource;
import com.mobiblanc.amdie.africa.network.datamanager.retrofit.RetrofitClient;
import com.mobiblanc.amdie.africa.network.models.authentication.SendSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.checkSMS.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.updateprofile.UpdateProfileData;
import com.mobiblanc.amdie.africa.network.models.cgu.CGUData;
import com.mobiblanc.amdie.africa.network.models.checkversion.CheckVersionData;
import com.mobiblanc.amdie.africa.network.models.contacts.favourite.AddFavouriteData;
import com.mobiblanc.amdie.africa.network.models.contacts.list.ContactsListData;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.like.LikeFeedData;
import com.mobiblanc.amdie.africa.network.models.logout.LogoutData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;
import com.mobiblanc.amdie.africa.network.models.messaging.discussions.DiscussionsListData;
import com.mobiblanc.amdie.africa.network.models.messaging.messages.MessagesListData;
import com.mobiblanc.amdie.africa.network.models.messaging.sending.SendMessageData;
import com.mobiblanc.amdie.africa.network.models.share.ShareAppData;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
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
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<SendSMSData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void checkSMS(String msisdn, String code, String firebaseToken, String lang, MutableLiveData<Resource<CheckSMSData>> mutableLiveData) {
        Call<CheckSMSData> call = RetrofitClient.getInstance().endpoint().checkSMS(msisdn, code, firebaseToken, lang);
        call.enqueue(new Callback<CheckSMSData>() {
            @Override
            public void onResponse(@NonNull Call<CheckSMSData> call, @NonNull Response<CheckSMSData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
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
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
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
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
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
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<MenuData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void getShareLink(String token,
                             String lang,
                             MutableLiveData<Resource<ShareAppData>> mutableLiveData) {
        Call<ShareAppData> call = RetrofitClient.getInstance().endpoint().getShareLink(token, lang);
        call.enqueue(new Callback<ShareAppData>() {
            @Override
            public void onResponse(@NonNull Call<ShareAppData> call, @NonNull Response<ShareAppData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<ShareAppData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void getDiscussionsList(String token,
                                   String lang,
                                   MutableLiveData<Resource<DiscussionsListData>> mutableLiveData) {
        Call<DiscussionsListData> call = RetrofitClient.getInstance().endpoint().getDiscussionsList(token, lang);
        call.enqueue(new Callback<DiscussionsListData>() {
            @Override
            public void onResponse(@NonNull Call<DiscussionsListData> call, @NonNull Response<DiscussionsListData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<DiscussionsListData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void getMessagesList(String token,
                                int receiver,
                                String lang,
                                MutableLiveData<Resource<MessagesListData>> mutableLiveData) {
        Call<MessagesListData> call = RetrofitClient.getInstance().endpoint().getMessagesList(token, receiver, lang);
        call.enqueue(new Callback<MessagesListData>() {
            @Override
            public void onResponse(@NonNull Call<MessagesListData> call, @NonNull Response<MessagesListData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<MessagesListData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void sendMessage(String token,
                            int receiver,
                            String message,
                            String lang,
                            MutableLiveData<Resource<SendMessageData>> mutableLiveData) {
        Call<SendMessageData> call = RetrofitClient.getInstance().endpoint().sendMessage(token, receiver, message, lang);
        call.enqueue(new Callback<SendMessageData>() {
            @Override
            public void onResponse(@NonNull Call<SendMessageData> call, @NonNull Response<SendMessageData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<SendMessageData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void getCGU(String token,
                       String lang,
                       MutableLiveData<Resource<CGUData>> mutableLiveData) {
        Call<CGUData> call = RetrofitClient.getInstance().endpoint().getCGU(token, lang);
        call.enqueue(new Callback<CGUData>() {
            @Override
            public void onResponse(@NonNull Call<CGUData> call, @NonNull Response<CGUData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<CGUData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void logout(String token,
                       String lang,
                       MutableLiveData<Resource<LogoutData>> mutableLiveData) {
        Call<LogoutData> call = RetrofitClient.getInstance().endpoint().logout(token, lang);
        call.enqueue(new Callback<LogoutData>() {
            @Override
            public void onResponse(@NonNull Call<LogoutData> call, @NonNull Response<LogoutData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<LogoutData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void likeFeed(String token,
                         int id,
                         MutableLiveData<Resource<LikeFeedData>> mutableLiveData) {
        Call<LikeFeedData> call = RetrofitClient.getInstance().endpoint().likeFeed(token, id);
        call.enqueue(new Callback<LikeFeedData>() {
            @Override
            public void onResponse(@NonNull Call<LikeFeedData> call, @NonNull Response<LikeFeedData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<LikeFeedData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void getContactsList(String token,
                                int page,
                                String searchValue,
                                String lang,
                                MutableLiveData<Resource<ContactsListData>> mutableLiveData) {
        Call<ContactsListData> call = RetrofitClient.getInstance().endpoint().getContactsList(token, "mobile", page, searchValue, lang);
        call.enqueue(new Callback<ContactsListData>() {
            @Override
            public void onResponse(@NonNull Call<ContactsListData> call, @NonNull Response<ContactsListData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<ContactsListData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void addFavourite(String token,
                             int receiver,
                             MutableLiveData<Resource<AddFavouriteData>> mutableLiveData) {
        Call<AddFavouriteData> call = RetrofitClient.getInstance().endpoint().addFavourite(token, "mobile", receiver);
        call.enqueue(new Callback<AddFavouriteData>() {
            @Override
            public void onResponse(@NonNull Call<AddFavouriteData> call, @NonNull Response<AddFavouriteData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<AddFavouriteData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void removeFavourite(String token,
                                int id,
                                MutableLiveData<Resource<AddFavouriteData>> mutableLiveData) {
        Call<AddFavouriteData> call = RetrofitClient.getInstance().endpoint().removeFavourite(token, id);
        call.enqueue(new Callback<AddFavouriteData>() {
            @Override
            public void onResponse(@NonNull Call<AddFavouriteData> call, @NonNull Response<AddFavouriteData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<AddFavouriteData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void getFavouritesList(String token,
                                  String lang,
                                  MutableLiveData<Resource<ContactsListData>> mutableLiveData) {
        Call<ContactsListData> call = RetrofitClient.getInstance().endpoint().getFavouritesList(token, "mobile", lang);
        call.enqueue(new Callback<ContactsListData>() {
            @Override
            public void onResponse(@NonNull Call<ContactsListData> call, @NonNull Response<ContactsListData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<ContactsListData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

    public void getSuggestionsList(String token,
                                   int page,
                                   String lang,
                                   MutableLiveData<Resource<ContactsListData>> mutableLiveData) {
        Call<ContactsListData> call = RetrofitClient.getInstance().endpoint().getSuggestionsList(token, page, "mobile", lang);
        call.enqueue(new Callback<ContactsListData>() {
            @Override
            public void onResponse(@NonNull Call<ContactsListData> call, @NonNull Response<ContactsListData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<ContactsListData> call, @NonNull Throwable t) {
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
        Call<UpdateProfileData> call = RetrofitClient.getInstance().endpoint().updatemMentore(token, pictureProfil,
                pictureEntreprise, lang, canal, presentation, siege, secteur, chiffredaffaire, effectif, topics, experiences);
        call.enqueue(new Callback<UpdateProfileData>() {
            @Override
            public void onResponse(@NonNull Call<UpdateProfileData> call, @NonNull Response<UpdateProfileData> response) {
                if (response.body() != null)
                    if (response.body().getHeader().getStatus().equalsIgnoreCase("ko"))
                        mutableLiveData.setValue(Resource.error(response.body().getHeader().getMessage(), null));
                    else if (response.body().getHeader().getStatus().equalsIgnoreCase("ERROR_AUTHENTIFICATION"))
                        mutableLiveData.setValue(Resource.invalidToken(response.body()));
                    else
                        mutableLiveData.setValue(Resource.success(response.body()));
                else
                    mutableLiveData.setValue(Resource.error("Internal Server Error", null));
            }

            @Override
            public void onFailure(@NonNull Call<UpdateProfileData> call, @NonNull Throwable t) {
                HandleThrowableException(t, mutableLiveData);
            }
        });
    }

}
