package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateBadgeShownResponse {
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean SuccessFlag;

    @SerializedName("ErrorMessage")
    @Expose
    private String ErrorMessage;

    public Boolean getSuccessFlag() {
        return SuccessFlag;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }
}
