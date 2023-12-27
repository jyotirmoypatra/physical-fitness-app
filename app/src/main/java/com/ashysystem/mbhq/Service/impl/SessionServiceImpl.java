package com.ashysystem.mbhq.Service.impl;

import android.content.Context;

import com.ashysystem.mbhq.Service.interfaces.SessionService;
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
import retrofit2.Call;


/**
 * Created by android-krishnendu on 9/27/16.
 */

public class SessionServiceImpl extends ServiceSessionMain {

    private SessionService sessionService;
    public SessionServiceImpl(Context context) {
        super(context);
        this.sessionService=super.getRetrofit().create(SessionService.class);
    }

    public Call<AddRewardModel> addRewardApi(HashMap<String, Object> hashReq) {
        return sessionService.AddRewardApi(hashReq);
    }
    public Call<ShowIndividualVideoModel> getIndividualVideo(HashMap<String, Object> modelHashMap){
        return sessionService.getIndividualVideo(modelHashMap);
    }

    public Call<SessionOverViewModel> getSessionOverView(HashMap<String, Object> queryHm) {
        return sessionService.sessionOverView(queryHm);
    }

    public Call<ExerciseRequestModel> getExerciseList(HashMap<String, Object> modelHashMap) {
        return sessionService.getExerciseList(modelHashMap);
    }

    public Call<TargetExerciseModel> getTargetExercise(String url) {
        return sessionService.getTargetExercise(url);
    }

    public Call<CircuitListModel> getCircuitList(String url) {
        return sessionService.getCircuitList(url);
    }

    public Call<QuickEditCircuitResponseModel> getQuickEditList(HashMap<String, Object> modelHashMap){
        return sessionService.quickEditSquadDaily(modelHashMap);
    }

    public Call<DropDownSessionModel> getWorkoutSessionsFromDate(HashMap<String, Object> queryHm) {
        return sessionService.getWorkoutSessionFromDate(queryHm);
    }

    public Call<SwapModelForSession> addSwapCustomSession(HashMap<String, Object> queryHm) {
        return sessionService.addSwapCustomSession(queryHm);
    }

    public Call<EditDailySessionModel> editSquadDailySession(HashMap<String, Object> modelHashMap) {
        return sessionService.editSquadDaily(modelHashMap);
    }

    public Call<GetSquadWorkoutSessionModel> getSquadWorkoutSession(HashMap<String, Object> modelHashMap){
        return sessionService.getSquadWorkoutSession(modelHashMap);
    }

    public Call<ToggleModel> favoriteSessionToggle(HashMap<String, Object> modelHashMap){
        return sessionService.favoriteSessionToggle(modelHashMap);
    }
}
