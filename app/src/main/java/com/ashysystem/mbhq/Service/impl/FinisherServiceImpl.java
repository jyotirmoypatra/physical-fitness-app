package com.ashysystem.mbhq.Service.impl;

import android.content.Context;

import com.ashysystem.mbhq.Service.interfaces.FinisherService;

import com.ashysystem.mbhq.model.AddRewardModel;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Part;

/**
 * Created by android-krishnendu on 9/27/16.
 */

public class FinisherServiceImpl extends Service {

    private FinisherService finisherService;

    public FinisherServiceImpl(Context context) {
        super(context);
        this.finisherService = super.getRetrofit().create(FinisherService.class);
    }
    public FinisherServiceImpl(Context context,String jwt) {
        super(context,jwt);
        this.finisherService = super.getRetrofit().create(FinisherService.class);
    }

    public Call<GetUserPaidStatusModel> getUserPaidStatusApi(HashMap<String, Object> modelHashMap) {
        return finisherService.getUserPaidStatusApi(modelHashMap);
    }

    public Call<LoginResponse> getLogin(HashMap<String, Object> modelHashMap) {
        return finisherService.getLogin(modelHashMap);
    }
    public Call<JsonObject> updatePassword(HashMap<String, Object> modelHashMap) {
        return finisherService.UpdatePassword(modelHashMap);
    }
    public Call<JsonObject> forgotPassword(HashMap<String, Object> modelHashMap) {
        return finisherService.forgotPassword(modelHashMap);
    }

    public Call<AddUpdateMyAchievementModel> addUpdateAchievementWithPhoto(ProgressRequestBody fileBody, @Part("jsonString") okhttp3.RequestBody json) {
        MultipartBody.Part file = null;
        if (fileBody != null) {
            file = MultipartBody.Part.createFormData("picture", fileBody.getFileName(), fileBody);
        }
        return finisherService.addUpdateAchievementWithPhoto(file, json, System.currentTimeMillis());
    }
    public Call<JsonObject> setDuration(HashMap<String, Object> modelHashMap) {
        return finisherService.setDuration(modelHashMap);
    }
    public Call<JsonObject> setDurationForMeditation(HashMap<String, Object> modelHashMap) {
        return finisherService.UpdateEventItemVideoTime(modelHashMap);
    }
    public Call<GetStreakDataResponse> getMeditationStreakData(HashMap<String, Object> object) {
        return finisherService.getMeditationStreakData(object);
    }
    public Call<GetStreakDataResponse> getStreakData(HashMap<String, Object> object) {
        return finisherService.getStreakData(object);
    }
    public Call<Eqfolder> updateEqname(HashMap<String, Object> modelHashMap) {
        return finisherService.updateeqname(modelHashMap);
    }
    public Call<GetPrompt> getJounalPromptofDay(HashMap<String, Object> modelHashMap) {
        return finisherService.getJounalPromptofDay(modelHashMap);
    }

    public Call<GetMeditationCacheExpiryTimeResponse> GetMeditationCacheExpiryTime(HashMap<String, Object> modelHashMap) {
        return finisherService.GetMeditationCacheExpiryTime(modelHashMap);
    }
    public Call<UpdateBadgeShownResponse> updateBadgeShown(HashMap<String, Object> modelHashMap) {
        return finisherService.updateBadgeShown(modelHashMap);
    }
    public Call<GetTaskStatusForDateResponse> getTaskStatusForDate(HashMap<String, Object> modelHashMap) {
        return finisherService.getTaskStatusForDate(modelHashMap);
    }
    public Call<JSONObject> addPhotoInUploadQueue(ProgressRequestBody fileBody, @Part("jsonString") okhttp3.RequestBody json) {

        MultipartBody.Part file = null;
        if (fileBody != null) {
            file = MultipartBody.Part.createFormData("picture", fileBody.getFileName(), fileBody);
        }

        return finisherService.uploadPhotos(file, json, System.currentTimeMillis());
    }
    public Call<AddUpdateGratitudeModel> addUpdateGratitudeWithPhoto(ProgressRequestBody fileBody, @Part("jsonString") okhttp3.RequestBody json) {

        MultipartBody.Part file = null;
        if (fileBody != null) {
            file = MultipartBody.Part.createFormData("picture", fileBody.getFileName(), fileBody);
        }

        return finisherService.addUpdateGratitudeWithPhoto(file, json, System.currentTimeMillis());
    }
    public Call<JsonObject> GetUnreadCount(HashMap<String, Object> modelHashMap) {
        return finisherService.GetUnreadMessageCountForUser(modelHashMap);
    }

    public Call<AddCourseResponseModel> addCourse(HashMap<String, Object> modelHashMap) {
        return finisherService.addCourse(modelHashMap);
    }

    public Call<JsonObject> updateTrialDate(HashMap<String, Object> modelHashMap) {
        return finisherService.updateTrialDate(modelHashMap);
    }

