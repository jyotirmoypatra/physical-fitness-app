package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMeditationCacheExpiryTimeResponse {
    @SerializedName("ExpiryDateTime")
    @Expose
    private String expiryDateTime;

    @SerializedName("IsExpired")
    @Expose
    private boolean isExpired;

    @SerializedName("SuccessFlag")
    @Expose
    private boolean successFlag;

    @SerializedName("ErrorMessage")
    @Expose
    private Object ErrorMessage;

    public String getExpiryDateTime() {
        return expiryDateTime;
    }

    public boolean getIsExpired() {
        return isExpired;
    }

    public boolean getSuccessFlag() {
        return successFlag;
    }
}
