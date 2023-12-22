package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by android-krishnendu on 1/30/17.
 */

public class ShowIndividualVideoModel {

    @SerializedName("ExerciseDetail")
    @Expose
    private ExerciseDetail exerciseDetail;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public ExerciseDetail getExerciseDetail() {
        return exerciseDetail;
    }

    public void setExerciseDetail(ExerciseDetail exerciseDetail) {
        this.exerciseDetail = exerciseDetail;
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
