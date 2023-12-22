package com.ashysystem.mbhq.Service.interfaces;

import com.ashysystem.mbhq.model.AddUpdateGratitudeModel;
import com.ashysystem.mbhq.model.AvailableCourseModel;
import com.ashysystem.mbhq.model.BucketListModel;
import com.ashysystem.mbhq.model.CourseDetailModel;
import com.ashysystem.mbhq.model.GetGratitudeCacheExpiryTimeResponse;
import com.ashysystem.mbhq.model.GetMeditationCacheExpiryTimeResponse;
import com.ashysystem.mbhq.model.GetPrompt;
import com.ashysystem.mbhq.model.GetStreakDataResponse;
import com.ashysystem.mbhq.model.GetTaskStatusForDateResponse;
import com.ashysystem.mbhq.model.GetUserPaidStatusModel;


import com.ashysystem.mbhq.model.GetWinTheWeekStatsResponse;
import com.ashysystem.mbhq.model.IndividualAchievementModel;
import com.ashysystem.mbhq.model.IndividualBucketListModel;
import com.ashysystem.mbhq.model.LoginRes.LoginResponse;
import com.ashysystem.mbhq.model.MeditationCourseModel;
import com.ashysystem.mbhq.model.MeditationTagResponse;
import com.ashysystem.mbhq.model.MyAchievementsListModel;
import com.ashysystem.mbhq.model.MyValueListResponse;
import com.ashysystem.mbhq.model.ProgressCourseResponse;
import com.ashysystem.mbhq.model.TodayPage.GetAppHomePageValuesResponseModel;
import com.ashysystem.mbhq.model.UpdateBadgeShownResponse;
import com.ashysystem.mbhq.model.eqfolder.Eqfolder;
import com.ashysystem.mbhq.model.eqfolder.Folderdefaultresponse;
import com.ashysystem.mbhq.model.habit_hacker.GetHabitTemplatesResponseModel;
import com.ashysystem.mbhq.model.habit_hacker.GetUserHabitSwapModel;
import com.ashysystem.mbhq.model.habit_hacker.HabbitCalendarModel;
import com.ashysystem.mbhq.model.habit_hacker.SearchUserHabitSwapsModel;
import com.ashysystem.mbhq.model.livechat.GetMbhqLiveChatTagsResponse;
import com.ashysystem.mbhq.model.livechat.SearchMbhqLiveChatByTagResponse;
import com.ashysystem.mbhq.model.response.AddCourseResponseModel;
import com.ashysystem.mbhq.model.response.AddUpdateMyAchievementModel;
import com.ashysystem.mbhq.model.response.suggestedmedicin.Suggestedmedicin;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by android-krishnendu on 9/27/16.
 */

public interface FinisherService {

    @POST("api/MbhqMember/IsValidMbHQUser")
    Call<GetUserPaidStatusModel> getUserPaidStatusApi(@Body HashMap<String, Object> modelHashMap);

    @POST("api/MbhqMember/MbHQLogon")
    Call<LoginResponse> getLogin(@Body HashMap<String, Object> modelHashMap);

    @POST("api/MbhqMember/UpdatePassword")
    Call<JsonObject> UpdatePassword(@Body HashMap<String, Object> modelHashMap);

    @POST("api/MbhqMember/ForgotPassword")
    Call<JsonObject> forgotPassword(@Body HashMap<String, Object> modelHashMap);

