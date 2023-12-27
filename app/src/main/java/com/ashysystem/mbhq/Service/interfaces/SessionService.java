package com.ashysystem.mbhq.Service.interfaces;
import com.ashysystem.mbhq.model.AddRewardModel;
import com.ashysystem.mbhq.model.CircuitListModel;
import com.ashysystem.mbhq.model.DropDownSessionModel;
import com.ashysystem.mbhq.model.EditDailySessionModel;
import com.ashysystem.mbhq.model.ExerciseRequestModel;
import com.ashysystem.mbhq.model.GetSquadWorkoutSessionModel;
import com.ashysystem.mbhq.model.QuickEditCircuitResponseModel;
import com.ashysystem.mbhq.model.SessionOverViewModel;
import com.ashysystem.mbhq.model.ShowIndividualVideoModel;
import com.ashysystem.mbhq.model.SwapModelForSession;
import com.ashysystem.mbhq.model.TargetExerciseModel;
import com.ashysystem.mbhq.model.ToggleModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by android-krishnendu on 9/27/16.
 */

public interface SessionService
{
    @POST("api/user/AddSquadUserAction")
    Call<AddRewardModel> AddRewardApi(@Body HashMap<String, Object> modelHashMap);
    @POST("api/exercise/GetExercise")
    Call<ShowIndividualVideoModel> getIndividualVideo(@Body HashMap<String, Object> modelHashMap);

    @GET("api/values/GetWorkoutDetails")
    Call<SessionOverViewModel> sessionOverView(@QueryMap Map<String, Object> options);

    @POST("api/Exercise/GetExercises")
    Call<ExerciseRequestModel> getExerciseList(@Body HashMap<String, Object> modelHashMap);

    @Headers("Content-Type:application/json")
    @GET
    Call<TargetExerciseModel> getTargetExercise(@Url String url);

    @Headers("Content-Type:application/json")
    @GET
    Call<CircuitListModel>getCircuitList(@Url String url);

    @POST("api/SquadDailySession/QuickEditDailySession")
    Call<QuickEditCircuitResponseModel> quickEditSquadDaily(@Body HashMap<String, Object> modelHashMap);

    @Headers("Content-Type:application/json")
    @POST("api/SquadDailySession/GetWorkoutSessionsFromDate")
    Call<DropDownSessionModel> getWorkoutSessionFromDate(@Body HashMap<String, Object> modelHashMap);

    @Headers("Content-Type:application/json")
    @POST("api/SquadDailySession/AddSwapCustomSession")
    Call<SwapModelForSession> addSwapCustomSession(@Body HashMap<String, Object> modelHashMap);


    @POST("api/SquadDailySession/EditSquadDailySession")
    Call<EditDailySessionModel> editSquadDaily(@Body HashMap<String, Object> modelHashMap);

    @POST("api/Exercise/GetSquadWorkoutSession")
    Call<GetSquadWorkoutSessionModel> getSquadWorkoutSession(@Body HashMap<String, Object> modelHashMap);

    @POST("api/squadexercise/FavoriteSessionToggle")
    Call<ToggleModel> favoriteSessionToggle(@Body HashMap<String, Object> modelHashMap);
}
