
package com.ashysystem.mbhq.model.LoginRes;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("SessionDetail")
    @Expose
    private SessionDetail sessionDetail;
    @SerializedName("PurchasedPrograms")
    @Expose
    private List<Object> purchasedPrograms;
    @SerializedName("IsSubscribed")
    @Expose
    private Boolean isSubscribed;
    @SerializedName("Jwt")
    @Expose
    private String jwt;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public SessionDetail getSessionDetail() {
        return sessionDetail;
    }

    public void setSessionDetail(SessionDetail sessionDetail) {
        this.sessionDetail = sessionDetail;
    }

    public List<Object> getPurchasedPrograms() {
        return purchasedPrograms;
    }

    public void setPurchasedPrograms(List<Object> purchasedPrograms) {
        this.purchasedPrograms = purchasedPrograms;
    }

    public Boolean getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(Boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
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
