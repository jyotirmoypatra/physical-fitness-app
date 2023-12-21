package com.ashysystem.mbhq.model.response;

/**
 * Created by android-arindam on 24/2/17.
 */


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class AddCourseResponseModel {

    @SerializedName("NewData")
    @Expose
    private Integer newData;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public Integer getNewData() {
        return newData;
    }

    public void setNewData(Integer newData) {
        this.newData = newData;
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
