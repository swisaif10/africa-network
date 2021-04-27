package com.mobiblanc.amdie.africa.network.datamanager.retrofit;

import com.mobiblanc.amdie.africa.network.models.authentication.SendSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.checkSMS.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.updateprofile.UpdateProfileData;
import com.mobiblanc.amdie.africa.network.models.checkversion.CheckVersionData;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.like.LikeModel;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;
import com.mobiblanc.amdie.africa.network.models.search.init_montoring.InitMontoringData;
import com.mobiblanc.amdie.africa.network.models.search.profile.Profile;
import com.mobiblanc.amdie.africa.network.models.search.update_mentore.UpdateMentoreData;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

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
    @POST(ApiEndpoints.ChECK_SMS_URL)
    Call<CheckSMSData> checkSMS(@Field("msisdn") String msisdn,
                                @Field("code_sms") String code,
                                @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.UPDATE_PROFILE_URL)
    Call<UpdateProfileData> updateProfile(@Field("token") String token,
                                          @Field("nom") String lastName,
                                          @Field("nomentreprise") String company,
                                          @Field("fonction") String job,
                                          @Field("email") String email,
                                          @Field("prenom") String firstName,
                                          @Field("country") int country,
                                          @Field("city") int city,
                                          @Field("nationality") int nationality);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_FEEDS_URL)
    Call<GetFeedData> getFeeds(@Field("token") String msisdn,
                               @Field("canal") String canal,
                               @Field("secteurs") String sectors,
                               @Field("type") String type,
                               @Field("date") String date,
                               @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_MENU_URL)
    Call<MenuData> getMenu(@Field("token") String token,
                           @Field("canal") String canal,
                           @Field("lang") String lang);


    @Multipart
    @POST(ApiEndpoints.UPDATE_MENTORE_URL)
    Call<UpdateMentoreData> updatemMentore(@Part("token") RequestBody token,
                                           @Part MultipartBody.Part pictureProfil,
                                           @Part MultipartBody.Part pictureEntreprise,
                                           @Part("lang") RequestBody lang,
                                           @Part("canal") RequestBody canal,
                                           @Part("presentation") RequestBody presentation,
                                           @Part("siege") RequestBody siege,
                                           @Part("secteur") RequestBody secteur,
                                           @Part("chiffredaffaire") RequestBody chiffredaffaire,
                                           @Part("effectif") RequestBody effectif,
                                           @Part("topics") RequestBody topics,

                                           @Part("devise") RequestBody devise,
                                           @Part("produits") RequestBody produit);


    @FormUrlEncoded
    @POST(ApiEndpoints.GET_INIT_MONTORING_URL)
    Call<InitMontoringData> getInit_Montoring(@Field("token") String token,
                                              @Field("canal") String canal,
                                              @Field("lang") String lang);

    @FormUrlEncoded
    @POST(ApiEndpoints.SET_LIKE_URL)
    Call<LikeModel> setLikeFeed(@Field("token") String token,
                                @Field("id") int id);

    @FormUrlEncoded
    @POST(ApiEndpoints.GET_PROFILE)
    Call<Profile> getProfile(@Field("token") String token,
                             @Field("canal") String canal,
                             @Field("lang") String lang);
}
