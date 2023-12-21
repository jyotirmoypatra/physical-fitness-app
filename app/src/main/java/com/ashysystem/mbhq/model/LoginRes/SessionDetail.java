
package com.ashysystem.mbhq.model.LoginRes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionDetail {

    @SerializedName("UserSessionID")
    @Expose
    private Integer userSessionID;
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("EmailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("IsSquadMember")
    @Expose
    private Boolean isSquadMember;
    @SerializedName("IsMbHQMember")
    @Expose
    private Boolean isMbHQMember;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("HasFacebookLogin")
    @Expose
    private Boolean hasFacebookLogin;
    @SerializedName("Timezone")
    @Expose
    private String timezone;
    @SerializedName("UnitPreference")
    @Expose
    private Integer unitPreference;
    @SerializedName("ProfilePicUrl")
    @Expose
    private String profilePicUrl;
    @SerializedName("ApplyRounding")
    @Expose
    private Boolean applyRounding;
    @SerializedName("DiscoverableStatus")
    @Expose
    private Boolean discoverableStatus;
    @SerializedName("SignupMethod")
    @Expose
    private Integer signupMethod;
    @SerializedName("MbhqAccessType")
    @Expose
    private Integer mbhqAccessType;
    @SerializedName("AndroidDeviceID")
    @Expose
    private String androidDeviceID;
    @SerializedName("IosDeviceID")
    @Expose
    private String iosDeviceID;
    @SerializedName("IsPaid")
    @Expose
    private Boolean isPaid;
    @SerializedName("SubscriptionEndDate")
    @Expose
    private Object subscriptionEndDate;
    @SerializedName("CancellationDate")
    @Expose
    private Object cancellationDate;
    @SerializedName("ABBBCOnlineUserId")
    @Expose
    private Integer aBBBCOnlineUserId;
    @SerializedName("ABBBCOnlineUserSessionId")
    @Expose
    private Integer aBBBCOnlineUserSessionId;
    @SerializedName("IsFirstLogon")
    @Expose
    private Boolean isFirstLogon;
    @SerializedName("ActiveUntil")
    @Expose
    private String activeUntil;
    @SerializedName("TokenExpiry")
    @Expose
    private String tokenExpiry;
    @SerializedName("IsPasswordUpdated")
    @Expose
    private Boolean isPasswordUpdated;
    @SerializedName("WeeklyOverview")
    @Expose
    private Boolean weeklyOverview;
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("HabitAccess")
    @Expose
    private Boolean habitAccess;
    @SerializedName("EqJournalAccess")
    @Expose
    private Boolean eqJournalAccess;
    @SerializedName("MeditationAccess")
    @Expose
    private Boolean meditationAccess;
    @SerializedName("ForumAccess")
    @Expose
    private Boolean forumAccess;
    @SerializedName("LiveChatAccess")
    @Expose
    private Boolean liveChatAccess;
    @SerializedName("TestsAccess")
    @Expose
    private Boolean testsAccess;
    @SerializedName("CourseAccess")
    @Expose
    private Boolean courseAccess;
    @SerializedName("EqFolders")
    @Expose
    private List<EqFolder> eqFolders;

    public Integer getUserSessionID() {
        return userSessionID;
    }

    public void setUserSessionID(Integer userSessionID) {
        this.userSessionID = userSessionID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsSquadMember() {
        return isSquadMember;
    }

    public void setIsSquadMember(Boolean isSquadMember) {
        this.isSquadMember = isSquadMember;
    }

    public Boolean getIsMbHQMember() {
        return isMbHQMember;
    }

    public void setIsMbHQMember(Boolean isMbHQMember) {
        this.isMbHQMember = isMbHQMember;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getHasFacebookLogin() {
        return hasFacebookLogin;
    }

    public void setHasFacebookLogin(Boolean hasFacebookLogin) {
        this.hasFacebookLogin = hasFacebookLogin;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getUnitPreference() {
        return unitPreference;
    }

    public void setUnitPreference(Integer unitPreference) {
        this.unitPreference = unitPreference;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public Boolean getApplyRounding() {
        return applyRounding;
    }

    public void setApplyRounding(Boolean applyRounding) {
        this.applyRounding = applyRounding;
    }

    public Boolean getDiscoverableStatus() {
        return discoverableStatus;
    }

    public void setDiscoverableStatus(Boolean discoverableStatus) {
        this.discoverableStatus = discoverableStatus;
    }

    public Integer getSignupMethod() {
        return signupMethod;
    }

    public void setSignupMethod(Integer signupMethod) {
        this.signupMethod = signupMethod;
    }

    public Integer getMbhqAccessType() {
        return mbhqAccessType;
    }

    public void setMbhqAccessType(Integer mbhqAccessType) {
        this.mbhqAccessType = mbhqAccessType;
    }

    public String getAndroidDeviceID() {
        return androidDeviceID;
    }

    public void setAndroidDeviceID(String androidDeviceID) {
        this.androidDeviceID = androidDeviceID;
    }

    public String getIosDeviceID() {
        return iosDeviceID;
    }

    public void setIosDeviceID(String iosDeviceID) {
        this.iosDeviceID = iosDeviceID;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Object getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(Object subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public Object getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Object cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public Integer getABBBCOnlineUserId() {
        return aBBBCOnlineUserId;
    }

    public void setABBBCOnlineUserId(Integer aBBBCOnlineUserId) {
        this.aBBBCOnlineUserId = aBBBCOnlineUserId;
    }

    public Integer getABBBCOnlineUserSessionId() {
        return aBBBCOnlineUserSessionId;
    }

    public void setABBBCOnlineUserSessionId(Integer aBBBCOnlineUserSessionId) {
        this.aBBBCOnlineUserSessionId = aBBBCOnlineUserSessionId;
    }

    public Boolean getIsFirstLogon() {
        return isFirstLogon;
    }

    public void setIsFirstLogon(Boolean isFirstLogon) {
        this.isFirstLogon = isFirstLogon;
    }

    public String getActiveUntil() {
        return activeUntil;
    }

    public void setActiveUntil(String activeUntil) {
        this.activeUntil = activeUntil;
    }

    public String getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(String tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public Boolean getIsPasswordUpdated() {
        return isPasswordUpdated;
    }

    public void setIsPasswordUpdated(Boolean isPasswordUpdated) {
        this.isPasswordUpdated = isPasswordUpdated;
    }

    public Boolean getWeeklyOverview() {
        return weeklyOverview;
    }

    public void setWeeklyOverview(Boolean weeklyOverview) {
        this.weeklyOverview = weeklyOverview;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getHabitAccess() {
        return habitAccess;
    }

    public void setHabitAccess(Boolean habitAccess) {
        this.habitAccess = habitAccess;
    }

    public Boolean getEqJournalAccess() {
        return eqJournalAccess;
    }

    public void setEqJournalAccess(Boolean eqJournalAccess) {
        this.eqJournalAccess = eqJournalAccess;
    }

    public Boolean getMeditationAccess() {
        return meditationAccess;
    }

    public void setMeditationAccess(Boolean meditationAccess) {
        this.meditationAccess = meditationAccess;
    }

    public Boolean getForumAccess() {
        return forumAccess;
    }

    public void setForumAccess(Boolean forumAccess) {
        this.forumAccess = forumAccess;
    }

    public Boolean getLiveChatAccess() {
        return liveChatAccess;
    }

    public void setLiveChatAccess(Boolean liveChatAccess) {
        this.liveChatAccess = liveChatAccess;
    }

    public Boolean getTestsAccess() {
        return testsAccess;
    }

    public void setTestsAccess(Boolean testsAccess) {
        this.testsAccess = testsAccess;
    }

    public Boolean getCourseAccess() {
        return courseAccess;
    }

    public void setCourseAccess(Boolean courseAccess) {
        this.courseAccess = courseAccess;
    }

    public List<EqFolder> getEqFolders() {
        return eqFolders;
    }

    public void setEqFolders(List<EqFolder> eqFolders) {
        this.eqFolders = eqFolders;
    }

}
