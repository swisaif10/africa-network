package com.mobiblanc.amdie.africa.network.datamanager.retrofit;

import com.mobiblanc.amdie.africa.network.models.authentication.SendSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.checkSMS.CheckSMSData;
import com.mobiblanc.amdie.africa.network.models.authentication.updateprofile.UpdateProfileData;
import com.mobiblanc.amdie.africa.network.models.checkversion.CheckVersionData;
import com.mobiblanc.amdie.africa.network.models.feed.GetFeedData;
import com.mobiblanc.amdie.africa.network.models.menu.MenuData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
}
