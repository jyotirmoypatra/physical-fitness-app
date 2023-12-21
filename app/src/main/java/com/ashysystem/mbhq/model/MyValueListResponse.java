package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by android-arindam on 6/3/17.
 */


    public class MyValueListResponse {

        @SerializedName("Details")
        @Expose
        private List<Detail> details = null;
        @SerializedName("SuccessFlag")
        @Expose
        private Boolean successFlag;
        @SerializedName("ErrorMessage")
        @Expose
        private String errorMessage;

        public List<Detail> getDetails() {
            return details;
        }

        public void setDetails(List<Detail> details) {
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
    public class Detail {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("rank")
        @Expose
        private Integer rank;
        @SerializedName("CreatedAt")
        @Expose
        private String createdAt;
        @SerializedName("CreatedBy")
        @Expose
        private Integer createdBy;
        @SerializedName("IsDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("Status")
        @Expose
        private Boolean status;
        @SerializedName("GoalValuesMaps")
        @Expose
        private Object goalValuesMaps;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public Object getGoalValuesMaps() {
            return goalValuesMaps;
        }

        public void setGoalValuesMaps(Object goalValuesMaps) {
            this.goalValuesMaps = goalValuesMaps;
        }

    }
}
