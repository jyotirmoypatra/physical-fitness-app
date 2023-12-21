package com.ashysystem.mbhq.model.habit_hacker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HabitSwap_hidden {

    @SerializedName("HabitId")
    @Expose
    private Integer habitId;
    @SerializedName("HabitName")
    @Expose
    private String habitName;
    @SerializedName("ActionId")
    @Expose
    private Integer actionId;
    @SerializedName("ActionWhere")
    @Expose
    private String actionWhere;
    @SerializedName("Cue")
    @Expose
    private String cue;
    @SerializedName("Craving")
    @Expose
    private Object craving;
    @SerializedName("OldActionBreak")
    @Expose
    private String oldActionBreak;
    @SerializedName("MoreSeen")
    @Expose
    private String moreSeen;
    @SerializedName("Routine")
    @Expose
    private String routine;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Reward")
    @Expose
    private String reward;
    @SerializedName("MakeMoreRewarding")
    @Expose
    private Object makeMoreRewarding;
    @SerializedName("NoteHidden")
    @Expose
    private String noteHidden;
    @SerializedName("NoteHarder")
    @Expose
    private Object noteHarder;
    @SerializedName("NoteHorrible")
    @Expose
    private String noteHorrible;
    @SerializedName("DateCreated")
    @Expose
    private String dateCreated;
    @SerializedName("DateUpdated")
    @Expose
    private String dateUpdated;
    @SerializedName("AccountabilityNotes")
    @Expose
    private String accountabilityNotes;
    @SerializedName("NewAction")
    @Expose
    private NewAction newAction;
    @SerializedName("SwapAction")
    @Expose
    private SwapAction swapAction;
    @SerializedName("ManualOrder")
    @Expose
    private Object manualOrder;
    @SerializedName("UserId")
    @Expose
    private Integer userId;

    private transient  Boolean isBreakShowing = false;

    public Boolean getBreakShowing() {
        return isBreakShowing;
    }

    public HabitSwap_hidden setBreakShowing(Boolean breakShowing) {
        isBreakShowing = breakShowing;
        return this;
    }

    public Integer getHabitId() {
        return habitId;
    }

    public void setHabitId(Integer habitId) {
        this.habitId = habitId;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getActionWhere() {
        return actionWhere;
    }

    public void setActionWhere(String actionWhere) {
        this.actionWhere = actionWhere;
    }

    public String getCue() {
        return cue;
    }

    public void setCue(String cue) {
        this.cue = cue;
    }

    public Object getCraving() {
        return craving;
    }

    public void setCraving(Object craving) {
        this.craving = craving;
    }

    public String getOldActionBreak() {
        return oldActionBreak;
    }

    public void setOldActionBreak(String oldActionBreak) {
        this.oldActionBreak = oldActionBreak;
    }

    public String getMoreSeen() {
        return moreSeen;
    }

    public void setMoreSeen(String moreSeen) {
        this.moreSeen = moreSeen;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public Object getMakeMoreRewarding() {
        return makeMoreRewarding;
    }

    public void setMakeMoreRewarding(Object makeMoreRewarding) {
        this.makeMoreRewarding = makeMoreRewarding;
    }

    public String getNoteHidden() {
        return noteHidden;
    }

    public void setNoteHidden(String noteHidden) {
        this.noteHidden = noteHidden;
    }

    public Object getNoteHarder() {
        return noteHarder;
    }

    public void setNoteHarder(Object noteHarder) {
        this.noteHarder = noteHarder;
    }

    public String getNoteHorrible() {
        return noteHorrible;
    }

    public void setNoteHorrible(String noteHorrible) {
        this.noteHorrible = noteHorrible;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getAccountabilityNotes() {
        return accountabilityNotes;
    }

    public void setAccountabilityNotes(String accountabilityNotes) {
        this.accountabilityNotes = accountabilityNotes;
    }

    public NewAction getNewAction() {
        return newAction;
    }

    public void setNewAction(NewAction newAction) {
        this.newAction = newAction;
    }

    public SwapAction getSwapAction() {
        return swapAction;
    }

    public void setSwapAction(SwapAction swapAction) {
        this.swapAction = swapAction;
    }

    public Object getManualOrder() {
        return manualOrder;
    }

    public void setManualOrder(Object manualOrder) {
        this.manualOrder = manualOrder;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
