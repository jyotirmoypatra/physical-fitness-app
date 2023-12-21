package com.ashysystem.mbhq.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetPrompt {

    @SerializedName("PromptOftheDay")
    @Expose
    private String promptOftheDay;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public String getPromptOftheDay() {
        return promptOftheDay;
    }

    public void setPromptOftheDay(String promptOftheDay) {
        this.promptOftheDay = promptOftheDay;
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