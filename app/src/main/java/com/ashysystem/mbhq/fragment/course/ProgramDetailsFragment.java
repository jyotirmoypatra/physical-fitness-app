package com.ashysystem.mbhq.fragment.course;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.fragment.CommunityFragment;
import com.ashysystem.mbhq.fragment.programme.OnlyProgramPurchasedFragment;
import com.ashysystem.mbhq.model.AvailableCourseModel;
import com.ashysystem.mbhq.model.response.AddCourseResponseModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgramDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgramDetailsFragment extends Fragment {

    AvailableCourseModel.Course program;

    View vi;

    TextView programName, authorName, extraText, programDescription, programPrice, readMore, meetTheAuthorText;

    ImageView authorImage, coverImage;

    Button purchaseProgramBtn, startProgramBtn, viewProgramsBtn, joinMembershipBtn, backBtn;

    ConstraintLayout buyProgramContainer, liveSeminarDetails;

    SharedPreference sharedPreference;

    private String TAG = "ProgramDetailsFragment";

    public static ProgramDetailsFragment newInstance(AvailableCourseModel.Course program) {

        ProgramDetailsFragment fragment = new ProgramDetailsFragment();
        fragment.program = program;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("fragment load check","programe details fragment");

        if (vi == null) {
            vi = inflater.inflate(R.layout.fragment_program_details, container, false);
            sharedPreference = new SharedPreference(this.requireContext());
        } else {
            if (Util.boolBackGroundServiceRunningProgram && Util.bundleProgramDetailsForBackground != null) {
                ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseArticleDetailsNewFragment());
                ((MainActivity) getActivity()).loadFragment(new CourseArticleDetailsNewFragment(), "CourseArticleDetailsNew", Util.bundleProgramDetailsForBackground);
            }
        }
        return vi;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {

        coverImage = vi.findViewById(R.id.coverImage);

        authorImage = vi.findViewById(R.id.authorImage);

        authorName = vi.findViewById(R.id.authorName);

        extraText = vi.findViewById(R.id.extraText);

        meetTheAuthorText = vi.findViewById(R.id.meetTheAuthorText);

        programName = vi.findViewById(R.id.programName);

        programDescription = vi.findViewById(R.id.programDescription);

        programPrice = vi.findViewById(R.id.programPrice);

        purchaseProgramBtn = vi.findViewById(R.id.purchaseProgram);

        startProgramBtn = vi.findViewById(R.id.startProgram);

        joinMembershipBtn = vi.findViewById(R.id.joinMembership);

        viewProgramsBtn = vi.findViewById(R.id.viewProgramsBtn);

        buyProgramContainer = vi.findViewById(R.id.buyProgramContainer);

        readMore = vi.findViewById(R.id.readMore);

        backBtn = vi.findViewById(R.id.backBtn);

        liveSeminarDetails = vi.findViewById(R.id.liveSeminarDetails);

        ImageView liveWebinarLinkCopyImg = vi.findViewById(R.id.liveWebinarLinkCopyImg);
        ImageView passwordCopyImg = vi.findViewById(R.id.passwordCopyImg);
        ImageView forumLinkCopyImg = vi.findViewById(R.id.forumLinkCopyImg);

        /*ImageView mbhqForumLinkCopyImg = vi.findViewById(R.id.mbhqForumLinkCopyImg);
        ImageView fbForumLinkCopyImg = vi.findViewById(R.id.fbForumLinkCopyImg);*/
        TextView officialStartDateTimeTxt = vi.findViewById(R.id.officialStartDateTimeTxt);
        TextView weeklyWebinarTimingTxt = vi.findViewById(R.id.weeklyWebinarTimingTxt);
        TextView forumLinkStatic = vi.findViewById(R.id.forumLinkStatic);
        /*TextView mbhqForumLinkStatic = vi.findViewById(R.id.mbhqForumLinkStatic);
        TextView fbForumLinkStatic = vi.findViewById(R.id.fbForumLinkStatic);*/
        TextView passwordTxt = vi.findViewById(R.id.passwordTxt);
        TextView liveWebinarLinkTxt = vi.findViewById(R.id.liveWebinarLinkTxt);
        TextView forumLinkTxt = vi.findViewById(R.id.forumLinkTxt);
        /*Button mbhqForumLinkBtn = vi.findViewById(R.id.mbhqForumLinkBtn);
        Button fbForumLinkBtn = vi.findViewById(R.id.fbForumLinkBtn);*/
        ViewGroup webinarLinkContainer = vi.findViewById(R.id.webinarLinkContainer);

        Glide.with(getActivity()).load(program.getImageURL2())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.empty_image_old)
                .error(R.drawable.empty_image_old)
                .dontTransform()
                .dontAnimate()
                .into(coverImage);

        Glide.with(getActivity()).load(program.getAuthorImage())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.empty_image_old)
                .error(R.drawable.empty_image_old)
                .dontTransform()
                .dontAnimate()
                .into(authorImage);

        try {

            weeklyWebinarTimingTxt.setText("Every " + program.getLiveWebinarDay() + " at " + program.getLiveWebinarTime() + " AEST");
            passwordTxt.setText(program.getLiveWebinarPassword());

            SimpleDateFormat desiredDateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
            SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            forumLinkStatic.setText(program.getCourseName() + " Forum :");
            /*fbForumLinkStatic.setText(program.getCourseName()+" FB Forum :");
            mbhqForumLinkStatic.setText(program.getCourseName()+" MBHQ Forum :");*/

            officialStartDateTimeTxt.setText(desiredDateFormat.format(apiDateFormat.parse(program.getOfficialStartDate())));

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        authorName.setText(program.getAuthorName());

        meetTheAuthorText.setText("Meet " + program.getAuthorName());

        //extraText.setText(program.getAuthorDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            extraText.setText(Html.fromHtml(program.getAuthorDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            extraText.setText(Html.fromHtml(program.getAuthorDescription()));
        }

        programName.setText(program.getCourseName());

        Spanned courseInfo = Html.fromHtml(program.getCourseInfo());

        SpannableString readMoreString = new SpannableString(getString(R.string.click_to_read_more));
        readMoreString.setSpan(new UnderlineSpan(), 0, readMoreString.length(), 0);

        SpannableString readLessString = new SpannableString(getString(R.string.click_to_read_less));
        readLessString.setSpan(new UnderlineSpan(), 0, readLessString.length(), 0);

        SpannableString clickHereString = new SpannableString(getString(R.string.click_here));
        clickHereString.setSpan(new UnderlineSpan(), 0, clickHereString.length(), 0);

        forumLinkTxt.setText(clickHereString);
        liveWebinarLinkTxt.setText(clickHereString);

        programDescription.setText(courseInfo);

        readMore.setOnClickListener(view -> {

            if (isTextViewEllipsized(programDescription)) {

                programDescription.setMaxLines(Integer.MAX_VALUE);

                programDescription.setEllipsize(null);

                readMore.setText(readLessString);

            } else {

                programDescription.setMaxLines(4);

                programDescription.setEllipsize(TextUtils.TruncateAt.END);

                readMore.setText(readMoreString);

            }

        });

        programPrice.setText("$" + program.getPrice());

        purchaseProgramBtn.setOnClickListener(view -> {

            Uri purchaseUrlWithoutUserDetails = Uri.parse(program.getPurchaseUrl());
            Uri purchaseUrl = Uri.parse(program.getPurchaseUrl() + "?checkout[email]=" + sharedPreference.read("USEREMAIL", "") + "&checkout[shipping_address][first_name]=" + sharedPreference.read("firstName", "") + "&checkout[shipping_address][last_name]=" + sharedPreference.read("lastName", ""));
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, purchaseUrl);
                startActivity(browserIntent);
            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, purchaseUrlWithoutUserDetails);
                    startActivity(browserIntent);
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                }
            }

        });

        startProgramBtn.setOnClickListener(view -> {

            if (program.getTags().contains("Masterclass")) {

                makejson(program, program.getCourseType(), getTodayDate());

            } else {

                openAlertDialogForStart(program, program.getCourseType());

            }
        });

        joinMembershipBtn.setOnClickListener(view -> {
            ((MainActivity) requireActivity()).clearCacheForParticularFragment(new OnlyProgramPurchasedFragment());
            ((MainActivity) requireActivity()).loadFragment(new OnlyProgramPurchasedFragment(), "OnlyProgramPurchased", null);
        });

        viewProgramsBtn.setOnClickListener(view -> {

            switch (program.getStatus()) {

                case 1:

                case 2:

                    if (program.getTags().contains("Masterclass")) {

                        Bundle fromCourseBundle = new Bundle();
                        fromCourseBundle.putInt("COURSEID", program.getUserSquadCourseId());
                        fromCourseBundle.putInt("ARTICLEID", program.getFirstArticleId());
                        fromCourseBundle.putString("AUTHOR", program.getAuthorName());
                        fromCourseBundle.putString("type", program.getCourseType());
                        fromCourseBundle.putString("IMAGE_URL", program.getImageURL2());
                        fromCourseBundle.putString("origin", "CourseFragment");
                        Log.i(TAG, fromCourseBundle.toString());
                        Log.i(TAG, fromCourseBundle.getString("origin"));
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseArticleDetailsNewFragment());
                        ((MainActivity) getActivity()).loadFragment(new CourseArticleDetailsNewFragment(), "CourseArticleDetailsNew", fromCourseBundle);

                    } else {
                        ((MainActivity) requireActivity()).clearCacheForParticularFragment(new CourseDetailsFragment());

                        CourseDetailsFragment courseDetailsFragment = new CourseDetailsFragment();

                        Bundle bundle = getArguments().getBundle("BUNDLE_FOR_COURSE_DETAIL_FRAGMENT");

                        courseDetailsFragment.setArguments(bundle);

                        ((MainActivity) requireActivity()).loadFragment(courseDetailsFragment, "detail", null);
                    }

                    break;

                case 3:
                    AlertDialogCustom alertDialogCustom = new AlertDialogCustom(requireContext());
                    alertDialogCustom.ShowDialog("Alert!", "Do you want to enroll in this course again?", true);
                    alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                        @Override
                        public void onDone(String title) {
                            openAlertDialogForStart(program, program.getCourseType());
                        }

                        @Override
                        public void onCancel(String title) {

                        }
                    });
                    break;

                default:
                    break;
            }

        });

        if ((program.getSubscriptionType() == 0 || program.getSubscriptionType() == 1) && sharedPreference.read("PROGRAM_PURCHASE_ONLY", "").equalsIgnoreCase("TRUE")) {

            joinMembershipBtn.setVisibility(View.VISIBLE);

            buyProgramContainer.setVisibility(View.GONE);

            startProgramBtn.setVisibility(View.GONE);

            viewProgramsBtn.setVisibility(View.GONE);

            liveSeminarDetails.setVisibility(View.GONE);

            webinarLinkContainer.setVisibility(View.GONE);

        } else if (program.getHasSubscribed() || program.getSubscriptionType() == 0 || program.getSubscriptionType() == 1) {

            if (program.getIsLiveCourse()) {
                liveSeminarDetails.setVisibility(View.VISIBLE);
                webinarLinkContainer.setVisibility(View.VISIBLE);
            } else {
                liveSeminarDetails.setVisibility(View.GONE);
                webinarLinkContainer.setVisibility(View.GONE);
            }

            switch (program.getStatus()) {

                case 0:

                    buyProgramContainer.setVisibility(View.GONE);

                    startProgramBtn.setVisibility(View.VISIBLE);

                    viewProgramsBtn.setVisibility(View.GONE);

                    break;

                case 1:
                    viewProgramsBtn.setText("IN PROGRESS");

                    buyProgramContainer.setVisibility(View.GONE);

                    startProgramBtn.setVisibility(View.GONE);

                    viewProgramsBtn.setVisibility(View.VISIBLE);
                    break;

                case 2:
                    viewProgramsBtn.setText("PAUSED");

                    buyProgramContainer.setVisibility(View.GONE);

                    startProgramBtn.setVisibility(View.GONE);

                    viewProgramsBtn.setVisibility(View.VISIBLE);
                    break;

                case 3:
                    viewProgramsBtn.setText("COMPLETED");

                    buyProgramContainer.setVisibility(View.GONE);

                    startProgramBtn.setVisibility(View.GONE);

                    viewProgramsBtn.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }

            joinMembershipBtn.setVisibility(View.GONE);

        } else if (program.getIsLiveCourse()) {

            buyProgramContainer.setVisibility(View.VISIBLE);

            startProgramBtn.setVisibility(View.GONE);

            viewProgramsBtn.setVisibility(View.GONE);

            liveSeminarDetails.setVisibility(View.VISIBLE);

            webinarLinkContainer.setVisibility(View.GONE);

            joinMembershipBtn.setVisibility(View.GONE);

        } else if (program.getSubscriptionType() == 2) {

            buyProgramContainer.setVisibility(View.VISIBLE);

            startProgramBtn.setVisibility(View.GONE);

            viewProgramsBtn.setVisibility(View.GONE);

            liveSeminarDetails.setVisibility(View.GONE);

            webinarLinkContainer.setVisibility(View.GONE);

            joinMembershipBtn.setVisibility(View.GONE);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).loadFragment(new CourseFragment(), "Course", null);
            }
        });


        liveWebinarLinkCopyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Efc live webinar link", program.getLiveWebinarUrl());
                clipboard.setPrimaryClip(clip);
                Util.showToast(getContext(), "Link copied");
            }
        });

        passwordCopyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Efc live webinar password", program.getLiveWebinarPassword());
                clipboard.setPrimaryClip(clip);
                Util.showToast(getContext(), "Password copied");
            }
        });

        forumLinkCopyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("forum link", program.getForumUrl());

                if (!program.getOtherForumUrl().isEmpty()) {

                    clip = ClipData.newPlainText("forum link", program.getOtherForumUrl());

                } else if (!program.getFBForumUrl().isEmpty()) {

                    clip = ClipData.newPlainText("forum link", program.getFBForumUrl());

                } else if (!program.getMbhqForumUrl().isEmpty()) {

                    clip = ClipData.newPlainText("forum link", program.getMbhqForumUrl());

                } else {
                    clip = ClipData.newPlainText("forum link", program.getForumUrl());
                }

                clipboard.setPrimaryClip(clip);
                Util.showToast(getContext(), "Link copied");
            }
        });


        liveWebinarLinkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(program.getLiveWebinarUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.android.chrome");
                requireActivity().startActivity(intent);
            }
        });

        forumLinkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!program.getOtherForumUrl().isEmpty()) {

                    Uri uri = Uri.parse(program.getOtherForumUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.android.chrome");
                    requireActivity().startActivity(intent);

                } else if (!program.getFBForumUrl().isEmpty()) {

                    Uri uri = Uri.parse(program.getFBForumUrl());
                    try {
                        ApplicationInfo applicationInfo = requireActivity().getPackageManager().getApplicationInfo("com.facebook.katana", 0);
                        if (applicationInfo.enabled) {
                            uri = Uri.parse("fb://facewebmodal/f?href=" + uri);
                        }
                    } catch (PackageManager.NameNotFoundException ignored) {
                        ignored.printStackTrace();
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    requireActivity().startActivity(intent);

                } else if (!program.getMbhqForumUrl().isEmpty()) {

                    if (!sharedPreference.read("COMMUNITY_ACCESS_TOKEN", "").equals("")) {

                        Uri uri = Uri.parse(program.getMbhqForumUrl())
                                .buildUpon()
                                .appendQueryParameter("token", sharedPreference.read("COMMUNITY_ACCESS_TOKEN", ""))
                                .build();

                        ((MainActivity) requireActivity()).clearCacheForParticularFragment(new CommunityFragment());
                        Bundle bun = new Bundle();
                        bun.putString("COMMUNITY_EXTRA_URL", uri.toString());
                        ((MainActivity) requireActivity()).loadFragment(new CommunityFragment(), "CommunityFragment", bun);

                    } else {
                        Log.i("ProgramDetailsFragment", "img click");
                        ((MainActivity) requireActivity()).imgForum.performClick();
                    }

                } else {

                    Uri uri = Uri.parse(program.getForumUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.android.chrome");
                    requireActivity().startActivity(intent);
                }
            }
        });

        /*fbForumLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(program.getFBForumUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.android.chrome");
                requireActivity().startActivity(intent);
            }
        });

        mbhqForumLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(program.getMbhqForumUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.android.chrome");
                requireActivity().startActivity(intent);
            }
        });*/

        /*ViewTreeObserver vto = programDescription.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = programDescription.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
                if (isTextViewEllipsized(programDescription)) {
                    readMore.setVisibility(View.VISIBLE);
                } else {
                    readMore.setVisibility(View.GONE);
                }
            }
        });*/

    }

    private void openAlertDialogForStart(AvailableCourseModel.Course course, String courseType) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.DATE, 7);
        System.out.println("Date " + c.getTime());
        Log.e("date print--", c.getTime() + "??");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Dialog dialog = new Dialog(this.requireContext(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_reset_course);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        RelativeLayout rlNextWeek = dialog.findViewById(R.id.rlNextWeek);
        RelativeLayout rlToday = dialog.findViewById(R.id.rlToday);
        TextView txtNextWeekDate = dialog.findViewById(R.id.txtNextWeekDate);
        txtNextWeekDate.setText("Next Monday \n(" + simpleDateFormat.format(c.getTime()) + ")");
        rlToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                makejson(course, courseType, getTodayDate());
            }
        });
        rlNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                c.add(Calendar.DATE, 7);
                System.out.println("Date " + c.getTime());
                Log.e("date print--", c.getTime() + "??");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
                String cDate = simpleDateFormat.format(c.getTime());
                dialog.dismiss();
                makejson(course, courseType, cDate);
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

    private void makejson(AvailableCourseModel.Course course, String weekType, String cDate) {
        JSONObject rootJson = new JSONObject();
        JSONObject courseObj = new JSONObject();
        try {
            SharedPreference sharedPreference = new SharedPreference(this.requireContext());
            rootJson.put("UserID", sharedPreference.read("UserID", ""));
            rootJson.put("Key", Util.KEY);
            rootJson.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            courseObj.put("CourseType", course.getCourseType());
            courseObj.put("isAllArticleRead", course.getIsAllArticleRead());
            courseObj.put("CourseName", course.getCourseName());
            courseObj.put("UserSquadCourseId", course.getUserSquadCourseId());
            courseObj.put("isPeriodFinished", course.getIsPeriodFinished());
            courseObj.put("CourseStartDate", cDate);
            courseObj.put("IsEnroll", course.getIsEnroll());
            courseObj.put("CourseId", course.getCourseId());
            courseObj.put("WeekNumber", course.getWeekNumber());
            rootJson.put("CourseList", courseObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> queryHm = new HashMap<>();
        //queryHm=(HashMap<String, Object>)receiveBundle.getSerializable("data");
        try {
            queryHm = (HashMap<String, Object>) Util.jsonToMap(rootJson);
            addCourseApi(queryHm, course.getCourseName(), weekType, course.getCourseId(), course.getStatus());
            Log.e("print json", queryHm + "?" + rootJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addCourseApi(HashMap<String, Object> queryHm, final String name, final String weekType, int courseId, Integer status) {

        if (Connection.checkConnection(requireContext())) {
            final ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(requireContext());
            sharedPreference.writeBoolean("COURSE_LIST_SHOULD_RENEW", "", true);
            FinisherServiceImpl finisherService = new FinisherServiceImpl(requireContext());
            Call<AddCourseResponseModel> serverCall = finisherService.addCourse(queryHm);
            serverCall.enqueue(new Callback<AddCourseResponseModel>() {
                @Override
                public void onResponse(Call<AddCourseResponseModel> call, Response<AddCourseResponseModel> response) {
                    progressDialog.dismiss();
                    Log.e("success", "su");
                    if (response.body() != null) {
                        final AddCourseResponseModel resData = response.body();

                        if (resData != null) {
                            if (resData.getNewData() != null) {

                                if (program.getTags().contains("Masterclass")) {

                                    Bundle fromCourseBundle = new Bundle();
                                    fromCourseBundle.putInt("COURSEID", program.getUserSquadCourseId());
                                    fromCourseBundle.putInt("ARTICLEID", program.getFirstArticleId());
                                    fromCourseBundle.putString("AUTHOR", program.getAuthorName());
                                    fromCourseBundle.putString("type", program.getCourseType());
                                    fromCourseBundle.putString("IMAGE_URL", program.getImageURL2());
                                    fromCourseBundle.putString("origin", "CourseFragment");
                                    Log.i(TAG, fromCourseBundle.toString());
                                    Log.i(TAG, fromCourseBundle.getString("origin"));
                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseArticleDetailsNewFragment());
                                    ((MainActivity) getActivity()).loadFragment(new CourseArticleDetailsNewFragment(), "CourseArticleDetailsNew", fromCourseBundle);

                                } else {
                                    int newCourseId = resData.getNewData();
                                    CourseDetailsFragment courseDetailsFragment = new CourseDetailsFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("EnrollCourseId", newCourseId);
                                    bundle.putString("name", name);
                                    bundle.putString("type", weekType);
                                    bundle.putInt("plainCourseid", courseId);
                                    bundle.putString("IMAGE_URL", program.getImageURL2());
                                    bundle.putBoolean("isfirstTime", true);
                                    bundle.putInt("status", status);
                                    courseDetailsFragment.setArguments(bundle);
                                    ((MainActivity) requireActivity()).loadFragment(courseDetailsFragment, "detail", null);
                                }

                            } else
                                Util.showToast(requireContext(), "An error occurred");

                        } else
                            Util.showToast(requireContext(), "An error occurred");
                    }
                }

                @Override
                public void onFailure(Call<AddCourseResponseModel> call, Throwable t) {
                    Log.e("error", "er");
                    progressDialog.dismiss();

                }
            });
        } else {
            Util.showToast(requireContext(), Util.networkMsg);
        }
    }

    private String getTodayDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        Date today = new Date();
        return format.format(today);
    }

    private Boolean isTextViewEllipsized(TextView textView) {

        Layout layout = textView.getLayout();
        if (layout != null) {
            int lines = layout.getLineCount();

            if (lines > 0) {
                int ellipsisCount = layout.getEllipsisCount(lines - 1);
                return ellipsisCount > 0;
            }
        }
        return false;

    }
}