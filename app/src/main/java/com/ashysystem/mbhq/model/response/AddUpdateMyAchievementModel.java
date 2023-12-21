package com.ashysystem.mbhq.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by android-krishnendu on 29/6/17.
 */

public class AddUpdateMyAchievementModel {

    @SerializedName("Details")
    @Expose
    private MyAchievementsListInnerModel details = null;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public MyAchievementsListInnerModel getDetails() {
        return details;
    }

    public void setDetails(MyAchievementsListInnerModel details) {
        this.details = details;
    }

    public Boolean getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Boolean successFlag) {
        this.successFlag = successFlag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
