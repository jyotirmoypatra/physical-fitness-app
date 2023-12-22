package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by android-krishnendu on 1/30/17.
 */

public class ExerciseDetail {

    @SerializedName("ExerciseId")
    @Expose
    private Integer exerciseId;
    @SerializedName("ExerciseName")
    @Expose
    private String exerciseName;
    @SerializedName("Tips")
    @Expose
    private List<Object> tips = null;
    @SerializedName("Instructions")
    @Expose
    private List<Object> instructions = null;
    @SerializedName("Photos")
    @Expose
    private List<String> photos = null;
    @SerializedName("PhotosSmallPath")
    @Expose
    private List<String> photosSmallPath = null;
    @SerializedName("VideoUrl")
    @Expose
    private String videoUrl;
    @SerializedName("PublicUrl")
    @Expose
    private String publicUrl;
    @SerializedName("Equipments")
    @Expose
    private List<Object> equipments = null;
    @SerializedName("SubstituteEquipments")
    @Expose
    private List<Object> substituteEquipments = null;
    @SerializedName("SubstituteExercises")
    @Expose
    private List<Object> substituteExercises = null;
    @SerializedName("AltBodyWeights")
    @Expose
    private List<Object> altBodyWeights = null;
    @SerializedName("AltBodyWeightExercises")
    @Expose
    private List<Object> altBodyWeightExercises = null;
    @SerializedName("Tags")
    @Expose
    private Object tags;
    @SerializedName("LabelOnly")
    @Expose
    private Boolean labelOnly;

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

    public List<Object> getTips() {
        return tips;
    }

    public void setTips(List<Object> tips) {
        this.tips = tips;
    }

    public List<Object> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Object> instructions) {
        this.instructions = instructions;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<String> getPhotosSmallPath() {
        return photosSmallPath;
    }

    public void setPhotosSmallPath(List<String> photosSmallPath) {
        this.photosSmallPath = photosSmallPath;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public List<Object> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Object> equipments) {
        this.equipments = equipments;
    }

    public List<Object> getSubstituteEquipments() {
        return substituteEquipments;
    }

    public void setSubstituteEquipments(List<Object> substituteEquipments) {
        this.substituteEquipments = substituteEquipments;
    }

    public List<Object> getSubstituteExercises() {
        return substituteExercises;
    }

    public void setSubstituteExercises(List<Object> substituteExercises) {
        this.substituteExercises = substituteExercises;
    }

    public List<Object> getAltBodyWeights() {
        return altBodyWeights;
    }

    public void setAltBodyWeights(List<Object> altBodyWeights) {
        this.altBodyWeights = altBodyWeights;
    }

    public List<Object> getAltBodyWeightExercises() {
        return altBodyWeightExercises;
    }

    public void setAltBodyWeightExercises(List<Object> altBodyWeightExercises) {
        this.altBodyWeightExercises = altBodyWeightExercises;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }

    public Boolean getLabelOnly() {
        return labelOnly;
    }

    public void setLabelOnly(Boolean labelOnly) {
        this.labelOnly = labelOnly;
    }

}
