package com.ashysystem.mbhq.model;

import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android-krishnendu on 3/16/17.
 */

public class MyAchievementsListModel {

    @SerializedName("Details")
    @Expose
    private ArrayList<MyAchievementsListInnerModel> details = null;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("TotalCount")
    @Expose
    private Integer TotalCount;

    public Integer getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(Integer totalCount) {
        TotalCount = totalCount;
    }

    public ArrayList<MyAchievementsListInnerModel> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<MyAchievementsListInnerModel> details) {
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
