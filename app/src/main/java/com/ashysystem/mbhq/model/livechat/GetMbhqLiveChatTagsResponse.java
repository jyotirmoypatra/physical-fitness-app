package com.ashysystem.mbhq.model.livechat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetMbhqLiveChatTagsResponse implements Serializable {

    @SerializedName("TagList")
    @Expose
    private List<Tag> tagList;


    @SerializedName("SuccessFlag")
    @Expose
    private boolean successFlag;

    @SerializedName("ErrorMessage")
    @Expose
    private Object ErrorMessage;

    public List<Tag> getTagList() {
        return tagList;
    }

    public boolean getSuccessFlag() {
        return successFlag;
    }

    /*public class Tag implements Serializable, Parcelable {

        @SerializedName("TagName")
        @Expose
        private String tagName;

        @SerializedName("TagID")
        @Expose
        private int tagID;

        @SerializedName("TotalEvents")
        @Expose
        private int totalEvents;

        public String getTagName() {
            return tagName;
        }

        public int getTagID() {
            return tagID;
        }

        public int getTotalEvents() {
            return totalEvents;
        }

        public static final  Creator<Tag> CREATOR = new Creator<Tag>() {


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


    }*/
}
