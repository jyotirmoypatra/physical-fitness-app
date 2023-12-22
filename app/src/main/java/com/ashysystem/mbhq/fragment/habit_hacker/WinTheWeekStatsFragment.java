package com.ashysystem.mbhq.fragment.habit_hacker;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.model.GetWinTheWeekStatsResponse;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WinTheWeekStatsFragment extends Fragment {

    public WinTheWeekStatsFragment() {
        // Required empty public constructor
    }

    public static WinTheWeekStatsFragment newInstance() {
        WinTheWeekStatsFragment fragment = new WinTheWeekStatsFragment();
        return fragment;
    }

    private String TAG = "WinTheWeekStatsFragment";

    private TextView daysWonCount, weeksWonCount;

    private View rlBack;

    private ViewGroup daysStatsContainer, weeksStatsContainer;

    private TextView textInfoWinTheWeek;

    private GetWinTheWeekStatsResponse winTheWeekStats = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_win_the_week_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daysWonCount = view.findViewById(R.id.daysWonCount);
        weeksWonCount = view.findViewById(R.id.weeksWonCount);
        textInfoWinTheWeek = view.findViewById(R.id.textInfoWinTheWeek);
        daysStatsContainer = view.findViewById(R.id.daysStatsContainer);
        weeksStatsContainer = view.findViewById(R.id.weeksStatsContainer);
        rlBack = view.findViewById(R.id.rlBack);

        daysWonCount.setText("0");
        weeksWonCount.setText("0");


        daysStatsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (winTheWeekStats != null) {
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(WinTheWeekDetailsFragment.newInstance(winTheWeekStats, false));
                    ((MainActivity) getActivity()).loadFragment(WinTheWeekDetailsFragment.newInstance(winTheWeekStats, false), "WinTheWeekDetailsFragment", null);
                }
            }
        });

        weeksStatsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (winTheWeekStats != null) {
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(WinTheWeekDetailsFragment.newInstance(winTheWeekStats, true));
                    ((MainActivity) getActivity()).loadFragment(WinTheWeekDetailsFragment.newInstance(winTheWeekStats, true), "WinTheWeekDetailsFragment", null);
                }
            }
        });

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
            }
        });

        textInfoWinTheWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWinTheWeekInfoDialog();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        callWinTheWeekStatsAPI();
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
    }

    public void callWinTheWeekStatsAPI() {

        if (Connection.checkConnection(requireContext())) {

            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            SharedPreference sharedPreference = new SharedPreference(getContext());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserID", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<GetWinTheWeekStatsResponse> jsonObjectCall = finisherService.getWinTheWeekStats(hashReq);
            jsonObjectCall.enqueue(new Callback<GetWinTheWeekStatsResponse>() {

                @Override
                public void onResponse(Call<GetWinTheWeekStatsResponse> call, Response<GetWinTheWeekStatsResponse> response) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        winTheWeekStats = response.body();
                        Log.i(TAG, response.body().getDayWins().toString());

                        daysWonCount.setText(winTheWeekStats.getDayWins().toString());
                        weeksWonCount.setText(winTheWeekStats.getWeeklyWins().toString());

                    }
                }

                @Override
                public void onFailure(Call<GetWinTheWeekStatsResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    t.printStackTrace();

                }

            });

        } else {
            Util.showToast(getContext(), Util.networkMsg);
        }
    }

    private void openWinTheWeekInfoDialog(){

        Dialog dialog = new Dialog(getContext(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_info_win_the_week);

        View closeModal = dialog.findViewById(R.id.closeModal);

        closeModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}