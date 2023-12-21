package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddRewardModel {
    @SerializedName("UserActionId")
    @Expose
    private Integer userActionId;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public Integer getUserActionId() {
        return userActionId;
    }

    public void setUserActionId(Integer userActionId) {
        this.userActionId = userActionId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
