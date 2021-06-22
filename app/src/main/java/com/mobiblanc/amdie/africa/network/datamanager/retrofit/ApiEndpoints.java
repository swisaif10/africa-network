package com.mobiblanc.amdie.africa.network.datamanager.retrofit;

public interface ApiEndpoints {
    String CHECK_VERSION_URL = "api/check-version";
    String SEND_SMS_URL = "api/send/sms";
    String ChECK_SMS_URL = "api/check/sms";
    String COMPLETE_REGISTRATION_URL = "api/update/profile";
    String SEND_OTP_EMAIL_URL = "api/send/otp-email";
    String CHECK_OTP_EMAIL_URL = "api/check/otp";
    String GET_FEEDS_URL = "api/get/feed";
    String GET_MENU_URL = "api/menu";
    String SHARE_APP_URL = "api/assistance/share";
    String GET_DISCUSSIONS_URL = "api/histo/messenger";
    String GET_MESSAGES_URL = "api/list/messenger";
    String SEND_MESSAGE_URL = "api/add/messenger";
    String GET_CGU_URL = "api/assistance/conditions-generales";
    String LOGOUT_URL = "api/logout";
    String LIKE_FEED_URL = "api/page/like";
    String GET_CONTACTS_URL = "api/search/monitoring";
    String ADD_CONTACT_TO_FAVOURITE_URL = "api/add/demande";
    String REMOVE_CONTACT_FROM_FAVOURITE_URL = "api/remove/demande";
    String GET_FAVOURITES_LIST_URL = "api/list/demande";
    String GET_SUGGESTIONS_LIST_URL = "api/search/top-monitoring";
    String UPDATE_PROFILE_URL = "api/update/mentore";
    String INIT_PROFILE_FORM_URL = "api/init/montoring ";
    String GET_PROFILE_DETAILS_URL = "api/get/profile";
    String GET_PROFILE_FOR_UPDATE_URL = "api/get/mentore";
    String LINKEDIN_AUTHENTICATION_URL = "api/linkedin/callback";
    String GET_COUNTRIES_LIST_URL = "api/countries";
    String GET_CONTACTS_FILTER_FORM_URL = "api/search/form";
}