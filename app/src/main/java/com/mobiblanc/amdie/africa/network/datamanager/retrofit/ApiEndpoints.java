package com.mobiblanc.amdie.africa.network.datamanager.retrofit;

public interface ApiEndpoints {
    String CHECK_VERSION_URL = "api-be/api/check-version";
    String SEND_SMS_URL = "api-be/api/send/sms";
    String ChECK_SMS_URL = "api-be/api/check/sms";
    String UPDATE_PROFILE_URL = "api-be/api/update/profile";
    String GET_FEEDS_URL = "api-be/api/get/feed";
    String GET_MENU_URL = "api-be/api/menu";
    String SHARE_APP_URL = "api-be/api/assistance/share";
    String GET_DISCUSSIONS_URL = "api-be/api/histo/messenger";
    String GET_MESSAGES_URL = "api-be/api/list/messenger";
    String SEND_MESSAGE_URL = "api-be/api/add/messenger";
    String GET_CGU_URL = "api-be/api/assistance/conditions-generales";
    String LOGOUT_URL = "api-be/api/logout";
    String LIKE_FEED_URL = "api-be/api/page/like";
    String GET_CONTACTS_URL = "api-be/api/search/monitoring";
    String ADD_CONTACT_TO_FAVOURITE_URL = "api-be/api/add/demande";
    String REMOVE_CONTACT_FROM_FAVOURITE_URL = "api-be/api/remove/demande";
    String GET_FAVOURITES_LIST_URL = "api-be/api/list/demande";
    String GET_SUGGESTIONS_LIST_URL = "api-be/api/search/top-monitoring";

    String UPDATE_MENTORE_URL = "api-be/api/update/mentore";
    String GET_INIT_MONTORING_URL = "api-be/api/init/montoring ";
    String GET_PROFILE = "api-be/api/get/profile";
}