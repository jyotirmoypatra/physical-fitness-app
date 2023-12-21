package com.ashysystem.mbhq.model.livechat;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Tag implements Serializable, Parcelable {

    @SerializedName("TagName")
    @Expose
    private String tagName;

    @SerializedName("TagID")
    @Expose
    private int tagID;

    @SerializedName("TotalEvents")
    @Expose
    private int totalEvents;

    boolean isSelected;

    public String getTagName() {
        return tagName;
    }

    public int getTagID() {
        return tagID;
    }

    public int getTotalEvents() {
        return totalEvents;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setTotalEvents(int totalEvents) {
        this.totalEvents = totalEvents;
    }

    public Tag(String tagName, int tagId, int totalEvents, boolean isSelected) {
        setTagName(tagName);
        setTagID(tagId);
        setTotalEvents(totalEvents);
        setSelected(isSelected);
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        public Tag[] newArray(int size) {
            return (new Tag[size]);
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tagID);
        dest.writeString(tagName);
        dest.writeInt(totalEvents);
    }


    protected Tag(Parcel in) {
        this.tagID = ((Integer) in.readInt());
        this.tagName = ((String) in.readString());
        this.totalEvents = ((Integer) in.readInt());
    }


}
