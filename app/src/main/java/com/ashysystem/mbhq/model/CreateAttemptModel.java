package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateAttemptModel {
    @SerializedName("Details")
    @Expose
    private Details details;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
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
    ///////////////////////
    public class Details {

        @SerializedName("QuestionnaireAttemptId")
        @Expose
        private Integer questionnaireAttemptId;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("QuestionnaireTypeId")
        @Expose
        private Integer questionnaireTypeId;
        @SerializedName("QuestionnaireType")
        @Expose
        private Object questionnaireType;
        @SerializedName("QuestionnaireStatusID")
        @Expose
        private Integer questionnaireStatusID;
        @SerializedName("QuestionnaireStatus")
        @Expose
        private Object questionnaireStatus;
        @SerializedName("Total")
        @Expose
        private Integer total;
        @SerializedName("Result")
        @Expose
        private Object result;
        @SerializedName("CreatedAt")
        @Expose
        private String createdAt;
        @SerializedName("UpdatedAt")
        @Expose
        private Object updatedAt;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("IsDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("RahePerceivedStressAnswerList")
        @Expose
        private Object rahePerceivedStressAnswerList;
        @SerializedName("CohenStressScaleAnswerList")
        @Expose
        private Object cohenStressScaleAnswerList;
        @SerializedName("QuestionnaireHappinessAnswerList")
        @Expose
        private List<Object> questionnaireHappinessAnswerList = null;

        public Integer getQuestionnaireAttemptId() {
            return questionnaireAttemptId;
        }

        public void setQuestionnaireAttemptId(Integer questionnaireAttemptId) {
            this.questionnaireAttemptId = questionnaireAttemptId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getQuestionnaireTypeId() {
            return questionnaireTypeId;
        }

        public void setQuestionnaireTypeId(Integer questionnaireTypeId) {
            this.questionnaireTypeId = questionnaireTypeId;
        }

        public Object getQuestionnaireType() {
            return questionnaireType;
        }

        public void setQuestionnaireType(Object questionnaireType) {
            this.questionnaireType = questionnaireType;
        }

        public Integer getQuestionnaireStatusID() {
            return questionnaireStatusID;
        }

        public void setQuestionnaireStatusID(Integer questionnaireStatusID) {
            this.questionnaireStatusID = questionnaireStatusID;
        }

        public Object getQuestionnaireStatus() {
            return questionnaireStatus;
        }

        public void setQuestionnaireStatus(Object questionnaireStatus) {
            this.questionnaireStatus = questionnaireStatus;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Object getRahePerceivedStressAnswerList() {
            return rahePerceivedStressAnswerList;
        }

        public void setRahePerceivedStressAnswerList(Object rahePerceivedStressAnswerList) {
            this.rahePerceivedStressAnswerList = rahePerceivedStressAnswerList;
        }

        public Object getCohenStressScaleAnswerList() {
            return cohenStressScaleAnswerList;
        }

        public void setCohenStressScaleAnswerList(Object cohenStressScaleAnswerList) {
            this.cohenStressScaleAnswerList = cohenStressScaleAnswerList;
        }

        public List<Object> getQuestionnaireHappinessAnswerList() {
            return questionnaireHappinessAnswerList;
        }

        public void setQuestionnaireHappinessAnswerList(List<Object> questionnaireHappinessAnswerList) {
            this.questionnaireHappinessAnswerList = questionnaireHappinessAnswerList;
        }

    }
}
