package com.ashysystem.mbhq.model;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.ashysystem.mbhq.model.MeditationCourseModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MeditationDataViewModel extends ViewModel {

    private String TAG = "MeditationViewModel";
    public List<MeditationCourseModel.Webinar> lstTotalDataM = new ArrayList<>();
    public JSONArray arrJson = new JSONArray();

    public MeditationDataViewModel() {
        super();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared");

    }
}
