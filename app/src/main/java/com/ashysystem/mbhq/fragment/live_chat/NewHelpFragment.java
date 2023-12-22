package com.ashysystem.mbhq.fragment.live_chat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.activity.WebViewActivity;
import com.ashysystem.mbhq.adapter.ImageSlideShowAdapter;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddFirstPage;
import com.ashysystem.mbhq.fragment.meditation.MeditationFragment;
import com.ashysystem.mbhq.util.SharedPreference;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;


public class NewHelpFragment extends Fragment {

    View mainView;
    TabLayout tabs;

    NestedScrollView walkthroughContent;

    ConstraintLayout walthroughFragmentContainer, content;
    WebView customWebview;

    Button mbhqOverViewBtn,eqjournal, gratitudeBtn, growthBtn, meditationBtn, habitsBtn, programBtn, testsBtn, forumBtn, bucketListBtn, aeroplaneModeBtn,meditation2Btn;

    int selectedTabPosition = 0;

    ProgressDialog prDialog;

    private String pdf = "https://squad-live.s3-ap-southeast-2.amazonaws.com/MbHQ+Happiness+Habit/Mbhq_help.pdf";

    private ArrayList<Integer> forumImages = new ArrayList<>();
    private ArrayList<Integer> overviewImages = new ArrayList<>();

    public static NewHelpFragment newInstance(int selectedTabPosition) {
        NewHelpFragment fragment = new NewHelpFragment();
        fragment.selectedTabPosition = selectedTabPosition - 1;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_new_help, container, false);
        initView();
        forumImages.add(R.drawable.forum_11);
        forumImages.add(R.drawable.forum_2);

        overviewImages.add(R.drawable.welcome_overview1);
        overviewImages.add(R.drawable.img11);
        overviewImages.add(R.drawable.img21);
        overviewImages.add(R.drawable.img31);
        overviewImages.add(R.drawable.img41);
        overviewImages.add(R.drawable.img51);
        overviewImages.add(R.drawable.overview_slide61);
        overviewImages.add(R.drawable.img71);

        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(this.getClass().getSimpleName(), "on act created");

        Objects.requireNonNull(tabs.getTabAt(selectedTabPosition)).select();


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(this.getClass().getSimpleName(), "on resume");
        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.VISIBLE);
    }

    public void initView() {

        tabs = mainView.findViewById(R.id.tabs);
        walthroughFragmentContainer = mainView.findViewById(R.id.walthroughFragmentContainer);
        walkthroughContent = mainView.findViewById(R.id.walkthroughContent);
        content = mainView.findViewById(R.id.content);
        customWebview = mainView.findViewById(R.id.customWebview);
        eqjournal= mainView.findViewById(R.id.eqjournal);
        mbhqOverViewBtn = mainView.findViewById(R.id.mbhqOverViewBtn);
        gratitudeBtn = mainView.findViewById(R.id.gratitudeBtn);
        growthBtn = mainView.findViewById(R.id.growthBtn);
        meditationBtn = mainView.findViewById(R.id.meditationBtn);
        habitsBtn = mainView.findViewById(R.id.habitsBtn);
        programBtn = mainView.findViewById(R.id.programBtn);
        testsBtn = mainView.findViewById(R.id.testsBtn);
        forumBtn = mainView.findViewById(R.id.forumBtn);
        bucketListBtn = mainView.findViewById(R.id.bucketListBtn);
        aeroplaneModeBtn = mainView.findViewById(R.id.aeroplaneModeBtn);
        meditation2Btn = mainView.findViewById(R.id.meditation2Btn);

        mbhqOverViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageSlideShowDialog(overviewImages);

            }
        });
        eqjournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMeditationTwoDialog_eq();
            }
        });
        gratitudeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // openGratitudeDialog();
                openMeditationTwoDialog_eq_felling();
                // openGratitudeDialog_eq();
            }
        });


        meditationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMeditationDialog();
            }
        });

        meditation2Btn.setOnClickListener(view -> {
            openMeditationTwoDialog();
        });

        habitsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHabitDialog();
            }
        });

        bucketListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBucketListDialog();
            }
        });

        programBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProgramsDialog();
            }
        });

        testsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTestsDialog();
            }
        });

        forumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/groups/250625228700325/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                //openImageSlideShowDialog(forumImages);
            }
        });

        aeroplaneModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAeroplaneModeDialog();
            }
        });


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Log.i("help_selected","0");
                        walkthroughContent.setVisibility(View.GONE);
                        walthroughFragmentContainer.setVisibility(View.GONE);
                        customWebview.setVisibility(View.VISIBLE);
                        customWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
                        customWebview.setWebViewClient(new MyWebViewClient());
                        customWebview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
                        Log.i("help_selected","0");
                        break;
                    case 1:
                        walkthroughContent.setVisibility(View.VISIBLE);
                        walthroughFragmentContainer.setVisibility(View.GONE);
                        customWebview.setVisibility(View.GONE);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Log.i(this.getClass().getSimpleName(), "on init view");

    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            try {
                prDialog = new ProgressDialog(getContext());
                prDialog.setMessage("Please wait ...");
                prDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (prDialog != null) {
                prDialog.dismiss();
            }
        }
    }

    private void openImageSlideShowDialog(@DrawableRes ArrayList<Integer> images) {

        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_overview_slideshow);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        ViewPager slidesPager = dialog.findViewById(R.id.slidesPager);

        slidesPager.setAdapter(new ImageSlideShowAdapter(images));

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
/*
    private void openGratitudeDialog_eq_new() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_growth_eq_new);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnGotIt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }
*/