    public Call<JsonObject> GetCoachToken(String url, HashMap<String, Object> modelHashMap) {
        return finisherService.GetCoachToken(url, modelHashMap);
    }
    public Call<GetAppHomePageValuesResponseModel> getGrowthHomapage(HashMap<String, Object> modelHashMap) {
        return finisherService.getGrowthHomapage(modelHashMap);
    }
    public Call<GetAppHomePageValuesResponseModel> getGrowthHomePageHabitOnly(HashMap<String, Object> modelHashMap) {
        return finisherService.GetGrowthHomePageHabitOnly(modelHashMap);
    }
    public Call<JsonObject> deleteHabitSwap(HashMap<String, Object> modelHashMap) {
        return finisherService.deleteHabitSwap(modelHashMap);
    }
    public Call<JsonObject> UpdateMultipleTaskStatus(HashMap<String, Object> modelHashMap) {
        return finisherService.UpdateMultipleTaskStatus(modelHashMap);
    }
    public Call<GetGratitudeCacheExpiryTimeResponse> GetGratitudeCacheExpiryTime(HashMap<String, Object> modelHashMap) {
        return finisherService.GetGratitudeCacheExpiryTime(modelHashMap);
    }

    public Call<JsonObject> updateVitaminTask(JsonObject object) {
        return finisherService.updateVitaminTask(object);
    }
    public Call<JsonObject> updateHabitStatsPreference(HashMap<String, Object> modelHashMap) {
        return finisherService.updateHabitStatsPreference(modelHashMap);
    }
    public Call<JsonObject> updateHabitSwapManualOrder(HashMap<String, Object> modelHashMap) {
        return finisherService.updateHabitSwapManualOrder(modelHashMap);
    }
    public Call<JsonObject> updateWeeklyOverviewFlag(HashMap<String, Object> modelHashMap) {
        return finisherService.updateWeeklyOverviewFlag(modelHashMap);
    }
    public Call<JsonObject> updateAutoCompleteHabitFlag(HashMap<String, Object> modelHashMap) {
        return finisherService.updateAutoCompleteHabitFlag(modelHashMap);
    }
    public Call<JsonObject> emailUserHabitSwaps(HashMap<String, Object> modelHashMap) {
        return finisherService.emailUserHabitSwaps(modelHashMap);
    }
    public Call<SearchUserHabitSwapsModel> searchUserHabitSwaps(HashMap<String, Object> modelHashMap) {
        return finisherService.searchUserHabitSwaps(modelHashMap);
    }
    public Call<JsonObject> getWeeklyOverviewFlag(HashMap<String, Object> modelHashMap) {
        return finisherService.getWeeklyOverviewFlag(modelHashMap);
    }
    public Call<JsonObject> getAutoCompleteHabitFlag(HashMap<String, Object> modelHashMap) {
        return finisherService.getAutoCompleteHabitFlag(modelHashMap);
    }
    public Call<JsonObject> addUpdateHabitSwap(HashMap<String, Object> modelHashMap) {
        return finisherService.addUpdateHabitSwap(modelHashMap);
    }
    public Call<JsonObject> addUpdateBucketWithPhoto(ProgressRequestBody fileBody, @Part("jsonString") okhttp3.RequestBody json) {

        MultipartBody.Part file = null;
        if (fileBody != null) {
            file = MultipartBody.Part.createFormData("picture", fileBody.getFileName(), fileBody);
        }

        return finisherService.addUpdateBucketWithPhoto(file, json, System.currentTimeMillis());
    }
    public Call<BucketListModel> getBucketList(HashMap<String, Object> modelHashMap) {
        return finisherService.getBucketList(modelHashMap);
    }
    public Call<IndividualBucketListModel> getIndividualBucket(HashMap<String, Object> modelHashMap) {
        return finisherService.getIndividualBucket(modelHashMap);
    }
    public Call<JsonObject> UpdateBucketListManualOrder(HashMap<String, Object> modelHashMap) {
        return finisherService.UpdateBucketListManualOrder(modelHashMap);
    }
    public Call<JsonObject> deleteBucket(HashMap<String, Object> modelHashMap) {
        return finisherService.deleteBucket(modelHashMap);
    }
    public Call<JsonObject> ChangeBucketStatusAPI(HashMap<String, Object> modelHashMap) {
        return finisherService.ChangeBucketStatusAPI(modelHashMap);
    }
    public Call<JsonObject> addUpdateBucket(HashMap<String, Object> modelHashMap) {
        return finisherService.addUpdateBucket(modelHashMap);
    }
    public Call<MyValueListResponse> getValueListApi(HashMap<String, Object> modelHashMap) {
        return finisherService.getValueListApi(modelHashMap);
    }

