package com.ashysystem.mbhq.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserPaidStatusModel {


    @SerializedName("SubscriptionType")
    @Expose
    private Integer SubscriptionType;
    @SerializedName("IsPaid")
    @Expose
    private Boolean isPaid;
    @SerializedName("FreeMbhqSubscriber")
    @Expose
    private FreeWorkoutsUser freeWorkoutsUser;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;

    @SerializedName("MbhqAccessType")
    @Expose
    private Integer MbhqAccessType;
    @SerializedName("HabitAccess")
    @Expose
    private Boolean HabitAccess;
    @SerializedName("EqJournalAccess")
    @Expose
    private Boolean EqJournalAccess;
    @SerializedName("MeditationAccess")
    @Expose
    private Boolean MeditationAccess;
    @SerializedName("ForumAccess")
    @Expose
    private Boolean ForumAccess;
    @SerializedName("LiveChatAccess")
    @Expose
    private Boolean LiveChatAccess;
    @SerializedName("TestsAccess")
    @Expose
    private Boolean TestsAccess;
    @SerializedName("CourseAccess")
    @Expose
    private Boolean CourseAccess;


    public Integer getMbhqAccessType() {
        return MbhqAccessType;
    }

    public void setMbhqAccessType(Integer mbhqAccessType) {
        MbhqAccessType = mbhqAccessType;
    }

    public Boolean getHabitAccess() {
        return HabitAccess;
    }

    public void setHabitAccess(Boolean habitAccess) {
        HabitAccess = habitAccess;
    }

    public Boolean getEqJournalAccess() {
        return EqJournalAccess;
    }

    public void setEqJournalAccess(Boolean eqJournalAccess) {
        EqJournalAccess = eqJournalAccess;
    }

    public Boolean getMeditationAccess() {
        return MeditationAccess;
    }

    public void setMeditationAccess(Boolean meditationAccess) {
        MeditationAccess = meditationAccess;
    }

    public Boolean getForumAccess() {
        return ForumAccess;
    }

    public void setForumAccess(Boolean forumAccess) {
        ForumAccess = forumAccess;
    }

    public Boolean getLiveChatAccess() {
        return LiveChatAccess;
    }

    public void setLiveChatAccess(Boolean liveChatAccess) {
        LiveChatAccess = liveChatAccess;
    }

    public Boolean getTestsAccess() {
        return TestsAccess;
    }

    public void setTestsAccess(Boolean testsAccess) {
        TestsAccess = testsAccess;
    }

    public Boolean getCourseAccess() {
        return CourseAccess;
    }

    public void setCourseAccess(Boolean courseAccess) {
        CourseAccess = courseAccess;
    }

    public Boolean getSquadMember() {
        return IsSquadMember;
    }

    public void setSquadMember(Boolean squadMember) {
        IsSquadMember = squadMember;
    }

    @SerializedName("IsSquadMember")
    @Expose
    private Boolean IsSquadMember;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public FreeWorkoutsUser getFreeWorkoutsUser() {
        return freeWorkoutsUser;
    }

    public void setFreeWorkoutsUser(FreeWorkoutsUser freeWorkoutsUser) {
        this.freeWorkoutsUser = freeWorkoutsUser;
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

    public Integer getSubscriptionType() {
        return SubscriptionType;
    }

    public void setSubscriptionType(Integer subscriptionType) {
        SubscriptionType = subscriptionType;
    }
    public class FreeWorkoutsUser {

        @SerializedName("Success")
        @Expose
        private Boolean success;
        @SerializedName("ErrorMessage")
        @Expose
        private String errorMessage;
        @SerializedName("NonSubscribedUserSessionId")
        @Expose
        private Integer nonSubscribedUserSessionId;
        @SerializedName("AbbbcUserId")
        @Expose
        private Integer abbbcUserId;
        @SerializedName("AbbbcUserSessionId")
        @Expose
        private Integer abbbcUserSessionId;
        @SerializedName("SquadUserId")
        @Expose
        private Integer squadUserId;
        @SerializedName("HasTrialExpired")
        @Expose
        private Boolean hasTrialExpired;
        @SerializedName("TrialStartDate")
        @Expose
        private String trialStartDate;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("Mobile")
        @Expose
        private String mobile;
        @SerializedName("Password")
        @Expose
        private String password;
        @SerializedName("IsFBUser")
        @Expose
        private Boolean isFBUser;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public Integer getNonSubscribedUserSessionId() {
            return nonSubscribedUserSessionId;
        }

        public void setNonSubscribedUserSessionId(Integer nonSubscribedUserSessionId) {
            this.nonSubscribedUserSessionId = nonSubscribedUserSessionId;
        }

        public Integer getAbbbcUserId() {
            return abbbcUserId;
        }

        public void setAbbbcUserId(Integer abbbcUserId) {
            this.abbbcUserId = abbbcUserId;
        }

        public Integer getAbbbcUserSessionId() {
            return abbbcUserSessionId;
        }

        public void setAbbbcUserSessionId(Integer abbbcUserSessionId) {
            this.abbbcUserSessionId = abbbcUserSessionId;
        }

        public Integer getSquadUserId() {
            return squadUserId;
        }

        public void setSquadUserId(Integer squadUserId) {
            this.squadUserId = squadUserId;
        }

        public Boolean getHasTrialExpired() {
            return hasTrialExpired;
        }

        public void setHasTrialExpired(Boolean hasTrialExpired) {
            this.hasTrialExpired = hasTrialExpired;
        }

        public String getTrialStartDate() {
            return trialStartDate;
        }

        public void setTrialStartDate(String trialStartDate) {
            this.trialStartDate = trialStartDate;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Boolean getIsFBUser() {
            return isFBUser;
        }

        public void setIsFBUser(Boolean isFBUser) {
            this.isFBUser = isFBUser;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

    }
}
