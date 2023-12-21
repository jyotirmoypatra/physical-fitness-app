
package com.ashysystem.mbhq.model.LoginRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EqFolder {

    @SerializedName("UserEqFolderId")
    @Expose
    private Integer userEqFolderId;
    @SerializedName("FolderName")
    @Expose
    private String folderName;
    @SerializedName("IsDefaultView")
    @Expose
    private Boolean isDefaultView;

    public Integer getUserEqFolderId() {
        return userEqFolderId;
    }

    public void setUserEqFolderId(Integer userEqFolderId) {
        this.userEqFolderId = userEqFolderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Boolean getIsDefaultView() {
        return isDefaultView;
    }

    public void setIsDefaultView(Boolean isDefaultView) {
        this.isDefaultView = isDefaultView;
    }

}
