package com.mobiblanc.amdie.africa.network.datamanager.retrofit;

import com.mobiblanc.amdie.africa.network.models.authentication.checksms.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.completeregistraion.CompleteRegistrationData;
import com.mobiblanc.amdie.africa.network.models.authentication.sendsms.SendSMSData;
import com.mobiblanc.amdie.africa.network.models.cgu.CGUData;
import com.mobiblanc.amdie.africa.network.models.checkversion.CheckVersionData;
import com.mobiblanc.amdie.africa.network.models.contacts.favourite.AddFavouriteData;
import com.mobiblanc.amdie.africa.network.models.contacts.filter.ContactsFilterData;
import com.mobiblanc.amdie.africa.network.models.contacts.list.ContactsListData;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.like.LikeFeedData;
import com.mobiblanc.amdie.africa.network.models.logout.LogoutData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;
import com.mobiblanc.amdie.africa.network.models.messaging.discussions.DiscussionsListData;
import com.mobiblanc.amdie.africa.network.models.messaging.messages.MessagesListData;
import com.mobiblanc.amdie.africa.network.models.messaging.sending.SendMessageData;
import com.mobiblanc.amdie.africa.network.models.profile.countries.CountriesListData;
import com.mobiblanc.amdie.africa.network.models.profile.details.ProfileDetailsData;
import com.mobiblanc.amdie.africa.network.models.profile.initform.InitProfileFormData;
import com.mobiblanc.amdie.africa.network.models.profile.update.ProfileDetailsForUpdateData;
import com.mobiblanc.amdie.africa.network.models.profile.update.UpdateProfileData;
import com.mobiblanc.amdie.africa.network.models.share.ShareAppData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServices {

    @FormUrlEncoded
    @POST(ApiEndpoints.CHECK_VERSION_URL)
    Call<CheckVersionData> checkVersion(@Field("version") String version,
                                        @Field("os") String os);

    @FormUrlEncoded
    @POST(ApiEndpoints.SEND_SMS_URL)
    Call<SendSMSData> sendSMS(@Field("msisdn") String msisdn,
                              @Field("lang") String lang,
                              @Field("device_uid") String uid);

    @FormUrlEncoded
    @POST(ApiEndpoints.LINKEDIN_AUTHENTICATION_URL)
    Call<CheckSMSData> loginWithLinkedin(@Field("code") String code,
                                         @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.ChECK_SMS_URL)
    Call<CheckSMSData> checkSMS(@Field("msisdn") String msisdn,
                                @Field("code_sms") String code,
                                @Field("firebase_token") String firebaseToken,
                                @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.SEND_OTP_EMAIL_URL)
    Call<SendSMSData> sendOTPByEmail(@Field("msisdn") String msisdn,
                                     @Field("lang") String lang,
                                     @Field("device_uid") String uid);

    @FormUrlEncoded
    @POST(ApiEndpoints.CHECK_OTP_EMAIL_URL)
    Call<CheckSMSData> checkOTPByEmail(@Field("msisdn") String msisdn,
                                       @Field("code_sms") String code,
                                       @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.COMPLETE_REGISTRATION_URL)
    Call<CompleteRegistrationData> completeRegistration(@Field("token") String token,
                                                        @Field("gender") String gender,
                                                        @Field("nom") String lastName,
                                                        @Field("nomentreprise") String company,
                                                        @Field("fonction") String job,
                                                        @Field("email") String email,
                                                        @Field("prenom") String firstName,
                                                        @Field("country") int country,
                                                        @Field("city") int city,
                                                        @Field("nationality") int nationality,
                                                        @Field("firebase_token") String firebaseToken,
                                                        @Field("code") String code,
                                                        @Field("telephone") String phoneNumber,
                                                        @Field("fonction_text") String otherJob);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_FEEDS_URL)
    Call<GetFeedData> getFeeds(@Field("token") String msisdn,
                               @Field("canal") String canal,
                               @Field("secteurs") String sectors,
                               @Field("type") String type,
                               @Field("date") String date,
                               @Field("must_liked") Boolean mostLiked,
                               @Field("offset") int offset,
                               @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_MENU_URL)
    Call<MenuData> getMenu(@Field("token") String token,
                           @Field("canal") String canal,
                           @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.SHARE_APP_URL)
    Call<ShareAppData> getShareLink(@Field("token") String token,
                                    @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_DISCUSSIONS_URL)
    Call<DiscussionsListData> getDiscussionsList(@Field("token") String token,
                                                 @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_MESSAGES_URL)
    Call<MessagesListData> getMessagesList(@Field("token") String token,
                                           @Field("receiver") int receiver,
                                           @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.SEND_MESSAGE_URL)
    Call<SendMessageData> sendMessage(@Field("token") String token,
                                      @Field("receiver") int receiver,
                                      @Field("message") String message,
                                      @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_CGU_URL)
    Call<CGUData> getCGU(@Field("token") String token,
                         @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.LOGOUT_URL)
    Call<LogoutData> logout(@Field("token") String token,
                            @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.LIKE_FEED_URL)
    Call<LikeFeedData> likeFeed(@Field("token") String token,
                                @Field("id") int id);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_CONTACTS_URL)
    Call<ContactsListData> getContactsList(@Field("token") String token,
                                           @Field("canal") String canal,
                                           @Field("page") int page,
                                           @Field("country_id") String countryId,
                                           @Field("secteur_id") String sectorId,
                                           @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.ADD_CONTACT_TO_FAVOURITE_URL)
    Call<AddFavouriteData> addFavourite(@Field("token") String token,
                                        @Field("canal") String canal,
                                        @Field("receiver") int receiver);

    @FormUrlEncoded
    @POST(ApiEndpoints.REMOVE_CONTACT_FROM_FAVOURITE_URL)
    Call<AddFavouriteData> removeFavourite(@Field("token") String token,
                                           @Field("id") int id);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_FAVOURITES_LIST_URL)
    Call<ContactsListData> getFavouritesList(@Field("token") String token,
                                             @Field("canal") String canal,
                                             @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_SUGGESTIONS_LIST_URL)
    Call<ContactsListData> getSuggestionsList(@Field("token") String token,
                                              @Field("page") int page,
                                              @Field("canal") String canal,
                                              @Field("searchValue") String searchValue,
                                              @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.INIT_PROFILE_FORM_URL)
    Call<InitProfileFormData> initProfileForm(@Field("token") String token,
                                              @Field("canal") String canal,
                                              @Field("lang") String lang);

    @Multipart
    @POST(ApiEndpoints.UPDATE_PROFILE_URL)
    Call<UpdateProfileData> updateProfile(@Part("token") RequestBody token,
                                          @Part MultipartBody.Part profilePicture,
                                          @Part MultipartBody.Part companyPicture,
                                          @Part("lang") RequestBody lang,
                                          @Part("canal") RequestBody canal,
                                          @Part("presentation") RequestBody presentation,
                                          @Part("siege") RequestBody headquarter,
                                          @Part("secteur") RequestBody sector,
                                          @Part("chiffredaffaire") RequestBody revenues,
                                          @Part("effectif") RequestBody companySize,
                                          @Part("topics") RequestBody topics,
                                          @Part("devise") RequestBody currency,
                                          @Part("produits") RequestBody products);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_PROFILE_DETAILS_URL)
    Call<ProfileDetailsData> getProfile(@Field("token") String token,
                                        @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_PROFILE_FOR_UPDATE_URL)
    Call<ProfileDetailsForUpdateData> getProfileForUpdate(@Field("token") String token,
                                                          @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_COUNTRIES_LIST_URL)
    Call<CountriesListData> getCountries(@Field("token") String token,
                                         @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_CONTACTS_FILTER_FORM_URL)
    Call<ContactsFilterData> getContactsFilterForm(@Field("token") String token,
                                                   @Field("lang") String lang);
}
