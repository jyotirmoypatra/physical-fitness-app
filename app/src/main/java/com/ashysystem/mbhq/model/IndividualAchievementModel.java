package com.ashysystem.mbhq.model;

import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by android-krishnendu on 3/16/17.
 */

public class IndividualAchievementModel {

    @SerializedName("Details")
    @Expose
    private MyAchievementsListInnerModel details;
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
