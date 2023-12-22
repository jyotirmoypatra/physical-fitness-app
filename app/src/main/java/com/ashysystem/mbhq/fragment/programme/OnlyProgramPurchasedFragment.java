package com.ashysystem.mbhq.fragment.programme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.adapter.OnlyProgramPurchasedAdapter;
import com.ashysystem.mbhq.fragment.course.CourseFragment;
import com.ashysystem.mbhq.model.AvailableCourseModel;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class OnlyProgramPurchasedFragment extends Fragment {

    View globalView;

    RecyclerView purchasedProgramRV;

    TextView restrictionBaitTxt, greetUserTxt, emailTxt;

    ImageView imgCross;

    Button updateNowBtn;

    List<AvailableCourseModel.Course> purchasedCourses = new ArrayList<>();

    String gratitudeBaitString = "To use the gratitude and growth journals please update your membership here.";
    String meditationBaitString = "To calm you mind and focus your energy please update your membership here.";
    String habitBaitString = "To use the habit tracker and bucket list please update your membership here.";
    String forumBaitString = "To connect with our community of mindful, conscious and health focused members please update your membership here.";
    String memberOnlyBaitString = "To use member only program please update your membership here.";

    String forPage = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("FOR_PAGE")) {
            forPage = getArguments().getString("FOR_PAGE");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (globalView == null) {
            globalView = inflater.inflate(R.layout.fragment_only_program_purchased, null);

            initView();

            return globalView;
        } else {
            return globalView;
        }
    }

    private void initView() {

        purchasedProgramRV = globalView.findViewById(R.id.purchasedProgramRV);
        restrictionBaitTxt = globalView.findViewById(R.id.restrictionBaitTxt);
        greetUserTxt = globalView.findViewById(R.id.greetUserTxt);
        emailTxt = globalView.findViewById(R.id.emailTxt);
        imgCross = globalView.findViewById(R.id.imgCross);
        updateNowBtn = globalView.findViewById(R.id.updateNowBtn);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).loadFragment(new CourseFragment(), "Course", null);
            }
        });

        updateNowBtn.setOnClickListener(view -> {
            SharedPreference sharedPreference = new SharedPreference(getActivity());
            String URL = Util.SERVERURL +  "home/MbhqRedirect?mode=signup&userId=" + sharedPreference.read("UserID","");
            //String URL = "https://mindbodyhq.com/collections/memberships";
            Log.e("PURCHASE_URL",URL + ">>>>>>>");
            Uri uri = Uri.parse(URL); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.android.chrome");
            getActivity().startActivity(intent);



        });

        purchasedProgramRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        SharedPreference sharedPreference = new SharedPreference(getActivity());

        greetUserTxt.setText("HI " + sharedPreference.read("firstName", "").toUpperCase() + "!");
        emailTxt.setText("Your Email is: " + sharedPreference.read("USEREMAIL", ""));

        if (forPage.equals("GRATITUDE")) {
            restrictionBaitTxt.setText(gratitudeBaitString);
        } else if (forPage.equals("MEDITATION")) {
            restrictionBaitTxt.setText(meditationBaitString);
        } else if (forPage.equals("HABIT")) {
            restrictionBaitTxt.setText(habitBaitString);
        } else if(forPage.equals("FORUM")){
            restrictionBaitTxt.setText(forumBaitString);
        }else {
            restrictionBaitTxt.setText(memberOnlyBaitString);
        }

        try {
            Gson gson = new Gson();

            AvailableCourseModel availableCourseModel = gson.fromJson(sharedPreference.read("AVAILABLE_COURSE_MODEL", ""), AvailableCourseModel.class);

            List<AvailableCourseModel.Course> courses = availableCourseModel.getCourses();

            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getHasSubscribed()) {
                    purchasedCourses.add(courses.get(i));
                    Log.i("OnlyProgramPurchased", courses.get(i).getCourseName());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        purchasedProgramRV.setAdapter(new OnlyProgramPurchasedAdapter(purchasedCourses));


    }
}
