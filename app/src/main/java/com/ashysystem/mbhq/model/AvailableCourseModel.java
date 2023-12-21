package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class AvailableCourseModel {

    @SerializedName("Courses")
    @Expose
    private List<Course> courses = null;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
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

    public static class Course {
        public String getImageURL() {
            return ImageURL;
        }

        public void setImageURL(String imageURL) {
            ImageURL = imageURL;
        }

        @SerializedName("ImageURL")
        @Expose
        private String ImageURL;

        @SerializedName("ImageUrl2")
        @Expose
        private String ImageUrl2;


        public Integer getNoOfModules() {
            return NoOfModules;
        }

        public void setNoOfModules(Integer noOfModules) {
            NoOfModules = noOfModules;
        }

        public Integer getStatus() {
            return Status;
        }

        public void setStatus(Integer status) {
            Status = status;
        }

        @SerializedName("Status")
        @Expose
        private Integer Status;
        @SerializedName("NoOfModules")
        @Expose
        private Integer NoOfModules;
        @SerializedName("CourseId")
        @Expose
        private Integer courseId;
        @SerializedName("CourseName")
        @Expose
        private String courseName;
        @SerializedName("CourseStartDate")
        @Expose
        private String courseStartDate;
        @SerializedName("WeekNumber")
        @Expose
        private Integer weekNumber;
        @SerializedName("IsEnroll")
        @Expose
        private Boolean isEnroll;
        @SerializedName("UserSquadCourseId")
        @Expose
        private Integer userSquadCourseId;
        @SerializedName("isPeriodFinished")
        @Expose
        private Boolean isPeriodFinished;
        @SerializedName("CourseType")
        @Expose
        private String courseType;
        @SerializedName("isAllArticleRead")
        @Expose
        private Boolean isAllArticleRead;

        public String getOverviewText() {
            return OverviewText;
        }

        public void setOverviewText(String overviewText) {
            OverviewText = overviewText;
        }

        public String getCourseInfo() {
            if(CourseInfo == null){
                return "";
            }
            return CourseInfo;
        }

        public void setCourseInfo(String courseInfo) {
            CourseInfo = courseInfo;
        }

        @SerializedName("CourseInfo")
        @Expose
        private String CourseInfo;

        @SerializedName("OverviewText")
        @Expose
        private String OverviewText;

        @SerializedName("IsLiveCourse")
        @Expose
        private Boolean IsLiveCourse;

        @SerializedName("HasSubscribed")
        @Expose
        private Boolean HasSubscribed;

        @SerializedName("SubscriptionType")
        @Expose
        private Integer SubscriptionType;

        @SerializedName("Price")
        @Expose
        private Double Price;

        @SerializedName("AuthorName")
        @Expose
        private String AuthorName;

        @SerializedName("AuthorImage")
        @Expose
        private String AuthorImage;

        @SerializedName("AuthorDescription")
        @Expose
        private String AuthorDescription;

        @SerializedName("Tags")
        @Expose
        private List<String> Tags;

        @SerializedName("PurchaseUrl")
        @Expose
        private String PurchaseUrl;

        @SerializedName("ForumUrl")
        @Expose
        private String ForumUrl;

        @SerializedName("OtherForumUrl")
        @Expose
        private String OtherForumUrl;

        @SerializedName("FBForumUrl")
        @Expose
        private String FBForumUrl;

        @SerializedName("MbhqForumUrl")
        @Expose
        private String MbhqForumUrl;

        @SerializedName("LiveWebinarUrl")
        @Expose
        private String LiveWebinarUrl;

        @SerializedName("LiveWebinarTime")
        @Expose
        private String LiveWebinarTime;

        @SerializedName("LiveWebinarDay")
        @Expose
        private String LiveWebinarDay;

        @SerializedName("LiveWebinarPassword")
        @Expose
        private String LiveWebinarPassword;

        @SerializedName("DateOfficialStartDate")
        @Expose
        private String DateOfficialStartDate;

        @SerializedName("DateEnrollmentEndDate")
        @Expose
        private String DateEnrollmentEndDate;

        @SerializedName("OfficialStartDate")
        @Expose
        private String OfficialStartDate;

        @SerializedName("EnrollmentEndDate")
        @Expose
        private String EnrollmentEndDate;

        @SerializedName("FirstArticleId")
        @Expose
        private Integer firstArticleId;


        public Integer getCourseId() {
            return courseId;
        }

        public void setCourseId(Integer courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            if(courseName == null){
                return "";
            }
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseStartDate() {
            return courseStartDate;
        }

        public void setCourseStartDate(String courseStartDate) {
            this.courseStartDate = courseStartDate;
        }

        public Integer getWeekNumber() {
            return weekNumber;
        }

        public void setWeekNumber(Integer weekNumber) {
            this.weekNumber = weekNumber;
        }

        public Boolean getIsEnroll() {
            return isEnroll;
        }

        public void setIsEnroll(Boolean isEnroll) {
            this.isEnroll = isEnroll;
        }

        public Integer getUserSquadCourseId() {
            return userSquadCourseId;
        }

        public void setUserSquadCourseId(Integer userSquadCourseId) {
            this.userSquadCourseId = userSquadCourseId;
        }

        public Boolean getIsPeriodFinished() {
            return isPeriodFinished;
        }

        public void setIsPeriodFinished(Boolean isPeriodFinished) {
            this.isPeriodFinished = isPeriodFinished;
        }

        public String getCourseType() {
            return courseType;
        }

        public void setCourseType(String courseType) {
            this.courseType = courseType;
        }

        public Boolean getIsAllArticleRead() {
            return isAllArticleRead;
        }

        public void setIsAllArticleRead(Boolean isAllArticleRead) {
            this.isAllArticleRead = isAllArticleRead;
        }

        public Boolean getIsLiveCourse() {
            if(EnrollmentEndDate == null){
                return false;
            }
            return IsLiveCourse;
        }

        public Boolean getHasSubscribed() {
            if(HasSubscribed == null){
                return false;
            }
            return HasSubscribed;
        }

        public Integer getSubscriptionType() {
            if(SubscriptionType == null){
                return -1;
            }
            return SubscriptionType;
        }

        public void setSubscriptionType(Integer subscriptionType) {
            this.SubscriptionType = subscriptionType;
        }

        public Double getPrice() {
            if(Price == null){
                return 0.00;
            }
            return Price;
        }

        public String getAuthorName() {
            if(AuthorName == null){
                return "";
            }
            return AuthorName;
        }

        public void setAuthorName(String authorName) {
            this.AuthorName = authorName;
        }



        public String getAuthorImage() {
            if(AuthorImage == null){
                return "";
            }
            return AuthorImage;
        }

        public String getAuthorDescription() {
            if(AuthorDescription == null){
                return "";
            }
            return AuthorDescription;
        }

        public String getPurchaseUrl() {
            if(PurchaseUrl == null){
                return "";
            }
            return PurchaseUrl;
        }

        public String getForumUrl() {
            if(ForumUrl == null){
                return "";
            }
            return ForumUrl;
        }

        public String getOtherForumUrl() {
            if(OtherForumUrl == null){
                return "";
            }
            return OtherForumUrl;
        }

        public String getFBForumUrl() {
            if(FBForumUrl == null){
                return "";
            }
            return FBForumUrl;
        }

        public String getMbhqForumUrl() {
            if(MbhqForumUrl == null){
                return "";
            }
            return MbhqForumUrl;
        }


        public String getLiveWebinarUrl() {
            if(LiveWebinarUrl == null){
                return "";
            }
            return LiveWebinarUrl;
        }

        public String getLiveWebinarTime() {
            if(LiveWebinarTime == null){
                return "";
            }
            return LiveWebinarTime;
        }

        public String getLiveWebinarDay() {
            if(LiveWebinarDay == null){
                return "";
            }
            return LiveWebinarDay;
        }

        public String getLiveWebinarPassword() {
            if(LiveWebinarPassword == null){
                return "";
            }
            return LiveWebinarPassword;
        }

        public String getDateOfficialStartDate() {
            if(DateOfficialStartDate == null){
                return "";
            }
            return DateOfficialStartDate;
        }

        public String getDateEnrollmentEndDate() {
            if(DateEnrollmentEndDate == null){
                return "";
            }
            return DateEnrollmentEndDate;
        }

        public String getOfficialStartDate() {
            if(OfficialStartDate == null){
                return "";
            }
            return OfficialStartDate;
        }

        public String getEnrollmentEndDate() {
            if(EnrollmentEndDate == null){
                return "";
            }
            return EnrollmentEndDate;
        }

        public String getImageURL2() {
            if(ImageUrl2 == null){
                return "";
            }
            return ImageUrl2;
        }

        public void setImageURL2(String imageUrl2) {
            this.ImageUrl2 = imageUrl2;
        }

        public List<String> getTags() {
            if(Tags == null){
                return (new ArrayList<String>());
            }
            return Tags;
        }

        public void setFirstArticleId(Integer firstArticleId) {
            this.firstArticleId = firstArticleId;
        }

        public Integer getFirstArticleId() {
            if(firstArticleId == null){
                return 0;
            }
            return firstArticleId;
        }


    }

}
