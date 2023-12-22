package com.ashysystem.mbhq.model;

/**
 * Created by android-arindam on 24/2/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProgressCourseResponse {

    @SerializedName("ArticleCount")
    @Expose
    private Integer articleCount;
    @SerializedName("UserArticleReadCount")
    @Expose
    private Integer userArticleReadCount;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public Integer getUserArticleReadCount() {
        return userArticleReadCount;
    }

    public void setUserArticleReadCount(Integer userArticleReadCount) {
        this.userArticleReadCount = userArticleReadCount;
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



