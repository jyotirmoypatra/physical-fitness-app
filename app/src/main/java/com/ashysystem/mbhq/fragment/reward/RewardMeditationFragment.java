package com.ashysystem.mbhq.fragment.reward;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;

import com.ashysystem.mbhq.fragment.habit_hacker.MbhqTodayMainFragment;
import com.ashysystem.mbhq.model.GetStreakDataResponse;
import com.ashysystem.mbhq.model.StreakData;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RewardMeditationFragment extends Fragment {

    TextView txtGratitudeStreak, txtPBStreak, txtTotal;
    ImageView imgThree, imgCompleteThree, imgSeven, imgCompleteSeven, imgFourteen, imgCompleteFourteen, imgTwentyOne, imgCompleteTwentyOne, imgTwentyEight, imgCompleteTwentyEight, imgThirtyFive,
            imgCompleteThirtyFive, imgFortyTwo, imgCompleteFortyTwo, imgFifty, imgCompleteFifty,
            imgSeventyFive, imgCompleteSeventyFive, imgHundred, imgCompleteHundred, imgOneTwentyFive, imgCompleteOneTwentyFive, imgOneFifty, imgCompleteOneFifty,
            imgOneSeventyFive, imgCompleteOneSeventyFive, imgTwoHundred, imgCompleteTwoHundred, imgTwoTwentyFive, imgCompleteTwoTwentyFive, imgTwoFifty, imgCompleteTwoFifty, imgTwoSeventyFive,
            imgCompleteTwoSeventyFive, imgThreeHundred, imgCompleteThreeHundred, imgThreeTwentyFive, imgCompleteThreeTwentyFive, imgThreeFifty, imgCompleteThreeFifty, imgThreeSeventyFive,
            imgCompleteThreeSeventyFive, imgFourHundred, imgCompleteFourHundred, imgFourTwentyFive, imgCompleteFourTwentyFive, imgFourFifty, imgCompleteFourFifty, imgFourSeventyFive,
            imgCompleteFourSeventyFive, imgFiveHundred, imgCompleteFiveHundred, imgFiveFifty,
            imgCompleteFiveFifty, imgSixHundred, imgCompleteSixHundred, imgSixFifty,
            imgCompleteSixFifty,
            imgSevenHundred, imgCompleteSevenHundred, imgSevenFifty, imgCompleteSevenFifty, imgEightHundred, imgCompleteEightHundred, imgEightFifty, imgCompleteEightFifty, imgNineHundred, imgCompleteNineHundred,
            imgNineFifty, imgCompleteNineFifty, imgThousand, imgCompleteThousand, imgElevenHundred, imgCompleteElevenHundred, imgTwelveHundred, imgCompleteTwelveHundred,
            imgThirteenHundred, imgCompleteThirteenHundred, imgFourteenHundred, imgCompleteFourteenHundred, imgFifteenHundred, imgCompleteFifteenHundred, imgSixteenHundred,
            imgCompleteSixteenHundred, imgSeventeenHundred, imgCompleteSeventeenHundred,
            imgEighteenHundred, imgCompleteEighteenHundred, imgNineteenHundred, imgCompleteNineteenHundred,
            imgTwoThousand, imgCompleteTwoThousand, imgTwentyTwoFifty, imgCompleteTwentyTwoFifty,
            imgTwntyFiveHundred, imgCompleteTwntyFiveHundred, imgTwentySevenFifty, imgCompleteTwentySevenFifty,
            imgThreeThousand, imgCompleteThreeThousand, imgFiveThousand, imgCompleteFiveThousand, imgTenThousand, imgCompleteTenThousand;

    TextView txtThree, txtSeven, txtFourteen, txtTwentyOne, txtTwentyEight, txtThirtyFive, txtFortyTwo,
            txtFifty, txtSeventyFive, txtHundred, txtOneTwentyFive, txtOneFifty, txtOneSeventyFive,
            txtTwoHundred, txtTwoTwentyFive, txtTwoFifty, txtTwoSeventyFive, txtThreeHundred,
            txtThreeTwentyFive, txtThreeFifty, txtThreeSeventyFive, txtFourHundred, txtFourTwentyFive, txtFourFifty, txtFourSeventyFive, txtFiveHundred, txtFiveFifty, txtSixHundred,
            txtSixFifty, txtSevenHundred, txtSevenFifty, txtEightHundred, txtEightFifty,
            txtNineHundred, txtNineFifty, txtThousand, txtElevenHundred, txtTwelveHundred,
            txtThirteenHundred, txtFourteenHundred, txtFifteenHundred, txtSixteenHundred,
            txtSeventeenHundred, txtEighteenHundred, txtNineteenHundred, txtTwoThousand,
            txtTwentyTwoFifty, txtTwntyFiveHundred, txtTwentySevenFifty, txtThreeThousand,
            txtFiveThousand, txtTenThousand;

    RelativeLayout rlCompleteThree, rlCompleteSeven, rlCompleteFourteen, rlCompleteTwentyOne,
            rlCompleteTwentyEight, rlCompleteThirtyFive, rlCompleteFortyTwo, rlCompleteFifty, rlCompleteSeventyFive, rlCompleteHundred, rlCompleteOneTwentyFive, rlCompleteOneFifty,
            rlCompleteOneSeventyFive, rlCompleteTwoHundred, rlCompleteTwoTwentyFive,
            rlCompleteTwoFifty, rlCompleteTwoSeventyFive, rlCompleteThreeHundred, rlCompleteThreeTwentyFive,
            rlCompleteThreeFifty, rlCompleteThreeSeventyFive, rlCompleteFourHundred, rlCompleteFourTwentyFive, rlCompleteFourFifty, rlCompleteFourSeventyFive, rlCompleteFiveHundred,
            rlCompleteFiveFifty, rlCompleteSixHundred, rlCompleteSixFifty, rlCompleteSevenHundred, rlCompleteSevenFifty, rlCompleteEightHundred, rlCompleteEightFifty,
            rlCompleteNineHundred, rlCompleteNineFifty, rlCompleteThousand, rlCompleteElevenHundred, rlCompleteTwelveHundred, rlCompleteThirteenHundred, rlCompleteFourteenHundred,
            rlCompleteFifteenHundred, rlCompleteSixteenHundred, rlCompleteSeventeenHundred,
            rlCompleteEighteenHundred, rlCompleteNineteenHundred, rlCompleteTwoThousand, rlCompleteTwentyTwoFifty,
            rlCompleteTwntyFiveHundred, rlCompleteTwentySevenFifty, rlCompleteThreeThousand, rlCompleteFiveThousand, rlCompleteTenThousand;

    Toolbar toolbar;
    SharedPreference sharedPreference;

    Integer globalTotalStrakValue = 0;
    private View view;
    private SwipeRefreshLayout swipeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) return view;
        view = inflater.inflate(R.layout.fragment_reward_new, null);

        funToolBar();

        TextView lblGratitudeStreak = view.findViewById(R.id.lblGratitudeStreak);
        lblGratitudeStreak.setText("Meditation Streak");

        txtGratitudeStreak = view.findViewById(R.id.txtGratitudeStreak);
        txtPBStreak = view.findViewById(R.id.txtPBStreak);
        txtTotal = view.findViewById(R.id.txtTotal);
        txtThree = view.findViewById(R.id.txtThree);
        txtSeven = view.findViewById(R.id.txtSeven);
        txtFourteen = view.findViewById(R.id.txtFourteen);
        txtTwentyEight = view.findViewById(R.id.txtTwentyEight);
        txtThousand = view.findViewById(R.id.txtThousand);
        txtFiveThousand = view.findViewById(R.id.txtFiveThousand);
        txtTenThousand = view.findViewById(R.id.txtTenThousand);

        imgCompleteThree = view.findViewById(R.id.imgCompleteThree);
        imgCompleteSeven = view.findViewById(R.id.imgCompleteSeven);
        imgCompleteFourteen = view.findViewById(R.id.imgCompleteFourteen);
        imgCompleteTwentyOne = view.findViewById(R.id.imgCompleteTwentyOne);
        imgCompleteTwentyEight = view.findViewById(R.id.imgCompleteTwentyEight);
        imgSeventyFive = view.findViewById(R.id.imgSeventyFive);
        imgHundred = view.findViewById(R.id.imgHundred);
        imgOneTwentyFive = view.findViewById(R.id.imgOneTwentyFive);
        imgOneFifty = view.findViewById(R.id.imgOneFifty);
        imgOneFifty = view.findViewById(R.id.imgOneFifty);
        imgOneSeventyFive = view.findViewById(R.id.imgOneSeventyFive);
        imgTwoHundred = view.findViewById(R.id.imgTwoHundred);
        imgTwoTwentyFive = view.findViewById(R.id.imgTwoTwentyFive);
        imgTwoFifty = view.findViewById(R.id.imgTwoFifty);
        imgTwoSeventyFive = view.findViewById(R.id.imgTwoSeventyFive);
        imgThreeHundred = view.findViewById(R.id.imgThreeHundred);
        imgThreeTwentyFive = view.findViewById(R.id.imgThreeTwentyFive);
        imgThreeFifty = view.findViewById(R.id.imgThreeFifty);
        imgThreeSeventyFive = view.findViewById(R.id.imgThreeSeventyFive);
        imgFourHundred = view.findViewById(R.id.imgFourHundred);
        imgFourTwentyFive = view.findViewById(R.id.imgFourTwentyFive);
        imgFourFifty = view.findViewById(R.id.imgFourFifty);
        imgFourSeventyFive = view.findViewById(R.id.imgFourSeventyFive);
        imgFiveHundred = view.findViewById(R.id.imgFiveHundred);
        imgFiveFifty = view.findViewById(R.id.imgFiveFifty);
        imgSixHundred = view.findViewById(R.id.imgSixHundred);
        imgSixFifty = view.findViewById(R.id.imgSixFifty);
        imgSevenHundred = view.findViewById(R.id.imgSevenHundred);
        imgSevenFifty = view.findViewById(R.id.imgSevenFifty);
        imgEightHundred = view.findViewById(R.id.imgEightHundred);
        imgEightFifty = view.findViewById(R.id.imgEightFifty);
        imgNineHundred = view.findViewById(R.id.imgNineHundred);
        imgNineFifty = view.findViewById(R.id.imgNineFifty);
        imgThousand = view.findViewById(R.id.imgThousand);
        imgElevenHundred = view.findViewById(R.id.imgElevenHundred);
        imgTwelveHundred = view.findViewById(R.id.imgTwelveHundred);
        imgThirteenHundred = view.findViewById(R.id.imgThirteenHundred);
        imgFourteenHundred = view.findViewById(R.id.imgFourteenHundred);
        imgFifteenHundred = view.findViewById(R.id.imgFifteenHundred);
        imgSixteenHundred = view.findViewById(R.id.imgSixteenHundred);
        imgSeventeenHundred = view.findViewById(R.id.imgSeventeenHundred);
        imgEighteenHundred = view.findViewById(R.id.imgEighteenHundred);
        imgNineteenHundred = view.findViewById(R.id.imgNineteenHundred);
        imgTwoThousand = view.findViewById(R.id.imgTwoThousand);
        imgTwentyTwoFifty = view.findViewById(R.id.imgTwentyTwoFifty);
        imgTwntyFiveHundred = view.findViewById(R.id.imgTwntyFiveHundred);
        imgTwentySevenFifty = view.findViewById(R.id.imgTwentySevenFifty);
        imgThreeThousand = view.findViewById(R.id.imgThreeThousand);
        imgFiveThousand = view.findViewById(R.id.imgFiveThousand);
        imgTenThousand = view.findViewById(R.id.imgTenThousand);

        txtTwentyOne = view.findViewById(R.id.txtTwentyOne);
        txtThirtyFive = view.findViewById(R.id.txtThirtyFive);
        txtFortyTwo = view.findViewById(R.id.txtFortyTwo);
        txtFifty = view.findViewById(R.id.txtFifty);
        txtSeventyFive = view.findViewById(R.id.txtSeventyFive);
        txtHundred = view.findViewById(R.id.txtHundred);
        txtOneTwentyFive = view.findViewById(R.id.txtOneTwentyFive);
        txtOneFifty = view.findViewById(R.id.txtOneFifty);
        txtOneSeventyFive = view.findViewById(R.id.txtOneSeventyFive);
        txtTwoHundred = view.findViewById(R.id.txtTwoHundred);
        txtTwoTwentyFive = view.findViewById(R.id.txtTwoTwentyFive);
        txtTwoFifty = view.findViewById(R.id.txtTwoFifty);
        txtTwoSeventyFive = view.findViewById(R.id.txtTwoSeventyFive);
        txtThreeHundred = view.findViewById(R.id.txtThreeHundred);
        txtThreeTwentyFive = view.findViewById(R.id.txtThreeTwentyFive);
        txtThreeFifty = view.findViewById(R.id.txtThreeFifty);
        txtThreeSeventyFive = view.findViewById(R.id.txtThreeSeventyFive);
        txtFourHundred = view.findViewById(R.id.txtFourHundred);
        txtFourTwentyFive = view.findViewById(R.id.txtFourTwentyFive);
        txtFourFifty = view.findViewById(R.id.txtFourFifty);
        txtFourSeventyFive = view.findViewById(R.id.txtFourSeventyFive);
        txtFiveHundred = view.findViewById(R.id.txtFiveHundred);
        txtFiveFifty = view.findViewById(R.id.txtFiveFifty);
        txtSixHundred = view.findViewById(R.id.txtSixHundred);
        txtSixFifty = view.findViewById(R.id.txtSixFifty);
        txtSevenHundred = view.findViewById(R.id.txtSevenHundred);
        txtSevenFifty = view.findViewById(R.id.txtSevenFifty);
        txtEightHundred = view.findViewById(R.id.txtEightHundred);
        txtEightFifty = view.findViewById(R.id.txtEightFifty);
        txtNineHundred = view.findViewById(R.id.txtNineHundred);
        txtNineFifty = view.findViewById(R.id.txtNineFifty);
        txtThousand = view.findViewById(R.id.txtThousand);
        txtElevenHundred = view.findViewById(R.id.txtElevenHundred);
        txtTwelveHundred = view.findViewById(R.id.txtTwelveHundred);
        txtThirteenHundred = view.findViewById(R.id.txtThirteenHundred);
        txtFourteenHundred = view.findViewById(R.id.txtFourteenHundred);
        txtFifteenHundred = view.findViewById(R.id.txtFifteenHundred);
        txtSixteenHundred = view.findViewById(R.id.txtSixteenHundred);
        txtSeventeenHundred = view.findViewById(R.id.txtSeventeenHundred);
        txtEighteenHundred = view.findViewById(R.id.txtEighteenHundred);
        txtNineteenHundred = view.findViewById(R.id.txtNineteenHundred);
        txtTwoThousand = view.findViewById(R.id.txtTwoThousand);
        txtTwentyTwoFifty = view.findViewById(R.id.txtTwentyTwoFifty);
        txtTwntyFiveHundred = view.findViewById(R.id.txtTwntyFiveHundred);
        txtTwentySevenFifty = view.findViewById(R.id.txtTwentySevenFifty);
        txtThreeThousand = view.findViewById(R.id.txtThreeThousand);
        txtFiveThousand = view.findViewById(R.id.txtFiveThousand);
        txtTenThousand = view.findViewById(R.id.txtTenThousand);


        rlCompleteThree = view.findViewById(R.id.rlCompleteThree);
        rlCompleteSeven = view.findViewById(R.id.rlCompleteSeven);
        rlCompleteFourteen = view.findViewById(R.id.rlCompleteFourteen);
        rlCompleteTwentyOne = view.findViewById(R.id.rlCompleteTwentyOne);
        rlCompleteTwentyEight = view.findViewById(R.id.rlCompleteTwentyEight);
        rlCompleteThirtyFive = view.findViewById(R.id.rlCompleteThirtyFive);
        rlCompleteFortyTwo = view.findViewById(R.id.rlCompleteFortyTwo);
        rlCompleteFifty = view.findViewById(R.id.rlCompleteFifty);
        rlCompleteSeventyFive = view.findViewById(R.id.rlCompleteSeventyFive);
        rlCompleteHundred = view.findViewById(R.id.rlCompleteHundred);
        rlCompleteOneTwentyFive = view.findViewById(R.id.rlCompleteOneTwentyFive);
        rlCompleteOneFifty = view.findViewById(R.id.rlCompleteOneFifty);
        rlCompleteOneSeventyFive = view.findViewById(R.id.rlCompleteOneSeventyFive);
        rlCompleteTwoHundred = view.findViewById(R.id.rlCompleteTwoHundred);
        rlCompleteTwoTwentyFive = view.findViewById(R.id.rlCompleteTwoTwentyFive);
        rlCompleteTwoFifty = view.findViewById(R.id.rlCompleteTwoFifty);
        rlCompleteTwoSeventyFive = view.findViewById(R.id.rlCompleteTwoSeventyFive);
        rlCompleteThreeHundred = view.findViewById(R.id.rlCompleteThreeHundred);
        rlCompleteThreeTwentyFive = view.findViewById(R.id.rlCompleteThreeTwentyFive);
        rlCompleteThreeFifty = view.findViewById(R.id.rlCompleteThreeFifty);
        rlCompleteThreeSeventyFive = view.findViewById(R.id.rlCompleteThreeSeventyFive);
        rlCompleteFourHundred = view.findViewById(R.id.rlCompleteFourHundred);
        rlCompleteFourTwentyFive = view.findViewById(R.id.rlCompleteFourTwentyFive);
        rlCompleteFourFifty = view.findViewById(R.id.rlCompleteFourFifty);
        rlCompleteFourSeventyFive = view.findViewById(R.id.rlCompleteFourSeventyFive);
        rlCompleteFiveHundred = view.findViewById(R.id.rlCompleteFiveHundred);
        rlCompleteFiveHundred = view.findViewById(R.id.rlCompleteFiveHundred);
        rlCompleteFiveFifty = view.findViewById(R.id.rlCompleteFiveFifty);
        rlCompleteSixHundred = view.findViewById(R.id.rlCompleteSixHundred);
        rlCompleteSixFifty = view.findViewById(R.id.rlCompleteSixFifty);
        rlCompleteSevenHundred = view.findViewById(R.id.rlCompleteSevenHundred);
        rlCompleteSevenFifty = view.findViewById(R.id.rlCompleteSevenFifty);
        rlCompleteEightHundred = view.findViewById(R.id.rlCompleteEightHundred);
        rlCompleteEightFifty = view.findViewById(R.id.rlCompleteEightFifty);
        rlCompleteNineHundred = view.findViewById(R.id.rlCompleteNineHundred);
        rlCompleteNineFifty = view.findViewById(R.id.rlCompleteNineFifty);
        rlCompleteThousand = view.findViewById(R.id.rlCompleteThousand);
        rlCompleteElevenHundred = view.findViewById(R.id.rlCompleteElevenHundred);
        rlCompleteTwelveHundred = view.findViewById(R.id.rlCompleteTwelveHundred);
        rlCompleteThirteenHundred = view.findViewById(R.id.rlCompleteThirteenHundred);
        rlCompleteFourteenHundred = view.findViewById(R.id.rlCompleteFourteenHundred);
        rlCompleteFifteenHundred = view.findViewById(R.id.rlCompleteFifteenHundred);
        rlCompleteSixteenHundred = view.findViewById(R.id.rlCompleteSixteenHundred);
        rlCompleteSeventeenHundred = view.findViewById(R.id.rlCompleteSeventeenHundred);
        rlCompleteEighteenHundred = view.findViewById(R.id.rlCompleteEighteenHundred);
        rlCompleteNineteenHundred = view.findViewById(R.id.rlCompleteNineteenHundred);
        rlCompleteTwoThousand = view.findViewById(R.id.rlCompleteTwoThousand);
        rlCompleteTwentyTwoFifty = view.findViewById(R.id.rlCompleteTwentyTwoFifty);
        rlCompleteTwntyFiveHundred = view.findViewById(R.id.rlCompleteTwntyFiveHundred);
        rlCompleteTwentySevenFifty = view.findViewById(R.id.rlCompleteTwentySevenFifty);
        rlCompleteThreeThousand = view.findViewById(R.id.rlCompleteThreeThousand);
        rlCompleteFiveThousand = view.findViewById(R.id.rlCompleteFiveThousand);
        rlCompleteTenThousand = view.findViewById(R.id.rlCompleteTenThousand);

        imgThirtyFive = view.findViewById(R.id.imgThirtyFive);
        imgFortyTwo = view.findViewById(R.id.imgFortyTwo);
        imgFifty = view.findViewById(R.id.imgFifty);
        imgCompleteFifty = view.findViewById(R.id.imgCompleteFifty);
        imgCompleteFifty = view.findViewById(R.id.imgCompleteFifty);
        imgCompleteFortyTwo = view.findViewById(R.id.imgCompleteFortyTwo);
        imgCompleteThirtyFive = view.findViewById(R.id.imgCompleteThirtyFive);
        imgTwentyOne = view.findViewById(R.id.imgTwentyOne);
        imgTwentyOne = view.findViewById(R.id.imgTwentyOne);
        imgThree = view.findViewById(R.id.imgThree);
        imgSeven = view.findViewById(R.id.imgSeven);
        imgFourteen = view.findViewById(R.id.imgFourteen);
        imgTwentyEight = view.findViewById(R.id.imgTwentyEight);
        imgThousand = view.findViewById(R.id.imgThousand);
        imgFiveThousand = view.findViewById(R.id.imgFiveThousand);
        imgTenThousand = view.findViewById(R.id.imgTenThousand);
        swipeLayout = view.findViewById(R.id.swipeLayout);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMeditationStreakData();
                swipeLayout.setRefreshing(false);
            }
        });

        sharedPreference = new SharedPreference(getActivity());

        populateData();
        getMeditationStreakData();

        return view;
    }

    private void getMeditationStreakData() {
        TextView lblGratitudeStreak = view.findViewById(R.id.lblGratitudeStreak);
        lblGratitudeStreak.setText("Meditation Streak");

        if (Connection.checkConnection(getContext())) {
            ProgressDialog progressDialog = ProgressDialog.show(getContext(), "", "Please wait...");
            progressDialog.setCancelable(true);
            SharedPreference sharedPreference = new SharedPreference(getContext());
            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("UserCurrentDate", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
            hashReq.put("Key", Util.KEY);


            Log.e("RequestSent", hashReq.toString());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getContext());
            Call<GetStreakDataResponse> serverCall = finisherService.getMeditationStreakData(hashReq);
            serverCall.enqueue(new Callback<GetStreakDataResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<GetStreakDataResponse> call, Response<GetStreakDataResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        GetStreakDataResponse dataResponse = response.body();
                        if (dataResponse != null && dataResponse.getSuccessFlag()) {
                            StreakData streakData = dataResponse.getStreakData();
                            String currentStreak = String.valueOf(streakData.getCurrentStreak()) + System.lineSeparator();
                            sharedPreference.write("CURRENT_STREAK_MEDITATION", "", String.valueOf(streakData.getCurrentStreak()));
                            sharedPreference.write("TOP_STREAK_MEDITATION", "", String.valueOf(streakData.getTopStreak()));
                            sharedPreference.write("LAST_STREAK_MEDITATION", "", String.valueOf(streakData.getLastStreak()));
                            sharedPreference.write("TOTAL_STREAK_MEDITATION", "", String.valueOf(streakData.getTotal()));

                            populateData();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetStreakDataResponse> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });


        }
    }

    private void populateData() {
        if (!sharedPreference.read("CURRENT_STREAK_MEDITATION", "").equals("")) {
            txtGratitudeStreak.setText(sharedPreference.read("CURRENT_STREAK_MEDITATION", ""));
        } else {
            txtGratitudeStreak.setText("0");
        }
        if (!sharedPreference.read("TOP_STREAK_MEDITATION", "").equals("")) {
            txtPBStreak.setText(sharedPreference.read("TOP_STREAK_MEDITATION", ""));
        } else {
            txtPBStreak.setText("0");
        }
        if (!sharedPreference.read("TOTAL_STREAK_MEDITATION", "").equals("")) {
            txtTotal.setText(sharedPreference.read("TOTAL_STREAK_MEDITATION", ""));
        } else {
            txtTotal.setText("0");
        }

        try {
            globalTotalStrakValue = Integer.parseInt(sharedPreference.read("TOTAL_STREAK_MEDITATION", ""));
            Log.e("GRATITUDE_STREAK_VALUE", globalTotalStrakValue + ">>>>");
        } catch (Exception e) {
            e.printStackTrace();
        }


        if ((globalTotalStrakValue >= 3 && globalTotalStrakValue < 7) || globalTotalStrakValue >= 7) {
            imgThree.setVisibility(View.GONE);
            txtThree.setVisibility(View.GONE);
            rlCompleteThree.setVisibility(View.VISIBLE);
        }
        if ((globalTotalStrakValue >= 7 && globalTotalStrakValue < 14) || globalTotalStrakValue >= 14) {
            imgSeven.setVisibility(View.GONE);
            txtSeven.setVisibility(View.GONE);
            rlCompleteSeven.setVisibility(View.VISIBLE);
        }
        if ((globalTotalStrakValue >= 14 && globalTotalStrakValue < 21) || globalTotalStrakValue >= 21) {
            imgFourteen.setVisibility(View.GONE);
            txtFourteen.setVisibility(View.GONE);
            rlCompleteFourteen.setVisibility(View.VISIBLE);
        }
        if ((globalTotalStrakValue >= 21 && globalTotalStrakValue < 28) || globalTotalStrakValue >= 28) {
            imgTwentyOne.setVisibility(View.GONE);
            txtTwentyOne.setVisibility(View.GONE);
            rlCompleteTwentyOne.setVisibility(View.VISIBLE);
        }
        if ((globalTotalStrakValue >= 28 && globalTotalStrakValue < 35) || globalTotalStrakValue >= 35) {
            imgTwentyEight.setVisibility(View.GONE);
            txtTwentyEight.setVisibility(View.GONE);
            rlCompleteTwentyEight.setVisibility(View.VISIBLE);
        }
        if ((globalTotalStrakValue >= 35 && globalTotalStrakValue < 42) || globalTotalStrakValue >= 42) {
            imgThirtyFive.setVisibility(View.GONE);
            txtThirtyFive.setVisibility(View.GONE);
            rlCompleteThirtyFive.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 42 && globalTotalStrakValue < 50) || globalTotalStrakValue >= 50) {
            imgFortyTwo.setVisibility(View.GONE);
            txtFortyTwo.setVisibility(View.GONE);
            rlCompleteFortyTwo.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 50 && globalTotalStrakValue < 75) || globalTotalStrakValue >= 75) {
            imgFifty.setVisibility(View.GONE);
            txtFifty.setVisibility(View.GONE);
            rlCompleteFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 75 && globalTotalStrakValue < 100) || globalTotalStrakValue >= 100) {
            imgSeventyFive.setVisibility(View.GONE);
            txtSeventyFive.setVisibility(View.GONE);
            rlCompleteSeventyFive.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 100 && globalTotalStrakValue < 125) || globalTotalStrakValue >= 125) {
            imgHundred.setVisibility(View.GONE);
            txtHundred.setVisibility(View.GONE);
            rlCompleteHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 125 && globalTotalStrakValue < 150) || globalTotalStrakValue >= 150) {
            imgOneTwentyFive.setVisibility(View.GONE);
            txtOneTwentyFive.setVisibility(View.GONE);
            rlCompleteOneTwentyFive.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 150 && globalTotalStrakValue < 175) || globalTotalStrakValue >= 175) {
            imgOneFifty.setVisibility(View.GONE);
            txtOneFifty.setVisibility(View.GONE);
            rlCompleteOneFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 175 && globalTotalStrakValue < 200) || globalTotalStrakValue >= 200) {
            imgOneSeventyFive.setVisibility(View.GONE);
            txtOneSeventyFive.setVisibility(View.GONE);
            rlCompleteOneSeventyFive.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 200 && globalTotalStrakValue < 225) || globalTotalStrakValue >= 225) {
            imgTwoHundred.setVisibility(View.GONE);
            txtTwoHundred.setVisibility(View.GONE);
            rlCompleteTwoHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 225 && globalTotalStrakValue < 250) || globalTotalStrakValue >= 250) {
            imgTwoTwentyFive.setVisibility(View.GONE);
            txtTwoTwentyFive.setVisibility(View.GONE);
            rlCompleteTwoTwentyFive.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 250 && globalTotalStrakValue < 275) || globalTotalStrakValue >= 275) {
            imgTwoFifty.setVisibility(View.GONE);
            txtTwoFifty.setVisibility(View.GONE);
            rlCompleteTwoFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 275 && globalTotalStrakValue < 300) || globalTotalStrakValue >= 300) {
            imgTwoSeventyFive.setVisibility(View.GONE);
            txtTwoSeventyFive.setVisibility(View.GONE);
            rlCompleteTwoSeventyFive.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 300 && globalTotalStrakValue < 325) || globalTotalStrakValue >= 325) {
            imgThreeHundred.setVisibility(View.GONE);
            txtThreeHundred.setVisibility(View.GONE);
            rlCompleteThreeHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 325 && globalTotalStrakValue < 350) || globalTotalStrakValue >= 350) {
            imgThreeTwentyFive.setVisibility(View.GONE);
            txtThreeTwentyFive.setVisibility(View.GONE);
            rlCompleteThreeTwentyFive.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 350 && globalTotalStrakValue < 375) || globalTotalStrakValue >= 375) {
            imgThreeFifty.setVisibility(View.GONE);
            txtThreeFifty.setVisibility(View.GONE);
            rlCompleteThreeFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 375 && globalTotalStrakValue < 400) || globalTotalStrakValue >= 375) {
            imgThreeSeventyFive.setVisibility(View.GONE);
            txtThreeSeventyFive.setVisibility(View.GONE);
            rlCompleteThreeSeventyFive.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 400 && globalTotalStrakValue < 425) || globalTotalStrakValue >= 425) {
            imgFourHundred.setVisibility(View.GONE);
            txtFourHundred.setVisibility(View.GONE);
            rlCompleteFourHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 425 && globalTotalStrakValue < 450) || globalTotalStrakValue >= 450) {
            imgFourTwentyFive.setVisibility(View.GONE);
            txtFourTwentyFive.setVisibility(View.GONE);
            rlCompleteFourTwentyFive.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 450 && globalTotalStrakValue < 475) || globalTotalStrakValue >= 475) {
            imgFourFifty.setVisibility(View.GONE);
            txtFourFifty.setVisibility(View.GONE);
            rlCompleteFourFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 475 && globalTotalStrakValue < 500) || globalTotalStrakValue >= 500) {
            imgFourSeventyFive.setVisibility(View.GONE);
            txtFourSeventyFive.setVisibility(View.GONE);
            rlCompleteFourSeventyFive.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 500 && globalTotalStrakValue < 550) || globalTotalStrakValue >= 550) {
            imgFiveHundred.setVisibility(View.GONE);
            txtFiveHundred.setVisibility(View.GONE);
            rlCompleteFiveHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 550 && globalTotalStrakValue < 600) || globalTotalStrakValue >= 600) {
            imgFiveFifty.setVisibility(View.GONE);
            txtFiveFifty.setVisibility(View.GONE);
            rlCompleteFiveFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 600 && globalTotalStrakValue < 650) || globalTotalStrakValue >= 650) {
            imgSixHundred.setVisibility(View.GONE);
            txtSixHundred.setVisibility(View.GONE);
            rlCompleteSixHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 650 && globalTotalStrakValue < 700) || globalTotalStrakValue >= 700) {
            imgSixFifty.setVisibility(View.GONE);
            txtSixFifty.setVisibility(View.GONE);
            rlCompleteSixFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 700 && globalTotalStrakValue < 750) || globalTotalStrakValue >= 750) {
            imgSevenHundred.setVisibility(View.GONE);
            txtSevenHundred.setVisibility(View.GONE);
            rlCompleteSevenHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 750 && globalTotalStrakValue < 800) || globalTotalStrakValue >= 800) {
            imgSevenFifty.setVisibility(View.GONE);
            txtSevenFifty.setVisibility(View.GONE);
            rlCompleteSevenFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 800 && globalTotalStrakValue < 850) || globalTotalStrakValue >= 850) {
            imgEightHundred.setVisibility(View.GONE);
            txtEightHundred.setVisibility(View.GONE);
            rlCompleteEightHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 850 && globalTotalStrakValue < 900) || globalTotalStrakValue >= 900) {
            imgEightFifty.setVisibility(View.GONE);
            txtEightFifty.setVisibility(View.GONE);
            rlCompleteEightFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 900 && globalTotalStrakValue < 950) || globalTotalStrakValue >= 950) {
            imgNineHundred.setVisibility(View.GONE);
            txtNineHundred.setVisibility(View.GONE);
            rlCompleteNineHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 950 && globalTotalStrakValue < 1000) || globalTotalStrakValue >= 1000) {
            imgNineFifty.setVisibility(View.GONE);
            txtNineFifty.setVisibility(View.GONE);
            rlCompleteNineFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 1000 && globalTotalStrakValue < 1100) || globalTotalStrakValue >= 1100) {
            imgThousand.setVisibility(View.GONE);
            txtThousand.setVisibility(View.GONE);
            rlCompleteThousand.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 1100 && globalTotalStrakValue < 1200) || globalTotalStrakValue >= 1200) {
            imgElevenHundred.setVisibility(View.GONE);
            txtElevenHundred.setVisibility(View.GONE);
            rlCompleteElevenHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 1200 && globalTotalStrakValue < 1300) || globalTotalStrakValue >= 1300) {
            imgTwelveHundred.setVisibility(View.GONE);
            txtTwelveHundred.setVisibility(View.GONE);
            rlCompleteTwelveHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 1300 && globalTotalStrakValue < 1400) || globalTotalStrakValue >= 1400) {
            imgThirteenHundred.setVisibility(View.GONE);
            txtThirteenHundred.setVisibility(View.GONE);
            rlCompleteThirteenHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 1400 && globalTotalStrakValue < 1500) || globalTotalStrakValue >= 1500) {
            imgFourteenHundred.setVisibility(View.GONE);
            txtFourteenHundred.setVisibility(View.GONE);
            rlCompleteFourteenHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 1500 && globalTotalStrakValue < 1600) || globalTotalStrakValue >= 1600) {
            imgFifteenHundred.setVisibility(View.GONE);
            txtFifteenHundred.setVisibility(View.GONE);
            rlCompleteFifteenHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 1600 && globalTotalStrakValue < 1700) || globalTotalStrakValue >= 1700) {
            imgSixteenHundred.setVisibility(View.GONE);
            txtSixteenHundred.setVisibility(View.GONE);
            rlCompleteSixteenHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 1700 && globalTotalStrakValue < 1800) || globalTotalStrakValue >= 1800) {
            imgSeventeenHundred.setVisibility(View.GONE);
            txtSeventeenHundred.setVisibility(View.GONE);
            rlCompleteSeventeenHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 1800 && globalTotalStrakValue < 1900) || globalTotalStrakValue >= 1900) {
            imgEighteenHundred.setVisibility(View.GONE);
            txtEighteenHundred.setVisibility(View.GONE);
            rlCompleteEighteenHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 1900 && globalTotalStrakValue < 2000) || globalTotalStrakValue >= 2000) {
            imgNineteenHundred.setVisibility(View.GONE);
            txtNineteenHundred.setVisibility(View.GONE);
            rlCompleteNineteenHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 2000 && globalTotalStrakValue < 2250) || globalTotalStrakValue >= 2250) {
            imgTwoThousand.setVisibility(View.GONE);
            txtTwoThousand.setVisibility(View.GONE);
            rlCompleteTwoThousand.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 2250 && globalTotalStrakValue < 2500) || globalTotalStrakValue >= 2500) {
            imgTwentyTwoFifty.setVisibility(View.GONE);
            txtTwentyTwoFifty.setVisibility(View.GONE);
            rlCompleteTwentyTwoFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 2500 && globalTotalStrakValue < 2750) || globalTotalStrakValue >= 2750) {
            imgTwntyFiveHundred.setVisibility(View.GONE);
            txtTwntyFiveHundred.setVisibility(View.GONE);
            rlCompleteTwntyFiveHundred.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 2750 && globalTotalStrakValue < 3000) || globalTotalStrakValue >= 3000) {
            imgTwentySevenFifty.setVisibility(View.GONE);
            txtTwentyTwoFifty.setVisibility(View.GONE);
            rlCompleteTwentyTwoFifty.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 3000 && globalTotalStrakValue < 5000) || globalTotalStrakValue >= 5000) {
            imgThreeThousand.setVisibility(View.GONE);
            txtThreeThousand.setVisibility(View.GONE);
            rlCompleteThreeThousand.setVisibility(View.VISIBLE);
        }

        if ((globalTotalStrakValue >= 5000 && globalTotalStrakValue < 10000) || globalTotalStrakValue >= 10000) {
            imgFiveThousand.setVisibility(View.GONE);
            txtFiveThousand.setVisibility(View.GONE);
            rlCompleteFiveThousand.setVisibility(View.VISIBLE);
        }

        if (globalTotalStrakValue >= 10000) {
            imgTenThousand.setVisibility(View.GONE);
            txtTenThousand.setVisibility(View.GONE);
            rlCompleteTenThousand.setVisibility(View.VISIBLE);
        }

        /*rlCompleteThree.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(3, false);
        });

        rlCompleteSeven.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(7, false);
        });

        rlCompleteFourteen.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(14, false);
        });

        rlCompleteTwentyOne.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(21, false);
        });

        rlCompleteTwentyEight.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(28, false);
        });

        rlCompleteThirtyFive.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(35, false);
        });

        rlCompleteFortyTwo.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(42, false);
        });

        rlCompleteFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(50, false);
        });

        rlCompleteSeventyFive.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(75, false);
        });

        rlCompleteHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(100, false);
        });

        rlCompleteOneTwentyFive.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(125, false);
        });

        rlCompleteOneFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(150, false);
        });

        rlCompleteOneSeventyFive.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(175, false);
        });

        rlCompleteTwoHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(200, false);
        });

        rlCompleteTwoTwentyFive.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(225, false);
        });

        rlCompleteTwoFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(250, false);
        });

        rlCompleteTwoSeventyFive.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(275, false);
        });

        rlCompleteThreeHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(300, false);
        });

        rlCompleteThreeTwentyFive.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(325, false);
        });

        rlCompleteThreeFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(350, false);
        });

        rlCompleteThreeSeventyFive.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(375, false);
        });

        rlCompleteFourHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(400, false);
        });

        rlCompleteFourTwentyFive.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(425, false);
        });

        rlCompleteFourFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(450, false);
        });

        rlCompleteFourSeventyFive.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(475, false);
        });

        rlCompleteFiveHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(500, false);
        });

        rlCompleteFiveFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(550, false);
        });

        rlCompleteSixHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(600, false);
        });

        rlCompleteSixFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(650, false);
        });

        rlCompleteSevenHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(700, false);
        });

        rlCompleteSevenFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(750, false);
        });

        rlCompleteEightHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(800, false);
        });

        rlCompleteEightFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(850, false);
        });

        rlCompleteNineHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(900, false);
        });

        rlCompleteNineFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(950, false);
        });

        rlCompleteThousand.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(1000, false);
        });

        rlCompleteElevenHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(1100, false);
        });

        rlCompleteTwelveHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(1200, false);
        });

        rlCompleteThirteenHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(1300, false);
        });

        rlCompleteFourteenHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(1400, false);
        });

        rlCompleteFifteenHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(1500, false);
        });

        rlCompleteSixteenHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(1600, false);
        });

        rlCompleteSeventeenHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(1700, false);
        });

        rlCompleteEighteenHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(1800, false);
        });

        rlCompleteNineteenHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(1900, false);
        });

        rlCompleteTwoThousand.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(2000, false);
        });

        rlCompleteTwentyTwoFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(2250, false);
        });

        rlCompleteTwntyFiveHundred.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(2500, false);
        });

        rlCompleteTwentySevenFifty.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(2750, false);
        });

        rlCompleteThreeThousand.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(3000, false);
        });

        rlCompleteFiveThousand.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(5000, false);
        });

        rlCompleteTenThousand.setOnClickListener(view1 -> {
            ((MainActivity) getActivity()).openDialogForGratitudeRewardSecond(10000, false);
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();

        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.VISIBLE);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);

                    return true;

                }

                return false;
            }
        });
    }


    private void funToolBar() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView imgLogo = (ImageView) toolbar.findViewById(R.id.imgLogo);
        ImageView imgLeftBack = (ImageView) toolbar.findViewById(R.id.imgLeftBack);
        ImageView imgRightBack = (ImageView) toolbar.findViewById(R.id.imgRightBack);
        ImageView imgFilter = (ImageView) toolbar.findViewById(R.id.imgFilter);
        ImageView imgCircleBack = (ImageView) toolbar.findViewById(R.id.imgCircleBack);
        ImageView imgHelp = (ImageView) toolbar.findViewById(R.id.imgHelp);
        ImageView imgCalender = (ImageView) toolbar.findViewById(R.id.imgCalender);
        FrameLayout frameNotification = (FrameLayout) toolbar.findViewById(R.id.frameNotification);
        TextView txtPageHeader = (TextView) toolbar.findViewById(R.id.txtPageHeader);

        imgRightBack.setClickable(true);
        imgRightBack.setEnabled(true);
        frameNotification.setVisibility(View.GONE);
        imgFilter.setVisibility(View.GONE);
        //   //imgFilter.setBackgroundResource(R.drawable.filter);
        imgRightBack.setVisibility(View.GONE);
        txtPageHeader.setVisibility(View.GONE);
        imgLeftBack.setVisibility(View.GONE);
        if (new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true")) {
            if (Util.isSevenDayOver(getActivity())) {
                imgCircleBack.setVisibility(View.VISIBLE);
            } else {
                imgCircleBack.setVisibility(View.VISIBLE);
            }
        } else {
            if (new SharedPreference(getActivity()).read("SQUADLITE", "").equals("TRUE")) {
                imgCircleBack.setVisibility(View.VISIBLE);
            } else {
                imgCircleBack.setVisibility(View.VISIBLE);
            }
        }
        imgHelp.setVisibility(View.GONE);
        imgCalender.setVisibility(View.GONE);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));

        /*imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showPromotionalDialogs();

                if (new SharedPreference(getActivity()).read("compChk", "").equals("false")) {
                    ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
                } else {
                    ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "home", null);
                }
            }
        });*/

        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments() != null && getArguments().containsKey("NEWHOME")) {
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", getArguments());
                } else
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
            }
        });
    }

}
