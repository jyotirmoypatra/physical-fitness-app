package com.ashysystem.mbhq.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.model.LoginRes.LoginResponse;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;


import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends Activity implements View.OnClickListener {
    private static final int TYPE_PP = 1;
    private static final int TYPE_TOS = 2;
    public static String APPSFLYER_ID = "";
    RelativeLayout rlLogIn;
    EditText edtEmailAddress, edtPassword;
    SharedPreference sharedPreference;
    Button btn_sign_up;
    String fitbitAccessToken = "";
    String ANDROID_ID = "";
    String deeplinkType = "";
    TextView tv_terms, txtForgotPassword;
    ImageView imgBack;
    TextView txtBack;
    String GARMIN_OAUTHVERIFIER = "";
    RelativeLayout rlSevenDayTrial, rlSignUpNow;
    private Context mContext = LogInActivity.this;
    private ProgressDialog progressDialog;
//    private static ArrayList<UserEqFolder> myModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen_new);
        ANDROID_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Util.refresh_gratitude="yes";
        initWidgets();
    }

    @SuppressLint("MissingPermission")
    private void initWidgets() {
        sharedPreference = new SharedPreference(LogInActivity.this);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");

        tv_terms = findViewById(R.id.tv_terms);
        tv_terms.setOnClickListener(this);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtForgotPassword.setOnClickListener(this);


        rlLogIn = findViewById(R.id.rlLogIn);
        edtEmailAddress = findViewById(R.id.edtEmailAddress);
        edtPassword = findViewById(R.id.edtPassword);

        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(this);

        rlLogIn.setOnClickListener(this);
        imgBack = findViewById(R.id.imgBack);
        txtBack = findViewById(R.id.txtBack);

        rlSevenDayTrial = findViewById(R.id.rlSevenDayTrial);
        rlSignUpNow = findViewById(R.id.rlSignUpNow);

        rlSignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent registerIntent = new Intent(mContext, RegisterActivity.class);
//                registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(registerIntent);
//                finish();
            }
        });


        SpannableString ss = new SpannableString("By continuing you accept our Privacy Policy and Terms of Service");
        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // do some thing
               showModal(TYPE_PP);
            }

            /**
             * Makes the text underlined and in the link color.
             *
             * @param ds
             */
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);
            }
        };

        ClickableSpan span2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // do another thing
               showModal(TYPE_TOS);
            }

            /**
             * Makes the text underlined and in the link color.
             *
             * @param ds
             */
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);
            }
        };

        ss.setSpan(span1, 29, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(span2, 48, 64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_terms.setText(ss);
        tv_terms.setMovementMethod(LinkMovementMethod.getInstance());

        if (getIntent() != null && getIntent().hasExtra("UserInfo")) {
            Bundle bundle = getIntent().getBundleExtra("UserInfo");
            edtEmailAddress.setText(bundle.getString("email"));
            edtPassword.setText(bundle.getString("password"));
            if (bundle.getBoolean("isFacebookUser", false) && bundle.containsKey("fbToken"))
                edtPassword.setTag(bundle.getString("fbToken"));
            rlLogIn.performClick();
        } else if ((!sharedPreference.read("ABBBCOnlineUserId", "").equals("") && sharedPreference.read("IsNonSubscribeUser", "") != null && sharedPreference.read("IsNonSubscribeUser", "").equals("false"))
                || (!sharedPreference.read("ABBBCOnlineUserId", "").equals("") && sharedPreference.read("IsNonSubscribeUser", "") == null)) {

            if (sharedPreference.readBoolean("loggedInFacebook", "")) {

            }
            edtEmailAddress.setText(sharedPreference.read("USEREMAIL", ""));
            edtPassword.setText(sharedPreference.read("USERPASSWORD", ""));
            rlLogIn.performClick();
        } else if (!sharedPreference.read("USEREMAIL", "").equals("") && !sharedPreference.read("USERPASSWORD", "").equals("") && sharedPreference.read("IsNonSubscribeUser", "").equals("false")) {
            edtEmailAddress.setText(sharedPreference.read("USEREMAIL", ""));
            edtPassword.setText(sharedPreference.read("USERPASSWORD", ""));
            rlLogIn.performClick();
        } else if (!sharedPreference.read("USEREMAIL", "").equals("")) {
            edtEmailAddress.setText(sharedPreference.read("USEREMAIL", ""));
        } else if (!sharedPreference.read("USEREMAIL", "").equals("") && !sharedPreference.read("USERPASSWORD", "").equals("") && getIntent() != null && getIntent().hasExtra("SETPROGRAM_LOGIN")) {
            edtEmailAddress.setText(sharedPreference.read("USEREMAIL", ""));
            edtPassword.setText(sharedPreference.read("USERPASSWORD", ""));
            rlLogIn.performClick();
        } else {
            Log.e("ELLLLLL", ">>>>>>>>>");
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent  i = new Intent(LogInActivity.this, SignUpActivity.class);
              startActivity(i);
                finish();
            }
        });

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == rlLogIn) {
            performLogin();
        }  else if (v == btn_sign_up) {
//            Intent signUpIntent = new Intent(mContext, SignUpActivity.class);
//            signUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(signUpIntent);
//            finish();
        } else if (v == txtForgotPassword) {
            openDialogForForgotPassword();
        }
    }


    private void openDialogForForgotPassword() {

        final Dialog dialog = new Dialog(LogInActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forgot_password);

        EditText edtForgotPassword = dialog.findViewById(R.id.edtForgotPassword);
        RelativeLayout rlCancel = dialog.findViewById(R.id.rlCancel);
        RelativeLayout rlSubmit = dialog.findViewById(R.id.rlSubmit);

        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        rlSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtForgotPassword.getText().toString().equals("")&& isValidEmail(edtForgotPassword.getText().toString().trim())) {
                    dialog.dismiss();
                    apiCallForForgotPassword(edtForgotPassword.getText().toString());
                } else {
                    Toast.makeText(LogInActivity.this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private boolean isValidEmail(String email) {
        // Using Android Patterns class to validate email
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void apiCallForForgotPassword(String emailId) {

        if (Connection.checkConnection(LogInActivity.this)) {
            final ProgressDialog progressDialog = ProgressDialog.show(LogInActivity.this, "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(LogInActivity.this);

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Email", emailId);

            FinisherServiceImpl finisherService = new FinisherServiceImpl(LogInActivity.this);
            Call<JsonObject> jsonObjectCall = finisherService.forgotPassword(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            Toast.makeText(LogInActivity.this, "Successfully Submited", Toast.LENGTH_SHORT).show();
                        } else {

                            Util.showDialog(LogInActivity.this, "Alert!", response.body().get("ErrorMessage").getAsString(), false);
                        }
                    } else {
                        Toast.makeText(LogInActivity.this, "Enter valid email address", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            showToast(LogInActivity.this, Util.networkMsg);
        }

    }



    private void performLogin() {

        if (Connection.checkConnection(this)) {


            if(!"".equalsIgnoreCase(edtPassword.getText().toString())){
                WeakReference<LogInActivity> activityReference = new WeakReference<>(this);

// ...

                if (activityReference.get() != null && !activityReference.get().isFinishing()) {
                    progressDialog.show();
                }
            /*if (!isFinishing()) {
                 progressDialog = ProgressDialog.show(this, "", "Please wait...");
            }*/

                HashMap<String, Object> loginJson = new HashMap<>();

                try {

                    loginJson.put("Key", Util.KEY);
                    loginJson.put("Email", edtEmailAddress.getText().toString());
                    loginJson.put("Password", edtPassword.getText().toString());
                    if (edtPassword.getTag() != null)
                        loginJson.put("FacebookToken", edtPassword.getTag().toString());

                    loginJson.put("IncludeAbbbcOnline", true);

                    //loginJson.put("AndroidDeviceId",ANDROID_ID);
                    loginJson.put("AppsFlyerId", APPSFLYER_ID);

                    try {
                        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        loginJson.put("AppVersion", pInfo.versionCode);
                        loginJson.put("DeviceId", ANDROID_ID); /////
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    //loginJson.put("AppVersion", 16);
                    loginJson.put("AppPlatform", "Android");

                } catch (Exception e) {
                    e.printStackTrace();
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                }


                FinisherServiceImpl finisherService = new FinisherServiceImpl(this);
                Call<LoginResponse> serverCall = finisherService.getLogin(loginJson);
                serverCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        //progressDialog.dismiss();
                        LoginResponse responseBody = response.body();
                        if (responseBody != null && responseBody.getSuccessFlag()) {

                            try {

                                Util. accesstype=String.valueOf(responseBody.getSessionDetail().getMbhqAccessType());
                                Util. CourseAccess=responseBody.getSessionDetail().getCourseAccess();

                                Util. HabitAccess=responseBody.getSessionDetail().getHabitAccess();

                                Util. EqJournalAccess=responseBody.getSessionDetail().getEqJournalAccess();

                                Util. MeditationAccess=responseBody.getSessionDetail().getMeditationAccess();

                                Util. ForumAccess=responseBody.getSessionDetail().getForumAccess();

                                Util. LiveChatAccess=responseBody.getSessionDetail().getLiveChatAccess();

                                Util. TestsAccess=responseBody.getSessionDetail().getTestsAccess();
                                Log.i("111111",Util. accesstype);
                                Log.i("222222",String.valueOf(Util. CourseAccess));
                                Log.i("333333",String.valueOf(Util. HabitAccess));
                                Log.i("444444",String.valueOf( Util. EqJournalAccess));
                                Log.i("555555",String.valueOf( Util. MeditationAccess));
                                Log.i("666666",String.valueOf( Util. ForumAccess));
                                Log.i("777777",String.valueOf( Util. LiveChatAccess));
                                Log.i("888888",String.valueOf( Util. TestsAccess));

                                sharedPreference.write("EQJournalPurchaseUrl", "", responseBody.getEQJournalPurchaseUrl());
                                sharedPreference.write("HabitPurchaseUrl", "", responseBody.getHabitPurchaseUrl());
                                sharedPreference.write("MeditationPurchaseUrl", "", responseBody.getMeditationPurchaseUrl());
                                sharedPreference.write("TestsPurchaseUrl", "", responseBody.getTestsPurchaseUrl());

                                long savedMillis = System.currentTimeMillis();
                                if (sharedPreference.read("logintime", "").equals("")) {
                                    sharedPreference.write("logintime", "", savedMillis + "");
                                }

                                //FOR QUOTE INITIALIZATION
                                if (sharedPreference.read("FIRST_LOGIN_TIME", "").equals("")) {
                                    sharedPreference.clear("signupdate");
                                    sharedPreference.clear("dbquote");
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                    Calendar calendar = Calendar.getInstance();
                                    String strDt = simpleDateFormat.format(calendar.getTime());
                                    sharedPreference.write("FIRST_LOGIN_TIME", "", strDt);
                                    sharedPreference.write("habbitFirstTime_1", "", "true");
                                    sharedPreference.write("habbitFirstTime", "", "true");
                                    sharedPreference.write("habbitFirstTime_firstpopup", "", "true");
                                    sharedPreference.write("meditationFirstTime", "", "true"); //
                                    sharedPreference.write("achievementFirstTime", "", "true"); //
                                    sharedPreference.write("courseFirstTime", "", "true"); //
                                }
                                Util.session =  responseBody.getSessionDetail().getUserSessionID().toString();
                                Util.jwt = responseBody.getJwt();
                                sharedPreference.write("jwt", "",  Util.jwt);


                                sharedPreference.write("my_list_eqfolder", "", new Gson().toJson(responseBody.getSessionDetail().getEqFolders()));
                                Integer accesstype=responseBody.getSessionDetail().getMbhqAccessType();
                                Log.i("printttttttttttttttttttttttttttttt",String.valueOf(accesstype));
                                Log.i("111111",String.valueOf(accesstype));
                                Boolean HabitAccess=responseBody.getSessionDetail().getHabitAccess();
                                Log.i("111111",String.valueOf(HabitAccess));
                                Boolean EqJournalAccess=responseBody.getSessionDetail().getEqJournalAccess();
                                Log.i("111111",String.valueOf(EqJournalAccess));
                                Boolean MeditationAccess=responseBody.getSessionDetail().getMeditationAccess();
                                Log.i("111111",String.valueOf(MeditationAccess));
                                Boolean ForumAccess=responseBody.getSessionDetail().getForumAccess();
                                Log.i("111111",String.valueOf(ForumAccess));
                                Boolean LiveChatAccess=responseBody.getSessionDetail().getLiveChatAccess();
                                Log.i("111111",String.valueOf(LiveChatAccess));
                                Boolean TestsAccess=responseBody.getSessionDetail().getTestsAccess();
                                Log.i("111111",String.valueOf(TestsAccess));
                                Boolean CourseAccess=responseBody.getSessionDetail().getCourseAccess();
                                Log.i("111111",String.valueOf(CourseAccess));
                                sharedPreference.write("accesstype", "", String.valueOf(accesstype));
                                sharedPreference.write("HabitAccess", "", String.valueOf(HabitAccess));
                                sharedPreference.write("EqJournalAccess", "", String.valueOf(EqJournalAccess));
                                sharedPreference.write("MeditationAccess", "", String.valueOf(MeditationAccess));
                                sharedPreference.write("ForumAccess", "", String.valueOf(ForumAccess));
                                sharedPreference.write("LiveChatAccess", "", String.valueOf(LiveChatAccess));
                                sharedPreference.write("TestsAccess", "", String.valueOf(TestsAccess));
                                sharedPreference.write("CourseAccess", "", String.valueOf(CourseAccess));
                                String accesstype1=sharedPreference.read("accesstype","");
                                String habit_access=sharedPreference.read("HabitAccess","");
                                String eq_access=sharedPreference.read("EqJournalAccess","");
                                String medi_access=sharedPreference.read("MeditationAccess","");
                                String forum_access=sharedPreference.read("ForumAccess","");
                                String Live_access=sharedPreference.read("LiveChatAccess","");
                                String Test_acess=sharedPreference.read("TestsAccess","");
                                String Course_access=sharedPreference.read("CourseAccess","");

                                Log.i("1111111100",eq_access);
                                Log.i("2222222200",medi_access);
                                Log.i("3333333300",accesstype1);
                                Log.i("4444444400",habit_access);
                                Log.i("5555555500",forum_access);
                                Log.i("6666666600",Live_access);
                                Log.i("7777777700",Test_acess);
                                Log.i("8888888800",Course_access);

                                Util.userid = responseBody.getSessionDetail().getUserID().toString();
                                Log.e("userid123",Util.userid+ " " +Util.session);



                                String sessionId = responseBody.getSessionDetail().getUserSessionID().toString();
                                Log.e("sessioni","sessionid login>>>>>>>>>>>>"+sessionId);
                                String userId = responseBody.getSessionDetail().getUserID().toString();
                                sharedPreference.write("UserSessionID", "", sessionId);
                                sharedPreference.write("UserID", "", userId);


                                sharedPreference.write("ABBBCOnlineUserId", "", responseBody.getSessionDetail().getABBBCOnlineUserId().toString());
                                sharedPreference.write("ABBBCOnlineUserSessionId", "", responseBody.getSessionDetail().getABBBCOnlineUserSessionId().toString());


                                if (!responseBody.getSessionDetail().getToken().equals("")) {
                                    sharedPreference.write("MEDITATION_TEST_NOW_TOKEN", "", responseBody.getSessionDetail().getToken());
                                }

                                if ( !responseBody.getSessionDetail().getActiveUntil().equals("")) {
                                    sharedPreference.write("ACTIVEUNTIL_DATE", "", responseBody.getSessionDetail().getActiveUntil());
                                }

                                if (responseBody.getSessionDetail().getTokenExpiry().equals("")) {
                                    sharedPreference.write("TOKEN_EXPIRY_DATE", "", responseBody.getSessionDetail().getTokenExpiry());
                                }

                                if (responseBody.getSessionDetail().getDiscoverableStatus()) {
                                    sharedPreference.write("discover", "strdiscover", "true");
                                    new Util().addRewardsApi(LogInActivity.this, 4, "users", sharedPreference.read("ABBBCOnlineUserId", ""), false);
                                } else {
                                    sharedPreference.write("discover", "strdiscover", "false");
                                }
                                sharedPreference.write("IsNonSubscribeUser", "", "false");


                                try {
                                    if (responseBody.getSessionDetail().getSignupMethod() == 0) {
                                        sharedPreference.write("SIGNUPMETHOD", "", "WEB");
                                    } else {
                                        sharedPreference.write("SIGNUPMETHOD", "", "APP");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    sharedPreference.write("SIGNUPMETHOD", "", "WEB");
                                }

                                Calendar calendar = Calendar.getInstance();
                                String lastLoginTime = Util.globalFormatWithOutT.format(calendar.getTime());
                                sharedPreference.write("LASTLOGINTIME", "", lastLoginTime);

                                sharedPreference.write("USEREMAIL", "", edtEmailAddress.getText().toString());
                                sharedPreference.write("USERPASSWORD", "", edtPassword.getText().toString());

                                sharedPreference.write("compChk", "", "true");

                                HashMap<String, Object> map = new HashMap<>();
                                if (responseBody.getSessionDetail().getIsFirstLogon()) {
                                    sharedPreference.write("slider", "", "");
                                } else {
                                    if (sharedPreference.read("PROGRAM_PURCHASE_ONLY", "").equals("TRUE")) {
                                        sharedPreference.write("slider", "", "");
                                    } else {
                                        sharedPreference.write("slider", "", "true");
                                    }
                                }

                                if (!TextUtils.isEmpty(responseBody.getSessionDetail().getFirstName())) {
                                    map.put("FirstName", responseBody.getSessionDetail().getFirstName());
                                    Log.e("FIRST NAME","name---"+responseBody.getSessionDetail().getFirstName());
                                    sharedPreference.write("firstName", "", responseBody.getSessionDetail().getFirstName());


                                    SharedPreferences sp = getApplicationContext().getSharedPreferences("firstName",0);
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putString("firstName",responseBody.getSessionDetail().getFirstName());
                                    editor.apply();
                                }

                                if (!TextUtils.isEmpty(responseBody.getSessionDetail().getLastName())) {
                                    map.put("LastName", responseBody.getSessionDetail().getLastName());
                                    sharedPreference.write("lastName", "", responseBody.getSessionDetail().getLastName());
                                }

                                if (!TextUtils.isEmpty(responseBody.getSessionDetail().getEmailAddress())) {
                                    map.put("EmailAddress", responseBody.getSessionDetail().getEmailAddress());
                                }

                                if (!TextUtils.isEmpty(responseBody.getSessionDetail().getPhoneNumber())) {
                                    map.put("PhoneNumber", responseBody.getSessionDetail().getPhoneNumber());
                                }


                                if (!responseBody.getSessionDetail().getIsPasswordUpdated()) {
                                    if (progressDialog != null && progressDialog.isShowing())
                                        progressDialog.dismiss();

                                    Dialog dialog = new Dialog(mContext, R.style.DialogTheme);
                                    dialog.setContentView(R.layout.dialog_update_password);
                                    RelativeLayout rlPassword = dialog.findViewById(R.id.rlPassword);
                                    EditText edtPasswordNew = dialog.findViewById(R.id.edtPasswordNew);
                                    ImageView imgClose = dialog.findViewById(R.id.imgClose);
                                    imgClose.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    rlPassword.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String oldPassword = edtPassword.getText().toString();
                                            String newPassword = edtPasswordNew.getText().toString();
                                            if (!newPassword.equals("")) {

                                                try {
                                                    if (!responseBody.getIsSubscribed() && responseBody.getPurchasedPrograms().size()>0) {
                                                        updatePasswordApi(oldPassword, newPassword, dialog, false);
                                                    } else {
                                                        updatePasswordApi(oldPassword, newPassword, dialog, true);
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                            } else {
                                                Util.showToast(mContext, "Please enter password");
                                            }
                                        }
                                    });
                                    dialog.show();
                                } else {

                                    SharedPreference sharedPreference = new SharedPreference(mContext);
                                    Intent intent = null;
//                                    intent = new Intent(mContext, DemoSliderActivity.class);
                                    if (!responseBody.getIsSubscribed() && responseBody.getPurchasedPrograms().size()>0) { Util.withfilterlist_afterbackfrommeditationdetails.clear();
                                        sharedPreference.write("PROGRAM_PURCHASE_ONLY", "", "TRUE");
                                        intent = new Intent(mContext, MainActivity.class);
                                        intent.putExtra("FROM_LOGIN", "TRUE");
                                    } else {
                                        sharedPreference.write("PROGRAM_PURCHASE_ONLY", "", "");
                                        if (sharedPreference.read("slider", "").equals("")) {
                                            intent = new Intent(mContext, DemoSliderActivity.class);
                                            sharedPreference.write("slider", "", "true");
                                        } else {
                                            Util.withfilterlist_afterbackfrommeditationdetails.clear();
                                            intent = new Intent(mContext, MainActivity.class);//
                                            intent.putExtra("FROM_LOGIN", "TRUE");
                                        }
                                    }

                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("FROMLOGIN", "TRUE");
                                    startActivity(intent);
                                    finish();
                                }


                            } catch (Exception e) {
                                if (progressDialog != null && progressDialog.isShowing())
                                    progressDialog.dismiss();
                                e.printStackTrace();

                                Util.showDialog(LogInActivity.this, "Alert !", "msg", false);
                            }

                        }else{
                            if (progressDialog != null && progressDialog.isShowing())
                                progressDialog.dismiss();
                            Util.showDialog(mContext, "Error", "Please give a valid password.", false);

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }else{
                Util.showDialog(mContext, "Error", "Password should not be blank", false);

            }

        }

    }




   private void updatePasswordApi(String oldPassword, String newPassword, Dialog dialog, Boolean isSubscribed) {
        ProgressDialog progressDialog = ProgressDialog.show(mContext, "", "Please wait...");

        HashMap<String, Object> reqHash = new HashMap<>();
        reqHash.put("UserID", Integer.parseInt(sharedPreference.read("UserID", "")));
        reqHash.put("Key", Util.KEY);
        reqHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
        reqHash.put("OldPassword", oldPassword);
        reqHash.put("NewPassword", newPassword);
        FinisherServiceImpl finisherService = new FinisherServiceImpl(mContext);
        Call<JsonObject> jsonObjectCall = finisherService.updatePassword(reqHash);
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                progressDialog.dismiss();
                try {
                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            dialog.dismiss();
                            SharedPreference sharedPreference = new SharedPreference(mContext);
                            sharedPreference.write("massUser", "", "true");
                            sharedPreference.write("USERPASSWORD", "", newPassword);

                            if (!isSubscribed) {
                                Util.withfilterlist_afterbackfrommeditationdetails.clear();
                                sharedPreference.write("PROGRAM_PURCHASE_ONLY", "", "TRUE");
                                Intent registerIntent = new Intent(mContext, MainActivity.class);
                                registerIntent.putExtra("FROM_LOGIN", "TRUE");
                                registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(registerIntent);
                                finish();
                            } else {
                                Util.withfilterlist_afterbackfrommeditationdetails.clear();
                                sharedPreference.write("PROGRAM_PURCHASE_ONLY", "", "");
                                if (getIntent() != null && getIntent().hasExtra("SETPROGRAM_LOGIN")) {
                                   Intent registerIntent = new Intent(mContext, MainActivity.class);
                                    registerIntent.putExtra("FROM_LOGIN", "TRUE");
                                    registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    registerIntent.putExtra("SETPROGRAM_LOGIN", "TRUE");
                                    startActivity(registerIntent);
                                    finish();
                                } else {

                                    Intent registerIntent = null;
                                    if (sharedPreference.read("slider", "").equals("")) {
                                        /*registerIntent = new Intent(mContext, DemoSliderActivity.class);
                                        sharedPreference.write("slider", "", "true");*/
                                    } else {
                                        Util.withfilterlist_afterbackfrommeditationdetails.clear();
                                        registerIntent = new Intent(mContext, MainActivity.class);
                                        registerIntent.putExtra("FROM_LOGIN", "TRUE");
                                    }
                                   registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(registerIntent);
                                    finish();
                                }
                            }
                        } else {
                           // showToast(mContext, getString(R.string.something_went_wrong_message));
                        }
                    } else {
                        Util.showDialog(mContext, "Error", response.errorBody().string(), false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("onFailure: ", t.getMessage());
                Util.showDialog(mContext, "Error", t.getMessage(), false);
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void showModal(int type) {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dialog.setContentView(R.layout.layout_term);
        WebView webView = dialog.findViewById(R.id.webView);
        PDFView pdfView = dialog.findViewById(R.id.pdfView);
        ImageButton btnClose = dialog.findViewById(R.id.btn_close);
        webView.setVisibility(View.GONE);
        pdfView.setVisibility(View.GONE);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (type == TYPE_TOS) {
pdfView.setVisibility(View.VISIBLE);
            String pdf = "https://www.thesquadtours.com/downloads/squad_terms.pdf";
            pdfView.fromAsset("squad_terms.pdf")
                    .defaultPage(0)
                    .swipeHorizontal(false)
                    .load();


            webView.setVisibility(View.VISIBLE);
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(true);
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            settings.setDomStorageEnabled(true);
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.setScrollbarFadingEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }

            webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    if (progressDialog != null && !progressDialog.isShowing()) {
                        Log.e("progress bar open", "123");
                        progressDialog.show();
                    }
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            //webView.loadUrl("https://docs.google.com/gview?url=https://www.thesquadtours.com/downloads/squad_terms.pdf");
            webView.loadUrl("https://mindbodyhq.com/pages/t-and-c");

        } else if (type == TYPE_PP) {
            webView.setVisibility(View.VISIBLE);
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(true);
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            settings.setDomStorageEnabled(true);
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.setScrollbarFadingEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    if (progressDialog != null && !progressDialog.isShowing()) {
                        Log.e("progress bar open", "123");
                        progressDialog.show();
                    }
                }


                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                }



                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
if (!url.startsWith("https://docs.google.com/gview")&&url.contains(".pdf")) {
                        url=String.format("http://docs.google.com/gview?url=%s",url);
                        view.loadUrl(url);
                        return true;
                    }


                    return false;
                }
            });
            //webView.loadUrl("http://docs.google.com/gview?url=https://www.thesquadtours.com/home/privacypolicy");
            webView.loadUrl("https://mindbodyhq.com/pages/t-and-c");
        }
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.show();
    }



    public static void showToast(Context context, String msg) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }
}
