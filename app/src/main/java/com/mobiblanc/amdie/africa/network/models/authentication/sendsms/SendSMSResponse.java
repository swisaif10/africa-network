package com.mobiblanc.amdie.africa.network.models.authentication.sendsms;

import com.google.gson.annotations.SerializedName;

public class SendSMSResponse {
    @SerializedName("resend_by_email")
    private Boolean resendByEmail;

    public Boolean getResendByEmail() {
        return resendByEmail;
    }

    public void setResendByEmail(Boolean resendByEmail) {
        this.resendByEmail = resendByEmail;
    }
}
