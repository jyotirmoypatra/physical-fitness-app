
package com.ashysystem.mbhq.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeditationTagResponse  {

    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("Tags")
    @Expose
    private List<Tag> tags;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
