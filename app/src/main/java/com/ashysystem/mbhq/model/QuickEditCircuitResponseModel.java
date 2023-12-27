package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by android-krishnendu on 1/27/17.
 */

public class QuickEditCircuitResponseModel {

    @SerializedName("OldExerciseSessionId")
    @Expose
    private Integer oldExerciseSessionId;
    @SerializedName("ExerciseSessionId")
    @Expose
    private Integer exerciseSessionId;
    @SerializedName("IsPersonalised")
    @Expose
    private Boolean isPersonalised;
    @SerializedName("ExercisesModel")
    @Expose
    private List<ExercisesModel> exercisesModel = null;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public Integer getOldExerciseSessionId() {
        return oldExerciseSessionId;
    }

    public void setOldExerciseSessionId(Integer oldExerciseSessionId) {
        this.oldExerciseSessionId = oldExerciseSessionId;
    }

    public Integer getExerciseSessionId() {
        return exerciseSessionId;
    }

    public void setExerciseSessionId(Integer exerciseSessionId) {
        this.exerciseSessionId = exerciseSessionId;
    }

    public Boolean getIsPersonalised() {
        return isPersonalised;
    }

    public void setIsPersonalised(Boolean isPersonalised) {
        this.isPersonalised = isPersonalised;
    }

    public List<ExercisesModel> getExercisesModel() {
        return exercisesModel;
    }

    public void setExercisesModel(List<ExercisesModel> exercisesModel) {
        this.exercisesModel = exercisesModel;
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