/*
    private void openGratitudeDialog_eq() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_growth_eq);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnGotIt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }
*/

/*
    private void openGratitudeDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_gratitude);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnGotIt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }
*/

/*
    private void openGrowthDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_growth);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnGotIt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }
*/

    private void openMeditationTwoDialog_eq_felling()
    {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_meditation_two2);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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


    private void openMeditationTwoDialog_eq()
    {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_meditation_two1);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

    private void openMeditationTwoDialog()
    {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_meditation_two);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

    private void openMeditationDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_meditation);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);
        Button btnFindMyLevel = dialog.findViewById(R.id.btnFindMyLevel);
        Button btnMeditateNow = dialog.findViewById(R.id.btnMeditateNow);
        LinearLayout btnDontShow = dialog.findViewById(R.id.btnDontShow);
        btnDontShow.setVisibility(View.GONE);

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnFindMyLevel.setOnClickListener(new View.OnClickListener(){
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
        btnMeditateNow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // ((MainActivity) getActivity()).rlMeditation.performClick();
                ((MainActivity) getActivity()).clearCacheForParticularFragment(new com.ashysystem.mbhq.fragment.meditation.MeditationFragment());
                com.ashysystem.mbhq.fragment.meditation.MeditationFragment meditationFragment = new MeditationFragment();
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

    private void openHabitDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_habbit);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        ImageView imgHabitAdd = dialog.findViewById(R.id.imgHabitAdd);
        RelativeLayout rlCreate = dialog.findViewById(R.id.rlCreate);
        RelativeLayout rlShowMeExamples = dialog.findViewById(R.id.rlShowMeExamples);
        RelativeLayout rlGotIt = dialog.findViewById(R.id.rlGotIt);

        rlShowMeExamples.setOnClickListener(view -> {
            dialog.dismiss();
        /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mindbodyhq.com/blogs/habit-hacking-inspo"));
        browserIntent.setPackage("com.android.chrome");
        startActivity(browserIntent);*/
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            String pdf = "https://squad-live.s3-ap-southeast-2.amazonaws.com/MbHQ+Happiness+Habit/Habit_change_examples.pdf";
            intent.putExtra("WEBVIEWURL", "http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
            startActivity(intent);
        });

        rlCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((MainActivity) getActivity()).loadFragment(new HabitHackerAddFirstPage(), "HabitHackerAddFirstPage", null);
            }
        });

        rlGotIt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openBucketListDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_bucket);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        RelativeLayout rlGotIt = dialog.findViewById(R.id.rlGotIt);
        rlGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

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

    private void openProgramsDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_programs);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

    private void openTestsDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_tests);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnGotIt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private void openAeroplaneModeDialog() {
        Dialog dialog = new Dialog(requireActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_aeroplane_mode);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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


}