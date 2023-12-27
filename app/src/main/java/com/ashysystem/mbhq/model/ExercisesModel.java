package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by android-krishnendu on 1/27/17.
 */

public class ExercisesModel {

    @SerializedName("ExerciseId")
    @Expose
    private Integer exerciseId;
    @SerializedName("ExerciseName")
    @Expose
    private String exerciseName;
    @SerializedName("OldExerciseId")
    @Expose
    private Integer oldExerciseId;

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Integer getOldExerciseId() {
        return oldExerciseId;
    }

    public void setOldExerciseId(Integer oldExerciseId) {
        this.oldExerciseId = oldExerciseId;
    }

}
