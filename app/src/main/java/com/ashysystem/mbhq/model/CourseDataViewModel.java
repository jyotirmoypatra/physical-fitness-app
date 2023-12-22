package com.ashysystem.mbhq.model;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CourseDataViewModel extends ViewModel {



    public List<AvailableCourseModel.Course> lstTotalDataM = new ArrayList<>();

    /////////////////////////////////////////
    public List<AvailableCourseModel.Course> myInProgressPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> myPausedPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> myCompletedPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> myAllPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> myVirginPrograms = new ArrayList<>();

    /////////////////////////////////////////
    public List<AvailableCourseModel.Course> inProgressMemberPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> pausedMemberPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> completedMemberPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> allMemberPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> virginMemberPrograms = new ArrayList<>();

    ///////////////////////////////////////////////
    public List<AvailableCourseModel.Course> allLivePrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> pausedLivePrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> inProgressLivePrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> completedLivePrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> virginLivePrograms = new ArrayList<>();

    ///////////////////////////////////////////////////
    public List<AvailableCourseModel.Course> allPaidPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> pausedPaidPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> inProgressPaidPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> completedPaidPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> virginPaidPrograms = new ArrayList<>();

    ///////////////////////////////////////////////////
    public List<AvailableCourseModel.Course> allMaterclassPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> pausedMaterclassPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> inProgressMaterclassPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> completedMaterclassPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> virginMaterclassPrograms = new ArrayList<>();

    ///////////////////////////////////////////////////
    public List<AvailableCourseModel.Course> allPaidMaterclassPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> pausedPaidMaterclassPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> inProgressPaidMaterclassPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> completedPaidMaterclassPrograms = new ArrayList<>();
    public List<AvailableCourseModel.Course> virginPaidMaterclassPrograms = new ArrayList<>();

    //////////////////////////////////////////

    public List<AvailableCourseModel.Course> allPodcastPrograms = new ArrayList<AvailableCourseModel.Course>(){{
        add(new AvailableCourseModel.Course(){{
            setCourseId(-1);
            setCourseName("MindBodyHQ");
            setCourseType("");
            setStatus(0);
            setSubscriptionType(0);
            setAuthorName("Levi Walz");
            setImageURL2("https://squad-live.s3-ap-southeast-2.amazonaws.com/mbHQ+images/MindbodyPODCAST+for+App.png");
            setWeekNumber(-1);
        }});
    }};

    public ArrayList<String> allTags = new ArrayList<String>();

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared");

    }

}
