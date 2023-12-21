package com.ashysystem.mbhq.model.habit_hacker;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetHabitTemplatesResponseModel {

    @SerializedName("HabitTemplates")
    @Expose
    private List<HabitTemplate> habitTemplates = null;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public List<HabitTemplate> getHabitTemplates() {
        return habitTemplates;
    }

    public void setHabitTemplates(List<HabitTemplate> habitTemplates) {
        this.habitTemplates = habitTemplates;
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

    public class HabitTemplate {

        @SerializedName("Section")
        @Expose
        private String section;
        @SerializedName("TemplateDetails")
        @Expose
        private List<TemplateDetail> templateDetails = null;

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public List<TemplateDetail> getTemplateDetails() {
            return templateDetails;
        }

        public void setTemplateDetails(List<TemplateDetail> templateDetails) {
            this.templateDetails = templateDetails;
        }

    }

    public class TemplateDetail {

        @SerializedName("HabitTemplateId")
        @Expose
        private Integer habitTemplateId;
        @SerializedName("HabitToCreate")
        @Expose
        private String habitToCreate;
        @SerializedName("HabitToBreak")
        @Expose
        private String habitToBreak;
        @SerializedName("HabitFrequencyId")
        @Expose
        private Integer habitFrequencyId;
        @SerializedName("IsSundayTask")
        @Expose
        private Boolean isSundayTask;
        @SerializedName("IsMondayTask")
        @Expose
        private Boolean isMondayTask;
        @SerializedName("IsTuesdayTask")
        @Expose
        private Boolean isTuesdayTask;
        @SerializedName("IsWednesdayTask")
        @Expose
        private Boolean isWednesdayTask;
        @SerializedName("IsThursdayTask")
        @Expose
        private Boolean isThursdayTask;
        @SerializedName("IsFridayTask")
        @Expose
        private Boolean isFridayTask;
        @SerializedName("IsSaturdayTask")
        @Expose
        private Boolean isSaturdayTask;
        @SerializedName("TaskMonthly")
        @Expose
        private Integer taskMonthly;
        @SerializedName("ImageUrl")
        @Expose
        private String imageUrl;

        public Integer getHabitTemplateId() {
            return habitTemplateId;
        }

        public void setHabitTemplateId(Integer habitTemplateId) {
            this.habitTemplateId = habitTemplateId;
        }

        public String getHabitToCreate() {
            return habitToCreate;
        }

        public void setHabitToCreate(String habitToCreate) {
            this.habitToCreate = habitToCreate;
        }

        public String getHabitToBreak() {
            return habitToBreak;
        }

        public void setHabitToBreak(String habitToBreak) {
            this.habitToBreak = habitToBreak;
        }

        public Integer getHabitFrequencyId() {
            return habitFrequencyId;
        }

        public void setHabitFrequencyId(Integer habitFrequencyId) {
            this.habitFrequencyId = habitFrequencyId;
        }

        public Boolean getIsSundayTask() {
            return isSundayTask;
        }

        public void setIsSundayTask(Boolean isSundayTask) {
            this.isSundayTask = isSundayTask;
        }

        public Boolean getIsMondayTask() {
            return isMondayTask;
        }

        public void setIsMondayTask(Boolean isMondayTask) {
            this.isMondayTask = isMondayTask;
        }

        public Boolean getIsTuesdayTask() {
            return isTuesdayTask;
        }

        public void setIsTuesdayTask(Boolean isTuesdayTask) {
            this.isTuesdayTask = isTuesdayTask;
        }

        public Boolean getIsWednesdayTask() {
            return isWednesdayTask;
        }

        public void setIsWednesdayTask(Boolean isWednesdayTask) {
            this.isWednesdayTask = isWednesdayTask;
        }

        public Boolean getIsThursdayTask() {
            return isThursdayTask;
        }

        public void setIsThursdayTask(Boolean isThursdayTask) {
            this.isThursdayTask = isThursdayTask;
        }

        public Boolean getIsFridayTask() {
            return isFridayTask;
        }

        public void setIsFridayTask(Boolean isFridayTask) {
            this.isFridayTask = isFridayTask;
        }

        public Boolean getIsSaturdayTask() {
            return isSaturdayTask;
        }

        public void setIsSaturdayTask(Boolean isSaturdayTask) {
            this.isSaturdayTask = isSaturdayTask;
        }

        public Integer getTaskMonthly() {
            return taskMonthly;
        }

        public void setTaskMonthly(Integer taskMonthly) {
            this.taskMonthly = taskMonthly;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

    }

}
