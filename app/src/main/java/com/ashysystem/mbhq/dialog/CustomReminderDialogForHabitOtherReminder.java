package com.ashysystem.mbhq.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.model.habit_hacker.NewAction;
import com.ashysystem.mbhq.util.TimePickerControllerWithTextView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomReminderDialogForHabitOtherReminder extends DialogFragment {
    Button mButton;
    EditText mEditText;
    public onSubmitListener mListener;
    private RelativeLayout btnApply;
    String text = "";
    //////////////////////
    private TextView txtReminderFrequencyType, txtReminderDayFrequencyMonth, txtAmPmUp, txtAmPmDownwn;
    private Spinner spReminderFrequency, spMonth, spAmPm, spBtwn, spAmPmDown;
    View vwLLReminder, vwDay, vwYear, vwWK, vwDown;
    private LinearLayout llWk, llDay, llDaily, llYear, llAmPmUp, llAmPmDown, llBtwnDown, llReminderFrequencySpinner;
    private boolean twiceDailyBool = false, betweenTwiceBetween = false;
    private Button buttonS, buttonM, buttonT, buttonW, buttonTH, buttonF, buttonSa, buttonJan, buttonFeb, buttonMar, buttonApr, buttonMay, buttonJun, buttonJul, buttonAug, buttonSep, buttonOct, buttonNov, buttonDec;
    private boolean boolS = false, boolM = false, boolT = false, boolW = false, boolTH = false, boolF = false, boolSa = false, boolJan = false, boolFeb = false, boolMar = false, boolApr = false, boolMay = false, boolJun = false, boolJul = false, boolAug = false, boolSep = false, boolOct = false, boolNov = false, boolDec = false;
    private JSONObject dataJsonReq = new JSONObject();
    private CheckBox chkPushNotification, chkEmailNotification;
    private String jsonString = "";
    RelativeLayout rlBack;
    TextView txtCalender_;
    LinearLayout llBtwnDown1;
    String receiveModel = "";
    NewAction globalGratitudeModel;
    String[] arrFreq = {"Daily", "Twice Daily", "Weekly", "Fortnightly", "Monthly", "Yearly"};
    String[] arrBtwn = {"At", "Between", "Twice Between"};
    List<String> lstReminderMonth;
    List<String> lstAmPm;

    int reminderFrequencyCheck = 0;
    TextView txtTimePicker, txtTimePickerDown;

    boolean push_notification = false;

    TextView txtReminderSettingsHeading;
    View vwPush, vwEmail, vwBtwnUp;
    LinearLayout llPush, llEmail, llBtwnUp;
    TextView txtReminderFrequency;
    String habitname = "";
    String day_value = "";
    String month_of_year = "";
    int day_of_month = 1;
    String day_of_week = "sun";
    String inputTime = "12:00 PM";
    String inputTime_2nd = "12:00 PM";
    String outputTime = "";
    String outputTime_2nd = "";
    String[] array_week = new String[7];
    List<String> array_week_list = new ArrayList<String>();

    String[] array_month = new String[12];
    List<String> array_month_list = new ArrayList<String>();

    String final_day_string="";
    String final_month_string="";
    public interface onSubmitListener {
        void setOnSubmitListener(JSONObject arg);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_custom_reminder);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        initView(dialog);
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        if (0 == month) {
            month_of_year = "jan";
        } else if (1 == month) {
            month_of_year = "feb";
        } else if (2 == month) {
            month_of_year = "march";
        } else if (3 == month) {
            month_of_year = "april";
        } else if (4 == month) {
            month_of_year = "may";
        } else if (5 == month) {
            month_of_year = "june";
        } else if (6 == month) {
            month_of_year = "july";
        } else if (7 == month) {
            month_of_year = "august";
        } else if (8 == month) {
            month_of_year = "sept";
        } else if (9 == month) {
            month_of_year = "oct";
        } else if (10 == month) {
            month_of_year = "nov";
        } else if (11 == month) {
            month_of_year = "dec";
        } else {
            month_of_year = "jan";
        }


        if (getArguments() != null) {
            if (getArguments().containsKey("MODEL")) {
                habitname = getArguments().getString("HABIT_NAME");
              //  llBtwnDown1.setVisibility(View.VISIBLE);
                llBtwnDown1.setVisibility(View.GONE);
                receiveModel = getArguments().getString("MODEL");
                Log.e("RECEIVE_MODEL", receiveModel + ">>>>>");
                populateJsonForGratitude(receiveModel);
            } else {
                initJson();
            }

            if (getArguments().containsKey("PUSH_NOTIFICATION")) {
                push_notification = getArguments().getBoolean("PUSH_NOTIFICATION");

            }
            Log.e("DIA_PUSHHH", "onCreateDialog: " + push_notification );
            /////////////////////////////////////////////////////////////////////////
            if(push_notification){
                chkPushNotification.setChecked(true);
            }else {
                chkPushNotification.setChecked(false);
            }
            /////////////////////////////////////////////////////////////////////////



        } else {
            initJson();
        }


        outputTime = formattingdate();
        outputTime_2nd = formattingdate_2nd();

        return dialog;
    }

    public String formattingdate() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HHmmss'Z'");

        //String inputTime = "09:00 PM";
        Date date = null;
        try {
            date = inputFormat.parse(inputTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String outputTime_ = outputFormat.format(cal.getTime());
        return outputTime_;
    }

    public String formattingdate_2nd() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HHmmss'Z'");

        //String inputTime = "09:00 PM";
        Date date = null;
        try {
            date = inputFormat.parse(inputTime_2nd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String outputTime_2 = outputFormat.format(cal.getTime());
        return outputTime_2;
    }

    private void initJson() {
        try {
            dataJsonReq.put("FrequencyId", 1);
            dataJsonReq.put("MonthReminder", 1);
            dataJsonReq.put("ReminderOption", 1);
            dataJsonReq.put("ReminderAt1", 43200);
            dataJsonReq.put("ReminderAt2", 0);
            dataJsonReq.put("Email", false);
            dataJsonReq.put("PushNotification", true);
            dataJsonReq.put("Sunday", false);
            dataJsonReq.put("Monday", false);
            dataJsonReq.put("Tuesday", false);
            dataJsonReq.put("Wednesday", false);
            dataJsonReq.put("Thursday", false);
            dataJsonReq.put("Friday", false);
            dataJsonReq.put("Saturday", false);
            dataJsonReq.put("January", false);
            dataJsonReq.put("February", false);
            dataJsonReq.put("March", false);
            dataJsonReq.put("April", false);
            dataJsonReq.put("April", false);
            dataJsonReq.put("May", false);
            dataJsonReq.put("June", false);
            dataJsonReq.put("July", false);
            dataJsonReq.put("August", false);
            dataJsonReq.put("September", false);
            dataJsonReq.put("October", false);
            dataJsonReq.put("November", false);
            dataJsonReq.put("December", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateJsonForGratitude(String receiveModel) {
        Gson gson = new Gson();
        globalGratitudeModel = gson.fromJson(receiveModel, NewAction.class);
        /*if(globalGratitudeModel.getApril()){
            Log.i("APRIL_1","1");
       }*/


        try {
            dataJsonReq.put("Sunday", globalGratitudeModel.getSunday());
            dataJsonReq.put("Monday", globalGratitudeModel.getMonday());
            dataJsonReq.put("Tuesday", globalGratitudeModel.getTuesday());
            dataJsonReq.put("Wednesday", globalGratitudeModel.getWednesday());
            dataJsonReq.put("Thursday", globalGratitudeModel.getThursday());
            dataJsonReq.put("Friday", globalGratitudeModel.getFriday());
            dataJsonReq.put("Saturday", globalGratitudeModel.getSaturday());

            dataJsonReq.put("January", globalGratitudeModel.getJanuary());
            dataJsonReq.put("February", globalGratitudeModel.getFebruary());
            dataJsonReq.put("March", globalGratitudeModel.getMarch());
            dataJsonReq.put("April", globalGratitudeModel.getApril());
            dataJsonReq.put("May", globalGratitudeModel.getMay());
            dataJsonReq.put("June", globalGratitudeModel.getJune());
            dataJsonReq.put("July", globalGratitudeModel.getJuly());
            dataJsonReq.put("August", globalGratitudeModel.getAugust());
            dataJsonReq.put("September", globalGratitudeModel.getSeptember());
            dataJsonReq.put("October", globalGratitudeModel.getOctober());
            dataJsonReq.put("November", globalGratitudeModel.getNovember());
            dataJsonReq.put("December", globalGratitudeModel.getDecember());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (globalGratitudeModel.getSunday() != null && globalGratitudeModel.getSunday()) {
            boolS = true;
            buttonS.setBackground(null);
            buttonS.setBackgroundResource(R.drawable.circle_blue_month);
        }


        if (globalGratitudeModel.getMonday() != null && globalGratitudeModel.getMonday()) {
            boolM = true;
            buttonM.setBackgroundResource(R.drawable.circle_blue_month);
        }


        if (globalGratitudeModel.getTuesday() != null && globalGratitudeModel.getTuesday()) {
            boolT = true;
            buttonT.setBackgroundResource(R.drawable.circle_blue_month);
        }


        if (globalGratitudeModel.getWednesday() != null && globalGratitudeModel.getWednesday()) {
            boolW = true;
            buttonW.setBackgroundResource(R.drawable.circle_blue_month);
        }


        if (globalGratitudeModel.getThursday() != null && globalGratitudeModel.getThursday()) {
            boolTH = true;
            buttonTH.setBackgroundResource(R.drawable.circle_blue_month);
        }


        if (globalGratitudeModel.getFriday() != null && globalGratitudeModel.getFriday()) {
            boolF = true;
            buttonF.setBackgroundResource(R.drawable.circle_blue_month);
        }


        if (globalGratitudeModel.getSaturday() != null && globalGratitudeModel.getSaturday()) {
            boolSa = true;
            buttonSa.setBackgroundResource(R.drawable.circle_blue_month);
        }



        if (globalGratitudeModel.getJanuary() != null && globalGratitudeModel.getJanuary()) {
            boolJan = true;
            buttonJan.setBackgroundResource(R.drawable.circle_blue_month);
        }

        if (globalGratitudeModel.getFebruary() != null && globalGratitudeModel.getFebruary()) {
            boolFeb = true;
            buttonFeb.setBackgroundResource(R.drawable.circle_blue_month);
        }

        if (globalGratitudeModel.getMarch() != null && globalGratitudeModel.getMarch()) {
            boolMar = true;
            buttonMar.setBackgroundResource(R.drawable.circle_blue_month);
        }

        if (globalGratitudeModel.getApril() != null && globalGratitudeModel.getApril()) {

            boolApr = true;
            buttonApr.setBackgroundResource(R.drawable.circle_blue_month);
           // buttonApr.setVisibility(View.GONE);
          //  buttonApr.setBackground(getActivity().getDrawable(R.drawable.circle_blue_month));
        }

        if (globalGratitudeModel.getMay() != null && globalGratitudeModel.getMay()) {
            boolMay = true;
            buttonMay.setBackgroundResource(R.drawable.circle_blue_month);
        }

        if (globalGratitudeModel.getJune() != null && globalGratitudeModel.getJune()) {
            boolJun = true;
            buttonJun.setBackgroundResource(R.drawable.circle_blue_month);
        }

        if (globalGratitudeModel.getJuly() != null && globalGratitudeModel.getJuly()) {
            boolJul = true;
            buttonJul.setBackgroundResource(R.drawable.circle_blue_month);
        }

        if (globalGratitudeModel.getAugust() != null && globalGratitudeModel.getAugust()) {
            boolAug = true;
            buttonAug.setBackgroundResource(R.drawable.circle_blue_month);
        }

        if (globalGratitudeModel.getSeptember() != null && globalGratitudeModel.getSeptember()) {
            boolSep = true;
            buttonSep.setBackgroundResource(R.drawable.circle_blue_month);
        }

        if (globalGratitudeModel.getOctober() != null && globalGratitudeModel.getOctober()) {
            boolOct = true;
            buttonOct.setBackgroundResource(R.drawable.circle_blue_month);
        }

        if (globalGratitudeModel.getNovember() != null && globalGratitudeModel.getNovember()) {
            boolNov = true;
            buttonNov.setBackgroundResource(R.drawable.circle_blue_month);
        }

        if (globalGratitudeModel.getDecember() != null && globalGratitudeModel.getDecember()) {
            boolDec = true;
            buttonDec.setBackgroundResource(R.drawable.circle_blue_month);
        }


        if (getArguments() != null && getArguments().containsKey("HABIT_HACKER_TASK")) {
            //habitname= getArguments().getString("HABIT_NAME");
            txtReminderSettingsHeading.setVisibility(View.GONE);
            llPush.setVisibility(View.GONE);
            vwPush.setVisibility(View.GONE);
            llEmail.setVisibility(View.GONE);
            vwEmail.setVisibility(View.GONE);
           // llBtwnUp.setVisibility(View.GONE);
           // vwBtwnUp.setVisibility(View.GONE);
            txtReminderFrequency.setText("Frequency");
        }

        try {
            dataJsonReq.put("FrequencyId", globalGratitudeModel.getFrequencyId());
            dataJsonReq.put("PushNotification", true);
            if (globalGratitudeModel.getFrequencyId() != null && globalGratitudeModel.getFrequencyId() != 0) {
                spReminderFrequency.setSelection(globalGratitudeModel.getFrequencyId() - 1);
                txtReminderFrequencyType.setText(arrFreq[globalGratitudeModel.getFrequencyId() - 1]);

                if (globalGratitudeModel.getFrequencyId() - 1 == 0) {
                    txtReminderFrequencyType.setText("Daily");
                    day_value="daily";
                    llBtwnUp.setVisibility(View.VISIBLE);

                } else if (globalGratitudeModel.getFrequencyId() - 1 == 1) {
                    Log.i("APRIL_1","1");
                    txtReminderFrequencyType.setText("Twice Daily");
                    day_value="t_daily";
                    twiceDailyBool = true;
                    llBtwnDown.setVisibility(View.VISIBLE);
//                    llBtwnDown.setVisibility(View.GONE);

                    vwDown.setVisibility(View.VISIBLE);
//                    vwDown.setVisibility(View.GONE);

                } else if (globalGratitudeModel.getFrequencyId() - 1 == 2) {
                    vwLLReminder.setVisibility(View.VISIBLE);
                    llWk.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Weekly");
                    day_value="weekly";
                } else if (globalGratitudeModel.getFrequencyId() - 1 == 3) {
                    vwLLReminder.setVisibility(View.VISIBLE);
                    llWk.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Fortnightly");
                    day_value="fornight";
                } else if (globalGratitudeModel.getFrequencyId() - 1 == 4) {
                    vwYear.setVisibility(View.VISIBLE);
                    vwDay.setVisibility(View.VISIBLE);
                    llDay.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Monthly");
                    day_value="monthly";
                } else if (globalGratitudeModel.getFrequencyId() - 1 == 5) {
                    vwWK.setVisibility(View.VISIBLE);
                    vwYear.setVisibility(View.VISIBLE);
                    llYear.setVisibility(View.VISIBLE);
                    llBtwnUp.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Yearly");
                    day_value="yearly";
                }

            }

            dataJsonReq.put("MonthReminder", globalGratitudeModel.getMonthReminder());
            if (globalGratitudeModel.getMonthReminder() != null && globalGratitudeModel.getMonthReminder() != 0) {
                spMonth.setSelection(globalGratitudeModel.getMonthReminder() - 1);
                txtReminderDayFrequencyMonth.setText(lstReminderMonth.get(globalGratitudeModel.getMonthReminder() - 1));
            }

            dataJsonReq.put("ReminderOption", globalGratitudeModel.getReminderOption());
            if (globalGratitudeModel.getReminderOption() != null && globalGratitudeModel.getReminderOption() != 0) {
                spBtwn.setSelection(globalGratitudeModel.getReminderOption() - 1);
                if (globalGratitudeModel.getReminderOption() - 1 != 0) {
                    betweenTwiceBetween = true;
                }
                if (betweenTwiceBetween) {
                    llBtwnDown.setVisibility(View.VISIBLE);
//                    llBtwnDown.setVisibility(View.GONE);

                     vwDown.setVisibility(View.VISIBLE);
//                    vwDown.setVisibility(View.GONE);

                } else {
                    llBtwnDown.setVisibility(View.VISIBLE);
                    vwDown.setVisibility(View.VISIBLE);
                }
            }
            if (globalGratitudeModel.getReminderAt1() != null) {
                dataJsonReq.put("ReminderAt1", globalGratitudeModel.getReminderAt1());
            } else {
                dataJsonReq.put("ReminderAt1", 43200);
            }
            if (globalGratitudeModel.getReminderAt1() != null) {
                //spAmPm.setSelection(Integer.parseInt(globalGratitudeModel.getReminderAt1())-1);
                //txtAmPmUp.setText(lstAmPm.get(Integer.parseInt(globalGratitudeModel.getReminderAt1())-1));

                Integer at1Hour = 0;
                Integer at1Minute = 0;
                Integer at1Second = 0;

                at1Second = (globalGratitudeModel.getReminderAt1() % 60);
                Integer totalMinute = (globalGratitudeModel.getReminderAt1() / 60);
                at1Minute = totalMinute % 60;
                at1Hour = totalMinute / 60;

                String strTime = "";
                boolean boolPm = false;
                if (at1Hour == 12) {
                    at1Hour = 12;
                    boolPm = true;
                } else if (at1Hour > 12) {
                    at1Hour = at1Hour - 12;
                    boolPm = true;
                }

                strTime = pad(at1Hour) + ":" + pad(at1Minute);

                if (boolPm) {
                    strTime = strTime + " PM";
                } else {
                    strTime = strTime + " AM";
                }
                txtTimePicker.setText(strTime);

            }
            if (globalGratitudeModel.getReminderAt2() != null) {
                dataJsonReq.put("ReminderAt2", globalGratitudeModel.getReminderAt2());
            } else {
                dataJsonReq.put("ReminderAt2", 0);
            }
            if (globalGratitudeModel.getReminderAt2() != null) {
                //spAmPmDown.setSelection(Integer.parseInt(globalGratitudeModel.getReminderAt2())-1);
                //txtAmPmDownwn.setText(lstAmPm.get(Integer.parseInt(globalGratitudeModel.getReminderAt2())-1));

                Integer at2Hour = 0;
                Integer at2Minute = 0;
                Integer at2Second = 0;

                if (globalGratitudeModel.getReminderAt2() != null) {
                    at2Second = (globalGratitudeModel.getReminderAt2() % 60);
                    Integer totalMinute = (globalGratitudeModel.getReminderAt2() / 60);
                    at2Minute = totalMinute % 60;
                    at2Hour = totalMinute / 60;
                }

                String strTime = "";
                boolean boolPm = false;
                if (at2Hour == 12) {
                    at2Hour = 12;
                    boolPm = true;
                } else if (at2Hour > 12) {
                    at2Hour = at2Hour - 12;
                    boolPm = true;
                }

                strTime = pad(at2Hour) + ":" + pad(at2Minute);

                if (boolPm) {
                    strTime = strTime + " PM";
                } else {
                    strTime = strTime + " AM";
                }
                txtTimePickerDown.setText(strTime);

            }

            dataJsonReq.put("Email", globalGratitudeModel.getEmail());
            if (globalGratitudeModel.getEmail() != null && globalGratitudeModel.getEmail()) {
                chkEmailNotification.setChecked(true);
            }

            dataJsonReq.put("PushNotification", globalGratitudeModel.getPushNotification());
            if (globalGratitudeModel.getPushNotification() != null && globalGratitudeModel.getPushNotification()) {
                chkPushNotification.setChecked(true);
            }



        } catch (JSONException e) {
            Log.i("APRIL_1","hiiii");
            e.printStackTrace();

        }
    }

    private void initView(Dialog dialog) {
        llBtwnDown1 = (LinearLayout) dialog.findViewById(R.id.llBtwnDown1);
        txtCalender_ = (TextView) dialog.findViewById(R.id.txtCalender);
        chkPushNotification = (CheckBox) dialog.findViewById(R.id.chkPushNotification);
        chkEmailNotification = (CheckBox) dialog.findViewById(R.id.chkEmailNotification);
        txtReminderFrequencyType = (TextView) dialog.findViewById(R.id.txtReminderFrequencyType);
        txtAmPmUp = (TextView) dialog.findViewById(R.id.txtAmPmUp);
        spReminderFrequency = (Spinner) dialog.findViewById(R.id.spReminderFrequency);
        vwLLReminder = (View) dialog.findViewById(R.id.vwLLReminder);
        vwDay = (View) dialog.findViewById(R.id.vwDay);
        vwDown = (View) dialog.findViewById(R.id.vwDown);
        llWk = (LinearLayout) dialog.findViewById(R.id.llWk);
        llDay = (LinearLayout) dialog.findViewById(R.id.llDay);
        llYear = (LinearLayout) dialog.findViewById(R.id.llYear);
        llDaily = (LinearLayout) dialog.findViewById(R.id.llDaily);
        llBtwnDown = (LinearLayout) dialog.findViewById(R.id.llBtwnDown);
        llAmPmUp = (LinearLayout) dialog.findViewById(R.id.llAmPmUp);
        llReminderFrequencySpinner = (LinearLayout) dialog.findViewById(R.id.llReminderFrequencySpinner);
        txtReminderDayFrequencyMonth = (TextView) dialog.findViewById(R.id.txtReminderDayFrequencyMonth);
        txtAmPmDownwn = (TextView) dialog.findViewById(R.id.txtAmPmDownwn);
        spMonth = (Spinner) dialog.findViewById(R.id.spMonth);
        spAmPmDown = (Spinner) dialog.findViewById(R.id.spAmPmDown);
        llAmPmDown = (LinearLayout) dialog.findViewById(R.id.llAmPmDown);
        vwYear = (View) dialog.findViewById(R.id.vwYear);
        vwWK = (View) dialog.findViewById(R.id.vwWK);
        spBtwn = (Spinner) dialog.findViewById(R.id.spBtwn);
        spAmPm = (Spinner) dialog.findViewById(R.id.spAmPm);
        buttonS = (Button) dialog.findViewById(R.id.buttonS);
        buttonM = (Button) dialog.findViewById(R.id.buttonM);
        buttonT = (Button) dialog.findViewById(R.id.buttonT);
        buttonW = (Button) dialog.findViewById(R.id.buttonW);
        buttonTH = (Button) dialog.findViewById(R.id.buttonTH);
        buttonF = (Button) dialog.findViewById(R.id.buttonF);
        buttonSa = (Button) dialog.findViewById(R.id.buttonSa);
        buttonJan = (Button) dialog.findViewById(R.id.buttonJan);
        buttonFeb = (Button) dialog.findViewById(R.id.buttonFeb);
        buttonMar = (Button) dialog.findViewById(R.id.buttonMar);
        buttonApr = (Button) dialog.findViewById(R.id.buttonApr);
        buttonMay = (Button) dialog.findViewById(R.id.buttonMay);
        buttonJun = (Button) dialog.findViewById(R.id.buttonJun);
        buttonJul = (Button) dialog.findViewById(R.id.buttonJul);
        buttonAug = (Button) dialog.findViewById(R.id.buttonAug);
        buttonSep = (Button) dialog.findViewById(R.id.buttonSep);
        buttonOct = (Button) dialog.findViewById(R.id.buttonOct);
        buttonNov = (Button) dialog.findViewById(R.id.buttonNov);
        buttonDec = (Button) dialog.findViewById(R.id.buttonDec);
        btnApply = (RelativeLayout) dialog.findViewById(R.id.btnApply);
        rlBack = (RelativeLayout) dialog.findViewById(R.id.rlBack);
        txtTimePicker = dialog.findViewById(R.id.txtTimePicker);
        txtTimePicker.setText("12:00 PM");
        txtTimePickerDown = dialog.findViewById(R.id.txtTimePickerDown);

        txtReminderSettingsHeading = dialog.findViewById(R.id.txtReminderSettingsHeading);
        txtReminderFrequency = dialog.findViewById(R.id.txtReminderFrequency);
        vwPush = dialog.findViewById(R.id.vwPush);
        vwEmail = dialog.findViewById(R.id.vwEmail);
        vwBtwnUp = dialog.findViewById(R.id.vwBtwnUp);
        llPush = dialog.findViewById(R.id.llPush);
        llEmail = dialog.findViewById(R.id.llEmail);
        llBtwnUp = dialog.findViewById(R.id.llBtwnUp);
        txtTimePicker = dialog.findViewById(R.id.txtTimePicker);
        txtTimePickerDown = dialog.findViewById(R.id.txtTimePickerDown);


        txtCalender_.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                Log.e("day_value",day_value);
                Log.e("day_of_week",day_of_week);
                Log.e("day_of_month",String.valueOf(day_of_month));
                Log.e("month_of_year",month_of_year);
                Log.e("outputTime",outputTime);
                if(array_week.length>0){
                    final_day_string = String.join(",", array_week);

                }else{
                    final_day_string="";
                }


                if(array_month.length>0){
                    final_month_string = String.join(",", array_month);

                }else{
                    final_month_string="";
                }
                Log.e("final_month_string",final_month_string);

                Log.e("final_day_string",final_day_string);

                try {

                    if (null == habitname || "".equalsIgnoreCase(habitname)) {


                        Calendar beginTime = Calendar.getInstance();
                        long startMillis = beginTime.getTimeInMillis();
                        Calendar endTime = Calendar.getInstance();
                        endTime.add(Calendar.HOUR, 1);
                        long endMillis = endTime.getTimeInMillis();

//                        LocalTime localTime = LocalTime.parse(txtTimePicker.getText());
//                        int timeInMinutes = localTime.toSecondOfDay()/60;

                        String time = txtTimePicker.getText().toString();
                        String[] parts = time.split(":");
                        int hours = Integer.parseInt(parts[0]);
                        String a=parts[1];
                        String[] parts2 = a.split(" ");
                        int minutes = Integer.parseInt(parts2[0]);
                        int timeInMinutes = (hours * 60) + minutes;
                        long totatmiliSecond = timeInMinutes * 60 * 1000;
                        int timeInminute_end=timeInMinutes+60;
                        long timeinmilisecond_end=timeInminute_end * 60 * 1000;
                        Log.e("starttime",String.valueOf(timeInMinutes));


                        Intent intent = new Intent(Intent.ACTION_INSERT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra(CalendarContract.Events.DTSTART, totatmiliSecond);
                       // intent.putExtra(CalendarContract.Events.DTEND, timeinmilisecond_end);
                        intent.putExtra(CalendarContract.Events.DURATION, "PT1H");

                        intent.putExtra("beginTime", totatmiliSecond);
                        //intent.putExtra("endTime", timeinmilisecond_end);
                        intent.putExtra("allDay", true);
                        if ("daily".equalsIgnoreCase(day_value)) {
//                            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=30");
                            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=30");
                        } else if ("t_daily".equalsIgnoreCase(day_value)) {
                            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=2;INTERVAL=12");
//                            FREQ=DAILY;INTERVAL=12

                        } else if ("fornight".equalsIgnoreCase(day_value)) {
                            if("".equalsIgnoreCase(final_day_string)){
                                intent .putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;INTERVAL=2");
                            }else{
                                String common_str="FREQ=WEEKLY;INTERVAL=2;BYDAY="+final_day_string+";";
                                Log.e("common_str",common_str);
                                intent.putExtra(CalendarContract.Events.RRULE, common_str);
                            }
                            // intent .putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;INTERVAL=2");

                        } else if ("weekly".equalsIgnoreCase(day_value)) {


                            if("".equalsIgnoreCase(final_day_string)){
                                intent .putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY");
                            }else{
                                String common_str="FREQ=MONTHLY;BYDAY="+final_day_string+";";
                                Log.e("common_str",common_str);
                                intent .putExtra(CalendarContract.Events.RRULE, common_str);

                            }


                        } else if ("monthly".equalsIgnoreCase(day_value)) {
                            if (1 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=1;");

                            } else if (2 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=2;");

                            } else if (3 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=3;");

                            } else if (4 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=4;");

                            } else if (5 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=5;");

                            } else if (6 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=6;");

                            } else if (7 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=7;");

                            } else if (8 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=8;");

                            } else if (9 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=9;");

                            } else if (10 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=10;");

                            } else if (11 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=11;");

                            } else if (12 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=12;");

                            } else if (13 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=13;");

                            } else if (14 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=14;");

                            } else if (15 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=15;");

                            } else if (16 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=16;");

                            } else if (17 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=17;");

                            } else if (18 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=18;");

                            } else if (19 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=19;");

                            } else if (20 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=20;");

                            } else if (21 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=21;");

                            } else if (22 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=22;");

                            } else if (23 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=23;");

                            } else if (24 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=24;");

                            } else if (25 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=25;");

                            } else if (26 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=26;");

                            } else if (27 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=27;");

                            } else if (28 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=28;");

                            } else if (29 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=29;");

                            } else if (30 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=30;");

                            } else {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=1;");

                            }

                            // intent .putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY");

                        } else if ("yearly".equalsIgnoreCase(day_value)) {


                            if("".equalsIgnoreCase(final_month_string)){
                                intent .putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
                            }else{
                                String common_str="FREQ=YEARLY;COUNT=1;BYMONTH="+final_month_string;
                                Log.e("common_str",common_str);
                                intent.putExtra(CalendarContract.Events.RRULE, common_str);

                            }

//                            intent .putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

                        }else{
                            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=30");

                        }

                        intent.putExtra("title", "Test habit");
                        intent.putExtra("description", "Test habit");
                        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                        intent.putExtra(CalendarContract.Events.HAS_ALARM, 1);
                        intent.putExtra("eventLocation", "My location");
                        startActivity(intent);
                    } else {

                        Calendar beginTime = Calendar.getInstance();
                        long startMillis = beginTime.getTimeInMillis();
                        Calendar endTime = Calendar.getInstance();
                        endTime.add(Calendar.HOUR, 2160);
                        long endMillis = endTime.getTimeInMillis();

//                        LocalTime localTime = LocalTime.parse(txtTimePicker.getText());
//                        int timeInMinutes = localTime.toSecondOfDay()/60;


                        String time = txtTimePicker.getText().toString();
                        String[] parts = time.split(":");
                        int hours = Integer.parseInt(parts[0]);
                        String a=parts[1];
                        String[] parts2 = a.split(" ");
                        int minutes = Integer.parseInt(parts2[0]);
                        int timeInMinutes = (hours * 60) + minutes;
                        long totatmiliSecond = timeInMinutes * 60 * 1000;
                        int timeInminute_end=timeInMinutes+60;
                        long timeinmilisecond_end=timeInminute_end * 60 * 1000;
                        Log.e("starttime",String.valueOf(timeInMinutes));

                        Intent intent = new Intent(Intent.ACTION_INSERT);
                        intent.setType("vnd.android.cursor.item/event");
                        //intent.putExtra(CalendarContract.Events.DTSTART, totatmiliSecond);
//                        intent.putExtra(CalendarContract.Events.DTEND, timeinmilisecond_end);
                      //  intent.putExtra(CalendarContract.Events.DURATION, "PT1H");
                        intent.putExtra("beginTime", startMillis);
                        intent.putExtra("endTime", endMillis);
                        intent.putExtra("allDay", true);
                        if ("daily".equalsIgnoreCase(day_value)) {
//                            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=30;DTSTART="+outputTime);
                             intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=30");
                        } else if ("t_daily".equalsIgnoreCase(day_value)) {
                            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=2");
//                            FREQ=DAILY;INTERVAL=12

                        } else if ("fornight".equalsIgnoreCase(day_value)) {

                            if("".equalsIgnoreCase(final_day_string)){
                                intent .putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;INTERVAL=2");
                            }else{
                                String common_str="FREQ=WEEKLY;INTERVAL=2;BYDAY="+final_day_string+";";
                                Log.e("common_str",common_str);
                                intent.putExtra(CalendarContract.Events.RRULE, common_str);
                            }

                            // intent .putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;INTERVAL=2");

                        } else if ("weekly".equalsIgnoreCase(day_value)) {

                            if("".equalsIgnoreCase(final_day_string)){
                                intent .putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY");
                            }else{
                                String common_str="FREQ=MONTHLY;BYDAY="+final_day_string+";";
                                Log.e("common_str",common_str);
                                intent .putExtra(CalendarContract.Events.RRULE, common_str);
                            }

                            // intent .putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY");

                        } else if ("monthly".equalsIgnoreCase(day_value)) {
                            if (1 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=1;");

                            } else if (2 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=2;");

                            } else if (3 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=3;");

                            } else if (4 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=4;");

                            } else if (5 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=5;");

                            } else if (6 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=6;");

                            } else if (7 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=7;");

                            } else if (8 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=8;");

                            } else if (9 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=9;");

                            } else if (10 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=10;");

                            } else if (11 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=11;");

                            } else if (12 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=12;");

                            } else if (13 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=13;");

                            } else if (14 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=14;");

                            } else if (15 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=15;");

                            } else if (16 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=16;");

                            } else if (17 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=17;");

                            } else if (18 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=18;");

                            } else if (19 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=19;");

                            } else if (20 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=20;");

                            } else if (21 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=21;");

                            } else if (22 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=22;");

                            } else if (23 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=23;");

                            } else if (24 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=24;");

                            } else if (25 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=25;");

                            } else if (26 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=26;");

                            } else if (27 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=27;");

                            } else if (28 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=28;");

                            } else if (29 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=29;");

                            } else if (30 == day_of_month) {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=30;");

                            } else {
                                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY;BYMONTHDAY=1;");

                            }

                            // intent .putExtra(CalendarContract.Events.RRULE, "FREQ=MONTHLY");

                        } else if ("yearly".equalsIgnoreCase(day_value)) {


                            if("".equalsIgnoreCase(final_month_string)){
                                intent .putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
                            }else{
                                String common_str="FREQ=YEARLY;COUNT=1;BYMONTH="+final_month_string;
                                Log.e("common_str",common_str);
                                intent.putExtra(CalendarContract.Events.RRULE, common_str);

                            }


//                            intent .putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

                        }else{
                            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=30");

                        }


                        intent.putExtra("title", habitname);
                        intent.putExtra("description", habitname);
                        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                        intent.putExtra(CalendarContract.Events.HAS_ALARM, 1);
                        intent.putExtra("eventLocation", "My location");
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("starttime",e.getMessage());
                }

            }
        });


        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (push_notification) {
                    dismiss();
                } else {
                    dataJsonReq = null;
                    mListener.setOnSubmitListener(dataJsonReq);
                    dismiss();
                }
            }
        });

        lstReminderMonth = new ArrayList<>();
        lstAmPm = new ArrayList<>();
        for (int p = 1; p <= 30; p++) {
            lstReminderMonth.add(p + " Day of Month");
        }
        for (int p = 1; p < 12; p++) {
            lstAmPm.add(p + " am");
        }
        lstAmPm.add(12 + " pm");
        for (int p = 1; p < 12; p++) {
            lstAmPm.add(p + " pm");
        }
        lstAmPm.add(12 + " am");
        List<String> lstBtwn = Arrays.asList(arrBtwn);

        ArrayAdapter<String> adapterReminderFreq = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_textbold, arrFreq);
        adapterReminderFreq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterReminderFreqMonth = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_textbold, lstReminderMonth);
        adapterReminderFreqMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterBetween = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_textbold, lstBtwn);
        adapterBetween.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterAmPm = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_textbold, lstAmPm);
        adapterAmPm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spReminderFrequency.setAdapter(adapterReminderFreq);
        spMonth.setAdapter(adapterReminderFreqMonth);
        spBtwn.setAdapter(adapterBetween);
        spAmPm.setAdapter(adapterAmPm);
        spAmPmDown.setAdapter(adapterAmPm);
        spReminderFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (reminderFrequencyCheck != 0) {
                    spReminderFrequency.setVisibility(View.VISIBLE);
                    llReminderFrequencySpinner.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setVisibility(View.VISIBLE);
                }
                reminderFrequencyCheck++;
                vwLLReminder.setVisibility(View.GONE);
                llWk.setVisibility(View.GONE);
                vwDay.setVisibility(View.GONE);
                llDay.setVisibility(View.GONE);
                llYear.setVisibility(View.GONE);
                vwWK.setVisibility(View.GONE);
                vwYear.setVisibility(View.GONE);
                twiceDailyBool = false;
                if (position == 0) {

                    day_value = "daily";
                    txtReminderFrequencyType.setText("Daily");
                    twiceDailyBool = false;
                    if (betweenTwiceBetween) {
                        llBtwnDown.setVisibility(View.VISIBLE);
//                        llBtwnDown.setVisibility(View.GONE);

                         vwDown.setVisibility(View.VISIBLE);
//                        vwDown.setVisibility(View.GONE);
                    } else {
                        llBtwnDown.setVisibility(View.GONE);
                        vwDown.setVisibility(View.GONE);
                    }
                    try {
                        dataJsonReq.put("FrequencyId", 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if (position == 1) {
                    day_value = "t_daily";
                    txtReminderFrequencyType.setText("Twice Daily");
                    twiceDailyBool = true;
                    llBtwnDown.setVisibility(View.VISIBLE);
//                    llBtwnDown.setVisibility(View.GONE);

                     vwDown.setVisibility(View.VISIBLE);
//                    vwDown.setVisibility(View.GONE);

                    try {
                        dataJsonReq.put("FrequencyId", 2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (position == 2) {
                   /* buttonS.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonM.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonT.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonW.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonTH.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonF.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonSa.setBackgroundResource(R.drawable.circle_gray_month);
*/




                    final_day_string="";
                    array_week_list.clear();
                    array_week=new String[7];

                    day_value = "weekly";
                    vwLLReminder.setVisibility(View.VISIBLE);
                    llWk.setVisibility(View.VISIBLE);
                    twiceDailyBool = false;
                    if (betweenTwiceBetween) {
                        llBtwnDown.setVisibility(View.VISIBLE);
//                        llBtwnDown.setVisibility(View.GONE);

                           vwDown.setVisibility(View.VISIBLE);
//                        vwDown.setVisibility(View.GONE);

                    } else {
                        llBtwnDown.setVisibility(View.GONE);
                        vwDown.setVisibility(View.GONE);
                    }
                    txtReminderFrequencyType.setText("Weekly");
                    try {
                        dataJsonReq.put("FrequencyId", 3);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (position == 3) {

                    /*buttonS.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonM.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonT.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonW.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonTH.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonF.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonSa.setBackgroundResource(R.drawable.circle_gray_month);
*/



                    final_day_string="";
                    array_week_list.clear();
                    array_week=new String[7];

                    day_value = "fornight";
                    vwLLReminder.setVisibility(View.VISIBLE);
                    llWk.setVisibility(View.VISIBLE);
                    twiceDailyBool = false;
                    if (betweenTwiceBetween) {
                        llBtwnDown.setVisibility(View.VISIBLE);
//                        llBtwnDown.setVisibility(View.GONE);

                         vwDown.setVisibility(View.VISIBLE);
//                        vwDown.setVisibility(View.GONE);

                    } else {
                        llBtwnDown.setVisibility(View.GONE);
                        vwDown.setVisibility(View.GONE);
                    }
                    txtReminderFrequencyType.setText("Fortnightly");
                    try {
                        dataJsonReq.put("FrequencyId", 4);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (position == 4) {
                    day_value = "monthly";
                    vwYear.setVisibility(View.VISIBLE);
                    vwDay.setVisibility(View.VISIBLE);
                    twiceDailyBool = false;
                    if (betweenTwiceBetween) {
                        llBtwnDown.setVisibility(View.VISIBLE);
//                        llBtwnDown.setVisibility(View.GONE);

                         vwDown.setVisibility(View.VISIBLE);
//                        vwDown.setVisibility(View.GONE);

                    } else {
                        llBtwnDown.setVisibility(View.GONE);
                        vwDown.setVisibility(View.GONE);
                    }
                    llDay.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Monthly");
                    try {
                        dataJsonReq.put("FrequencyId", 5);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (position == 5) {

                    final_month_string="";
                   /* buttonJan.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonFeb.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonMar.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonApr.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonMay.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonJun.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonJul.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonAug.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonSep.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonOct.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonNov.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonDec.setBackgroundResource(R.drawable.circle_gray_month);
*/

                    final_month_string="";
                    array_month_list.clear();
                    array_month=new String[12];
                    day_value = "yearly";

                    vwDay.setVisibility(View.VISIBLE);
                    llDay.setVisibility(View.VISIBLE);

                    vwWK.setVisibility(View.VISIBLE);
                    vwYear.setVisibility(View.VISIBLE);
                    llYear.setVisibility(View.VISIBLE);
                    twiceDailyBool = false;
                    if (betweenTwiceBetween) {
                        llBtwnDown.setVisibility(View.VISIBLE);

                         vwDown.setVisibility(View.VISIBLE);

                    } else {
                        llBtwnDown.setVisibility(View.GONE);
                        vwDown.setVisibility(View.GONE);
                    }
                    txtReminderFrequencyType.setText("Yearly");
                    try {
                        dataJsonReq.put("FrequencyId", 6);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    day_of_month = position + 1;
                    dataJsonReq.put("MonthReminder", position + 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    spMonth.setVisibility(View.VISIBLE);
                    txtReminderDayFrequencyMonth.setVisibility(View.GONE);
                    txtReminderDayFrequencyMonth.setText(spMonth.getSelectedItem().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        llDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtReminderFrequencyType.setVisibility(View.GONE);
                spReminderFrequency.setVisibility(View.VISIBLE);
                llReminderFrequencySpinner.setVisibility(View.VISIBLE);
            }
        });
        txtReminderDayFrequencyMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtReminderDayFrequencyMonth.setVisibility(View.GONE);
                spMonth.setVisibility(View.VISIBLE);
            }
        });
        llAmPmUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAmPmUp.setVisibility(View.GONE);
                spAmPm.setVisibility(View.VISIBLE);


            }
        });
        llAmPmDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAmPmDownwn.setVisibility(View.GONE);
                //spAmPmDown.setVisibility(View.VISIBLE);

            }
        });
        spBtwn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    dataJsonReq.put("ReminderOption", (position + 1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (twiceDailyBool) {
//                    llBtwnDown.setVisibility(View.GONE);

//                    vwDown.setVisibility(View.GONE);

                } else {
                    if (position == 0) {
                        betweenTwiceBetween = false;
//                        llBtwnDown.setVisibility(View.GONE);
//                        vwDown.setVisibility(View.GONE);
                    } else {
                        betweenTwiceBetween = true;
                        llBtwnDown.setVisibility(View.VISIBLE);
//                        llBtwnDown.setVisibility(View.GONE);

                          vwDown.setVisibility(View.VISIBLE);
//                        vwDown.setVisibility(View.GONE);


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spAmPm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                txtAmPmUp.setVisibility(View.GONE);
                txtAmPmUp.setText(lstAmPm.get(position));
                //spAmPm.setVisibility(View.VISIBLE);
                try {
                    dataJsonReq.put("ReminderAt1", (position + 1) + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spAmPmDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                txtAmPmDownwn.setVisibility(View.GONE);
                txtAmPmDownwn.setText(lstAmPm.get(position));
                //spAmPmDown.setVisibility(View.VISIBLE);
                try {
                    dataJsonReq.put("ReminderAt2", (position + 1) + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtTimePicker.setOnClickListener(view -> {
            TimePickerControllerWithTextView pickerController = new TimePickerControllerWithTextView(getActivity(), txtTimePicker, "", "");
            inputTime = txtTimePicker.getText().toString();
            outputTime = formattingdate();

        });

        txtTimePickerDown.setOnClickListener(view -> {
            TimePickerControllerWithTextView pickerController = new TimePickerControllerWithTextView(getActivity(), txtTimePickerDown, "", "");
            inputTime_2nd = txtTimePickerDown.getText().toString();
            outputTime_2nd = formattingdate_2nd();
        });

        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolS) {

                            List<String> list = new ArrayList<String>(Arrays.asList(array_week));
                           list.remove("item2");
                             Log.e("array_week_list",String.valueOf(array_week_list.size()));
                           if(!array_week_list.contains("SU")){
                               array_week_list.add("SU");
                               Log.e("array_week_list",String.valueOf(array_week_list.size()));

                               array_week = array_week_list.toArray(new String[0]);
                               Log.e("array_week",String.valueOf(array_week.length));

                           }

                    day_of_week = "sun";
                    boolS = true;
                    buttonS.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Sunday", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    if(array_week_list.contains("SU")){
                        array_week_list.remove("SU");
                        Log.e("array_week_list",String.valueOf(array_week_list.size()));

                        array_week = array_week_list.toArray(new String[0]);
                        Log.e("array_week",String.valueOf(array_week.length));

                    }
                    day_of_week = "sun";
                    boolS = false;
                    buttonS.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Sunday", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        buttonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolM) {
                    Log.e("array_week_list",String.valueOf(array_week_list.size()));

                    if(!array_week_list.contains("MO")){
                        array_week_list.add("MO");
                        Log.e("array_week_list",String.valueOf(array_week_list.size()));

                        array_week = array_week_list.toArray(new String[0]);
                        Log.e("array_week",String.valueOf(array_week.length));

                    }
                    day_of_week = "mon";
                    boolM = true;
                    buttonM.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Monday", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    if(array_week_list.contains("MO")){
                        array_week_list.remove("MO");
                        array_week = array_week_list.toArray(new String[0]);
                        Log.e("array_week",String.valueOf(array_week.length));

                    }

                    day_of_week = "sun";
                    boolM = false;
                    buttonM.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Monday", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        buttonT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolT) {
                    Log.e("array_week_list",String.valueOf(array_week_list.size()));

                    if(!array_week_list.contains("TU")){
                        array_week_list.add("TU");
                        Log.e("array_week_list",String.valueOf(array_week_list.size()));

                        array_week = array_week_list.toArray(new String[0]);
                        Log.e("array_week",String.valueOf(array_week.length));

                    }
                    day_of_week = "tue";
                    boolT = true;
                    buttonT.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Tuesday", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_week_list.contains("TU")){
                        array_week_list.remove("TU");
                        array_week = array_week_list.toArray(new String[0]);
                    }
                    day_of_week = "sun";
                    boolT = false;
                    buttonT.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Tuesday", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolW) {
                    if(!array_week_list.contains("WE")){
                        array_week_list.add("WE");
                        array_week = array_week_list.toArray(new String[0]);
                    }
                    day_of_week = "wed";
                    boolW = true;
                    buttonW.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Wednesday", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_week_list.contains("WE")){
                        array_week_list.remove("WE");
                        array_week = array_week_list.toArray(new String[0]);
                    }
                    day_of_week = "sun";
                    boolW = false;
                    buttonW.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Wednesday", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolTH) {
                    if(!array_week_list.contains("TH")){
                        array_week_list.add("TH");
                        array_week = array_week_list.toArray(new String[0]);
                    }
                    day_of_week = "thu";
                    boolTH = true;
                    buttonTH.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Thursday", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_week_list.contains("TH")){
                        array_week_list.remove("TH");
                        array_week = array_week_list.toArray(new String[0]);
                    }
                    day_of_week = "sun";
                    boolTH = false;
                    buttonTH.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Thursday", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolF) {
                    if(!array_week_list.contains("FR")){
                        array_week_list.add("FR");
                        array_week = array_week_list.toArray(new String[0]);
                    }
                    day_of_week = "fri";
                    boolF = true;
                    buttonF.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Friday", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_week_list.contains("FR")){
                        array_week_list.remove("FR");
                        array_week = array_week_list.toArray(new String[0]);
                    }
                    day_of_week = "sun";
                    boolF = false;
                    buttonF.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Friday", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        buttonSa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolSa) {
                    if(!array_week_list.contains("SA")){
                        array_week_list.add("SA");
                        array_week = array_week_list.toArray(new String[0]);
                    }
                    day_of_week = "sat";
                    boolSa = true;
                    buttonSa.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Saturday", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_week_list.contains("SA")){
                        array_week_list.remove("SA");
                        array_week = array_week_list.toArray(new String[0]);
                    }
                    day_of_week = "sun";
                    boolSa = false;
                    buttonSa.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Saturday", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        buttonJan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolJan) {
                    if(!array_month_list.contains("1")){
                        array_month_list.add("1");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolJan = true;
                    buttonJan.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("January", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("1")){
                        array_month_list.remove("1");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolJan = false;
                    buttonJan.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("January", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonFeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolFeb) {
                    if(!array_month_list.contains("2")){
                        array_month_list.add("2");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "feb";
                    boolFeb = true;
                    buttonFeb.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("February", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("2")){
                        array_month_list.remove("2");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolFeb = false;
                    buttonFeb.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("February", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonMar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolMar) {
                    if(!array_month_list.contains("3")){
                        array_month_list.add("3");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "march";
                    boolMar = true;
                    buttonMar.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("March", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("3")){
                        array_month_list.remove("3");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolMar = false;
                    buttonMar.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("March", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonApr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolApr) {
                    if(!array_month_list.contains("4")){
                        array_month_list.add("4");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "april";
                    boolApr = true;
                    buttonApr.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("April", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("4")){
                        array_month_list.remove("4");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolApr = false;
                    buttonApr.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("April", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonMay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolMay) {
                    if(!array_month_list.contains("5")){
                        array_month_list.add("5");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "may";
                    boolMay = true;
                    buttonMay.setBackgroundResource(R.drawable.circle_blue_month);
                    buttonApr.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("May", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("5")){
                        array_month_list.remove("5");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolMay = false;
                    buttonMay.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonApr.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("May", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonJun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolJun) {
                    if(!array_month_list.contains("6")){
                        array_month_list.add("6");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "june";
                    boolJun = true;
                    buttonJun.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("June", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("6")){
                        array_month_list.remove("6");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolJun = false;
                    buttonJun.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("June", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonJul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolJul) {
                    if(!array_month_list.contains("7")){
                        array_month_list.add("7");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "july";
                    boolJul = true;
                    buttonJul.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("July", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("7")){
                        array_month_list.remove("7");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolJul = false;
                    buttonJul.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("July", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonAug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolAug) {
                    if(!array_month_list.contains("8")){
                        array_month_list.add("8");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "august";
                    boolAug = true;
                    buttonAug.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("August", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("8")){
                        array_month_list.remove("8");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolAug = false;
                    buttonAug.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("August", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonSep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolSep) {
                    if(!array_month_list.contains("9")){
                        array_month_list.add("9");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "sept";
                    boolSep = true;
                    buttonSep.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("September", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("9")){
                        array_month_list.remove("9");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolSep = false;
                    buttonSep.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("September", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonOct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolOct) {
                    if(!array_month_list.contains("10")){
                        array_month_list.add("10");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "oct";
                    boolOct = true;
                    buttonOct.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("October", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("10")){
                        array_month_list.remove("10");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolOct = false;
                    buttonOct.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("October", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonNov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolNov) {
                    if(!array_month_list.contains("11")){
                        array_month_list.add("11");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "nov";
                    boolNov = true;
                    buttonNov.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("November", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("11")){
                        array_month_list.remove("11");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolNov = false;
                    buttonNov.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("November", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolDec) {
                    if(!array_month_list.contains("12")){
                        array_month_list.add("12");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "dec";
                    boolDec = true;
                    buttonDec.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("December", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if(array_month_list.contains("12")){
                        array_month_list.remove("12");
                        array_month = array_month_list.toArray(new String[0]);
                    }
                    month_of_year = "jan";
                    boolDec = false;
                    buttonDec.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("December", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!txtTimePicker.getText().toString().equals("")) {
                    int hour = 0;
                    int minute = 0;
                    if (txtTimePicker.getText().toString().contains("PM")) {
                        String str = txtTimePicker.getText().toString();
                        String[] arrTime = str.split(":");
                        String strHour = arrTime[0];
                        String strMinAm = arrTime[1];
                        String[] arrMin = strMinAm.split("PM");
                        String strMin = arrMin[0].trim();
                        hour = Integer.parseInt(strHour);
                        if (hour != 12) {
                            hour = hour + 12;
                        }
                        minute = Integer.parseInt(strMin);
                    } else {
                        String str = txtTimePicker.getText().toString();
                        String[] arrTime = str.split(":");
                        String strHour = arrTime[0];
                        String strMinAm = arrTime[1];
                        String[] arrMin = strMinAm.split("AM");
                        String strMin = arrMin[0].trim();
                        hour = Integer.parseInt(strHour);
                        if (hour == 12) {
                            hour = hour + 12;
                        }
                        minute = Integer.parseInt(strMin);
                    }
                    Integer totatSecond = ((hour * 60) + minute) * 60;


                    Log.e("REMINDERAT1", totatSecond + ">>>>>>>>");
                    try {
                        dataJsonReq.put("ReminderAt1", totatSecond);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                if (txtTimePickerDown.getVisibility() == View.VISIBLE && !txtTimePickerDown.getText().toString().equals("")) {
                    int hour = 0;
                    int minute = 0;
                    if (txtTimePickerDown.getText().toString().contains("PM")) {
                        String str = txtTimePickerDown.getText().toString();
                        String[] arrTime = str.split(":");
                        String strHour = arrTime[0];
                        String strMinAm = arrTime[1];
                        String[] arrMin = strMinAm.split("PM");
                        String strMin = arrMin[0].trim();
                        hour = Integer.parseInt(strHour);
                        if (hour != 12) {
                            hour = hour + 12;
                        }
                        minute = Integer.parseInt(strMin);

                    } else {
                        String str = txtTimePickerDown.getText().toString();
                        String[] arrTime = str.split(":");
                        String strHour = arrTime[0];
                        String strMinAm = arrTime[1];
                        String[] arrMin = strMinAm.split("AM");
                        String strMin = arrMin[0].trim();
                        hour = Integer.parseInt(strHour);
                        if (hour == 12) {
                            hour = hour + 12;
                        }
                        minute = Integer.parseInt(strMin);
                    }
                    Integer totatSecond = ((hour * 60) + minute) * 60;

                    try {
                        dataJsonReq.put("ReminderAt2", totatSecond);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                mListener.setOnSubmitListener(dataJsonReq);
                dismiss();

                //

                if (NotificationManagerCompat.from(getActivity()).areNotificationsEnabled()) {

                } else {
                    // Notifications are not enabled, request the user to enable them
                    showNotificationPermissionDialog(getActivity());
                }


                //

            }
        });
        chkEmailNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        dataJsonReq.put("Email", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        dataJsonReq.put("Email", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
        chkPushNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        dataJsonReq.put("PushNotification", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        dataJsonReq.put("PushNotification", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });


    }


    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private void showNotificationPermissionDialog(final Activity context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enable Notifications");
        builder.setMessage("Please enable notifications for this app in the system settings.");

        // Positive button opens app settings
        builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAppSettings(context);
            }
        });

        // Negative button can be used for other actions or dismissing the dialog
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle cancellation or provide additional actions
            }
        });

        builder.show();
    }
    private void openAppSettings(Activity context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }


}
