
package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StreakData {

    @SerializedName("TopStreak")
    @Expose
    private Integer topStreak;
    @SerializedName("CurrentStreak")
    @Expose
    private Integer currentStreak;
    @SerializedName("Total")
    @Expose
    private Integer total;
    @SerializedName("LastStreak")
    @Expose
    private Integer lastStreak;

    public Integer getTopStreak() {
        return topStreak;
    }

    public void setTopStreak(Integer topStreak) {
        this.topStreak = topStreak;
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }



    public Integer getLastStreak() {
        return lastStreak;
    }

    public void setLastStreak(Integer lastStreak) {
        this.lastStreak = lastStreak;
    }

    public Integer getTotal() {
        return total;
    }

    public StreakData setTotal(Integer total) {
        this.total = total;
        return this;
    }

}
