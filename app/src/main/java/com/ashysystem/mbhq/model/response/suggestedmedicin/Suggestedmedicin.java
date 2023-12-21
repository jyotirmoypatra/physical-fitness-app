
package com.ashysystem.mbhq.model.response.suggestedmedicin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Suggestedmedicin implements Serializable {

    @SerializedName("MeditationDetails")
    @Expose
    private MeditationDetails meditationDetails;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public MeditationDetails getMeditationDetails() {
        return meditationDetails;
    }

    public void setMeditationDetails(MeditationDetails meditationDetails) {
        this.meditationDetails = meditationDetails;
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