    public Call<MeditationCourseModel> getGuidedMeditationsBySearchTag(HashMap<String, Object> modelHashMap) {
        return finisherService.GetGuidedMeditationsBySearchTag(modelHashMap);
    }

    public Call<JsonObject> ToggleEventLike(HashMap<String, Object> modelHashMap) {
        return finisherService.ToggleEventLike(modelHashMap);
    }
    public Call<MeditationTagResponse> getTagList(HashMap<String, Object> modelHashMap) {
        return finisherService.getTaglist(modelHashMap);
    }


    public Call<MeditationCourseModel> getArchivedWebinarsAbsolute(HashMap<String, Object> modelHashMap) {
        return finisherService.GetArchivedWebinarsAbsolute(modelHashMap);
    }


    public Call<SearchMbhqLiveChatByTagResponse> searchMbhqLiveChatByTag(HashMap<String, Object> modelHashMap) {
        return finisherService.SearchMbhqLiveChatByTag(modelHashMap);
    }

    public Call<GetMbhqLiveChatTagsResponse> getMbhqLiveChatTags(HashMap<String, Object> modelHashMap) {
        return finisherService.GetMbhqLiveChatTags(modelHashMap);
    }

    public Call<Suggestedmedicin> getsuggestedmeditation(HashMap<String, Object> modelHashMap) {
        return finisherService.getSuggestedmeditationdetails(modelHashMap);
    }

    public Call<GetHabitTemplatesResponseModel> getHabitTemplates(HashMap<String, Object> modelHashMap) {
        return finisherService.getHabitTemplates(modelHashMap);
    }
    public Call<GetUserHabitSwapModel> getUserHabitSwap(HashMap<String, Object> modelHashMap) {
        return finisherService.getUserHabitSwap(modelHashMap);
    }
    public Call<HabbitCalendarModel> GetHabitStats(HashMap<String, Object> modelHashMap) {
        return finisherService.GetHabitStats(modelHashMap);
    }
    public Call<JsonObject> UpdateTaskNote(HashMap<String, Object> modelHashMap) {
        return finisherService.UpdateTaskNote(modelHashMap);
    }


    public Call<JsonObject> emailReverseBucketList(HashMap<String, Object> modelHashMap) {
        return finisherService.emailReverseBucketList(modelHashMap);
    }
    public Call<MyAchievementsListModel> getMyAchievevmentsList(HashMap<String, Object> modelHashMap) {
        return finisherService.getMyAchievevmentsList(modelHashMap);
    }

    public Call<JsonObject> deleteAchievement(HashMap<String, Object> modelHashMap) {
        return finisherService.deleteAchievement(modelHashMap);
    }

    public Call<AddUpdateMyAchievementModel> addUpdateAchievement(HashMap<String, Object> modelHashMap) {
        return finisherService.addUpdateAchievement(modelHashMap);
    }

    public Call<Folderdefaultresponse> setDefault(HashMap<String, Object> modelHashMap) {
        return finisherService.setDefault(modelHashMap);
    }

    public Call<IndividualAchievementModel> selectAchievement(HashMap<String, Object> modelHashMap) {
        return finisherService.selectAchievement(modelHashMap);
    }

    public Call<JsonObject> moveEqfolder(HashMap<String, Object> modelHashMap) {
        return finisherService.moveeqname(modelHashMap);
    }




    public Call<JsonObject> updateHabitStatus(HashMap<String, Object> modelHashMap) {
        return finisherService.updateHabitStatus(modelHashMap);
    }
    public Call<GetWinTheWeekStatsResponse> getWinTheWeekStats(HashMap<String, Object> modelHashMap) {
        return finisherService.getWinTheWeekStats(modelHashMap);
    }
    public Call<AvailableCourseModel> getAvailableCourse(HashMap<String, Object> modelHashMap) {
        return finisherService.getAvailableCourse(modelHashMap);
    }
    public Call<ProgressCourseResponse> getProgressResponse(HashMap<String, Object> modelHashMap) {
        return finisherService.getProgressReponse(modelHashMap);
    }
    public Call<CourseDetailModel> getCourseDetail(HashMap<String, Object> modelHashMap) {
        return finisherService.getCourseDetails(modelHashMap);
    }
    public Call<JsonObject> updateCourseStats(HashMap<String, Object> modelHashMap) {
        return finisherService.UpdateCourseStatus(modelHashMap);
    }
    public Call<JsonObject> toggleMessageNotificationFlag(HashMap<String, Object> modelHashMap) {
        return finisherService.ToggleMessageNotificationFlag(modelHashMap);
    }
    public Call<JsonObject> toggleSeminarNotificationFlag(HashMap<String, Object> modelHashMap) {
        return finisherService.toggleSeminarNotificationFlag(modelHashMap);
    }
    public Call<JsonObject> refreshCourse(HashMap<String, Object> modelHashMap) {
        return finisherService.refreshCourse(modelHashMap);
    }

}
