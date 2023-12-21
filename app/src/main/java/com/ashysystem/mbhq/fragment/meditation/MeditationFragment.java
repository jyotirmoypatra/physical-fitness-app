package com.ashysystem.mbhq.fragment.meditation;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.Settings;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.adapter.MeditationCourseAdapter;
import com.ashysystem.mbhq.fragment.MbhqTodayMainFragment;
import com.ashysystem.mbhq.model.GetMeditationCacheExpiryTimeResponse;
import com.ashysystem.mbhq.model.MeditationCourseModel;

import com.ashysystem.mbhq.model.MeditationDataViewModel;
import com.ashysystem.mbhq.model.MeditationTagResponse;
import com.ashysystem.mbhq.model.Tag;
import com.ashysystem.mbhq.roomDatabase.Injection;
import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForMeditation;
import com.ashysystem.mbhq.roomDatabase.viewModel.MeditationViewModel;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.ashysystem.mbhq.view.CheckBoxOswaldRegular;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//test add jp
public class MeditationFragment extends Fragment {

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
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    View vi;
    RecyclerView rvCourseM,rvCourseM_main,rvCourseM_nointernate;
    List<MeditationCourseModel.Webinar> lstFilterTagData = new ArrayList<>();
    List<MeditationCourseModel.Webinar> lstFilterTagDataBreath = new ArrayList<>();
    List<MeditationCourseModel.Webinar> lstFilterTagDataMorning = new ArrayList<>();
    List<MeditationCourseModel.Webinar> lstFilterTagDataPower = new ArrayList<>();
    List<MeditationCourseModel.Webinar> lstFilterTagDataHealing = new ArrayList<>();
    ArrayList<String> arrSelectedTag = new ArrayList<>();
    ArrayList<String> arrSelectedDuration = new ArrayList<>();
    ArrayList<String> arrTagChoice = new ArrayList<>();
    ArrayList<Integer> arrDurationChoice = new ArrayList<>();
    ArrayList<Integer> arrLevelChoice = new ArrayList<>();
    LinearLayoutManager mLayoutManager,mLayoutManager_main,mLayoutManager_nointernate;
    int globalCount = 10;
    int previousCount = 0;
    String backto="";
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    RelativeLayout rlFilter;
    ArrayList<Integer> arrDuration = new ArrayList<>();
    ArrayList<Integer> arrLevel = new ArrayList<>();
    SharedPreference sharedPreference;
    Button btnFindMyLevel;
    ViewModelFactoryForMeditation factoryForMeditation;
    MeditationViewModel meditationViewModel;
    MeditationDataViewModel testViewModel;
    LinearLayout llTimeTagFilter, llNapTimeTagFilter, llHealingTagFilter;
    ImageView imgFab;
    RelativeLayout rlNoMeditationDownload,rlHealingsleep;
    RelativeLayout rlMindfulness;
    TextView txtBackDownload, txtNoDataFound, txtFavSuggestion;
    ConstraintLayout clNoFavouriteFound;
    boolean fromDetailsPage = false;
    String fromDetailsPage1 = "";

    String TAG = "MeditationFragment";
    private boolean favChk = false;
    private boolean loading = true;
    private EditText edtSearch;
//    private GenericAsynTask genericAsynTaskTAG;
    private RelativeLayout rlBreath, rlMorning, rlPowerNap, rlHealing, rlMainBlock, rlBack, rlBreathTimeBack, rlNapTimeBack, rlHealingBack;
    private ViewGroup llLandingOptions;
    private ImageView imgFilter, imgInfoMeditation;
    private boolean searchFlag = false;
    private RelativeLayout rl5Min, rl10Min, rl20Min, rl30Min, rlNapTime20Min, rlNapTime90Min, rlGuidedMeditations, rlHealingMeditations, rlVisualisations, rlPowerUp;
    private List<MeditationCourseModel.Webinar> searchData;
    private SwipeRefreshLayout swipeLayout;
    private String firstLoginTime = ""; /////////
    private String currentTime = ""; /////////
    private String meditationFirstTime = ""; //////////
    List<MeditationCourseModel.Webinar> concatinatelist = new ArrayList<>();
    List<MeditationCourseModel.Webinar> withfilterlist = new ArrayList<>();
    List<MeditationCourseModel.Webinar> lstTotalDataM_ = new ArrayList<>();

    ImageView img_notaccess;
    ArrayList<Tag> tagArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("state1111111111111111111111111111","onCreate3");
        sharedPreference = new SharedPreference(getActivity());
        medi_access=sharedPreference.read("MeditationAccess","");

        accesstype=sharedPreference.read("accesstype","");
        habit_access=sharedPreference.read("HabitAccess","");
        eq_access=sharedPreference.read("EqJournalAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");
        Test_acess=sharedPreference.read("TestsAccess","");
        Course_access=sharedPreference.read("CourseAccess","");
/*if("3".equalsIgnoreCase(accesstype)){
    if("false".equalsIgnoreCase(medi_access)){

    }else{
        Util.sourcepage="meditation";
        Log.i(TAG, "onCreate");
        Util.clearMeditation_onpause="yes";
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(networkChangeReceiver3, filter);
        if (getArguments() != null && getArguments().containsKey("BACKBUTTONCLICKED")) {
            // fromDetailsPage = Objects.equals(getArguments().getString("BACKBUTTONCLICKED"), "TRUE");
            fromDetailsPage1 = getArguments().getString("BACKBUTTONCLICKED");
            Log.i("clickedmechoice",fromDetailsPage1);
            Log.i(TAG, "fromDetails set to true");
            getArguments().remove("BACKBUTTONCLICKED");

        }
    }
}else{*/
        Util.sourcepage="meditation";
        Log.i(TAG, "onCreate");
        Util.clearMeditation_onpause="yes";
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(networkChangeReceiver3, filter);
        if (getArguments() != null && getArguments().containsKey("BACKBUTTONCLICKED")) {
            // fromDetailsPage = Objects.equals(getArguments().getString("BACKBUTTONCLICKED"), "TRUE");
            fromDetailsPage1 = getArguments().getString("BACKBUTTONCLICKED");
            Log.i("clickedmechoice",fromDetailsPage1);
            Log.i(TAG, "fromDetails set to true");
            getArguments().remove("BACKBUTTONCLICKED");

        }
//}





    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "onActivityCreated");

        sharedPreference = new SharedPreference(getActivity());
        medi_access=sharedPreference.read("MeditationAccess","");
//        medi_access="false";
        accesstype=sharedPreference.read("accesstype","");
//        accesstype="3";
        habit_access=sharedPreference.read("HabitAccess","");
        eq_access=sharedPreference.read("EqJournalAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");
        Test_acess=sharedPreference.read("TestsAccess","");
        Course_access=sharedPreference.read("CourseAccess","");

/*if("3".equalsIgnoreCase(accesstype)){
    if("false".equalsIgnoreCase(medi_access)){

    }else{
        factoryForMeditation = Injection.provideViewModelFactoryMeditation(getContext());
        meditationViewModel = ViewModelProviders.of(requireActivity(), factoryForMeditation).get(MeditationViewModel.class);
        testViewModel = ViewModelProviders.of(requireActivity()).get(MeditationDataViewModel.class);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getActivity())) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 289);
            }
        }
    }
}else{*/
        factoryForMeditation = Injection.provideViewModelFactoryMeditation(getContext());
//        meditationViewModel = ViewModelProviders.of(requireActivity(), factoryForMeditation).get(MeditationViewModel.class);
        meditationViewModel = new ViewModelProvider(requireActivity(), factoryForMeditation).get(MeditationViewModel.class);

//        testViewModel = ViewModelProviders.of(requireActivity()).get(MeditationDataViewModel.class);
        testViewModel = new ViewModelProvider(requireActivity()).get(MeditationDataViewModel.class);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getActivity())) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 289);
            }
        }
//}



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView");
        sharedPreference = new SharedPreference(getActivity());
        medi_access=sharedPreference.read("MeditationAccess","");
//        medi_access="false";
        accesstype=sharedPreference.read("accesstype","");