    @Headers("Accept: application/json")
    @Multipart
    @POST("api/mindset/AddUpdateReverseBucketAPIWithPhoto")
    Call<AddUpdateMyAchievementModel> addUpdateAchievementWithPhoto(@Part MultipartBody.Part file, @Part("jsonString") RequestBody json, @Query("time") Long timeStamp);
    @POST("api/MbhqCourse/UpdateVideoTime")
    Call<JsonObject> setDuration(@Body HashMap<String, Object> modelHashMap);
    @POST("api/UpdateEventItemVideoTime")
    Call<JsonObject> UpdateEventItemVideoTime(@Body HashMap<String, Object> modelHashMap);
    @Headers("Content-Type:application/json")
    @POST("api/mindset/GetMeditationStreakData")
    Call<GetStreakDataResponse> getMeditationStreakData(@Body HashMap<String, Object> modelHashMap);
    @Headers("Content-Type:application/json")
    //@POST("api/AppStreak/GetStreakData")
    @POST("api/mindset/GetGratitudeStreakData")
    Call<GetStreakDataResponse> getStreakData(@Body HashMap<String, Object> modelHashMap);
    @POST("api/eqjournal/UpdateEqFolderName")
    Call<Eqfolder> updateeqname(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/GetJounalPromptofDay")
    Call<GetPrompt> getJounalPromptofDay(@Body HashMap<String, Object> modelHashMap);
    @POST("api/MbhqMember/GetMeditationCacheExpiryTime")
    Call<GetMeditationCacheExpiryTimeResponse> GetMeditationCacheExpiryTime(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/UpdateBadgeShown")
    Call<UpdateBadgeShownResponse> updateBadgeShown(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/GetTaskStatusForDate")
    Call<GetTaskStatusForDateResponse> getTaskStatusForDate(@Body HashMap<String, Object> modelHashMap);
    @Headers("Accept: application/json")
    @Multipart
    @POST("api/mindset/UploadUniqueMbhqImage")
    Call<JSONObject> uploadPhotos(@Part MultipartBody.Part file, @Part("jsonString") RequestBody json, @Query("time") Long timeStamp);

    @Headers("Accept: application/json")
    @Multipart
    @POST("api/mindset/AddUpdateGratitudeAPIWithPhoto")
    Call<AddUpdateGratitudeModel> addUpdateGratitudeWithPhoto(@Part MultipartBody.Part file, @Part("jsonString") RequestBody json, @Query("time") Long timeStamp);
    @POST("api/MbhqCourse/GetUnreadMessageCountForMbhqUser")
    Call<JsonObject> GetUnreadMessageCountForUser(@Body HashMap<String, Object> modelHashMap);

    @POST("api/UpdateTrialDate/UpdateTrialDate")
    Call<JsonObject> updateTrialDate(@Body HashMap<String, Object> modelHashMap);

    @POST("api/MbhqCourse/AddUserCourse")
    Call<AddCourseResponseModel> addCourse(@Body HashMap<String, Object> modelHashMap);

    @POST
    Call<JsonObject> GetCoachToken(@Url String url, @Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/GetGrowthHomePage")
    Call<GetAppHomePageValuesResponseModel> getGrowthHomapage(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/GetGrowthHomePageHabitOnly")
    Call<GetAppHomePageValuesResponseModel> GetGrowthHomePageHabitOnly(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/DeleteHabitSwap")
    Call<JsonObject> deleteHabitSwap(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/UpdateMultipleTaskStatus")
    Call<JsonObject> UpdateMultipleTaskStatus(@Body HashMap<String, Object> modelHashMap);
    @POST("api/MbhqMember/GetServerDataStatus")
    Call<GetGratitudeCacheExpiryTimeResponse> GetGratitudeCacheExpiryTime(@Body HashMap<String, Object> modelHashMap); /////


    @Headers("Content-Type:application/json")
    @POST("api/Vitamin/UpdateTaskStatus")
    Call<JsonObject> updateVitaminTask(@Body JsonObject object);

    @POST("api/MbhqMember/UpdateHabitStatsPreference")
    Call<JsonObject> updateHabitStatsPreference(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/UpdateHabitSwapManualOrder")
    Call<JsonObject> updateHabitSwapManualOrder(@Body HashMap<String, Object> modelHashMap);
    @POST("api/MbhqMember/UpdateWeeklyOverviewFlag")
    Call<JsonObject> updateWeeklyOverviewFlag(@Body HashMap<String, Object> modelHashMap);
    @POST("api/MbhqMember/UpdateAutoCompleteHabitFlag")
    Call<JsonObject> updateAutoCompleteHabitFlag(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/EmailUserHabitSwaps")
    Call<JsonObject> emailUserHabitSwaps(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/SearchUserHabitSwaps")
    Call<SearchUserHabitSwapsModel> searchUserHabitSwaps(@Body HashMap<String, Object> modelHashMap);
    @POST("api/MbhqMember/GetWeeklyOverviewFlag")
    Call<JsonObject> getWeeklyOverviewFlag(@Body HashMap<String, Object> modelHashMap);
    @POST("api/MbhqMember/GetAutoCompleteHabitFlag")
    Call<JsonObject> getAutoCompleteHabitFlag(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/AddUpdateHabitSwap")
    Call<JsonObject> addUpdateHabitSwap(@Body HashMap<String, Object> modelHashMap);
    @Headers("Accept: application/json")
    @Multipart
    @POST("api/mindset/AddUpdateBucketAPIWithPhoto")
    Call<JsonObject> addUpdateBucketWithPhoto(@Part MultipartBody.Part file, @Part("jsonString") RequestBody json, @Query("time") Long timeStamp);
    @POST("api/mindset/GetBucketListAPI")
    Call<BucketListModel> getBucketList(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/GetBucketSelectAPI")
    Call<IndividualBucketListModel> getIndividualBucket(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/UpdateBucketListManualOrder")
    Call<JsonObject> UpdateBucketListManualOrder(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/DeleteBucketAPI")
    Call<JsonObject> deleteBucket(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/ChangeBucketStatusAPI")
    Call<JsonObject> ChangeBucketStatusAPI(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/AddUpdateBucketAPI")
    Call<JsonObject> addUpdateBucket(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/GetGoalValueListAPI")
    Call<MyValueListResponse> getValueListApi(@Body HashMap<String, Object> modelHashMap);

    @POST("api/user/GetGuidedMeditationsBySearchTag")
    Call<MeditationCourseModel> GetGuidedMeditationsBySearchTag(@Body HashMap<String, Object> modelHashMap);

    @POST("api/ToggleEventLike")
    Call<JsonObject> ToggleEventLike(@Body HashMap<String, Object> modelHashMap);

    @POST("api/GetEventTagList")
    Call<MeditationTagResponse> getTaglist(@Body HashMap<String, Object> modelHashMap);


    @POST("api/GetArchivedWebinarsAbsolute")
        //@POST("api/GetCourseList")
    Call<MeditationCourseModel> GetArchivedWebinarsAbsolute(@Body HashMap<String, Object> modelHashMap);



    @POST("api/user/SearchMbhqLiveChatByTag")
    Call<SearchMbhqLiveChatByTagResponse> SearchMbhqLiveChatByTag(@Body HashMap<String, Object> modelHashMap);

    @POST("/api/user/GetMbhqLiveChatTags")
    Call<GetMbhqLiveChatTagsResponse> GetMbhqLiveChatTags(@Body HashMap<String, Object> modelHashMap);

    @POST("api/user/GetMeditationEventItemDetails")
    Call<Suggestedmedicin> getSuggestedmeditationdetails(@Body HashMap<String, Object> modelHashMap);

    @POST("api/mindset/GetHabitTemplates")
    Call<GetHabitTemplatesResponseModel> getHabitTemplates(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/GetUserHabitSwap")
    Call<GetUserHabitSwapModel> getUserHabitSwap(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/GetHabitStats")
    Call<HabbitCalendarModel> GetHabitStats(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/UpdateTaskNote")
    Call<JsonObject> UpdateTaskNote(@Body HashMap<String, Object> modelHashMap);



    @POST("api/mindset/UpdateHabitStatus")
    Call<JsonObject> updateHabitStatus(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mindset/GetWinTheWeekStats")
    Call<GetWinTheWeekStatsResponse> getWinTheWeekStats(@Body HashMap<String, Object> modelHashMap);
    @POST("api/mbhqCourse/GetCourseList ")
    Call<AvailableCourseModel> getAvailableCourse(@Body HashMap<String, Object> modelHashMap);
    @POST("api/MbhqCourse/SetProgressBar")
    Call<ProgressCourseResponse> getProgressReponse(@Body HashMap<String, Object> modelHashMap);
    @POST("api/MbhqCourse/GetCourseDetail")
    Call<CourseDetailModel> getCourseDetails(@Body HashMap<String, Object> modelHashMap);
    @POST("api/MbhqCourse/UpdateCourseStatus")
    Call<JsonObject> UpdateCourseStatus(@Body HashMap<String, Object> modelHashMap);
    @POST("api/MbhqCourse/ToggleMessageNotificationFlag")
    Call<JsonObject> ToggleMessageNotificationFlag(@Body HashMap<String, Object> modelHashMap);
    @POST("api/MbhqCourse/ToggleSeminarNotificationFlag")
    Call<JsonObject> toggleSeminarNotificationFlag(@Body HashMap<String, Object> modelHashMap);
    @POST("api/ResetWeekChallenge")
    Call<JsonObject> refreshCourse(@Body HashMap<String, Object> modelHashMap);

    @POST("api/mindset/EmailReverseBucketList")
    Call<JsonObject> emailReverseBucketList(@Body HashMap<String, Object> modelHashMap);

    @POST("api/mindset/GetReverseBucketListAPI")
    Call<MyAchievementsListModel> getMyAchievevmentsList(@Body HashMap<String, Object> modelHashMap);

    @POST("api/mindset/DeleteReverseBucketAPI")
    Call<JsonObject> deleteAchievement(@Body HashMap<String, Object> modelHashMap);

    @POST("api/mindset/AddUpdateReverseBucketAPI")
    Call<AddUpdateMyAchievementModel> addUpdateAchievement(@Body HashMap<String, Object> modelHashMap);

    @POST("api/eqjournal/SetDefaultViewEqFolder")
    Call<Folderdefaultresponse> setDefault(@Body HashMap<String, Object> modelHashMap);

    @POST("api/mindset/GetReverseBuckeSelectAPI")
    Call<IndividualAchievementModel> selectAchievement(@Body HashMap<String, Object> modelHashMap);

    @POST("api/eqjournal/MoveEqJournalToFolder")
    Call<JsonObject> moveeqname(@Body HashMap<String, Object> modelHashMap);

}

