package com.ashysystem.mbhq.activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ashysystem.mbhq.BuildConfig;
import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by AQB Solutions PVT. LTD. on 31/5/17.
 */

public class SignUpActivity extends AppCompatActivity implements OnClickListener{

    private ViewPager viewpager;
    private Context mContext = SignUpActivity.this;
    private Button btn_login, btn_facebook, btn_email_login;
    private ProgressDialog progressDialog;
    SharedPreference sharedPreference;
    RelativeLayout rlSignUp, rlLogIn;
    String FROMMAINACTIVITY = "";
    private ProgressDialog dialog;
    ImageView imgBack;
    TextView txtBack;
    String GARMIN_OAUTHVERIFIER = "";
    private String fitbitAccessToken = "";
    private String deeplinkType = "";
    private String deeplinkWebURL = "";
    RelativeLayout rlCreateFreeAccount;
    TextView txtAlreadyHaveAccount;
    boolean CANCELSUBSCRIPTION = false;
    boolean purchaseBool = false;
    RelativeLayout rlLogInNew;
    String APP_PURCHASE = "";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("called_mainactivity","40");

        Intent meIntent = new Intent(this, SignUpActivity.class);
        meIntent.setData(intent.getData());
        startActivity(meIntent);

        finish();



    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_new);

        Log.i("SignUpActivity", "in Create");
        if(getIntent() != null && getIntent().getData() != null){
            Log.i("SignUpActivity", getIntent().getData().toString());
        }else {
            Log.i("SignUpActivity", "intent is null");
        }
        Log.e("ONCREATE",">>>>>>>>>>>>>");
        sharedPreference = new SharedPreference(SignUpActivity.this);
        sharedPreference.write("WELCOMECHECKONE", "", "true");
        sharedPreference.write("WELCOMECHECKTWO", "", "true");
        sharedPreference.write("WELCOMECHECKTHREE", "", "true");
        sharedPreference.write("WELCOMECHECKFOUR", "", "true");
        sharedPreference.write("compChk", "", "true");
        //sharedPreference.write("caltokg","","false");

        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);

        showProgress();

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("FROMMAINACTIVITY")) {
                FROMMAINACTIVITY = getIntent().getExtras().getString("FROMMAINACTIVITY");
            }
        }
        if (getIntent() != null && getIntent().getData() != null) {
            String totalUriString = getIntent().getData().toString();
            Log.e("TOTALURLSTRING 1", totalUriString + ">>>>>");
            if (totalUriString.contains("oauth_verifier")) {
                String[] arrToken1 = totalUriString.split("&");
                String[] arrToken2 = arrToken1[1].split("=");
                GARMIN_OAUTHVERIFIER = arrToken2[1];
            } else if (totalUriString.contains("isCancelled=true")) {
                CANCELSUBSCRIPTION = true;
                /*Intent intent = new Intent(SignUpActivity.this, RegisterFirstActivity.class);
                intent.putExtra("CANCELSUBSCRIPTION", "TRUE");
                startActivity(intent);
                finish();*/

                /*
                 * Get the date from callbackURL upto which user
                 * can get the access to the app.
                 * */
                if (totalUriString.contains("activeUntil=")) {
                    String[] arrCancelDate = totalUriString.split("activeUntil=");
                    if (arrCancelDate.length > 1) {
                        String strActiveUntil = arrCancelDate[1];
                        new SharedPreference(SignUpActivity.this).write("ACTIVEUNTIL_DATE", "", strActiveUntil);
                    }
                }

            }
        }

        Log.e("TRRRRRRR", ">>>>>>>>>>");

        Log.i("called_mainactivity","50");
        onQueryInventoryFinished();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("called_mainactivity","60");

        Log.e("ONRESUME",">>>>>>>>>>>>>");
    }

    public void onQueryInventoryFinished(){
        Log.i("called_mainactivity","-1");
        hideProgress();
        if (sharedPreference.read("SIGNUPMETHOD", "").equals("")) {
            purchaseBool = false;
        } else {
            purchaseBool = true;
        }


        if (sharedPreference.read("IsNonSubscribeUser", "").equals("") || sharedPreference.read("IsNonSubscribeUser", "").equals("true")) {
            sharedPreference.write("APPLY_ROUNDING", "", "TRUE");
        }

        if (purchaseBool && !CANCELSUBSCRIPTION) {

            Intent intent = null;

            if (!new SharedPreference(SignUpActivity.this).read("TOKEN_EXPIRY_DATE", "").equals("")) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    Date expiryDate = format.parse(new SharedPreference(SignUpActivity.this).read("TOKEN_EXPIRY_DATE", ""));

                    if (calendar.getTime().before(expiryDate)) {
                        if(!APP_PURCHASE.equals(""))
                        {
                            Log.i("called_mainactivity","10");
                            intent = new Intent(SignUpActivity.this, LogInActivity.class);
                        }else
                        {
                            Log.i("called_mainactivity","1");
                            Toast.makeText(this,"MainActivity",Toast.LENGTH_LONG).show();
//                            intent = new Intent(SignUpActivity.this, MainActivity.class);
                        }
                    } else {
                        Log.i("called_mainactivity","11");
                        intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.i("called_mainactivity","12");
                    intent = new Intent(SignUpActivity.this, LogInActivity.class);
                }
            } else {
                Log.i("called_mainactivity","13");
               // intent = new Intent(SignUpActivity.this, LogInActivity.class);
                Util.withfilterlist_afterbackfrommeditationdetails.clear();
                intent = new Intent(mContext, MainActivity.class);//
                intent.putExtra("FROM_LOGIN", "TRUE");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            }
            startActivity(intent);
            finish();
        } else {

            if (FROMMAINACTIVITY.equals("TRUE")) {
                initWidgets();
            } else if (sharedPreference.read("IsNonSubscribeUser", "").equals("true") && !sharedPreference.read("USEREMAIL", "").equals("") && !CANCELSUBSCRIPTION) {
                //Welcome activity

                if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(SignUpActivity.this)) {
                    //Intent intent = new Intent(SignUpActivity.this, AccessSquadFreeActivity.class);
                    Intent intent = null;
                    if(!APP_PURCHASE.equals(""))
                    {
                        Log.i("called_mainactivity","14");

                        intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    }else
                    {
                        Log.i("called_mainactivity","2");
                        Toast.makeText(this,"MainActivity",Toast.LENGTH_LONG).show();

//                        intent = new Intent(SignUpActivity.this, MainActivity.class);
                    }



                    startActivity(intent);
                    finish();

                } else if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && !Util.isSevenDayOver(SignUpActivity.this)) {



                } else {

                }

            } else if (sharedPreference.read("IsNonSubscribeUser", "").equals("false") && !sharedPreference.read("USEREMAIL", "").equals("") && !CANCELSUBSCRIPTION && sharedPreference.read("SIGNUPMETHOD", "").equals("WEB")) {

            } else if (sharedPreference.read("USEREMAIL", "").equals("") && sharedPreference.read("IsNonSubscribeUser", "").equals("") && !CANCELSUBSCRIPTION) {

                initWidgets();


            } else if (!sharedPreference.read("USEREMAIL", "").equals("") && sharedPreference.read("IsNonSubscribeUser", "").equals("") && !CANCELSUBSCRIPTION) {

            } else if (CANCELSUBSCRIPTION) {

            } else {
                //Stay with this activity
                initWidgets();

                if (!sharedPreference.read("SIGNUPMETHOD", "").equals("WEB")) {
                    Util.clearSharedPref(SignUpActivity.this);
                }
            }
        }
    }




    private void initWidgets() {
        Log.i("called_mainactivity","120");

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_facebook = (Button) findViewById(R.id.btn_facebook);
        btn_facebook.setOnClickListener(this);
        btn_email_login = (Button) findViewById(R.id.btn_email_login);
        btn_email_login.setOnClickListener(this);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(7);
        rlSignUp = (RelativeLayout) findViewById(R.id.rlSignUp);
        rlSignUp.setOnClickListener(this);
        rlLogIn = (RelativeLayout) findViewById(R.id.rlLogIn);
        rlLogIn.setOnClickListener(this);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        txtBack = (TextView) findViewById(R.id.txtBack);
        rlCreateFreeAccount = (RelativeLayout) findViewById(R.id.rlCreateFreeAccount);
        txtAlreadyHaveAccount = (TextView) findViewById(R.id.txtAlreadyHaveAccount);

        rlLogInNew = findViewById(R.id.rlLogInNew);

        rlLogInNew.setVisibility(View.VISIBLE);
        rlCreateFreeAccount.setVisibility(View.GONE);
        rlLogInNew.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, UserPaidStatusActivity.class);
            startActivity(intent);
            finish();
        });

        imgBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rlCreateFreeAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent intent = new Intent(SignUpActivity.this, RegisterFirstActivity.class);
                startActivity(intent);
                finish();*/
            }
        });

        txtBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SpannableString ss = new SpannableString("Already have an account? Login");
        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // do some thing
                Log.i("called_mainactivity","23");

                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                //finish();
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
        ss.setSpan(span1, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtAlreadyHaveAccount.setText(ss);
        txtAlreadyHaveAccount.setMovementMethod(LinkMovementMethod.getInstance());

        showHashKey(mContext);
    }

    /**
     * Initiate facebook login
     */


    @Override
    public void onClick(View view) {
        if (view == btn_login) {
            Intent logInIntent = null;

            if (!new SharedPreference(SignUpActivity.this).read("TOKEN_EXPIRY_DATE", "").equals("")) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    Date expiryDate = format.parse(new SharedPreference(SignUpActivity.this).read("TOKEN_EXPIRY_DATE", ""));

                    if (calendar.getTime().before(expiryDate)) {
                        Log.i("called_mainactivity","6");
                        Toast.makeText(this,"MainActivity",Toast.LENGTH_LONG).show();

                        //  logInIntent = new Intent(SignUpActivity.this, MainActivity.class);
                    } else {
                        Log.i("called_mainactivity","24");

                        logInIntent = new Intent(SignUpActivity.this, LogInActivity.class);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.i("called_mainactivity","25");

                    logInIntent = new Intent(SignUpActivity.this, LogInActivity.class);
                }
            } else {
                Log.i("called_mainactivity","26");

                logInIntent = new Intent(SignUpActivity.this, LogInActivity.class);
            }

            startActivity(logInIntent);
            finish();
        }else if (view == rlSignUp) {
           /* Intent registerIntent = new Intent(mContext, RegisterActivity.class);
            registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(registerIntent);*/
        } else if (view == rlLogIn) {
            Intent logInIntent = null;

            if (!new SharedPreference(SignUpActivity.this).read("TOKEN_EXPIRY_DATE", "").equals("")) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    Date expiryDate = format.parse(new SharedPreference(SignUpActivity.this).read("TOKEN_EXPIRY_DATE", ""));

                    if (calendar.getTime().before(expiryDate)) {
                        Log.i("called_mainactivity","7");
                        Toast.makeText(this,"MainActivity",Toast.LENGTH_LONG).show();
                        // logInIntent = new Intent(SignUpActivity.this, MainActivity.class);
                    } else {
                        Log.i("called_mainactivity","27");

                        logInIntent = new Intent(SignUpActivity.this, LogInActivity.class);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.i("called_mainactivity","28");

                    logInIntent = new Intent(SignUpActivity.this, LogInActivity.class);
                }
            } else {
                Log.i("called_mainactivity","29");

                logInIntent = new Intent(SignUpActivity.this, LogInActivity.class);
            }

            startActivity(logInIntent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("called_mainactivity","140");

    }

    public void showHashKey(Context context) {
        Log.i("called_mainactivity","150");

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Exception", e.getLocalizedMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.e("Exception", e.getLocalizedMessage());
        }
    }




    private void showProgress() {
        if (dialog != null && !dialog.isShowing())
            dialog.show();
    }

    private void hideProgress() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
