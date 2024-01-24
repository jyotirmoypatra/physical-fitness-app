package com.ashysystem.mbhq.activity;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.model.GetUserPaidStatusModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPaidStatusActivity extends Activity {

    EditText edtEmailAddress;
    RelativeLayout rlLetsGo;
    SharedPreference sharedPreference;
//    private FirebaseAnalytics mFirebaseAnalytics;
    SimpleDateFormat globalFormatWithOutT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat globalFormat = new SimpleDateFormat("yyyy-MM-dd");
//    private Facebook facebook;
    ImageView imgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("called_oncreate","1");
        setContentView(R.layout.activity_user_paid_status);

        SharedPreference sharedPreference = new SharedPreference(UserPaidStatusActivity.this);
        sharedPreference.write("WELCOMECHECKONE","","true");
        sharedPreference.write("WELCOMECHECKTWO","","true");
        sharedPreference.write("WELCOMECHECKTHREE","","true");
        sharedPreference.write("WELCOMECHECKFOUR","","true");
        sharedPreference.write("compChk","","true");


//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//
//        //Sets whether analytics collection is enabled for this app on this device.
//        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
//
//        //Sets the minimum engagement time required before starting a session. The default value is 10000 (10 seconds). Let's make it 20 seconds just for the fun
//        mFirebaseAnalytics.setMinimumSessionDuration(20000);
//
//        //Sets the duration of inactivity that terminates the current session. The default value is 1800000 (30 minutes).
//        mFirebaseAnalytics.setSessionTimeoutDuration(500);

        edtEmailAddress = findViewById(R.id.edtEmailAddress);
        rlLetsGo = findViewById(R.id.rlLetsGo);
        imgBack=findViewById(R.id.imgBack);
        sharedPreference = new SharedPreference(UserPaidStatusActivity.this);
        if("".equalsIgnoreCase(sharedPreference.read("USEREMAIL", ""))){
            Log.i("called_oncreate","2");
        }else{
//            Intent intent = new Intent(UserPaidStatusActivity.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();

         /*   Intent registerIntent = new Intent(UserPaidStatusActivity.this, MainActivity.class);
            registerIntent.putExtra("FROM_LOGIN", "TRUE");
            registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(registerIntent);
            finish();*/
        }
        rlLetsGo.setOnClickListener(view -> {

            if(edtEmailAddress.getText().toString().equals(""))
            {
                Util.showToast(UserPaidStatusActivity.this,"Please Enter Email Address");
            }else {

                getUserPaidStatusApiCall(edtEmailAddress.getText().toString());

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPaidStatusActivity.this, SignUpActivity.class);

                startActivity(intent);
                finish();
            }
        });

    }

    private void getUserPaidStatusApiCall(String EMAIL) {

        if (Connection.checkConnection(UserPaidStatusActivity.this)) {
            final ProgressDialog progressDialog = ProgressDialog.show(UserPaidStatusActivity.this, "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Email",EMAIL);

            FinisherServiceImpl finisherService = new FinisherServiceImpl(UserPaidStatusActivity.this);
            Call<GetUserPaidStatusModel> paidStatusModelCall = finisherService.getUserPaidStatusApi(hashReq);
            paidStatusModelCall.enqueue(new Callback<GetUserPaidStatusModel>() {
                @Override
                public void onResponse(Call<GetUserPaidStatusModel> call, Response<GetUserPaidStatusModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null && response.body().getSuccessFlag()) {

                        Util.clearSharedPref(UserPaidStatusActivity.this);

                        /////////////New    Code//////////////
                        if(response.body().getIsPaid()!=null&&response.body().getIsPaid())  //Paid User
                        {
                            sharedPreference=new SharedPreference(UserPaidStatusActivity.this);
                            sharedPreference.write("IsNonSubscribeUser", "", "false");
                            sharedPreference.write("USEREMAIL", "", EMAIL);
                            Intent intent = new Intent(UserPaidStatusActivity.this, LogInActivity.class);
                            startActivity(intent);
                            // finish();
                        }
                        else if(response.body().getFreeWorkoutsUser() != null)
                        {
                            openDialogForInactiveSubscription(response.body().getFreeWorkoutsUser(), EMAIL);
                        }
                        else if(response.body().getSubscriptionType()!=null&&response.body().getSubscriptionType()==-1)
                        {
                            if (response.body().getFreeWorkoutsUser()!=null && response.body().getFreeWorkoutsUser().getSuccess()) {

                                /////////////////////FIREBASE ANALYTICS//////////////////

                                //CHANE27_02_2019
                                new SharedPreference(UserPaidStatusActivity.this).write("compChk", "","false");

                                Bundle bundle = new Bundle();
                                bundle.putString("email", EMAIL);
                                bundle.putString("type", "email");
                                bundle.putString("sign_up_method", "email");
                                //mFirebaseAnalytics.logEvent("squad_signup", bundle);
                                //mFirebaseAnalytics.logEvent("sign_up", bundle);
                                sharedPreference=new SharedPreference(UserPaidStatusActivity.this);
                                ////////////////////////////////////////////////////////

                                sharedPreference.write("UserSessionID", "", response.body().getFreeWorkoutsUser().getNonSubscribedUserSessionId().toString());
                                sharedPreference.write("UserID", "", response.body().getFreeWorkoutsUser().getSquadUserId().toString());
                                sharedPreference.write("ABBBCOnlineUserId", "", response.body().getFreeWorkoutsUser().getAbbbcUserId().toString());
                                sharedPreference.write("ABBBCOnlineUserSessionId", "", response.body().getFreeWorkoutsUser().getAbbbcUserSessionId().toString());
                                sharedPreference.write("IsNonSubscribeUser", "", "true");
                                sharedPreference.write("email", "", EMAIL);
                                sharedPreference.write("USEREMAIL", "", EMAIL);
                                sharedPreference.write("USERPASSWORD", "", response.body().getFreeWorkoutsUser().getPassword());
                                sharedPreference.write("firstName", "", response.body().getFreeWorkoutsUser().getFirstName());
                                sharedPreference.write("lastName", "", response.body().getFreeWorkoutsUser().getLastName());
                                sharedPreference.write("isFacebookUser", "", response.body().getFreeWorkoutsUser().getIsFBUser() + "");

                                Calendar calendar100 = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                String strSignUpDate = sdf.format(calendar100.getTime());
                                sharedPreference.write("signupdate", "", strSignUpDate);

                                if (response.body().getFreeWorkoutsUser() != null && response.body().getFreeWorkoutsUser().getTrialStartDate() != null && !response.body().getFreeWorkoutsUser().getTrialStartDate().equals("") && !response.body().getFreeWorkoutsUser().getTrialStartDate().equals("null")) {
                                    Date responseDate = null;
                                    String strResponseDate = "", strcurrentDate = "";

                                    Calendar calendar = Calendar.getInstance();
                                    strcurrentDate = globalFormat.format(calendar.getTime());
                                    try {
                                        responseDate = globalFormatWithOutT.parse(response.body().getFreeWorkoutsUser().getTrialStartDate());
                                        strResponseDate = globalFormat.format(responseDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    if (strcurrentDate.equals(strResponseDate)) {
                                        sharedPreference.write("FIRST_INSTALL_DATE", "", globalFormatWithOutT.format(calendar.getTime()));
                                    } else {
                                        sharedPreference.write("FIRST_INSTALL_DATE", "", response.body().getFreeWorkoutsUser().getTrialStartDate());
                                    }
                                    sharedPreference.write("SEVENDAY_TRIAL_START", "", "TRUE");
                                    sharedPreference.write("AFTERLOGINFIRSTTIMEVIDEO", "", "TRUE");

                                } else {
                                    /////////////FOR FLOW CHANGE/////////////////////
                                    Calendar calendar = Calendar.getInstance();
                                    if (response.body().getFreeWorkoutsUser() != null && response.body().getFreeWorkoutsUser().getHasTrialExpired()) {
                                        sharedPreference.write("FIRST_INSTALL_DATE", "", response.body().getFreeWorkoutsUser().getTrialStartDate());
                                        sharedPreference.write("SEVENDAY_TRIAL_START", "", "TRUE");
                                    } else {
                                        calendar.add(Calendar.DATE, -8);
                                        sharedPreference.write("FIRST_INSTALL_DATE", "", globalFormatWithOutT.format(calendar.getTime()));
                                    }
                                    /////////////////////////////////////////////////
                                }


//                             new Util().addRewardsApi(UserPaidStatusActivity.this, 4, "users", "", false);  need to check
                               /* Intent intent = new Intent(UserPaidStatusActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                JsonObject jsonObjResponse = new JsonObject();
                                jsonObjResponse.addProperty("FirstName", response.body().getFreeWorkoutsUser().getFirstName());
                                jsonObjResponse.addProperty("LastName", response.body().getFreeWorkoutsUser().getLastName());
                                jsonObjResponse.addProperty("EmailAddress", EMAIL);
                                startActivity(intent);
                                finish();*/
                            } else {
                                if (response.body().getFreeWorkoutsUser()!=null && response.body().getFreeWorkoutsUser().getHasTrialExpired()) {

                                    //openDailogForAlradeyRegisteredUser();
                                    sharedPreference.write("UserSessionID", "", response.body().getFreeWorkoutsUser().getNonSubscribedUserSessionId()+"");
                                    sharedPreference.write("UserID", "", response.body().getFreeWorkoutsUser().getSquadUserId()+"");
                                    sharedPreference.write("ABBBCOnlineUserId", "", response.body().getFreeWorkoutsUser().getAbbbcUserId()+"");
                                    sharedPreference.write("ABBBCOnlineUserSessionId", "", response.body().getFreeWorkoutsUser().getAbbbcUserSessionId()+"");
                                    sharedPreference.write("IsNonSubscribeUser", "", "true");
                                    sharedPreference.write("email", "", EMAIL);
                                    sharedPreference.write("USEREMAIL", "", EMAIL);
                                    sharedPreference.write("firstName", "", response.body().getFreeWorkoutsUser().getFirstName());
                                    sharedPreference.write("lastName", "", response.body().getFreeWorkoutsUser().getLastName());
                                    sharedPreference.write("isFacebookUser", "", response.body().getFreeWorkoutsUser().getIsFBUser() + "");

                                    Calendar calendar100 = Calendar.getInstance();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                    String strSignUpDate = sdf.format(calendar100.getTime());
                                    sharedPreference.write("signupdate", "", strSignUpDate);

                                    if (!response.body().getFreeWorkoutsUser().getTrialStartDate().equals("")) {
                                        Date responseDate = null;
                                        String strResponseDate = "", strcurrentDate = "";

                                        Calendar calendar = Calendar.getInstance();
                                        strcurrentDate = globalFormat.format(calendar.getTime());
                                        try {
                                            responseDate = globalFormatWithOutT.parse(response.body().getFreeWorkoutsUser().getTrialStartDate());
                                            strResponseDate = globalFormat.format(responseDate);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }


                                        if (strcurrentDate.equals(strResponseDate)) {
                                            sharedPreference.write("FIRST_INSTALL_DATE", "", globalFormatWithOutT.format(calendar.getTime()));
                                        } else {
                                            sharedPreference.write("FIRST_INSTALL_DATE", "", response.body().getFreeWorkoutsUser().getTrialStartDate());
                                        }

                                    }

//                                    Intent intent = new Intent(UserPaidStatusActivity.this, MainActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
//                                    finish();

                                } else {
                                    if(response.body().getFreeWorkoutsUser()!=null)
                                    {
                                        Util.showToast(UserPaidStatusActivity.this, String.format(Locale.getDefault(), response.body().getFreeWorkoutsUser().getErrorMessage(), EMAIL));
                                    }else {
                                        //Util.showToast(UserPaidStatusActivity.this,"Something Went Wrong!");
                                        /*sharedPreference=new SharedPreference(UserPaidStatusActivity.this);
                                        sharedPreference.write("USEREMAIL", "", EMAIL);
                                        Intent intent = new Intent(UserPaidStatusActivity.this,RegisterFirstActivity.class);
                                        startActivity(intent);
                                        finish();*/

                                    }
                                }

                            }
                        }
                        else
                        {
                            AlertDialogCustom alertDialogCustom=new AlertDialogCustom(UserPaidStatusActivity.this);
                            alertDialogCustom.ShowDialog("Alert","Hi,\nYour email has not been recognised.Click here now",false);
                            alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                                                                 @Override
                                                                 public void onDone(String title) {
                                                                     Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.emotionalfitnessclub.com/"));
                                                                     startActivity(browserIntent);
                                                                 }

                                                                 @Override
                                                                 public void onCancel(String title) {

                                                                 }
                                                             }
                            );
                        }
                        /////////////New    Code/////////////
                    }
                }

                @Override
                public void onFailure(Call<GetUserPaidStatusModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(UserPaidStatusActivity.this, Util.networkMsg);
        }

    }

    private void openDialogForInactiveSubscription(GetUserPaidStatusModel.FreeWorkoutsUser freeWorkoutsUser, String EMAIL) {

        final Dialog dialog = new Dialog(UserPaidStatusActivity.this, R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_inactive_user);

        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        TextView txtHiUseName = dialog.findViewById(R.id.txtHiUseName);
        TextView txtCheckOut = dialog.findViewById(R.id.txtCheckOut);
        TextView txtEmail = dialog.findViewById(R.id.txtEmail);
        Button btnUpdateNow = dialog.findViewById(R.id.btnUpdateNow);

        imgCross.setOnClickListener(view -> {
            dialog.dismiss();
        });

        txtHiUseName.setText("Hi " + freeWorkoutsUser.getFirstName());
        txtCheckOut.setText(Html.fromHtml("Please update your membership. Use " + "<b>discount code mb20 for 20% off" + "</b>" + " at checkout."));
        txtEmail.setText(Html.fromHtml("Your email is: " + "<b>" + EMAIL + "</b>"));

        btnUpdateNow.setOnClickListener(view -> {
            dialog.dismiss();
            String URL = Util.SERVERURL +  "home/MbhqRedirect?mode=signup&userId=" + freeWorkoutsUser.getSquadUserId();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            startActivity(browserIntent);
        });

        dialog.show();
    }
}