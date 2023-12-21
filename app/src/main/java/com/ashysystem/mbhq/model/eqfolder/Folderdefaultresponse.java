
package com.ashysystem.mbhq.model.eqfolder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Folderdefaultresponse {

    @SerializedName("EqFolders")
    @Expose
    private ArrayList<UserEqFolder> userEqFolders;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public ArrayList<UserEqFolder> getUserEqFolders() {
        return userEqFolders;
    }

    public void setUserEqFolders(ArrayList<UserEqFolder> userEqFolders) {
        this.userEqFolders = userEqFolders;
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
