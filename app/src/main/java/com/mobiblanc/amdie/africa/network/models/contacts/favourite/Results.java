package com.mobiblanc.amdie.africa.network.models.contacts.favourite;

import com.google.gson.annotations.SerializedName;

public class Results {
    @SerializedName("demande_id")
    private int requestId;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