//        accesstype="3";
        habit_access=sharedPreference.read("HabitAccess","");
        eq_access=sharedPreference.read("EqJournalAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");
        Test_acess=sharedPreference.read("TestsAccess","");
        Course_access=sharedPreference.read("CourseAccess","");
        /*if("3".equalsIgnoreCase(accesstype)){

            if("false".equalsIgnoreCase(medi_access)){
                vi = inflater.inflate(R.layout.fragment_meditation, container, false);
                rlMainBlock = vi.findViewById(R.id.rlMainBlock);
                llLandingOptions = vi.findViewById(R.id.llLandingOptions);
                llLandingOptions.setVisibility(View.GONE);
                img_notaccess = vi.findViewById(R.id.img_notaccess_meditation);
                img_notaccess.setVisibility(View.VISIBLE);
                rlMainBlock.setVisibility(View.GONE);
                img_notaccess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse("https://www.mindbodyhq.com/efc"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
                return vi;
            }else{
                vi = inflater.inflate(R.layout.fragment_meditation, container, false);
                rlMainBlock = vi.findViewById(R.id.rlMainBlock);
                img_notaccess = vi.findViewById(R.id.img_notaccess_meditation);
                img_notaccess.setVisibility(View.GONE);
                rlMainBlock.setVisibility(View.GONE);
                return vi;
            }
        }else{*/
        vi = inflater.inflate(R.layout.fragment_meditation, container, false);
        rlMainBlock = vi.findViewById(R.id.rlMainBlock);
        img_notaccess = vi.findViewById(R.id.img_notaccess_meditation);
        img_notaccess.setVisibility(View.GONE);
        rlMainBlock.setVisibility(View.GONE);
        return vi;
        // }



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated");

        /*if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(medi_access)){

            }else{
                Log.i("strMeditationDetailsForBackground",Util.strMeditationDetailsForBackground);
                if (Util.boolBackGroundServiceRunningMeditation && !Util.strMeditationDetailsForBackground.equals("") && !fromDetailsPage) {
                    Log.i(TAG, "UTILVALUEEEEEE" + Util.strMeditationDetailsForBackground + ">>>>>");
                    Util.backto="";
                    ((MainActivity) requireActivity()).clearCacheForParticularFragment(new MeditationDetailsNew());
                    MeditationDetailsNew meditationDetails = new MeditationDetailsNew();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", Util.strMeditationDetailsForBackground);
                    meditationDetails.setArguments(bundle);
                    ((MainActivity) requireActivity()).loadFragment(meditationDetails, "MeditationDetailsNew", null);
                } else {
                    // fromDetailsPage = false;
                }


                rlHealingsleep= view.findViewById(R.id.rlHealingsleep);
                rvCourseM = view.findViewById(R.id.rvCourseM);
                rvCourseM_main=view.findViewById(R.id.rvCourseM_main);
                rvCourseM_nointernate=view.findViewById(R.id.rvCourseM_nointernate);
                rlMindfulness=view.findViewById(R.id.rlMindfulness);
                sharedPreference = new SharedPreference(getActivity());
                mLayoutManager = new LinearLayoutManager(getActivity());
                rvCourseM.setLayoutManager(mLayoutManager);
                mLayoutManager_main = new LinearLayoutManager(getActivity());
                rvCourseM_main.setLayoutManager(mLayoutManager_main);
                mLayoutManager_nointernate = new LinearLayoutManager(getActivity());
                rvCourseM_nointernate.setLayoutManager(mLayoutManager_nointernate);

                rlFilter = view.findViewById(R.id.rlFilter);
                imgFilter = view.findViewById(R.id.imgFilter);
                imgInfoMeditation = view.findViewById(R.id.imgInfoMeditation);
                edtSearch = view.findViewById(R.id.edtSearch);
                rlBreath = view.findViewById(R.id.rlBreath);
                rlMorning = view.findViewById(R.id.rlMorning);
                rlPowerNap = view.findViewById(R.id.rlPowerNap);
                rlHealing = view.findViewById(R.id.rlHealing);
                imgFab = view.findViewById(R.id.imgFab);
                llLandingOptions = view.findViewById(R.id.llLandingOptions);

                rlBack = view.findViewById(R.id.rlBack);
                rlBreathTimeBack = view.findViewById(R.id.rlBreathTimeBack);
                rlNapTimeBack = view.findViewById(R.id.rlNapTimeBack);
                rlHealingBack = view.findViewById(R.id.rlHealingBack);
                llTimeTagFilter = view.findViewById(R.id.llTimeTagFilter);
                llNapTimeTagFilter = view.findViewById(R.id.llNapTimeTagFilter);
                llHealingTagFilter = view.findViewById(R.id.llHealingTagFilter);
                rl5Min = view.findViewById(R.id.rl5Min);
                rl10Min = view.findViewById(R.id.rl10Min);
                rl20Min = view.findViewById(R.id.rl20Min);
                rl30Min = view.findViewById(R.id.rl30Min);
                rlNapTime20Min = view.findViewById(R.id.rlNapTime20Min);
                rlNapTime90Min = view.findViewById(R.id.rlNapTime90Min);
                rlPowerUp = view.findViewById(R.id.rlPowerUp);
                rlHealingMeditations = view.findViewById(R.id.rlHealingMeditations);
                rlGuidedMeditations = view.findViewById(R.id.rlGuidedMeditations);
                rlVisualisations = view.findViewById(R.id.rlVisualisations);
                rlNoMeditationDownload = view.findViewById(R.id.rlNoMeditationDownload);
                txtBackDownload = view.findViewById(R.id.txtBackDownload);
                txtNoDataFound = view.findViewById(R.id.txtNoDataFound);
                clNoFavouriteFound = view.findViewById(R.id.clNoFavouriteFound);
                txtFavSuggestion = view.findViewById(R.id.txtFavSuggestion);
                rlMainBlock.setVisibility(View.GONE);
                btnFindMyLevel = vi.findViewById(R.id.btnFindMyLevel);
                swipeLayout = vi.findViewById(R.id.swipeLayout);

                ImageSpan imageSpan = new ImageSpan(requireContext(), R.drawable.mbhq_heart_active_m);
                SpannableString spannableString = new SpannableString("Click the [heart icon pic] next to the meditations you love most and they will appear here.");

                spannableString.setSpan(imageSpan, 10, 26, 0);
                txtFavSuggestion.setText(spannableString);

                sharedPreference = new SharedPreference(getActivity());
                if (sharedPreference.read("dtPos", "").equals("") || Integer.parseInt(sharedPreference.read("dtPos", "")) == 1) {
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter);
                } else {
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                }

                rlBack.setOnClickListener(v -> {

                    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    if (!isConnected) {

                    }else{

                        sharedPreference.clear("med");
                        sharedPreference.clear("medT");
                        if (Connection.checkConnection(requireContext())) {
//                rlMainBlock.setVisibility(View.GONE);
//                llLandingOptions.setVisibility(View.VISIBLE);

                            if(backto.equalsIgnoreCase("")){
                                backto="";
                                edtSearch.setText("");
                                // Util.str_withfilterlist_afterbackfrommeditationdetails="";
                                rlMainBlock.setVisibility(View.GONE);
                                llLandingOptions.setVisibility(View.VISIBLE);
                            }else if("llTimeTagFilter".equalsIgnoreCase(backto)){
                                backto="";
                                edtSearch.setText("");
                                rlMainBlock.setVisibility(View.GONE);
                                llLandingOptions.setVisibility(View.GONE);
                                llTimeTagFilter.setVisibility(View.VISIBLE);
                            }else if("llNapTimeTagFilter".equalsIgnoreCase(backto)){
                                backto="";
                                edtSearch.setText("");
                                rlMainBlock.setVisibility(View.GONE);
                                llLandingOptions.setVisibility(View.GONE);
                                llNapTimeTagFilter.setVisibility(View.VISIBLE);
                            }else if("llHealingTagFilter".equalsIgnoreCase(backto)){
                                backto="";
                                edtSearch.setText("");
                                rlMainBlock.setVisibility(View.GONE);
                                llLandingOptions.setVisibility(View.GONE);
                                llHealingTagFilter.setVisibility(View.VISIBLE);
                            }else{
                                backto="";
                                edtSearch.setText("");
                                rlMainBlock.setVisibility(View.GONE);
                                rlMainBlock.setVisibility(View.GONE);
                                //  llLandingOptions.setVisibility(View.VISIBLE);
                            }




                        } else {

                            backto="";
                            edtSearch.setText("");
                            rlMainBlock.setVisibility(View.GONE);
                            rlMainBlock.setVisibility(View.GONE);
                            //llLandingOptions.setVisibility(View.VISIBLE);

//                ((MainActivity) requireActivity()).clearCacheForParticularFragment(new MbhqTodayMainFragment());
//                ((MainActivity) requireActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
                        }
                    }

                });
                rlBreathTimeBack.setOnClickListener(v -> {
                    sharedPreference.clear("med");
                    sharedPreference.clear("medT");
                    llLandingOptions.setVisibility(View.VISIBLE);
                    llTimeTagFilter.setVisibility(View.GONE);
                });
                rlNapTimeBack.setOnClickListener(v -> {
                    sharedPreference.clear("med");
                    sharedPreference.clear("medT");
                    llLandingOptions.setVisibility(View.VISIBLE);
                    llNapTimeTagFilter.setVisibility(View.GONE);
                });
                rlHealingBack.setOnClickListener(v -> {
                    sharedPreference.clear("med");
                    sharedPreference.clear("medT");
                    llLandingOptions.setVisibility(View.VISIBLE);
                    llHealingTagFilter.setVisibility(View.GONE);
                });
                rl5Min.setOnClickListener(v -> {
                    backto="llTimeTagFilter";

                    sharedPreference.write("medT", "", 5 + "");
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                    arrSelectedDuration.clear();
                    arrSelectedDuration.add("5 Min");
                    arrDurationChoice.add(5);
                    funSortdatabyTimeOutside(5);

                });
                rl10Min.setOnClickListener(v -> {
                    backto="llTimeTagFilter";
                    sharedPreference.write("medT", "", 10 + "");
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                    arrSelectedDuration.clear();
                    arrSelectedDuration.add("10 Min");
                    arrDurationChoice.add(10);
                    funSortdatabyTimeOutside(10);

                });
                rl20Min.setOnClickListener(v -> {
                    backto="llTimeTagFilter";
                    sharedPreference.write("medT", "", 20 + "");
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                    arrSelectedDuration.clear();
                    arrSelectedDuration.add("20 Min");
                    arrDurationChoice.add(20);
                    funSortdatabyTimeOutside(20);

                });
                rl30Min.setOnClickListener(v -> {
                    backto="llTimeTagFilter";
                    sharedPreference.write("medT", "", 30 + "");
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                    arrSelectedDuration.clear();
                    arrSelectedDuration.add("30 Min");
                    arrDurationChoice.add(30);
                    funSortdatabyTimeOutside(30);

                });

                rlMorning.setOnClickListener(v -> {

                    backto="llNapTimeTagFilter";
                    sharedPreference.write("med", "", 2 + "");
                    if (testViewModel.arrJson != null) {
                        if (lstFilterTagDataMorning.size() == 0) {
                            for (int k = 0; k < testViewModel.arrJson.length(); k++) {
                                try {
                                    JSONObject innerJson = testViewModel.arrJson.getJSONObject(k);
                                    String tagName = innerJson.getString("Description");
                                    for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                                        for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                            if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase(tagName)) {

                                                if (tagName.equalsIgnoreCase("Morning Routine")) {
                                                    lstFilterTagDataMorning.add(testViewModel.lstTotalDataM.get(p));
                                                    arrTagChoice.add("Morning Routine");
                                                }

                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        arrSelectedTag.clear();
                        arrSelectedTag.add("Morning Routine");
                        llLandingOptions.setVisibility(View.GONE);
                        rlMainBlock.setVisibility(View.VISIBLE);
                        rvCourseM.setAdapter(null);
                        llNapTimeTagFilter.setVisibility(View.GONE);
                        if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                            Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                        }else{
                            Util.withfilterlist_afterbackfrommeditationdetails.clear();
                            Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                        }
                        loadAvailableAdapterM(lstFilterTagDataMorning, lstFilterTagDataMorning.size(),1);

                    }


                });

                rlNapTime20Min.setOnClickListener(v -> {
                    backto="llNapTimeTagFilter";
                    sharedPreference.write("medT", "", 20 + "");
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                    arrSelectedDuration.clear();
                    arrSelectedDuration.add("20 Min");
                    arrDurationChoice.add(20);
                    sortPowerNapDataByTimeOutside(20);
                });
                rlNapTime90Min.setOnClickListener(v -> {
                    backto="llNapTimeTagFilter";
                    sharedPreference.write("medT", "", 90 + "");
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                    arrSelectedDuration.clear();
                    arrSelectedDuration.add("90 Min");
                    arrDurationChoice.add(90);
                    sortPowerNapDataByTimeOutside(90);
                });
                rlPowerUp.setOnClickListener(v -> {
                    edtSearch.setText("");
                    backto="llNapTimeTagFilter";
                    //sharedPreference.write("medT", "", 90 + "");
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                    //arrSelectedDuration.clear();
                    //arrSelectedDuration.add("90 Min");
                    //arrDurationChoice.add(90);
                    sortPowerNapDataByTimeOutside();
                });
                rlGuidedMeditations.setOnClickListener(v -> {
                    backto="llHealingTagFilter";
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                    sharedPreference.write("med", "", 7 + "");
                    sortHealingDataByTag("Guided Meditation");
                });
                rlVisualisations.setOnClickListener(v -> {
                    backto="llHealingTagFilter";
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                    sharedPreference.write("med", "", 6 + "");
                    sortHealingDataByTag("Visualisations");
                });
                rlHealingMeditations.setOnClickListener(v -> {
                    backto="llHealingTagFilter";
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                    sharedPreference.write("med", "", 5 + "");
                    sortHealingDataByTag("Healing Meditations");
                });
                rlHealingsleep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        backto="llHealingTagFilter";
                        lstFilterTagDataMorning.clear();
                        if (lstFilterTagDataMorning.size() == 0) {
                            for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                                if(testViewModel.lstTotalDataM.get(p).getTags().size()>1){
                                    for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                        if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase("Sleep")) {
                                            lstFilterTagDataMorning.add(testViewModel.lstTotalDataM.get(p));
                                            arrTagChoice.add("Sleep");
                                        }
                                    }
                                }
                            }

                            arrSelectedTag.clear();
                            arrSelectedTag.add("Sleep");
                            llLandingOptions.setVisibility(View.GONE);
                            rlMainBlock.setVisibility(View.VISIBLE);
                            rvCourseM.setAdapter(null);
                            llNapTimeTagFilter.setVisibility(View.GONE);
                            llHealingTagFilter.setVisibility(View.GONE);
                            if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                                Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                                Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                            }else{
                                Util.withfilterlist_afterbackfrommeditationdetails.clear();
                                Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                                Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                            }
                            loadAvailableAdapterM(lstFilterTagDataMorning, lstFilterTagDataMorning.size(),1);
                        }
                    }
                });

                imgFab.setOnClickListener(v -> {
                    edtSearch.setText("");
                    backto="";
                    imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                    favChk = true;
                    List<MeditationCourseModel.Webinar> lstFavModel = new ArrayList<>();
                    for (int x = 0; x < testViewModel.lstTotalDataM.size(); x++) {

                        if(null==testViewModel.lstTotalDataM.get(x).getLikes()){

                        }else{
                            if (testViewModel.lstTotalDataM.get(x).getLikes()) {
                                lstFavModel.add(testViewModel.lstTotalDataM.get(x));
                            }
                        }

                    }
                    llLandingOptions.setVisibility(View.GONE);
                    llTimeTagFilter.setVisibility(View.GONE);
                    rlMainBlock.setVisibility(View.VISIBLE);
                    rvCourseM.setAdapter(null);
                    loadAvailableAdapterM(lstFavModel, lstFavModel.size(), true);

                });
                rlBreath.setOnClickListener(v -> {
                    edtSearch.setText("");
                    if (testViewModel.arrJson != null) {
                        sharedPreference.write("med", "", 1 + "");
                        arrSelectedTag.clear();
                        arrSelectedTag.add("Breath Control");
                        arrTagChoice.add("Breath Control");
                        sharedPreference.write("med", "", 1 + "");
                        hideAllChoiceContainers();
                        llTimeTagFilter.setVisibility(View.VISIBLE);
                        if (lstFilterTagDataBreath.size() == 0) {
                            for (int k = 0; k < testViewModel.arrJson.length(); k++) {
                                try {
                                    JSONObject innerJson = testViewModel.arrJson.getJSONObject(k);
                                    String tagName = innerJson.getString("Description");
                                    for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                                        for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                            if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase(tagName)) {
                                                if (tagName.equalsIgnoreCase("Breath Control"))
                                                    lstFilterTagDataBreath.add(testViewModel.lstTotalDataM.get(p));

                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }

                });

                rlMindfulness.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        edtSearch.setText("");
                        backto="";
                        lstFilterTagDataMorning.clear();
                        if (lstFilterTagDataMorning.size() == 0) {
                            for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                                if(testViewModel.lstTotalDataM.get(p).getTags().size()>1){
                                    for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                        if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase("Mindfulness")) {
                                            lstFilterTagDataMorning.add(testViewModel.lstTotalDataM.get(p));
                                            arrTagChoice.add("Mindfulness");
                                        }
                                    }
                                }

                            }
                            arrSelectedTag.clear();
                            arrSelectedTag.add("Mindfulness");
                            llLandingOptions.setVisibility(View.GONE);
                            rlMainBlock.setVisibility(View.VISIBLE);
                            rvCourseM.setAdapter(null);
                            llNapTimeTagFilter.setVisibility(View.GONE);
                            if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                                Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                                Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                            }else{
                                Util.withfilterlist_afterbackfrommeditationdetails.clear();
                                Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                                Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                            }
                            loadAvailableAdapterM(lstFilterTagDataMorning, lstFilterTagDataMorning.size(),1);
                        }
                    }
                });
                rlPowerNap.setOnClickListener(v -> {
                    edtSearch.setText("");
                    sharedPreference.write("med", "", 3 + "");
                    if (testViewModel.arrJson != null) {
                        if (lstFilterTagDataPower.size() == 0) {
                            for (int k = 0; k < testViewModel.arrJson.length(); k++) {
                                try {
                                    JSONObject innerJson = testViewModel.arrJson.getJSONObject(k);
                                    String tagName = innerJson.getString("Description");
                                    for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                                        for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                            if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase(tagName)) {

                                                if (tagName.equalsIgnoreCase("Power Naps")) {
                                                    lstFilterTagDataPower.add(testViewModel.lstTotalDataM.get(p));
                                                    arrTagChoice.add("Power Naps");
                                                }
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        arrSelectedTag.clear();
                        arrSelectedTag.add("Power Naps");
                        hideAllChoiceContainers();
                        llNapTimeTagFilter.setVisibility(View.VISIBLE);

                    }

                });

                rlHealing.setOnClickListener(v -> {
                    edtSearch.setText("");
                    sharedPreference.write("med", "", 4 + "");
                    if (testViewModel.arrJson != null) {
                        if (lstFilterTagDataHealing.size() == 0) {
                            for (int k = 0; k < testViewModel.arrJson.length(); k++) {
                                try {
                                    JSONObject innerJson = testViewModel.arrJson.getJSONObject(k);
                                    String tagName = innerJson.getString("Description");
                                    for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                                        for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                            if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase(tagName)) {

                                                if (tagName.equalsIgnoreCase("Healing Meditations") || tagName.equalsIgnoreCase("Visualisations") || tagName.equalsIgnoreCase("Guided Meditation")) {
                                                    lstFilterTagDataHealing.add(testViewModel.lstTotalDataM.get(p));
                                                    arrTagChoice.add("Healing Meditations");
                                                }
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        arrSelectedDuration.clear();
                        arrDurationChoice.clear();
                        hideAllChoiceContainers();
                        llHealingTagFilter.setVisibility(View.VISIBLE);
                    }

                });

                rlFilter.setOnClickListener(v -> {
                    if (testViewModel.arrJson != null)
                        openFilterDialog(true, false);

                });

                imgInfoMeditation.setOnClickListener(v -> {
                    //String meditationInfoMediaUrl = "https://player.vimeo.com/external/391432653.sd.mp4?s=1405198804056d0157af37e37ae4b9d3bc032db2&profile_id=165";
                    //openFullscreenVideoDialog(meditationInfoMediaUrl);
                    ((MainActivity) requireActivity()).funTabBarforReward(false);
                });

                txtBackDownload.setOnClickListener(v -> ((MainActivity) requireActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null));



                swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        getMeditation();

                        swipeLayout.setRefreshing(false);
                    }
                });

                btnFindMyLevel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreference sharedPreference = new SharedPreference(getContext());
                        String URL = "https://meditate.mindbodyhq.com/members?token=" + sharedPreference.read("MEDITATION_TEST_NOW_TOKEN", "");
                        Uri uri = Uri.parse(URL);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setPackage("com.android.chrome");
                        requireActivity().startActivity(intent);
                    }
                });
                edtSearch.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {



                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

               *//* withfilterlist.clear();
                withfilterlist= ((MeditationCourseAdapter) rvCourseM.getAdapter()).getArrayList();
*//*
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {


                        if(s.toString().length()>0){
                            List<MeditationCourseModel.Webinar> avaiavlelist1 = new ArrayList<>();
                            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                            if (!isConnected) {
                                rlMainBlock.setVisibility(View.VISIBLE);
                                rvCourseM.setVisibility(View.VISIBLE);
                                rlNoMeditationDownload.setVisibility(View.GONE);
                                rvCourseM_main.setVisibility(View.GONE);
                                rvCourseM_nointernate.setVisibility(View.GONE);
                                if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                                    Util.withfilterlist_afterbackfrommeditationdetails.addAll(avaiavlelist1);
                                    Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                                }else{
                                    Util.withfilterlist_afterbackfrommeditationdetails.clear();
                                    Util.withfilterlist_afterbackfrommeditationdetails.addAll(avaiavlelist1);
                                    Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                                }
                                loadAvailableAdapterM(avaiavlelist1, avaiavlelist1.size(),0);
                                if (rvCourseM != null && rvCourseM.getAdapter() != null) {
                                    ((MeditationCourseAdapter) rvCourseM.getAdapter()).search(s.toString());

                                }
                            }else{
                                Log.i("m_called","1");
                                SharedPreferences preferences = getActivity().getSharedPreferences("MyAppPrefs1", Context.MODE_PRIVATE);
                                String jsonData = preferences.getString("my_total_medicine", "");
                                List<MeditationCourseModel.Webinar> avaiavlelist = new ArrayList<>();
                                avaiavlelist = new Gson().fromJson(jsonData, new TypeToken<List<MeditationCourseModel.Webinar>>() {}.getType());
                                Log.i("concatinatelist2",String.valueOf(avaiavlelist.size()));

                                rlMainBlock.setVisibility(View.VISIBLE);
                                rvCourseM.setVisibility(View.VISIBLE);
                                rlNoMeditationDownload.setVisibility(View.GONE);
                                rvCourseM_main.setVisibility(View.GONE);
                                rvCourseM_nointernate.setVisibility(View.GONE);
                                if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                                    Log.i("m_called","2");
                                    Util.withfilterlist_afterbackfrommeditationdetails.addAll(avaiavlelist);
                                    Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                                }else{
                                    Log.i("m_called","3");
                                    Util.withfilterlist_afterbackfrommeditationdetails.clear();
                                    Util.withfilterlist_afterbackfrommeditationdetails.addAll(avaiavlelist);
                                    Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                                }
                                loadAvailableAdapterM(avaiavlelist, avaiavlelist.size(),0);
                                if (rvCourseM != null && rvCourseM.getAdapter() != null) {
                                    ((MeditationCourseAdapter) rvCourseM.getAdapter()).search(s.toString());

                                }
                            }

                        }else if(s.toString().length()==0){
                            Log.i("m_called","4");
                            // Log.i("withfilterlist1","called");

                            rlMainBlock.setVisibility(View.VISIBLE);
                            rvCourseM.setVisibility(View.VISIBLE);

                            rlNoMeditationDownload.setVisibility(View.GONE);
                            rvCourseM_main.setVisibility(View.GONE);
                            rvCourseM_nointernate.setVisibility(View.GONE);


                            if(!"TRUE".equalsIgnoreCase(fromDetailsPage1)){

                                Log.i("m_called","5");
                                loadAvailableAdapterM(withfilterlist, withfilterlist.size(),0);
                                if (rvCourseM != null && rvCourseM.getAdapter() != null) {
                                    ((MeditationCourseAdapter) rvCourseM.getAdapter()).search(s.toString());


                                }
                            }else{
                                Log.i("m_called","6");
                                loadAvailableAdapterM( Util.withfilterlist,  Util.withfilterlist.size(),0);
                                if (rvCourseM != null && rvCourseM.getAdapter() != null) {
                                    ((MeditationCourseAdapter) rvCourseM.getAdapter()).search(s.toString());


                                }
                            }



                        }

                    }
                });
            }
        }else{*/
        Log.i("strMeditationDetailsForBackground",Util.strMeditationDetailsForBackground);
        if (Util.boolBackGroundServiceRunningMeditation && !Util.strMeditationDetailsForBackground.equals("") && !fromDetailsPage) {
            Log.i(TAG, "UTILVALUEEEEEE" + Util.strMeditationDetailsForBackground + ">>>>>");
            Util.backto="";
            ((MainActivity) requireActivity()).clearCacheForParticularFragment(new MeditationDetailsNew());
            MeditationDetailsNew meditationDetails = new MeditationDetailsNew();
            Bundle bundle = new Bundle();
            bundle.putString("data", Util.strMeditationDetailsForBackground);
            meditationDetails.setArguments(bundle);
            ((MainActivity) requireActivity()).loadFragment(meditationDetails, "MeditationDetailsNew", null);
        } else {
            // fromDetailsPage = false;
        }


        rlHealingsleep= view.findViewById(R.id.rlHealingsleep);
        rvCourseM = view.findViewById(R.id.rvCourseM);
        rvCourseM_main=view.findViewById(R.id.rvCourseM_main);
        rvCourseM_nointernate=view.findViewById(R.id.rvCourseM_nointernate);
        rlMindfulness=view.findViewById(R.id.rlMindfulness);
        sharedPreference = new SharedPreference(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvCourseM.setLayoutManager(mLayoutManager);
        mLayoutManager_main = new LinearLayoutManager(getActivity());
        rvCourseM_main.setLayoutManager(mLayoutManager_main);
        mLayoutManager_nointernate = new LinearLayoutManager(getActivity());
        rvCourseM_nointernate.setLayoutManager(mLayoutManager_nointernate);

        rlFilter = view.findViewById(R.id.rlFilter);
        imgFilter = view.findViewById(R.id.imgFilter);
        imgInfoMeditation = view.findViewById(R.id.imgInfoMeditation);
        edtSearch = view.findViewById(R.id.edtSearch);
        rlBreath = view.findViewById(R.id.rlBreath);
        rlMorning = view.findViewById(R.id.rlMorning);
        rlPowerNap = view.findViewById(R.id.rlPowerNap);
        rlHealing = view.findViewById(R.id.rlHealing);
        imgFab = view.findViewById(R.id.imgFab);
        llLandingOptions = view.findViewById(R.id.llLandingOptions);

        rlBack = view.findViewById(R.id.rlBack);
        rlBreathTimeBack = view.findViewById(R.id.rlBreathTimeBack);
        rlNapTimeBack = view.findViewById(R.id.rlNapTimeBack);
        rlHealingBack = view.findViewById(R.id.rlHealingBack);
        llTimeTagFilter = view.findViewById(R.id.llTimeTagFilter);
        llNapTimeTagFilter = view.findViewById(R.id.llNapTimeTagFilter);
        llHealingTagFilter = view.findViewById(R.id.llHealingTagFilter);
        rl5Min = view.findViewById(R.id.rl5Min);
        rl10Min = view.findViewById(R.id.rl10Min);
        rl20Min = view.findViewById(R.id.rl20Min);
        rl30Min = view.findViewById(R.id.rl30Min);
        rlNapTime20Min = view.findViewById(R.id.rlNapTime20Min);
        rlNapTime90Min = view.findViewById(R.id.rlNapTime90Min);
        rlPowerUp = view.findViewById(R.id.rlPowerUp);
        rlHealingMeditations = view.findViewById(R.id.rlHealingMeditations);
        rlGuidedMeditations = view.findViewById(R.id.rlGuidedMeditations);
        rlVisualisations = view.findViewById(R.id.rlVisualisations);
        rlNoMeditationDownload = view.findViewById(R.id.rlNoMeditationDownload);
        txtBackDownload = view.findViewById(R.id.txtBackDownload);
        txtNoDataFound = view.findViewById(R.id.txtNoDataFound);
        clNoFavouriteFound = view.findViewById(R.id.clNoFavouriteFound);
        txtFavSuggestion = view.findViewById(R.id.txtFavSuggestion);
        rlMainBlock.setVisibility(View.GONE);
        btnFindMyLevel = vi.findViewById(R.id.btnFindMyLevel);
        swipeLayout = vi.findViewById(R.id.swipeLayout);

        ImageSpan imageSpan = new ImageSpan(requireContext(), R.drawable.mbhq_heart_active_m);
        SpannableString spannableString = new SpannableString("Click the [heart icon pic] next to the meditations you love most and they will appear here.");

        spannableString.setSpan(imageSpan, 10, 26, 0);
        txtFavSuggestion.setText(spannableString);

        sharedPreference = new SharedPreference(getActivity());
        if (sharedPreference.read("dtPos", "").equals("") || Integer.parseInt(sharedPreference.read("dtPos", "")) == 1) {
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter);
        } else {
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
        }

        rlBack.setOnClickListener(v -> {

            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {

            }else{

                sharedPreference.clear("med");
                sharedPreference.clear("medT");
                if (Connection.checkConnection(requireContext())) {
//                rlMainBlock.setVisibility(View.GONE);
//                llLandingOptions.setVisibility(View.VISIBLE);

                    if(backto.equalsIgnoreCase("")){
                        backto="";
                        edtSearch.setText("");
                        // Util.str_withfilterlist_afterbackfrommeditationdetails="";
                        rlMainBlock.setVisibility(View.GONE);
                        llLandingOptions.setVisibility(View.VISIBLE);
                    }else if("llTimeTagFilter".equalsIgnoreCase(backto)){
                        backto="";
                        edtSearch.setText("");
                        rlMainBlock.setVisibility(View.GONE);
                        llLandingOptions.setVisibility(View.GONE);
                        llTimeTagFilter.setVisibility(View.VISIBLE);
                    }else if("llNapTimeTagFilter".equalsIgnoreCase(backto)){
                        backto="";
                        edtSearch.setText("");
                        rlMainBlock.setVisibility(View.GONE);
                        llLandingOptions.setVisibility(View.GONE);
                        llNapTimeTagFilter.setVisibility(View.VISIBLE);
                    }else if("llHealingTagFilter".equalsIgnoreCase(backto)){
                        backto="";
                        edtSearch.setText("");
                        rlMainBlock.setVisibility(View.GONE);
                        llLandingOptions.setVisibility(View.GONE);
                        llHealingTagFilter.setVisibility(View.VISIBLE);
                    }else{
                        backto="";
                        edtSearch.setText("");
                        rlMainBlock.setVisibility(View.GONE);
                        rlMainBlock.setVisibility(View.GONE);
                        //  llLandingOptions.setVisibility(View.VISIBLE);
                    }




                } else {

                    backto="";
                    edtSearch.setText("");
                    rlMainBlock.setVisibility(View.GONE);
                    rlMainBlock.setVisibility(View.GONE);
                    //llLandingOptions.setVisibility(View.VISIBLE);

//                ((MainActivity) requireActivity()).clearCacheForParticularFragment(new MbhqTodayMainFragment());
//                ((MainActivity) requireActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
                }
            }

        });
        rlBreathTimeBack.setOnClickListener(v -> {
            sharedPreference.clear("med");
            sharedPreference.clear("medT");
            llLandingOptions.setVisibility(View.VISIBLE);
            llTimeTagFilter.setVisibility(View.GONE);
        });
        rlNapTimeBack.setOnClickListener(v -> {
            sharedPreference.clear("med");
            sharedPreference.clear("medT");
            llLandingOptions.setVisibility(View.VISIBLE);
            llNapTimeTagFilter.setVisibility(View.GONE);
        });
        rlHealingBack.setOnClickListener(v -> {
            sharedPreference.clear("med");
            sharedPreference.clear("medT");
            llLandingOptions.setVisibility(View.VISIBLE);
            llHealingTagFilter.setVisibility(View.GONE);
        });
        rl5Min.setOnClickListener(v -> {
            backto="llTimeTagFilter";

            sharedPreference.write("medT", "", 5 + "");
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
            arrSelectedDuration.clear();
            arrSelectedDuration.add("5 Min");
            arrDurationChoice.add(5);
            funSortdatabyTimeOutside(5);

        });
        rl10Min.setOnClickListener(v -> {
            backto="llTimeTagFilter";
            sharedPreference.write("medT", "", 10 + "");
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
            arrSelectedDuration.clear();
            arrSelectedDuration.add("10 Min");
            arrDurationChoice.add(10);
            funSortdatabyTimeOutside(10);

        });
        rl20Min.setOnClickListener(v -> {
            backto="llTimeTagFilter";
            sharedPreference.write("medT", "", 20 + "");
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
            arrSelectedDuration.clear();
            arrSelectedDuration.add("20 Min");
            arrDurationChoice.add(20);
            funSortdatabyTimeOutside(20);

        });
        rl30Min.setOnClickListener(v -> {
            backto="llTimeTagFilter";
            sharedPreference.write("medT", "", 30 + "");
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
            arrSelectedDuration.clear();
            arrSelectedDuration.add("30 Min");
            arrDurationChoice.add(30);
            funSortdatabyTimeOutside(30);

        });

        rlMorning.setOnClickListener(v -> {

            backto="llNapTimeTagFilter";
            sharedPreference.write("med", "", 2 + "");
            /*if(testViewModel.arrJson != null)*//*sahenita*/
            if (testViewModel.arrJson .length()!=0) {
                if (lstFilterTagDataMorning.size() == 0) {

                    String jsonString = testViewModel.arrJson.toString();
                    System.out.println("JSON String: " + jsonString);
                    try{
                        JSONArray outerArray = new JSONArray(jsonString);
                        // Assuming testViewModel.arrJson is at index 0 of the outerArray
                        JSONArray innerArray = outerArray.getJSONArray(0);

                        for (int k = 0; k < innerArray.length(); k++) {
                            try {
                                JSONObject innerJson = innerArray.getJSONObject(k);
                                String tagName = innerJson.getString("Description");
                                for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                                    for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                        if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase(tagName)) {

                                            if (tagName.equalsIgnoreCase("Morning Routine")) {
                                                lstFilterTagDataMorning.add(testViewModel.lstTotalDataM.get(p));
                                                arrTagChoice.add("Morning Routine");
                                            }

                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }catch (Exception e){

                    }



                }
                arrSelectedTag.clear();
                arrSelectedTag.add("Morning Routine");
                llLandingOptions.setVisibility(View.GONE);
                rlMainBlock.setVisibility(View.VISIBLE);
                rvCourseM.setAdapter(null);
                llNapTimeTagFilter.setVisibility(View.GONE);
                if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                    Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                    Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                }else{
                    Util.withfilterlist_afterbackfrommeditationdetails.clear();
                    Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                    Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                }
                loadAvailableAdapterM(lstFilterTagDataMorning, lstFilterTagDataMorning.size(),1);

            }


        });

        rlNapTime20Min.setOnClickListener(v -> {
            backto="llNapTimeTagFilter";
            sharedPreference.write("medT", "", 20 + "");
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
            arrSelectedDuration.clear();
            arrSelectedDuration.add("20 Min");
            arrDurationChoice.add(20);
            sortPowerNapDataByTimeOutside(20);
        });
        rlNapTime90Min.setOnClickListener(v -> {
            backto="llNapTimeTagFilter";
            sharedPreference.write("medT", "", 90 + "");
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
            arrSelectedDuration.clear();
            arrSelectedDuration.add("90 Min");
            arrDurationChoice.add(90);
            sortPowerNapDataByTimeOutside(90);
        });
        rlPowerUp.setOnClickListener(v -> {
            edtSearch.setText("");
            backto="llNapTimeTagFilter";
            //sharedPreference.write("medT", "", 90 + "");
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
            //arrSelectedDuration.clear();
            //arrSelectedDuration.add("90 Min");
            //arrDurationChoice.add(90);
            sortPowerNapDataByTimeOutside();
        });
        rlGuidedMeditations.setOnClickListener(v -> {
            backto="llHealingTagFilter";
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
            sharedPreference.write("med", "", 7 + "");
            sortHealingDataByTag("Guided Meditation");
        });
        rlVisualisations.setOnClickListener(v -> {
            backto="llHealingTagFilter";
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
            sharedPreference.write("med", "", 6 + "");
            sortHealingDataByTag("Visualisations");
        });
        rlHealingMeditations.setOnClickListener(v -> {
            backto="llHealingTagFilter";
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
            sharedPreference.write("med", "", 5 + "");
            sortHealingDataByTag("Healing Meditations");
        });
        rlHealingsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backto="llHealingTagFilter";
                lstFilterTagDataMorning.clear();
                if (lstFilterTagDataMorning.size() == 0) {
                    for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                        if(testViewModel.lstTotalDataM.get(p).getTags().size()>1){
                            for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase("Sleep")) {
                                    lstFilterTagDataMorning.add(testViewModel.lstTotalDataM.get(p));
                                    arrTagChoice.add("Sleep");
                                }
                            }
                        }
                    }

                    arrSelectedTag.clear();
                    arrSelectedTag.add("Sleep");
                    llLandingOptions.setVisibility(View.GONE);
                    rlMainBlock.setVisibility(View.VISIBLE);
                    rvCourseM.setAdapter(null);
                    llNapTimeTagFilter.setVisibility(View.GONE);
                    llHealingTagFilter.setVisibility(View.GONE);
                    if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                        Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                        Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                    }else{
                        Util.withfilterlist_afterbackfrommeditationdetails.clear();
                        Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                        Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                    }
                    loadAvailableAdapterM(lstFilterTagDataMorning, lstFilterTagDataMorning.size(),1);
                }
            }
        });

        imgFab.setOnClickListener(v -> {
            edtSearch.setText("");
            backto="";
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
            favChk = true;
            List<MeditationCourseModel.Webinar> lstFavModel = new ArrayList<>();
            for (int x = 0; x < testViewModel.lstTotalDataM.size(); x++) {

                if(null==testViewModel.lstTotalDataM.get(x).getLikes()){

                }else{
                    if (testViewModel.lstTotalDataM.get(x).getLikes()) {
                        lstFavModel.add(testViewModel.lstTotalDataM.get(x));
                    }
                }

            }
            llLandingOptions.setVisibility(View.GONE);
            llTimeTagFilter.setVisibility(View.GONE);
            rlMainBlock.setVisibility(View.VISIBLE);
            rvCourseM.setAdapter(null);
            loadAvailableAdapterM(lstFavModel, lstFavModel.size(), true);

        });
        rlBreath.setOnClickListener(v -> {
            edtSearch.setText("");
            /*if(testViewModel.arrJson != null)*//*sahenita*/
            if (testViewModel.arrJson .length()!=0) {
                sharedPreference.write("med", "", 1 + "");
                arrSelectedTag.clear();
                arrSelectedTag.add("Breath Control");
                arrTagChoice.add("Breath Control");
                sharedPreference.write("med", "", 1 + "");
                hideAllChoiceContainers();
                llTimeTagFilter.setVisibility(View.VISIBLE);
                if (lstFilterTagDataBreath.size() == 0) {
                    String jsonString = testViewModel.arrJson.toString();
                    System.out.println("JSON String: " + jsonString);
                    try{
                        JSONArray outerArray = new JSONArray(jsonString);
                        // Assuming testViewModel.arrJson is at index 0 of the outerArray
                        JSONArray innerArray = outerArray.getJSONArray(0);
                        for (int k = 0; k < innerArray.length(); k++) {
                            try {
                                JSONObject innerJson = innerArray.getJSONObject(k);
                                String tagName = innerJson.getString("Description");
                                for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                                    for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                        if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase(tagName)) {
                                            if (tagName.equalsIgnoreCase("Breath Control"))
                                                lstFilterTagDataBreath.add(testViewModel.lstTotalDataM.get(p));

                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }

        });

        rlMindfulness.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                edtSearch.setText("");
                backto="";
                lstFilterTagDataMorning.clear();
                if (lstFilterTagDataMorning.size() == 0) {
                    for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                        if(testViewModel.lstTotalDataM.get(p).getTags().size()>1){
                            for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase("Mindfulness")) {
                                    lstFilterTagDataMorning.add(testViewModel.lstTotalDataM.get(p));
                                    arrTagChoice.add("Mindfulness");
                                }
                            }
                        }

                    }
                    arrSelectedTag.clear();
                    arrSelectedTag.add("Mindfulness");
                    llLandingOptions.setVisibility(View.GONE);
                    rlMainBlock.setVisibility(View.VISIBLE);
                    rvCourseM.setAdapter(null);
                    llNapTimeTagFilter.setVisibility(View.GONE);
                    if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                        Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                        Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                    }else{
                        Util.withfilterlist_afterbackfrommeditationdetails.clear();
                        Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagDataMorning);
                        Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                    }
                    loadAvailableAdapterM(lstFilterTagDataMorning, lstFilterTagDataMorning.size(),1);
                }
            }
        });
        rlPowerNap.setOnClickListener(v -> {
            edtSearch.setText("");
            sharedPreference.write("med", "", 3 + "");
            /*if(testViewModel.arrJson != null)*//*sahenita*/
            if (testViewModel.arrJson .length()!=0) {
                if (lstFilterTagDataPower.size() == 0) {
                    String jsonString = testViewModel.arrJson.toString();
                    System.out.println("JSON String: " + jsonString);
                    try{
                        JSONArray outerArray = new JSONArray(jsonString);
                        // Assuming testViewModel.arrJson is at index 0 of the outerArray
                        JSONArray innerArray = outerArray.getJSONArray(0);
                        for (int k = 0; k < innerArray.length(); k++) {
                            try {
                                JSONObject innerJson = innerArray.getJSONObject(k);
                                String tagName = innerJson.getString("Description");
                                for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                                    for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                        if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase(tagName)) {

                                            if (tagName.equalsIgnoreCase("Power Naps")) {
                                                lstFilterTagDataPower.add(testViewModel.lstTotalDataM.get(p));
                                                arrTagChoice.add("Power Naps");
                                            }
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }catch (Exception e){

                    }

                }
                arrSelectedTag.clear();
                arrSelectedTag.add("Power Naps");
                hideAllChoiceContainers();
                llNapTimeTagFilter.setVisibility(View.VISIBLE);

            }

        });

        rlHealing.setOnClickListener(v -> {
            edtSearch.setText("");
            sharedPreference.write("med", "", 4 + "");
            /*if(testViewModel.arrJson != null)*//*sahenita*/
            if (testViewModel.arrJson .length()!=0) {
                if (lstFilterTagDataHealing.size() == 0) {

                    String jsonString = testViewModel.arrJson.toString();
                    System.out.println("JSON String: " + jsonString);
                    try{
                        JSONArray outerArray = new JSONArray(jsonString);
                        // Assuming testViewModel.arrJson is at index 0 of the outerArray
                        JSONArray innerArray = outerArray.getJSONArray(0);

                        for (int k = 0; k < innerArray.length(); k++) {
                            try {
                                JSONObject innerJson = innerArray.getJSONObject(k);
                                String tagName = innerJson.getString("Description");
                                for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                                    for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                                        if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase(tagName)) {

                                            if (tagName.equalsIgnoreCase("Healing Meditations") || tagName.equalsIgnoreCase("Visualisations") || tagName.equalsIgnoreCase("Guided Meditation")) {
                                                lstFilterTagDataHealing.add(testViewModel.lstTotalDataM.get(p));
                                                arrTagChoice.add("Healing Meditations");
                                            }
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }catch (Exception e){

                    }


                }
                arrSelectedDuration.clear();
                arrDurationChoice.clear();
                hideAllChoiceContainers();
                llHealingTagFilter.setVisibility(View.VISIBLE);
            }

        });

        rlFilter.setOnClickListener(v -> {
            /*if(testViewModel.arrJson != null)*//*sahenita*/
            if (testViewModel.arrJson .length()!=0)
                openFilterDialog(true, false);

        });

        imgInfoMeditation.setOnClickListener(v -> {
            /*commented by sahenita (temporary)*/
           // ((MainActivity) requireActivity()).funTabBarforReward(false);
        });

        txtBackDownload.setOnClickListener(v -> ((MainActivity) requireActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null));



        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getMeditation();

                swipeLayout.setRefreshing(false);
            }
        });

        btnFindMyLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreference sharedPreference = new SharedPreference(getContext());
                String URL = "https://meditate.emotionalfitnessclub.com/members?token=" + sharedPreference.read("MEDITATION_TEST_NOW_TOKEN", "");
                Uri uri = Uri.parse(URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.android.chrome");
                requireActivity().startActivity(intent);
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {



            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

               /* withfilterlist.clear();
                withfilterlist= ((MeditationCourseAdapter) rvCourseM.getAdapter()).getArrayList();
*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                if(s.toString().length()>0){
                    List<MeditationCourseModel.Webinar> avaiavlelist1 = new ArrayList<>();
                    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    if (!isConnected) {
                        rlMainBlock.setVisibility(View.VISIBLE);
                        rvCourseM.setVisibility(View.VISIBLE);
                        rlNoMeditationDownload.setVisibility(View.GONE);
                        rvCourseM_main.setVisibility(View.GONE);
                        rvCourseM_nointernate.setVisibility(View.GONE);
                        if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                            Util.withfilterlist_afterbackfrommeditationdetails.addAll(avaiavlelist1);
                            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                        }else{
                            Util.withfilterlist_afterbackfrommeditationdetails.clear();
                            Util.withfilterlist_afterbackfrommeditationdetails.addAll(avaiavlelist1);
                            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                        }
                        loadAvailableAdapterM(avaiavlelist1, avaiavlelist1.size(),0);
                        if (rvCourseM != null && rvCourseM.getAdapter() != null) {
                            ((MeditationCourseAdapter) rvCourseM.getAdapter()).search(s.toString());

                        }
                    }else{
                        Log.i("m_called","1");
                        SharedPreferences preferences = getActivity().getSharedPreferences("MyAppPrefs1", Context.MODE_PRIVATE);
                        String jsonData = preferences.getString("my_total_medicine", "");
                        List<MeditationCourseModel.Webinar> avaiavlelist = new ArrayList<>();
                        avaiavlelist = new Gson().fromJson(jsonData, new TypeToken<List<MeditationCourseModel.Webinar>>() {}.getType());
                        Log.i("concatinatelist2",String.valueOf(avaiavlelist.size()));

                        rlMainBlock.setVisibility(View.VISIBLE);
                        rvCourseM.setVisibility(View.VISIBLE);
                        rlNoMeditationDownload.setVisibility(View.GONE);
                        rvCourseM_main.setVisibility(View.GONE);
                        rvCourseM_nointernate.setVisibility(View.GONE);
                        if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                            Log.i("m_called","2");
                            Util.withfilterlist_afterbackfrommeditationdetails.addAll(avaiavlelist);
                            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                        }else{
                            Log.i("m_called","3");
                            Util.withfilterlist_afterbackfrommeditationdetails.clear();
                            Util.withfilterlist_afterbackfrommeditationdetails.addAll(avaiavlelist);
                            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                        }
                        loadAvailableAdapterM(avaiavlelist, avaiavlelist.size(),0);
                        if (rvCourseM != null && rvCourseM.getAdapter() != null) {
                            ((MeditationCourseAdapter) rvCourseM.getAdapter()).search(s.toString());

                        }
                    }

                }else if(s.toString().length()==0){
                    Log.i("m_called","4");
                    // Log.i("withfilterlist1","called");

                    rlMainBlock.setVisibility(View.VISIBLE);
                    rvCourseM.setVisibility(View.VISIBLE);

                    rlNoMeditationDownload.setVisibility(View.GONE);
                    rvCourseM_main.setVisibility(View.GONE);
                    rvCourseM_nointernate.setVisibility(View.GONE);


                    if(!"TRUE".equalsIgnoreCase(fromDetailsPage1)){

                        Log.i("m_called","5");
                        loadAvailableAdapterM(withfilterlist, withfilterlist.size(),0);
                        if (rvCourseM != null && rvCourseM.getAdapter() != null) {
                            ((MeditationCourseAdapter) rvCourseM.getAdapter()).search(s.toString());


                        }
                    }else{
                        Log.i("m_called","6");
                        loadAvailableAdapterM( Util.withfilterlist,  Util.withfilterlist.size(),0);
                        if (rvCourseM != null && rvCourseM.getAdapter() != null) {
                            ((MeditationCourseAdapter) rvCourseM.getAdapter()).search(s.toString());


                        }
                    }



                }

            }
        });
        // }



    }
    private void getMeditation() {

        if (Connection.checkConnection(requireContext())) {
            // final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("Count", 100);
            hashReq.put("EventType", 20);

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<MeditationCourseModel> serverCall = finisherService.getArchivedWebinarsAbsolute(hashReq);
            serverCall.enqueue(new Callback<MeditationCourseModel>() {
                @Override
                public void onResponse(Call<MeditationCourseModel> call, Response<MeditationCourseModel> response) {
                    //  progressDialog.dismiss();
                    Log.e("success", "su");
                    if (response.body() != null) {
                        try {
                            rlNoMeditationDownload.setVisibility(View.GONE);
                            MeditationCourseModel lstData = response.body();

                            sharedPreference.writeBoolean("SHOW_SHOP_NOW_FOR_MEDITATIONS", "", lstData.getShowShopNow());
                            sharedPreference.write("SHOP_URL_FOR_MEDITATIONS", "", lstData.getShoppingURL());

                            testViewModel.lstTotalDataM.clear();
                            concatinatelist.clear();
                            List<MeditationEntity> meditationEntities = new ArrayList<>();

                            for (int i = 0; i < lstData.getWebinars().size(); i++) {
                                testViewModel.lstTotalDataM.add(lstData.getWebinars().get(i));

                                concatinatelist.add(lstData.getWebinars().get(i));
                                meditationEntities.add(
                                        new MeditationEntity(
                                                lstData.getWebinars().get(i).getEventItemID(), new Gson().toJson(lstData.getWebinars().get(i)), false, false
                                        )
                                );

                                if(null==lstData.getWebinars().get(i).getLikes()){

                                }else{
                                    if (lstData.getWebinars().get(i).getLikes()) {
                                        sharedPreference.writeBoolean("HAS_USER_A_FAV_MEDITATION", "", true);
                                    }
                                }


                            }

                            Log.i("concatinatelist0",String.valueOf(concatinatelist.size()));

                            SharedPreferences preferences = getActivity().getSharedPreferences("MyAppPrefs1", Context.MODE_PRIVATE);
                            String jsonData = preferences.getString("my_total_medicine", "");
                            List<MeditationCourseModel.Webinar> avaiavlelist = new ArrayList<>();
                            if (jsonData.isEmpty()){

                            }else{
                                avaiavlelist = new Gson().fromJson(jsonData, new TypeToken<List<MeditationCourseModel.Webinar>>() {}.getType());
                            }

                            if (jsonData.isEmpty() || concatinatelist.size()>avaiavlelist.size()) {
                                Log.i("concatinatelist1",String.valueOf(concatinatelist.size()));
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("my_total_medicine", new Gson().toJson(concatinatelist));
                                editor.apply();
                            }


                            if (testViewModel.lstTotalDataM.size() > 0) {
                                loadAvailableAdapterM(testViewModel.lstTotalDataM, testViewModel.lstTotalDataM.size(),1);



                            }

                            mDisposable.add(meditationViewModel.insertMeditations(meditationEntities)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        Log.i(TAG, "Meditation insert => TRUE");
                                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                                        String formattedDate = dateFormatter.format(new Date());
                                        Log.i(TAG, "Meditation insert time => " + formattedDate);
                                        sharedPreference.write("MEDITATION_INSERTION_DATE_TIME", "", formattedDate);
                                    }, throwable -> {
                                        Log.i(TAG, "Meditation insert => FALSE");
                                    }));


                            ///////////////////

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            populateTags();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MeditationCourseModel> call, Throwable t) {
                    t.printStackTrace();
                    // progressDialog.dismiss();

                }
            });
            rlFilter.setVisibility(View.VISIBLE);
        } else {

        }

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");

    }

    private void populateDurationAccordingToTags() {
        arrDuration.clear();
        for (int i = 0; i < arrTagChoice.size(); i++) {
            for (int j = 0; j < testViewModel.lstTotalDataM.size(); j++) {
                if (testViewModel.lstTotalDataM.get(j).getTags().contains(arrTagChoice.get(i))) {
                    if (!arrDuration.contains(testViewModel.lstTotalDataM.get(j).getDuration())) {
                        arrDuration.add(testViewModel.lstTotalDataM.get(j).getDuration());
                    }
                }
            }
        }
        Collections.sort(arrDuration);
    }


    private void openFilterDialog(Boolean showDialog, Boolean clickOnClearAll) {
        arrTagChoice.clear();
        arrDurationChoice.clear();
        final Dialog dialog = new Dialog(requireContext(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_meditation_filter);
        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        LinearLayout dynamicTag = dialog.findViewById(R.id.dynamicTag);
        LinearLayout dynamicDuration = dialog.findViewById(R.id.dynamicDuration);
        LinearLayout dynamicLevel = dialog.findViewById(R.id.dynamicLevel);
        RelativeLayout rlShowResults = dialog.findViewById(R.id.rlShowResults);
        CheckBox imgFabChk = dialog.findViewById(R.id.imgFabChk);
        RelativeLayout rlClearAll = dialog.findViewById(R.id.rlClearAll);
        EditText edtSearch = dialog.findViewById(R.id.edtSearch);


        ArrayList<CheckBox> arrTagCheckBox = new ArrayList<>();
        ArrayList<CheckBox> arrDurationBox = new ArrayList<>();

        String jsonString = testViewModel.arrJson.toString();
        System.out.println("JSON String: " + jsonString);
        try{
            JSONArray outerArray = new JSONArray(jsonString);
            // Assuming testViewModel.arrJson is at index 0 of the outerArray
            JSONArray innerArray = outerArray.getJSONArray(0);

            for (int x = 0; x < innerArray.length(); x++) {
                try {
                    JSONObject innerJson = innerArray.getJSONObject(x);
                    String tagName = innerJson.getString("Description");
                    View v = getLayoutInflater().inflate(R.layout.layout_dynamic_filter_meditation, null);
                    CheckBox imgChk = v.findViewById(R.id.imgChk);
                    //  TextView txtTagName=v.findViewById(R.id.txtTagName);
                    imgChk.setText(tagName);
                    arrTagCheckBox.add(imgChk);
                    imgChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                if (!arrTagChoice.contains(imgChk.getText().toString()))
                                    arrTagChoice.add(imgChk.getText().toString());
                            } else {
                                for (int u = 0; u < arrTagChoice.size(); u++) {
                                    if (arrTagChoice.get(u).equals(imgChk.getText().toString()))
                                        arrTagChoice.remove(u);
                                }

                            }
                            //populateDurationAccordingToTags();
                            //populateDurationViewsAccordingToTags(dynamicDuration, arrDurationBox);
                        }
                    });


                    dynamicTag.addView(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }catch (Exception e){

        }

        for (int x = 0; x < arrDuration.size(); x++) {
            View v = getLayoutInflater().inflate(R.layout.layout_dynamic_filter_meditation, null);
            // TextView txtTagName=v.findViewById(R.id.txtTagName);

            CheckBox imgChk = v.findViewById(R.id.imgChk);
            imgChk.setText(arrDuration.get(x) + " Min");
            arrDurationBox.add(imgChk);
            imgChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String[] valSp = imgChk.getText().toString().split(" ");
                    int val = Integer.parseInt(valSp[0]);
                    if (isChecked) {
                        if (!arrDurationChoice.contains(val))
                            arrDurationChoice.add(val);
                    } else {
                        for (int u = 0; u < arrDurationChoice.size(); u++) {
                            if (arrDurationChoice.get(u) == val)
                                arrDurationChoice.remove(u);
                        }

                    }
                }
            });
            dynamicDuration.addView(v);

        }

        for (int x = 0; x < arrLevel.size(); x++) {
            View v = getLayoutInflater().inflate(R.layout.layout_dynamic_filter_meditation, null);

            CheckBox imgChk = v.findViewById(R.id.imgChk);
            imgChk.setText("Level " + arrLevel.get(x));
            arrDurationBox.add(imgChk);
            imgChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String valSp[] = imgChk.getText().toString().split(" ");
                    int val = Integer.parseInt(valSp[1]);
                    if (isChecked) {
                        if (!arrLevelChoice.contains(val))
                            arrLevelChoice.add(val);
                    } else {
                        for (int u = 0; u < arrLevelChoice.size(); u++) {
                            if (arrLevelChoice.get(u) == val)
                                arrLevelChoice.remove(u);
                        }

                    }
                }
            });
            dynamicLevel.addView(v);

        }

        for (int r = 0; r < arrTagChoice.size(); r++) {
            Log.e("print name--", arrTagChoice.get(r));
        }
        for (int x = 0; x < arrSelectedTag.size(); x++) {
            for (int d = 0; d < arrTagCheckBox.size(); d++) {
                if (arrTagCheckBox.get(d).getText().equals(arrSelectedTag.get(x))) {
                    arrTagCheckBox.get(d).setChecked(true);
                }
            }
        }
        for (int x = 0; x < arrSelectedDuration.size(); x++) {
            for (int d = 0; d < arrDurationBox.size(); d++) {
                if (arrDurationBox.get(d).getText().equals(arrSelectedDuration.get(x))) {
                    arrDurationBox.get(d).setChecked(true);
                }
            }
        }
        if (favChk)
            imgFabChk.setChecked(true);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        rlClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.getText().clear();
                arrSelectedDuration.clear();
                arrSelectedTag.clear();
                favChk = false;
                searchFlag = true;
                imgFilter.setBackgroundResource(R.drawable.mbhq_filter);
                rvCourseM.setAdapter(null);
                loadAvailableAdapterM(testViewModel.lstTotalDataM, testViewModel.lstTotalDataM.size(),1);
                dialog.dismiss();

            }
        });
        rlShowResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgFilter.setBackgroundResource(R.drawable.mbhq_filter_green);
                lstFilterTagData.clear();
                ////////////Tag Filter////
                for (int x = 0; x < arrTagChoice.size(); x++) {
                    for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                        for (int s = 0; s < testViewModel.lstTotalDataM.get(p).getTags().size(); s++) {
                            if (testViewModel.lstTotalDataM.get(p).getTags().get(s).equalsIgnoreCase(arrTagChoice.get(x))) {
                                Log.e("Tag select--", testViewModel.lstTotalDataM.get(p).getTags().get(s) + "?");
                                lstFilterTagData.add(testViewModel.lstTotalDataM.get(p));
                            }
                        }
                    }
                }
                /////////////////Duration Filter////////////
                if (arrTagChoice.size() == 0) {

                    for (int x = 0; x < arrDurationChoice.size(); x++) {
                        for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {

                            if (testViewModel.lstTotalDataM.get(p).getDuration() == arrDurationChoice.get(x)) {
                                Log.e("Duration select a--", testViewModel.lstTotalDataM.get(p).getDuration() + "?");
                                lstFilterTagData.add(testViewModel.lstTotalDataM.get(p));
                            }

                        }
                    }

                } else {
                    List<MeditationCourseModel.Webinar> lstSep = new ArrayList<>();
                    for (int x = 0; x < arrDurationChoice.size(); x++) {
                        for (int p = 0; p < lstFilterTagData.size(); p++) {

                            if (lstFilterTagData.get(p).getDuration() == arrDurationChoice.get(x)) {
                                Log.e("Duration select b--", testViewModel.lstTotalDataM.get(p).getDuration() + "?");
                                lstSep.add(lstFilterTagData.get(p));
                            }

                        }
                    }
                    if (lstSep.size() > 0) {
                        lstFilterTagData.clear();
                        lstFilterTagData.addAll(lstSep);
                    } else if (arrDurationChoice.size() != 0)
                        lstFilterTagData.clear();

                }
                ///////////Level Filter////////////
                if (arrLevelChoice.size() != 0) {
                    List<MeditationCourseModel.Webinar> removedWebinarList = new ArrayList<>();
                    for (int p = 0; p < lstFilterTagData.size(); p++) {
                        boolean doesLevelMatched = false;
                        for (int x = 0; x < arrLevelChoice.size(); x++) {
                            if (lstFilterTagData.get(p).getLevel().equals(arrLevelChoice.get(x))) {
                                Log.i(this.getClass().getSimpleName(), testViewModel.lstTotalDataM.get(p).getLevel() + " level");
                                doesLevelMatched = true;

                            }
                        }

                        if (!doesLevelMatched) {

                            removedWebinarList.add(lstFilterTagData.get(p));

                        }
                    }

                    lstFilterTagData.removeAll(removedWebinarList);
                }

                ///////////////////////////

                if (imgFabChk.isChecked()) {
                    if (arrTagChoice.size() == 0 && arrDurationChoice.size() == 0) {


                        for (int p = 0; p < testViewModel.lstTotalDataM.size(); p++) {
                            if(null==testViewModel.lstTotalDataM.get(p).getLikes()){

                            }else{
                                if (testViewModel.lstTotalDataM.get(p).getLikes()) {
                                    // Log.e("Duration select a--",testViewModel.lstTotalDataM.get(p).getDuration()+"?");
                                    lstFilterTagData.add(testViewModel.lstTotalDataM.get(p));
                                }
                            }


                        }


                    } else {
                        List<MeditationCourseModel.Webinar> lstSep = new ArrayList<>();
                        for (int s = 0; s < lstFilterTagData.size(); s++) {
                            if(null==lstFilterTagData.get(s).getLikes()){

                            }else{
                                if (lstFilterTagData.get(s).getLikes())
                                    lstSep.add(lstFilterTagData.get(s));
                            }

                        }
                        if (lstSep.size() > 0) {
                            lstFilterTagData.clear();
                            lstFilterTagData.addAll(lstSep);
                        } else {
                            lstFilterTagData.clear();
                        }
                    }

                }
                rvCourseM.setAdapter(null);

                if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                    Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagData);
                    Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                }else{
                    Util.withfilterlist_afterbackfrommeditationdetails.clear();
                    Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagData);
                    Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                }
                loadAvailableAdapterM(lstFilterTagData, lstFilterTagData.size(),1);
                dialog.dismiss();
                /*if (lstFilterTagData.size() > 0) {
                    loadAvailableAdapterM(lstFilterTagData, lstFilterTagData.size());
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    Util.showToast(getActivity(), "No data found");
                }*/
                //////////////////////
                arrSelectedTag.clear();
                for (int d = 0; d < arrTagCheckBox.size(); d++) {
                    if (arrTagCheckBox.get(d).isChecked()) {
                        arrSelectedTag.add(arrTagCheckBox.get(d).getText().toString());
                    }
                }
                arrSelectedDuration.clear();
                for (int d = 0; d < arrDurationBox.size(); d++) {
                    if (arrDurationBox.get(d).isChecked()) {
                        arrSelectedDuration.add(arrDurationBox.get(d).getText().toString());
                    }
                }
                if (imgFabChk.isChecked())
                    favChk = true;
                else
                    favChk = false;
            }
        });

        if (showDialog) {
            dialog.show();
        }

        if (clickOnClearAll) {
            edtSearch.getText().clear();
            arrSelectedDuration.clear();
            arrSelectedTag.clear();
            favChk = false;
            searchFlag = true;
            imgFilter.setBackgroundResource(R.drawable.mbhq_filter);
            dialog.dismiss();
        }

    }

    private void funSortdatabyTimeOutside(int time) {
        List<MeditationCourseModel.Webinar> lstDurationModel = new ArrayList<>();
        for (int x = 0; x < lstFilterTagDataBreath.size(); x++) {
            if (lstFilterTagDataBreath.get(x).getDuration() == time) {
                lstDurationModel.add(lstFilterTagDataBreath.get(x));


            }
        }
        hideAllChoiceContainers();
        rlMainBlock.setVisibility(View.VISIBLE);
        rvCourseM.setAdapter(null);
        if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
            Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstDurationModel);
            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
        }else{
            Util.withfilterlist_afterbackfrommeditationdetails.clear();
            Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstDurationModel);
            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
        }
        loadAvailableAdapterM(lstDurationModel, lstDurationModel.size(),1);
       /* if (lstDurationModel.size() > 0) {

            MeditationCourseAdapter meditationCourseAdapter = new MeditationCourseAdapter(getActivity(), lstDurationModel, lstDurationModel.size());
            rvCourseM.setAdapter(meditationCourseAdapter);
        } else
            Util.showToast(getActivity(), "No data found");*/
    }

    private void openFullscreenVideoDialog(String URL) {
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_meditation_video_info);
        ImageView imgCross = (ImageView) dialog.findViewById(R.id.imgCross);
        ProgressBar progress = (ProgressBar) dialog.findViewById(R.id.progress);
        VideoView videoView = (VideoView) dialog.findViewById(R.id.video_view);
        progress.setVisibility(View.VISIBLE);
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        videoView.setVideoURI(Uri.parse(URL));
        MediaController mediaController = new
                MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progress.setVisibility(View.GONE);
                videoView.seekTo(0);
                videoView.start();

            }
        });
        dialog.show();
    }

    private void sortPowerNapDataByTimeOutside(int time) {
        List<MeditationCourseModel.Webinar> lstDurationModel = new ArrayList<>();
        for (int x = 0; x < lstFilterTagDataPower.size(); x++) {
            if (lstFilterTagDataPower.get(x).getDuration() == time) {
                lstDurationModel.add(lstFilterTagDataPower.get(x));
            }
        }
        hideAllChoiceContainers();
        rlMainBlock.setVisibility(View.VISIBLE);
        rvCourseM.setAdapter(null);
        if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
            Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstDurationModel);
            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
        }else{
            Util.withfilterlist_afterbackfrommeditationdetails.clear();
            Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstDurationModel);
            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
        }
        loadAvailableAdapterM(lstDurationModel, lstDurationModel.size(),1);
       /* if (lstDurationModel.size() > 0) {
            MeditationCourseAdapter meditationCourseAdapter = new MeditationCourseAdapter(getActivity(), lstDurationModel, lstDurationModel.size());
            rvCourseM.setAdapter(meditationCourseAdapter);
        } else
            Util.showToast(getActivity(), "No data found");*/
    }

    //for Power up
    private void sortPowerNapDataByTimeOutside() {
        List<MeditationCourseModel.Webinar> lstDurationModel = new ArrayList<>();
        for (int x = 0; x < lstFilterTagDataPower.size(); x++) {
            if (lstFilterTagDataPower.get(x).getDuration() != 20 && lstFilterTagDataPower.get(x).getDuration() != 90) {
                lstDurationModel.add(lstFilterTagDataPower.get(x));
            }
        }
        hideAllChoiceContainers();
        rlMainBlock.setVisibility(View.VISIBLE);
        rvCourseM.setAdapter(null);

        if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
            Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstDurationModel);
            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
        }else{
            Util.withfilterlist_afterbackfrommeditationdetails.clear();
            Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstDurationModel);
            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
        }
        loadAvailableAdapterM(lstDurationModel, lstDurationModel.size(),1);
    }

    private void sortHealingDataByTag(String tag) {
        if (tag.equalsIgnoreCase("Healing Meditations") || tag.equalsIgnoreCase("Visualisations") || tag.equalsIgnoreCase("Guided Meditation")) {

            arrSelectedTag.clear();
            arrSelectedTag.add(tag);
            arrTagChoice.add(tag);
            List<MeditationCourseModel.Webinar> lstTaggedModel = new ArrayList<>();
            for (int x = 0; x < lstFilterTagDataHealing.size(); x++) {
                for (int y = 0; y < lstFilterTagDataHealing.get(x).getTags().size(); y++) {
                    if (lstFilterTagDataHealing.get(x).getTags().get(y).equalsIgnoreCase(tag)) {
                        lstTaggedModel.add(lstFilterTagDataHealing.get(x));
                    }
                }
            }
            hideAllChoiceContainers();
            rlMainBlock.setVisibility(View.VISIBLE);
            rvCourseM.setAdapter(null);
            if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstTaggedModel);
                Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
            }else{
                Util.withfilterlist_afterbackfrommeditationdetails.clear();
                Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstTaggedModel);
                Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
            }

            loadAvailableAdapterM(lstTaggedModel, lstTaggedModel.size(),1);
            /*if (lstTaggedModel.size() > 0) {
                MeditationCourseAdapter meditationCourseAdapter = new MeditationCourseAdapter(getActivity(), lstTaggedModel, lstTaggedModel.size());
                rvCourseM.setAdapter(meditationCourseAdapter);
            } else
                Util.showToast(getActivity(), "No data found");*/

        }
    }

    private void hideAllChoiceContainers() {
        llLandingOptions.setVisibility(View.GONE);
        llTimeTagFilter.setVisibility(View.GONE);
        llNapTimeTagFilter.setVisibility(View.GONE);
        llHealingTagFilter.setVisibility(View.GONE);
        rlMainBlock.setVisibility(View.GONE);
    }
    private void populateTags() throws JSONException {
        if (Connection.checkConnection(getActivity())) {
            SharedPreference sharedPreference = new SharedPreference(getActivity());
            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            Log.e("userSession", "sessionid>>>" + sharedPreference.read("UserSessionID", ""));
            Log.e("key", "key>>>" + Util.KEY);
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<MeditationTagResponse> promptCall = finisherService.getTagList(hashReq);
            promptCall.enqueue(new Callback<MeditationTagResponse>() {
                @Override
                public void onResponse(Call<MeditationTagResponse> call, Response<MeditationTagResponse> response) {

                   /* if (response.isSuccessful()) {
                        testViewModel.arrJson = (JSONArray) response.body().getTags();

                        new SharedPreference(getActivity()).write("MEDITATION_TAGS", "", new Gson().toJson(response.body().getTags())
                        );

                        onGetEventTagsListSuccess();


                    }*/
                    if (response.isSuccessful()) {
                        try{
                            Log.e("Tag list>>>>>>>>>>>>","meditation tag>>>>>>>>>>"+response.body().getTags());
                            Gson gson = new Gson();

                            Type type = new TypeToken<ArrayList<Tag>>() {
                            }.getType();
                            tagArrayList = gson.fromJson(gson.toJson(response.body().getTags()), type);
                            Log.e("tag array list",""+tagArrayList);

                            JSONArray jsonArray = new JSONArray();
                            for (Tag obj : tagArrayList) {
                                JSONObject jsonObject = new JSONObject();
                                // Assuming MyObject has appropriate fields like 'name', 'age', etc.
                                jsonObject.put("EventTagID", obj.getEventTagID());
                                jsonObject.put("Description", obj.getDescription());
                                jsonObject.put("TotalEvents", obj.getDescription());
                                // Add other fields accordingly

                                jsonArray.put(jsonObject);

                            }
                            testViewModel.arrJson.put(jsonArray);
                            new SharedPreference(getActivity()).write("MEDITATION_TAGS", "", new Gson().toJson(tagArrayList));
                            onGetEventTagsListSuccess();
                            // onGetEventTagsListSuccess();

                        }catch(Exception e){
                            e.printStackTrace();
                        }


                    }else {
                        Log.e("error prompt", "errrrrrrrrrrrrrrrrrrrrrr");
                    }

                }

                @Override
                public void onFailure(Call<MeditationTagResponse> call, Throwable t) {
                    Log.e("error prompt", "errrrrrrrrrrrrrrrrrrrrrr");
                }
            });
        }else {
            Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG).show();

//            String strTagsjson = new SharedPreference(getActivity()).read("MEDITATION_TAGS", "");/*sahenita*/
               /* llLandingOptions.setVisibility(View.GONE);
                llTimeTagFilter.setVisibility(View.GONE);
                llNapTimeTagFilter.setVisibility(View.GONE);
                rlMainBlock.setVisibility(View.VISIBLE);*/
            try {

//                JSONObject jsonObject = new JSONObject(strTagsjson);/*sahenita*/
//                testViewModel.arrJson = jsonObject.getJSONArray("Tags");/*sahenita*/
                String json= sharedPreference.read("MEDITATION_TAGS", "");
                ArrayList<Tag> habitSwapList_ = new Gson().fromJson(json, new TypeToken<ArrayList<Tag>>(){}.getType());
                if(null!=habitSwapList_ ){
                    if(habitSwapList_.size()>0){

                        JSONArray jsonArray = new JSONArray();
                        for (Tag obj : habitSwapList_) {
                            JSONObject jsonObject = new JSONObject();
                            // Assuming MyObject has appropriate fields like 'name', 'age', etc.
                            jsonObject.put("EventTagID", obj.getEventTagID());
                            jsonObject.put("Description", obj.getDescription());
                            jsonObject.put("TotalEvents", obj.getDescription());
                            // Add other fields accordingly

                            jsonArray.put(jsonObject);

                        }
                        testViewModel.arrJson.put(jsonArray);
                    }
                }
                String userPress = sharedPreference.read("med", "");
                String userPressT = sharedPreference.read("medT", "");
                Log.e("print user option--", userPress + "??");
                int medChoice = -1;
                int medChoiceT = -1;
                if (!userPress.equals(""))
                    medChoice = Integer.parseInt(sharedPreference.read("med", ""));
                if (!userPressT.equals(""))
                    medChoiceT = Integer.parseInt(sharedPreference.read("medT", ""));
                Log.e("print med option--", medChoice + "??");

                openFilterDialog(false, true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
//    private void populateTags() {
//        try {
//            if (Connection.checkConnection(requireContext())) {
//                SharedPreference sharedPreference = new SharedPreference(getContext());
//                JSONObject requestJson = new JSONObject();
//                try {
//                    requestJson.put("Key", Util.KEY);
//                    requestJson.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                genericAsynTaskTAG = new GenericAsynTask("Please wait...", getActivity(), Util.GETEVENTTAGSLIST, "POST", requestJson, "", "");
//                genericAsynTaskTAG.progressTime = 2000;
//                genericAsynTaskTAG.setOnTaskListener(new GenericAsynTask.TaskListener() {
//                    @Override
//                    public void onSuccess(String success) {
//                        try {
//
//
//                            testViewModel.arrJson = jsonObject.getJSONArray("Tags");
//
//                            new SharedPreference(getActivity()).write("MEDITATION_TAGS", "", success);
//
//                            onGetEventTagsListSuccess();
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String error) {
//
//                    }
//                });
//
//            } else {
//                Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG).show();
//
//                String strTagsjson = new SharedPreference(getActivity()).read("MEDITATION_TAGS", "");
//               /* llLandingOptions.setVisibility(View.GONE);
//                llTimeTagFilter.setVisibility(View.GONE);
//                llNapTimeTagFilter.setVisibility(View.GONE);
//                rlMainBlock.setVisibility(View.VISIBLE);*/
//                try {
//                    JSONObject jsonObject = new JSONObject(strTagsjson);
//
//                    testViewModel.arrJson = jsonObject.getJSONArray("Tags");
//
//                    String userPress = sharedPreference.read("med", "");
//                    String userPressT = sharedPreference.read("medT", "");
//                    Log.e("print user option--", userPress + "??");
//                    int medChoice = -1;
//                    int medChoiceT = -1;
//                    if (!userPress.equals(""))
//                        medChoice = Integer.parseInt(sharedPreference.read("med", ""));
//                    if (!userPressT.equals(""))
//                        medChoiceT = Integer.parseInt(sharedPreference.read("medT", ""));
//                    Log.e("print med option--", medChoice + "??");
//
//                    openFilterDialog(false, true);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }//commented by jyoti

    private void onGetEventTagsListSuccess() {
        Log.i("medi_tation","14");
        for (int x = 0; x < testViewModel.lstTotalDataM.size(); x++) {
            if (!arrDuration.contains(testViewModel.lstTotalDataM.get(x).getDuration()) && testViewModel.lstTotalDataM.get(x).getDuration() != null)
                arrDuration.add(testViewModel.lstTotalDataM.get(x).getDuration());

            if (!arrLevel.contains(testViewModel.lstTotalDataM.get(x).getLevel()) && testViewModel.lstTotalDataM.get(x).getLevel() != null)
                arrLevel.add(testViewModel.lstTotalDataM.get(x).getLevel());
        }
        Log.i("medi_tation","15");
        ArrayList<Integer> levels = new ArrayList<Integer>();
        levels.add(1);
        levels.add(2);
        levels.add(3);
        levels.add(4);
        levels.add(5);
        levels.add(6);
        ArrayList<Integer> durations = new ArrayList<Integer>();
        durations.add(5);
        durations.add(10);
        durations.add(20);
        durations.add(30);
        durations.add(90);

        for (int x = 0; x < levels.size(); x++) {
            if (!arrLevel.contains(levels.get(x)))
                arrLevel.add(levels.get(x));
        }
        for (int x = 0; x < durations.size(); x++) {
            if (!arrDuration.contains(durations.get(x)))
                arrDuration.add(durations.get(x));
        }


        Collections.sort(arrLevel);
        Collections.sort(arrDuration);


        SharedPreference sharedPreference = new SharedPreference(requireContext());
        String userPress = sharedPreference.read("med", "");
        String userPressT = sharedPreference.read("medT", "");
        Log.e(TAG, "print user option :" + userPress + "??");
        int medChoice = -1;
        int medChoiceT = -1;
        if (!userPress.equals(""))
            medChoice = Integer.parseInt(sharedPreference.read("med", ""));
        if (!userPressT.equals(""))
            medChoiceT = Integer.parseInt(sharedPreference.read("medT", ""));
        Log.i(TAG, "print med option:" + medChoice);
        Log.i("medi_tation",String.valueOf(medChoice));
        Log.i("medi_tation",fromDetailsPage1);
        if (medChoice != -1 && "TRUE".equalsIgnoreCase(fromDetailsPage1)) {
            Log.i("medi_tation","yesssssssssss");
            rlMainBlock.setVisibility(View.VISIBLE);
            rvCourseM.setVisibility(View.VISIBLE);
            rlNoMeditationDownload.setVisibility(View.GONE);
            rvCourseM_main.setVisibility(View.GONE);
            rvCourseM_nointernate.setVisibility(View.GONE);
            if(!"".equalsIgnoreCase(Util.str_withfilterlist_afterbackfrommeditationdetails)){
                edtSearch.setText(Util.str_withfilterlist_afterbackfrommeditationdetails);
            }else{
                Log.i("medi_tation","Blank");
            }
            loadAvailableAdapterM(Util.withfilterlist_afterbackfrommeditationdetails, Util.withfilterlist_afterbackfrommeditationdetails.size(),0);
            Log.i("medi_tation",String.valueOf(Util.withfilterlist_afterbackfrommeditationdetails.size()));

            if (rvCourseM != null && rvCourseM.getAdapter() != null) {
                ((MeditationCourseAdapter) rvCourseM.getAdapter()).search(Util.str_withfilterlist_afterbackfrommeditationdetails.toString());

            }

        }else{

            if(!"TRUE".equalsIgnoreCase(fromDetailsPage1)){
                Log.i("medi_tation","no000000000000");
                hideAllChoiceContainers();
                rlMainBlock.setVisibility(View.GONE);
                llLandingOptions.setVisibility(View.VISIBLE);
            }


        }

        try{
            meditationFirstTime = sharedPreference.read("meditationFirstTime","");
            firstLoginTime = sharedPreference.read("FIRST_LOGIN_TIME", "");
            Log.e("FFLLLIIII", "onCreateView: " + firstLoginTime );

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Calendar calendar = Calendar.getInstance();
            currentTime = simpleDateFormat.format(calendar.getTime());
            Log.e("CURRRTIIII", "onCreateView: " + currentTime );

            Date dateFirstLoginDt = simpleDateFormat.parse(firstLoginTime);
            Date dateCurrentTime = simpleDateFormat.parse(currentTime);

            if (dateCurrentTime != null) {
                if(dateCurrentTime.after(dateFirstLoginDt) && "true".equals(meditationFirstTime)){
                    Log.e("CHHHHTIMMMMMM", "onCreateView: " + "TRUE" );
                    sharedPreference.write("meditationFirstTime", "", "false");

                    // show popup here
                    openMeditationDialog();

                }else {
                    Log.e("CHHHHTIMMMMMM", "onCreateView: " + "FALSE" );
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
/*commented by sahenita*/
/*
    private void openTagDialog() {
        SharedPreference sharedPreference = new SharedPreference(getActivity());
        try {

            final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
            dialog.setContentView(R.layout.dialog_common_filter_all);

            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);

            RelativeLayout rlCancel = (RelativeLayout) dialog.findViewById(R.id.rlCancel);
            LinearLayout llDynamic = (LinearLayout) dialog.findViewById(R.id.llDynamic);


            ///////////////////
            ArrayList<String> arrTag = new ArrayList<>();
            for (int i = 0; i < testViewModel.arrJson.length(); i++) {
                View view = ((MainActivity) getActivity()).getLayoutInflater().inflate(R.layout.dynamic_dialog_view, null);
                TextView txtDynamicName = (TextView) view.findViewById(R.id.txtDynamicName);
                TextView txtDynamicNumber = (TextView) view.findViewById(R.id.txtDynamicNumber);
                TextView txtDynamicFirstBracket = (TextView) view.findViewById(R.id.txtDynamicFirstBracket);
                TextView txtDynamicSecondBracket = (TextView) view.findViewById(R.id.txtDynamicSecondBracket);
                RelativeLayout rlDynamic = (RelativeLayout) view.findViewById(R.id.rlDynamic);
                ImageView imgTick = (ImageView) view.findViewById(R.id.imgTick);
                //JSONObject innerJson=testViewModel.arrJson.getJSONObject(i);
                // final  String selectedEventTypeId=innerJson.getString("EventID");
                JSONObject innerJson = testViewModel.arrJson.getJSONObject(i);
                final String selectedTagId = innerJson.getString("EventTagID");
                txtDynamicName.setText(innerJson.getString("Description"));
                txtDynamicNumber.setText(innerJson.getString("TotalEvents"));
                arrTag.add(innerJson.getString("Description"));

                view.setTag(i);
                //txtDynamicNumber.setText(innerJson.getString("TotalEvents"));
                //if(sharedPreference.read("FILTERTYPE","").equals("TAGS"))
                {
                    if (!sharedPreference.read("INNERFILTERPOS", "").equals("") && Integer.parseInt(sharedPreference.read("INNERFILTERPOS", "")) <= testViewModel.arrJson.length()) {
                        if (i == Integer.parseInt(sharedPreference.read("INNERFILTERPOS", ""))) {
                            //rlDynamic.setBackgroundResource(R.drawable.drawable_blue_rectangle);
                            imgTick.setBackgroundResource(R.drawable.active_check);
                                        */
/*txtDynamicName.setTextColor(Color.parseColor("#FFFFFF"));
                                        txtDynamicNumber.setTextColor(Color.parseColor("#FFFFFF"));
                                        txtDynamicFirstBracket.setTextColor(Color.parseColor("#FFFFFF"));
                                        txtDynamicSecondBracket.setTextColor(Color.parseColor("#FFFFFF"));*//*

                        } else {
                            //rlDynamic.setBackgroundResource(R.drawable.drawable_rectangular);
                            // imgTick.setBackgroundResource(R.drawable.inactive_check);
                                        */
/*txtDynamicName.setTextColor(Color.parseColor("#7DC8DF"));
                                        txtDynamicNumber.setTextColor(Color.parseColor("#7DC8DF"));
                                        txtDynamicFirstBracket.setTextColor(Color.parseColor("#7DC8DF"));
                                        txtDynamicSecondBracket.setTextColor(Color.parseColor("#7DC8DF"));*//*

                        }
                    }
                }*/
/*else
                            {
                                //rlDynamic.setBackgroundResource(R.drawable.drawable_rectangular);
                                imgTick.setBackgroundResource(R.drawable.inactive_check);
                                *//*
*/
/*txtDynamicName.setTextColor(Color.parseColor("#7DC8DF"));
                                txtDynamicNumber.setTextColor(Color.parseColor("#7DC8DF"));
                                txtDynamicFirstBracket.setTextColor(Color.parseColor("#7DC8DF"));
                                txtDynamicSecondBracket.setTextColor(Color.parseColor("#7DC8DF"));*//*
*/
/*
                            }*//*

                final String position = i + "";
                int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharedPreference.write("INNERFILTERPOS", "", position);
                        // for(int x=0;x<arrTag.size();x++)
                        lstFilterTagData.clear();
                        int tag = (Integer) view.getTag();


                        String matchTag = arrTag.get(tag);

                        for (int y = 0; y < testViewModel.lstTotalDataM.size(); y++) {
                            for (int z = 0; z < testViewModel.lstTotalDataM.get(y).getTags().size(); z++) {
                                Log.e("incoming tag--", testViewModel.lstTotalDataM.get(y).getTags().get(z) + "??");
                                if (matchTag.equals(testViewModel.lstTotalDataM.get(y).getTags().get(z))) {
                                    Log.e("match tag--", matchTag + "??");
                                    lstFilterTagData.add(testViewModel.lstTotalDataM.get(y));
                                    // break;
                                }
                            }
                        }
                        if(0==Util.withfilterlist_afterbackfrommeditationdetails.size()){
                            Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagData);
                            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                        }else{
                            Util.withfilterlist_afterbackfrommeditationdetails.clear();
                            Util.withfilterlist_afterbackfrommeditationdetails.addAll(lstFilterTagData);
                            Util.str_withfilterlist_afterbackfrommeditationdetails=edtSearch.getText().toString().trim();
                        }
                        loadAvailableAdapterM(lstFilterTagData, lstFilterTagData.size(),1);
                        */
/*if (lstFilterTagData.size() > 0) {
                            rvCourseM.setAdapter(null);

                        } else {
                            Util.showToast(getActivity(), "No data found");
                        }*//*



                        dialog.dismiss();

                    }
                });

                llDynamic.addView(view);
            }

            rlCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    private void checkMeditationCacheExpiration() {

        if (Connection.checkConnection(getContext())) {

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getContext());
            Call<GetMeditationCacheExpiryTimeResponse> serverCall = finisherService.GetMeditationCacheExpiryTime(hashReq);
            serverCall.enqueue(new Callback<GetMeditationCacheExpiryTimeResponse>() {
                @Override
                public void onResponse(Call<GetMeditationCacheExpiryTimeResponse> call, Response<GetMeditationCacheExpiryTimeResponse> response) {
                    //progressDialog.dismiss();
                    GetMeditationCacheExpiryTimeResponse responseBody = response.body();
                    if (responseBody != null && responseBody.getSuccessFlag()) {
                        try {
                            sharedPreference.write("MEDITATION_EXPIRATION_DATE_TIME", "", responseBody.getExpiryDateTime());
                            ///////////////////
                            // getGuidedMeditationList();
                            if(!"TRUE".equalsIgnoreCase(fromDetailsPage1)){
                                Log.i("medi_tation","no000000000000");
                                hideAllChoiceContainers();
                                rlMainBlock.setVisibility(View.GONE);
                                llLandingOptions.setVisibility(View.VISIBLE);
                            }
                            getMeditationsFromDB();/*sahenita*/
//                             getMeditation();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetMeditationCacheExpiryTimeResponse> call, Throwable t) {
                    //progressDialog.dismiss();
                    t.printStackTrace();
                    // getMeditationsFromDB();
                }
            });

        }

    }

    private void getMeditationsFromDB() throws JSONException {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String meditationExpirationTime = sharedPreference.read("MEDITATION_EXPIRATION_DATE_TIME", "");
        String meditationInsertionTime = sharedPreference.read("MEDITATION_INSERTION_DATE_TIME", "");

        Log.i(TAG, "MEDITATION_EXPIRATION_DATE_TIME => " + meditationExpirationTime);
        Log.i(TAG, "MEDITATION_INSERTION_DATE_TIME => " + meditationInsertionTime);

        boolean shouldMeditationsRenew = true;//false;
        try {
            if (
                    !dateFormatter.parse(meditationExpirationTime).after(dateFormatter.parse(meditationInsertionTime))
            ) {
                shouldMeditationsRenew = false;//true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i("medi_tation","1");
//        if (Connection.checkConnection(requireContext()) && !shouldMeditationsRenew) {
        if (!shouldMeditationsRenew) {
            Log.i("medi_tation","2");

            if (!testViewModel.lstTotalDataM.isEmpty()) {
                getGuidedMeditationList();
                /*if(testViewModel.arrJson != null)*//*sahenita*/
                if (testViewModel.arrJson .length()!=0) {
                    Log.i("medi_tation","3");
                    onGetEventTagsListSuccess();
                } else {
                    Log.i("medi_tation","4");
                    populateTags();
                }
            } else {
                Log.i("medi_tation","5");
                mDisposable.add(
                        meditationViewModel.getAllMeditation()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        meditations -> {
                                            Log.i("medi_tation","6");
                                            rlNoMeditationDownload.setVisibility(View.GONE);
                                            Log.i(TAG, "Meditation from db size = " + meditations.size());
                                            if (meditations.size() == 0) {
                                                Log.i("medi_tation","7");
                                                getMeditation();
                                            } else {
                                                Log.i("medi_tation","8");
                                                Gson gson = new Gson();
                                                testViewModel.lstTotalDataM.clear();
                                                for (int i = 0; i < meditations.size(); i++) {
                                                    MeditationCourseModel.Webinar webinar = gson.fromJson(meditations.get(i).getMeditationDetails(), MeditationCourseModel.Webinar.class);
                                                    testViewModel.lstTotalDataM.add(webinar);
                                                    if(null==webinar.getLikes()){

                                                    }else{

                                                        sharedPreference.writeBoolean("HAS_USER_A_FAV_MEDITATION", "", true);

                                                    }

                                                }



                                                /*if(testViewModel.arrJson != null)*//*sahenita*/
                                                if (testViewModel.arrJson .length()!=0) {
                                                    Log.i("medi_tation","9");
                                                    onGetEventTagsListSuccess();
                                                } else {
                                                    Log.i("medi_tation","10");
                                                    populateTags();
                                                }

                                              //  populateTags();
                                            }
                                        },
                                        throwable -> {
                                            getMeditation();
                                        }
                                )
                );
            }

        } else {
            getMeditation();
        }
    }


    private void loadAvailableAdapterM(List<MeditationCourseModel.Webinar> lstData, int Size,int check) {

        Log.i("clickedmechoice1",String.valueOf(Util.withfilterlist_afterbackfrommeditationdetails.size()));
        Log.i("clickedmechoice1",Util.str_withfilterlist_afterbackfrommeditationdetails);


        if(1==check){
            Log.i("m_called","7");
            Log.i("withfilterlist1",String.valueOf(lstData.size()));
            if(0==withfilterlist.size()){
                Log.i("m_called","8");
                Util.withfilterlist.clear();
                Util.withfilterlist.addAll(lstData);
                withfilterlist.addAll(lstData);

            }else{
                Log.i("m_called","9");
                withfilterlist.clear();
                Util.withfilterlist.clear();
                Util.withfilterlist.addAll(lstData);
                Log.i("m_called","size:"+String.valueOf( Util.withfilterlist.size()));
                withfilterlist.addAll(lstData);
                Log.i("m_called","size:"+String.valueOf( withfilterlist.size()));
            }


            Log.i("withfilterlist2",String.valueOf(withfilterlist.size()));

            loadAvailableAdapterM(lstData, Size, false);

        }else{
            Util.withfilterlist.addAll(lstData);
            loadAvailableAdapterM(lstData, Size, false);

        }


    }

    private void loadAvailableAdapterM(List<MeditationCourseModel.Webinar> lstData, int Size, boolean isFavouriteSelected) {

        loadAvailableAdapterM(lstData, Size, isFavouriteSelected, false);

    }


    private void loadAvailableAdapterM(List<MeditationCourseModel.Webinar> lstData, int Size, boolean isFavouriteSelected, boolean isDownloadedMeditation) {

        if (Size > 0) {
            txtNoDataFound.setVisibility(View.GONE);
            clNoFavouriteFound.setVisibility(View.GONE);
            searchData = lstData;
            Log.i(TAG, "isDownloadedMeditation:" + isDownloadedMeditation);
            MeditationCourseAdapter meditationCourseAdapter = new MeditationCourseAdapter(getActivity(), lstData, lstData.size(), isDownloadedMeditation);
            meditationCourseAdapter.setMeditationLikeListener(new MeditationCourseAdapter.MeditationCourseLikeListener() {
                @Override
                public void toggleLike(MeditationCourseModel.Webinar course) {
                    Log.i(TAG, "toggle like");
                    mDisposable.add(meditationViewModel.getMeditationById(course.getEventItemID())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(meditation -> {
                                meditation.setMeditationDetails(new Gson().toJson(course));

                                mDisposable.add(meditationViewModel.updateMeditation(meditation)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                            Log.i(TAG, "Meditation insert => TRUE");
                                        }, throwable -> {
                                            Log.i(TAG, "Meditation insert => FALSE");
                                        }));

                            }, throwable -> {

                            }));
                }
            });

            if (isDownloadedMeditation) {
                meditationCourseAdapter.setMeditationListener(new MeditationCourseAdapter.MeditationCourseListener() {
                    @Override
                    public void deleteMeditation(MeditationCourseModel.Webinar course) {
                        Log.i(TAG, "delete meditation");
                        Log.i(TAG, "meditation id : " + course.getEventItemID());

                        MeditationEntity medEntity = new MeditationEntity(course.getEventItemID(), new Gson().toJson(course), false, false);

                        mDisposable.add(
                                meditationViewModel.updateMeditation(medEntity)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                () -> {
                                                    Log.i(TAG, "meditation delete TRUE");
                                                    try {
                                                        String fileName = course.getEventItemVideoDetails().get(0).getDownloadURL();
                                                        File file = new File(getActivity().getCacheDir(), "SQUAD/" + fileName);
                                                        if (file.exists()) {
                                                            Log.i(TAG, "file exists");
                                                            if (file.delete()) {
                                                                Log.i(TAG, "file deleted");
                                                            } else {
                                                                Log.i(TAG, "file not deleted");
                                                            }
                                                        } else {
                                                            Log.i(TAG, "file does not exists");
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                    Toast.makeText(getActivity(), "Data successfully deleted", Toast.LENGTH_SHORT).show();
                                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
                                                    ((MainActivity) getActivity()).loadFragment(new MeditationFragment(), "MeditationFragment", null);
                                                },
                                                throwable -> {
                                                    Log.i(TAG, "meditation delete false");
                                                    Toast.makeText(getActivity(), "Data successfully deleted", Toast.LENGTH_SHORT).show();
                                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
                                                    ((MainActivity) getActivity()).loadFragment(new MeditationFragment(), "MeditationFragment", null);
                                                }
                                        )
                        );

                    }
                });
            }
            rvCourseM.setAdapter(meditationCourseAdapter);
            if (!loading && previousCount > 0)
                rvCourseM.scrollToPosition(lstData.size() - 1);
        } else {
            rvCourseM.setAdapter(null);
            if (isFavouriteSelected) {
                txtNoDataFound.setVisibility(View.GONE);
                clNoFavouriteFound.setVisibility(View.VISIBLE);
            } else {
                clNoFavouriteFound.setVisibility(View.GONE);
                txtNoDataFound.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
/*if("3".equalsIgnoreCase(accesstype)){
    if("false".equalsIgnoreCase(medi_access)){

    }else{
        ((MainActivity) getActivity()).funDrawer1();
        Log.i(TAG, "onResume");
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            Log.i("medi_tation", "calledshared");


        }else{
            // getMeditationsFromDB(); ///

            Log.i("medi_tation", "not calledshared");
            rlNoMeditationDownload.setVisibility(View.GONE);
            rvCourseM_nointernate.setVisibility(View.VISIBLE);
            checkMeditationCacheExpiration(); ////

            //  getGuidedMeditationList(); ///
        }

        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
    }
}else{*/
        ((MainActivity) getActivity()).funDrawer1();
        Log.i(TAG, "onResume");
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            Log.i("medi_tation", "calledshared");


        }else{
            // getMeditationsFromDB(); ///

            Log.i("medi_tation", "not calledshared");
            rlNoMeditationDownload.setVisibility(View.GONE);
            rvCourseM_nointernate.setVisibility(View.VISIBLE);
            checkMeditationCacheExpiration(); ////

            //  getGuidedMeditationList(); ///
        }

        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
//}



    }

    private void openMeditationDialog() {
        Dialog dialog = new Dialog(requireContext(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_meditation);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);
        Button btnFindMyLevel = dialog.findViewById(R.id.btnFindMyLevel);
        Button btnMeditateNow = dialog.findViewById(R.id.btnMeditateNow);
        LinearLayout btnDontShow = dialog.findViewById(R.id.btnDontShow);
        TextView tvDontShow = dialog.findViewById(R.id.tvDontShow);
        CheckBoxOswaldRegular ivCheck = dialog.findViewById(R.id.ivCheck);

        tvDontShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivCheck.isChecked())
                    sharedPreference.writeBoolean("HAS_MEDITATION_DIALOG_SHOWN", "", true);
                dialog.dismiss();
            }
        });

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        btnFindMyLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SharedPreference sharedPreference = new SharedPreference(getContext());
                String URL = "https://meditate.emotionalfitnessclub.com/members?token=" + sharedPreference.read("MEDITATION_TEST_NOW_TOKEN", "");
                Uri uri = Uri.parse(URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.android.chrome");
                requireActivity().startActivity(intent);
            }
        });
        btnMeditateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((MainActivity) getActivity()).rlMeditation.performClick();
            }
        });
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();


        Log.i(TAG, "onPause");

    }


    @Override
    public void onStop() {
        super.onStop();
/*if("3".equalsIgnoreCase(accesstype)){
    if("false".equalsIgnoreCase(medi_access)){

    }else{

        testViewModel.lstTotalDataM.clear();
        rlMainBlock.setVisibility(View.GONE);
        llLandingOptions.setVisibility(View.GONE);
        fromDetailsPage = false;

        Log.i(TAG, "onStop");
        mDisposable.clear();
        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
    }
}else{*/

        testViewModel.lstTotalDataM.clear();
        rlMainBlock.setVisibility(View.GONE);
        llLandingOptions.setVisibility(View.GONE);
        fromDetailsPage = false;

        Log.i(TAG, "onStop");
        mDisposable.clear();
        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
//}



    }

    private void getGuidedMeditationList() {

        if (Connection.checkConnection(requireContext())) {

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("UserId", sharedPreference.read("UserID", ""));
            hashMap.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashMap.put("Key", Util.KEY);

            Call<MeditationCourseModel> readUnreadResponseCall = finisherService.getGuidedMeditationsBySearchTag(hashMap);
            readUnreadResponseCall.enqueue(new Callback<MeditationCourseModel>() {
                @Override
                public void onResponse(Call<MeditationCourseModel> call, Response<MeditationCourseModel> response) {

                    //Util.showDialog(getContext(),"Alert",response.body().getCount().toString(),false);
                    if (response.body() != null) {
                        sharedPreference.write("GUIDED_MEDITATION_LIST", "", new Gson().toJson(response.body()));
                    }

                }

                @Override
                public void onFailure(Call<MeditationCourseModel> call, Throwable t) {
                    t.printStackTrace();

                }
            });

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

      /*  if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(medi_access)){

            }else{
                getActivity().unregisterReceiver(networkChangeReceiver3);

            }
        }else{*/
        getActivity().unregisterReceiver(networkChangeReceiver3);

//        }

    }


    private BroadcastReceiver networkChangeReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {
                Util.refresh_meditation="no";

                if(0==lstTotalDataM_.size()){
                    SharedPreferences preferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                    String jsonData = preferences.getString("my_downloaded_medicine", "");

                    if (jsonData.isEmpty()) {
                        backto="";
                        rlNoMeditationDownload.setVisibility(View.VISIBLE);
                        rlMainBlock.setVisibility(View.GONE);
                    } else {
        /*if(0==lstTotalDataM_.size()){

        }else{
            lstTotalDataM_.clear();
        }*/
                        testViewModel.lstTotalDataM.clear();
                        lstTotalDataM_ = new Gson().fromJson(jsonData, new TypeToken<List<MeditationCourseModel.Webinar>>() {}.getType());
                        rlMainBlock.setVisibility(View.VISIBLE);
                        llLandingOptions.setVisibility(View.GONE);
                        llTimeTagFilter.setVisibility(View.GONE);
                        llNapTimeTagFilter.setVisibility(View.GONE);
                        llHealingTagFilter.setVisibility(View.GONE);
                        rlNoMeditationDownload.setVisibility(View.GONE);
                        rvCourseM.setVisibility(View.VISIBLE);
                        rvCourseM_nointernate.setVisibility(View.GONE);
                        loadAvailableAdapterM(lstTotalDataM_, lstTotalDataM_.size(),1);
                    }
                }





            }else{
                lstTotalDataM_.clear();
                if(Util.refresh_meditation.equalsIgnoreCase("no")){
                    Log.i(TAG, "Internate");
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
                    Util.refresh_meditation="yes";
                    Util.refresh_gratitude="yes";
                    ((MainActivity) getActivity()).funMeditation();
                }


            }
        }
    };

}
