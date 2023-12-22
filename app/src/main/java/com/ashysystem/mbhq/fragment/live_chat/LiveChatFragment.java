package com.ashysystem.mbhq.fragment.live_chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashysystem.mbhq.R;

import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;

import com.ashysystem.mbhq.fragment.achievement.MyAchievementsFragment;
import com.ashysystem.mbhq.model.livechat.Chat;
import com.ashysystem.mbhq.model.livechat.GetMbhqLiveChatTagsResponse;
import com.ashysystem.mbhq.model.livechat.SearchMbhqLiveChatByTagResponse;
import com.ashysystem.mbhq.model.livechat.Tag;

import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveChatFragment extends Fragment implements View.OnClickListener, LiveChatAdapter.ItemInteractionListener, LiveChatChipsAdapter.ChipInteractionListener {
    /* String accesstype="3";
     String habit_access="true";
     String eq_access="true";
     String medi_access="true";
     String forum_access="true";
     String Live_access="true";
     String Test_acess="true";
     String Course_access="true";*/
    String accesstype="";
    String habit_access="";
    String eq_access="";
    String medi_access="";
    String forum_access="";
    String Live_access="";
    String Test_acess="";
    String Course_access="";
    public LiveChatFragment() {
        // Required empty public constructor
    }

    public static LiveChatFragment newInstance() {
        LiveChatFragment fragment = new LiveChatFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    private ImageView imgDown, imgCross, imgLogo, imgSearchBlue;
    private Button btnApplyTags, btnClearTags;
    private RecyclerView liveChatRecycler;
    private RecyclerView chipGroupTagContainer;
    private EditText edtTxtSearchTags;

    private ViewGroup tagsContainer, progressBarContainer;

    private String TAG = this.getClass().getSimpleName();

    private ArrayList<Tag> tags = new ArrayList<Tag>();
    private ArrayList<Tag> selectedTags = new ArrayList<Tag>();
    private final LiveChatAdapter liveChatAdapter = new LiveChatAdapter();

    private final LiveChatChipsAdapter liveChatChipsAdapter = new LiveChatChipsAdapter();

    private ColorStateList greenColorState = null;
    private ColorStateList whiteColorState = null;
    private ColorStateList lightGreenColorState = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("state1111111111111111111111111111","onCreate1");
        SharedPreference  sharedPreference = new SharedPreference(getActivity());
        if(Util.openliveplayer.equalsIgnoreCase("yes")){
            Util.openliveplayer="yes";
            ((MainActivity) requireActivity()).clearCacheForParticularFragment(LiveChatPlayerFragment.newInstance(Util.chat));
            ((MainActivity) requireActivity()).loadFragment(LiveChatPlayerFragment.newInstance(Util.chat), "LiveChatPlayer", null);

        }

        Live_access=sharedPreference.read("LiveChatAccess","");

        eq_access=sharedPreference.read("EqJournalAccess","");
        Course_access=sharedPreference.read("CourseAccess","");
        medi_access=sharedPreference.read("MeditationAccess","");
        accesstype=sharedPreference.read("accesstype","");
        habit_access=sharedPreference.read("HabitAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Test_acess=sharedPreference.read("TestsAccess","");
/*if("3".equalsIgnoreCase(accesstype)){
    if("false".equalsIgnoreCase(Live_access)){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("User don't have access to use Live chat") .setTitle("Efc");

        //Setting message manually and performing action on button click
        builder.setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if("habit".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).funHabits_();
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                        }else if("Eq".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).refershGamePoint(getActivity());
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                            Util.isReloadTodayMainPage = true;
                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                            ((MainActivity) getActivity()).fungratitudeicon();
                            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                        }else if("course".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).funDrawer1();
                            Util.refresh_gratitude="yes";
                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                            ((MainActivity) getActivity()).funCourse();
                        }else if("test".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).funDrawer1();

                            ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionariesFragment());
                            ((MainActivity)getActivity()).loadFragment(new QuestionariesFragment(),"Question",null);

                        }else if("meditation".equalsIgnoreCase(Util.sourcepage)){
                            Util.refresh_meditation="yes";
                            Util.refresh_gratitude="yes";
                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                            ((MainActivity) getActivity()).funMeditation();
                            Util.refresh_gratitude="yes";
                        }

                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        //Setting the title manually
        alert.show();
        // ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
    }else{
        Util.sourcepage="Live";
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(networkChangeReceiver4, filter);
    }
}else{*/
        Util.sourcepage="Live";
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(networkChangeReceiver4, filter);
//}



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      /*  if("false".equalsIgnoreCase(Live_access)) {
            return null;
        }else{*/
        return inflater.inflate(R.layout.fragment_live_chat, container, false);
        //}
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /*  if("false".equalsIgnoreCase(Live_access)) {
        }else{*/
        initView(view);
        //  }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    /*    if("false".equalsIgnoreCase(Live_access)) {
        }else{*/
        if (savedInstanceState != null) {

            tags = savedInstanceState.getParcelableArrayList("TAGS");
            selectedTags = savedInstanceState.getParcelableArrayList("SELECTED_TAGS");
            populateChatTags();
        } else {
            getLiveChatTags();
            searchLiveChats();
        }

        ViewCompat.setNestedScrollingEnabled(liveChatRecycler, false);

        imgDown.setOnClickListener(this);
        imgCross.setOnClickListener(this);
        btnApplyTags.setOnClickListener(this);
        btnClearTags.setOnClickListener(this);
        imgSearchBlue.setOnClickListener(this);
        imgLogo.setOnClickListener(this);
        liveChatRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        liveChatRecycler.setAdapter(liveChatAdapter);

        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(getContext())
                .setScrollingEnabled(true)
                .setMaxViewsInRow(4)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();

        chipGroupTagContainer.setLayoutManager(chipsLayoutManager);

        chipGroupTagContainer.setAdapter(liveChatChipsAdapter);

        greenColorState = ContextCompat.getColorStateList(requireContext(), R.color.live_chat_blue_light);
        whiteColorState = ContextCompat.getColorStateList(requireContext(), R.color.white);
        lightGreenColorState = ContextCompat.getColorStateList(requireContext(), R.color.live_chat_blue2);
//        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("TAGS", tags);
        outState.putParcelableArrayList("SELECTED_TAGS", selectedTags);
    }

    @Override
    public void onResume() {
        super.onResume();
       /* if("false".equalsIgnoreCase(Live_access)) {
        }else{*/
        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).toolbar.setVisibility(View.GONE);
        //  }

    }

    @Override
    public void onPause() {
        super.onPause();

        /*LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).llBottomMenu.setVisibility(View.VISIBLE);*/
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == imgLogo.getId()) {

            // ((MainActivity) requireActivity()).imgForum.performClick();
/*if("3".equalsIgnoreCase(accesstype)){
    if("false".equalsIgnoreCase(Live_access)){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("User don't have access to use Eq") .setTitle("Efc");

        //Setting message manually and performing action on button click
        builder.setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if("habit".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                        }else if("Eq".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).refershGamePoint(getActivity());
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                            Util.isReloadTodayMainPage = true;
                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                            ((MainActivity) getActivity()).fungratitudeicon();
                            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                        }else if("course".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).funDrawer1();
                            Util.refresh_gratitude="yes";
                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                            ((MainActivity) getActivity()).funCourse();
                        }else if("test".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).funDrawer1();

                            ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionariesFragment());
                            ((MainActivity)getActivity()).loadFragment(new QuestionariesFragment(),"Question",null);

                        }else if("meditation".equalsIgnoreCase(Util.sourcepage)){
                            Util.refresh_meditation="yes";
                            Util.refresh_gratitude="yes";
                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                            ((MainActivity) getActivity()).funMeditation();
                            Util.refresh_gratitude="yes";
                        }

                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        //Setting the title manually
        alert.show();
        // ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
    }else{
        ((MainActivity) requireActivity()).rlHabits.performClick();

    }
}else{*/
            ((MainActivity) requireActivity()).rlHabits.performClick();

//}


        } else if (v.getId() == imgCross.getId()) {

            tagsContainer.setVisibility(View.GONE);
            imgCross.setVisibility(View.GONE);
            imgDown.setVisibility(View.VISIBLE);

        } else if (v.getId() == imgDown.getId()) {

            imgDown.setVisibility(View.GONE);
            tagsContainer.setVisibility(View.VISIBLE);
            imgCross.setVisibility(View.VISIBLE);

        } else if (v.getId() == btnApplyTags.getId() || v.getId() == imgSearchBlue.getId()) {

            searchLiveChats();

        } else if (v.getId() == btnApplyTags.getId() || v.getId() == imgSearchBlue.getId()) {

            searchLiveChats();

            ///*hideKeyboard()
            edtTxtSearchTags.clearFocus();

        } else if (v.getId() == btnClearTags.getId()) {

            clearTags();
            //hideKeyboard()
            edtTxtSearchTags.clearFocus();
            searchLiveChats();

        }

    }

    @Override
    public void onItemClicked(Chat data) {
        Util.chat=data;
        if(Util.meditationsArrayList.size()>0){
            Util.meditationsArrayList.clear();
            if(null==data.getMeditations() ||0==data.getMeditations().size()){

            }else{
                Util.meditationsArrayList.addAll(data.getMeditations());
            }


        }else{
            if(null==data.getMeditations() ||0==data.getMeditations().size()){

            }else{
                Util.meditationsArrayList.addAll(data.getMeditations());
            }
        }
        Util.openliveplayer="yes";
        ((MainActivity) requireActivity()).clearCacheForParticularFragment(LiveChatPlayerFragment.newInstance(data));
        ((MainActivity) requireActivity()).loadFragment(LiveChatPlayerFragment.newInstance(data), "LiveChatPlayer", null);

    }

    @Override
    public void onChipClicked(Tag data, int position) {

        if (selectedTags.contains(data)) {
            selectedTags.remove(data);
            tags.get(position).setSelected(false);
        } else {
            selectedTags.add(data);
            tags.get(position).setSelected(true);
        }

        liveChatChipsAdapter.notifyItemChanged(position);

        Log.i(TAG, "chip clicked: " + data.getTagName() + " is selected: " + data.isSelected());

    }

    private void initView(View vi) {

        imgDown = vi.findViewById(R.id.imgDown);
        imgCross = vi.findViewById(R.id.imgCross);
        imgLogo = vi.findViewById(R.id.imgLogo);
        btnApplyTags = vi.findViewById(R.id.btnApplyTags);
        btnClearTags = vi.findViewById(R.id.btnClearTags);
        liveChatRecycler = vi.findViewById(R.id.liveChatRecycler);
        imgSearchBlue = vi.findViewById(R.id.imgSearchBlue);
        edtTxtSearchTags = vi.findViewById(R.id.edtTxtSearchTags);

        tagsContainer = vi.findViewById(R.id.tagsContainer);

        progressBarContainer = vi.findViewById(R.id.progressBarContainer);

        chipGroupTagContainer = vi.findViewById(R.id.chipGroupTagContainer);


        edtTxtSearchTags.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() == 0) {
                    clearTags();
                    //hideKeyboard()
                    edtTxtSearchTags.clearFocus();
                    searchLiveChats();

                }
            }
        });


    }

    private void populateChatTags() {


        liveChatChipsAdapter.setData(tags, this);



        /*chipGroupTagContainer.removeAllViews();

        for (int index = 0; index < tags.size(); index++) {

            Chip chip = new Chip(requireContext());

            chip.setText(tags.get(index).getTagName());
            chip.setChipBackgroundColor(greenColorState);
            chip.setChipStrokeColor(whiteColorState);
            chip.setTextColor(whiteColorState);
            chip.setChipStrokeWidth(4.0F);
            chip.setPadding(16, 16, 16, 16);
            //chip.setEnsureMinTouchTargetSize(false);

            int finalIndex = index;
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (selectedTags.contains(tags.get(finalIndex))) {
                        chip.setChipBackgroundColor(greenColorState);
                        chip.setTextColor(whiteColorState);
                        selectedTags.remove(tags.get(finalIndex));
                    } else {
                        selectedTags.add(tags.get(finalIndex));
                        chip.setChipBackgroundColor(lightGreenColorState);
                        chip.setTextColor(whiteColorState);
                    }
                }
            });

            chipGroupTagContainer.addView(chip);

        }*/

    }


    private void clearTags() {
        edtTxtSearchTags.getText().clear();
        unselectAllChips();
        selectedTags.clear();
    }

    private void unselectAllChips() {


        /*for (int index = 0; index < chipGroupTagContainer.getChildCount(); index++) {

            ((Chip) chipGroupTagContainer.getChildAt(index)).setChipBackgroundColor(greenColorState);
            ((Chip) chipGroupTagContainer.getChildAt(index)).setTextColor(whiteColorState);
        }*/

    }


    private void getLiveChatTags() {

        if (Connection.checkConnection(requireContext())) {

            progressBarContainer.setVisibility(View.VISIBLE);

            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<GetMbhqLiveChatTagsResponse> serverCall = finisherService.getMbhqLiveChatTags(hashReq);

            serverCall.enqueue(new Callback<GetMbhqLiveChatTagsResponse>() {
                @Override
                public void onResponse(Call<GetMbhqLiveChatTagsResponse> call, Response<GetMbhqLiveChatTagsResponse> response) {

                    progressBarContainer.setVisibility(View.GONE);

                    if (response.body() != null) {
                        try {
                            tags.clear();
                            tags.addAll(response.body().getTagList());
                            populateChatTags();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetMbhqLiveChatTagsResponse> call, Throwable t) {
                    t.printStackTrace();
                    progressBarContainer.setVisibility(View.GONE);

                }
            });

        } else {

            Util.showToast(getActivity(), Util.networkMsg);

        }
    }

    private void searchLiveChats() {

        if (Connection.checkConnection(requireContext())) {

            progressBarContainer.setVisibility(View.VISIBLE);

            SharedPreference sharedPreference = new SharedPreference(getActivity());

            ArrayList<String> tagString = new ArrayList<>();
            for (int index = 0; index < selectedTags.size(); index++) {
                tagString.add(selectedTags.get(index).getTagName());
            }

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("Count", 100);
            hashReq.put("Tags", tagString);
            hashReq.put("SearchText", edtTxtSearchTags.getText().toString().trim());

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<SearchMbhqLiveChatByTagResponse> serverCall = finisherService.searchMbhqLiveChatByTag(hashReq);

            serverCall.enqueue(new Callback<SearchMbhqLiveChatByTagResponse>() {
                @Override
                public void onResponse(Call<SearchMbhqLiveChatByTagResponse> call, Response<SearchMbhqLiveChatByTagResponse> response) {

                    progressBarContainer.setVisibility(View.GONE);

                    if (response.body() != null) {
                        try {

                            imgCross.performClick();

                            liveChatAdapter.setData(response.body().getChatList(), LiveChatFragment.this);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SearchMbhqLiveChatByTagResponse> call, Throwable t) {
                    t.printStackTrace();
                    progressBarContainer.setVisibility(View.GONE);

                }
            });

        } else {

            Util.showToast(getActivity(), Util.networkMsg);

        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if("false".equalsIgnoreCase(Live_access)) {
        }else{
            getActivity().unregisterReceiver(networkChangeReceiver4);

        }
    }

    private BroadcastReceiver networkChangeReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {
                if("3".equalsIgnoreCase(accesstype)&&"false".equalsIgnoreCase(eq_access)){

                }else{
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                    Util.calledgratitudeafterinternatecomming = false;
                    Util.isReloadTodayMainPage = true;
                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                    //((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
                    // Internet connection is lost
                }

            }else{
                Util.calledgratitudeafterinternatecomming=true;

            }
        }
    };

}