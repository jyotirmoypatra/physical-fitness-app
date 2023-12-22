package com.ashysystem.mbhq.fragment.habit_hacker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashysystem.mbhq.R;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.AlertDialogWithCustomButton;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitHackerAddSecondPage extends Fragment {

    RelativeLayout rlNext;
    EditText edtHabitBreak;
    Toolbar toolbar;
    HabitSwap globalHabitSwapModel;
    ImageView imgBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_hacker_add_second_page, null);

        funToolBar();

        rlNext = view.findViewById(R.id.rlNext);
        edtHabitBreak = view.findViewById(R.id.edtHabitBreak);
        imgBack = view.findViewById(R.id.imgBack);

        globalHabitSwapModel = ((HabitHackerAddMainFragment) getParentFragment()).getGlobalHabitSwapModel();

        /*if (getArguments() != null && getArguments().containsKey("HABIT_HACKER_ADD_MODEL")) {
            Gson gson = new Gson();
            globalHabitSwapModel = gson.fromJson(getArguments().getString("HABIT_HACKER_ADD_MODEL"), HabitSwap.class);
        }*/

        imgBack.setOnClickListener(view1 -> {

            AlertDialogWithCustomButton customButton = new AlertDialogWithCustomButton(getActivity());
            customButton.ShowDialog("Alert!", "Are you sure you want to exit?", true, "SAVE MY HABIT NOW", "YES");
            customButton.setAlertAction(new AlertDialogCustom.AlertResponse() {
                @Override
                public void onDone(String title) {
                    if (edtHabitBreak.getText().toString().equals("")) {
                        Util.showToast(getActivity(), "Please enter habit which you want to break");
                    } else {
                        if (globalHabitSwapModel != null) {
                            saveNewBreakHabit();
                        } else {
                            Util.showToast(getActivity(), "Something went wrong");
                        }
                    }
                }

                @Override
                public void onCancel(String title) {
                    if (Util.HABIT_ADDED_FROM_TODAY_PAGE) {
                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                    } else {
                        ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                    }
                }
            });

        });
        Util.edittext=edtHabitBreak.getText().toString();
        edtHabitBreak.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                String s=arg0.toString();
                Util.edittext=s;
                globalHabitSwapModel.getSwapAction().setName(s);
            }
        });
        rlNext.setOnClickListener(view1 -> {

            if (edtHabitBreak.getText().toString().equals("")) {
                Util.showToast(getActivity(), "Please enter habit which you want to break");
            } else {
                if (globalHabitSwapModel != null) {
                    ////////////////////////////////MODIFIEDDD////////////////////////////////////////////

                    globalHabitSwapModel.getSwapAction().setName(edtHabitBreak.getText().toString());
                    assert getParentFragment() != null;
                    ((HabitHackerAddMainFragment) getParentFragment()).saveAndNext(globalHabitSwapModel);
                } else {
                    Util.showToast(getActivity(), "Something went wrong");
                }
            }

            //((MainActivity)getActivity()).loadFragment(new HabitHackerAddOne(),"HabitHackerAddOne",null);

        });

        return view;
    }

    private void openDialogForQuidAddOrFullSetUp() {

        final boolean[] boolQuickAdd = {true};

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_quickadd_fullsetup);

        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        RelativeLayout rlQuickAdd = dialog.findViewById(R.id.rlQuickAdd);
        RelativeLayout rlFullSetUp = dialog.findViewById(R.id.rlFullSetUp);
        RelativeLayout rlTop = dialog.findViewById(R.id.rlTop);
        RelativeLayout rlBottom = dialog.findViewById(R.id.rlBottom);
        ImageView imgQuickAdd = dialog.findViewById(R.id.imgQuickAdd);
        ImageView imgFullSetUp = dialog.findViewById(R.id.imgFullSetUp);
        RelativeLayout rlCreateNowContinue = dialog.findViewById(R.id.rlCreateNowContinue);
        TextView txtCreateNowContinue = dialog.findViewById(R.id.txtCreateNowContinue);

        imgCross.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlQuickAdd.setOnClickListener(view -> {
            boolQuickAdd[0] = true;
            imgQuickAdd.setImageResource(R.drawable.mbhq_rd_chk);
            imgFullSetUp.setImageResource(R.drawable.mbhq_rd_uchk);
            txtCreateNowContinue.setText("Create Now");
        });

        rlFullSetUp.setOnClickListener(view -> {
            boolQuickAdd[0] = false;
            imgQuickAdd.setImageResource(R.drawable.mbhq_rd_uchk);
            imgFullSetUp.setImageResource(R.drawable.mbhq_rd_chk);
            txtCreateNowContinue.setText("Continue");
        });

        rlCreateNowContinue.setOnClickListener(view -> {
            dialog.dismiss();
            if (boolQuickAdd[0]) {
                saveNewBreakHabit();
            } else {
                Gson gson = new Gson();
                Bundle bundle = new Bundle();
                globalHabitSwapModel.getSwapAction().setName(edtHabitBreak.getText().toString());
                bundle.putString("HABIT_HACKER_ADD_MODEL", gson.toJson(globalHabitSwapModel));
//                ((MainActivity)getActivity()).loadFragment(new HabitHackerAddOne(),"HabitHackerAddOne",bundle);
                ((MainActivity) getActivity()).loadFragment(new HabitHackerAddMainFragment(), "HabitHackerAddMainFragment", bundle);
            }
        });

        rlTop.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlBottom.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private void saveNewBreakHabit() {

        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            SharedPreference sharedPreference = new SharedPreference(getActivity());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());

            globalHabitSwapModel.getSwapAction().setName(edtHabitBreak.getText().toString());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("Habit", globalHabitSwapModel);

            Call<JsonObject> jsonObjectCall = finisherService.addUpdateHabitSwap(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                        Util.showToast(getActivity(), "Successfully Saved");
                        Util.isReloadTodayMainPage = true;
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                        if (Util.HABIT_ADDED_FROM_TODAY_PAGE) {
                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                        } else {
                            ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }

    }


    @Override
    public void onResume() {
        super.onResume();

        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
        //((MainActivity) getActivity()).funTabBarforHabits();
        //((MainActivity) getActivity()).funDrawer();

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

    @Override
    public void onPause() {
        super.onPause();
        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.VISIBLE);
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
        //imgFilter.setBackgroundResource(R.drawable.filter);
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