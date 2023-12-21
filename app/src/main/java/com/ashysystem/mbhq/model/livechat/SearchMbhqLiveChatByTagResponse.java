package com.ashysystem.mbhq.model.livechat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchMbhqLiveChatByTagResponse implements Serializable {

    @SerializedName("ChatList")
    @Expose
    private List<Chat> chatList;

    @SerializedName("MoreAvailable")
    @Expose
    private boolean moreAvailable;

    @SerializedName("SuccessFlag")
    @Expose
    private boolean successFlag;

    @SerializedName("ErrorMessage")
    @Expose
    private Object ErrorMessage;

    public List<Chat> getChatList() {
        return chatList;
    }

    public boolean isMoreAvailable() {
        return moreAvailable;
    }

    public boolean getSuccessFlag() {
        return successFlag;
    }

}
