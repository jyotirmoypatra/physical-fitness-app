package com.ashysystem.mbhq.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.util.TimePickerControllerWithTextView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by android-krishnendu on 3/16/17.
 */

public class CustomReminderDialogForEditAchievement extends DialogFragment {


    Button mButton;
    EditText mEditText;
    public onSubmitListener mListener;
    String text = "";
    //////////////////////
    private TextView txtReminderFrequencyType,txtReminderDayFrequencyMonth,txtAmPmUp,txtAmPmDownwn;
    private Spinner spReminderFrequency,spMonth,spAmPm,spBtwn,spAmPmDown;
    View vwLLReminder,vwDay,vwYear,vwWK,vwDown;
    private LinearLayout llWk,llDay,llDaily,llYear,llAmPmUp,llAmPmDown,llBtwnDown,llReminderFrequencySpinner,llBtwnUp;
    private boolean twiceDailyBool=false,betweenTwiceBetween=false;
    private Button buttonS,buttonM,buttonT,buttonW,buttonTH,buttonF,buttonSa,buttonJan,buttonFeb,buttonMar,buttonApr,buttonMay,buttonJun,buttonJul,buttonAug,buttonSep,buttonOct,buttonNov,buttonDec;
    private boolean boolS=false,boolM=false,boolT=false,boolW=false,boolTH=false,boolF=false,boolSa=false,boolJan=false,boolFeb=false,boolMar=false,boolApr=false,boolMay=false,boolJun=false,boolJul=false,boolAug=false,boolSep=false,boolOct=false,boolNov=false,boolDec=false;
    private JSONObject dataJsonReq=new JSONObject();
    private CheckBox chkPushNotification,chkEmailNotification;
    private String jsonString="";
    RelativeLayout rlBack;

    String receiveModel="";
    MyAchievementsListInnerModel globalAchievementModel;
    String[] arrFreq={/*"Daily","Twice Daily",*/"Weekly","Fortnightly","Monthly","Yearly","This time next year"};
    String[] arrBtwn={"At","Between","Twice Between"};
    List<String> lstReminderMonth;
    List<String> lstAmPm;
    RelativeLayout btnApply;

    int reminderFrequencyCheck=0;

    TextView txtTimePicker,txtTimePickerDown;

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

        if(getArguments()!=null)
        {
            if(getArguments().containsKey("MODEL"))
            {
                receiveModel=getArguments().getString("MODEL");
                populateJsonForGratitude(receiveModel);
            }else {
                initJson();
            }
        }else {
            initJson();
        }

        return dialog;
    }

    private void initJson() {
        try {
            dataJsonReq.put("FrequencyId",1);
            dataJsonReq.put("MonthReminder",1);
            dataJsonReq.put("ReminderOption",1);
            dataJsonReq.put("ReminderAt1",43200);
            dataJsonReq.put("ReminderAt2",43200);
            dataJsonReq.put("Email",false);
            dataJsonReq.put("PushNotification",false);
            dataJsonReq.put("Sunday",false);
            dataJsonReq.put("Monday",false);
            dataJsonReq.put("Tuesday",false);
            dataJsonReq.put("Wednesday",false);
            dataJsonReq.put("Thursday",false);
            dataJsonReq.put("Friday",false);
            dataJsonReq.put("Saturday",false);
            dataJsonReq.put("January",false);
            dataJsonReq.put("February",false);
            dataJsonReq.put("March",false);
            dataJsonReq.put("April",false);
            dataJsonReq.put("April",false);
            dataJsonReq.put("May",false);
            dataJsonReq.put("June",false);
            dataJsonReq.put("July",false);
            dataJsonReq.put("August",false);
            dataJsonReq.put("September",false);
            dataJsonReq.put("October",false);
            dataJsonReq.put("November",false);
            dataJsonReq.put("December",false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateJsonForGratitude(String receiveModel)
    {
        Gson gson=new Gson();
        globalAchievementModel=gson.fromJson(receiveModel,MyAchievementsListInnerModel.class);
        try {
            dataJsonReq.put("FrequencyId",globalAchievementModel.getFrequencyId());
            dataJsonReq.put("PushNotification",true);
            if(globalAchievementModel.getFrequencyId()!=null && globalAchievementModel.getFrequencyId()!=0)
            {
                if(globalAchievementModel.getFrequencyId()-1==0)
                {
                    txtReminderFrequencyType.setText("Daily");

                }
                else if(globalAchievementModel.getFrequencyId()-1==1)
                {
                    txtReminderFrequencyType.setText("Twice Daily");
                    twiceDailyBool=true;
                    llBtwnDown.setVisibility(View.VISIBLE);
                    vwDown.setVisibility(View.VISIBLE);
                }
                else if(globalAchievementModel.getFrequencyId()-1==2)
                {
                    spReminderFrequency.setSelection(0);
                    txtReminderFrequencyType.setText(arrFreq[0]);

                    vwLLReminder.setVisibility(View.VISIBLE);
                    llWk.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Weekly");
                }
                else if(globalAchievementModel.getFrequencyId()-1==3)
                {
                    spReminderFrequency.setSelection(1);
                    txtReminderFrequencyType.setText(arrFreq[1]);

                    vwLLReminder.setVisibility(View.VISIBLE);
                    llWk.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Fortnightly");
                }
                else if(globalAchievementModel.getFrequencyId()-1==4)
                {
                    spReminderFrequency.setSelection(2);
                    txtReminderFrequencyType.setText(arrFreq[2]);

                    vwYear.setVisibility(View.VISIBLE);
                    vwDay.setVisibility(View.VISIBLE);
                    llDay.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Monthly");
                }
                else if(globalAchievementModel.getFrequencyId()-1==5)
                {
                    spReminderFrequency.setSelection(3);
                    txtReminderFrequencyType.setText(arrFreq[3]);

                    vwWK.setVisibility(View.VISIBLE);
                    vwYear.setVisibility(View.VISIBLE);
                    llYear.setVisibility(View.VISIBLE);
                    llDay.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Yearly");
                }

            }

            dataJsonReq.put("MonthReminder",globalAchievementModel.getMonthReminder());
            if(globalAchievementModel.getMonthReminder()!=null && globalAchievementModel.getMonthReminder()!=0)
            {
                spMonth.setSelection(globalAchievementModel.getMonthReminder()-1);
                txtReminderDayFrequencyMonth.setText(lstReminderMonth.get(globalAchievementModel.getMonthReminder()-1));
            }

            dataJsonReq.put("ReminderOption",globalAchievementModel.getReminderOption());
            if(globalAchievementModel.getReminderOption()!=null && globalAchievementModel.getReminderOption()!=0)
            {
                spBtwn.setSelection(globalAchievementModel.getReminderOption()-1);
                if(globalAchievementModel.getReminderOption()-1!=0)
                {
                    betweenTwiceBetween=true;
                }
                if(betweenTwiceBetween)
                {
                    llBtwnDown.setVisibility(View.VISIBLE);
                    vwDown.setVisibility(View.VISIBLE);
                }else {
                    llBtwnDown.setVisibility(View.GONE);
                    vwDown.setVisibility(View.GONE);
                }
            }

            dataJsonReq.put("ReminderAt1",globalAchievementModel.getReminderAt1());
            if(globalAchievementModel.getReminderAt1()!=null)
            {
                //spAmPm.setSelection(Integer.parseInt(globalAchievementModel.getReminderAt1())-1);
                //txtAmPmUp.setText(lstAmPm.get(Integer.parseInt(globalAchievementModel.getReminderAt1())-1));

                Integer at1Hour = 0;
                Integer at1Minute = 0;
                Integer at1Second = 0;

                at1Second = (globalAchievementModel.getReminderAt1() % 60);
                Integer totalMinute = (globalAchievementModel.getReminderAt1() / 60);
                at1Minute = totalMinute % 60;
                at1Hour = totalMinute / 60;

                String strTime ="";
                boolean boolPm = false;
                if(at1Hour == 12)
                {
                    at1Hour = 12;
                    boolPm = true;
                }else if(at1Hour>12)
                {
                    at1Hour = at1Hour - 12;
                    boolPm = true;
                }

                strTime = pad(at1Hour) + ":" + pad(at1Minute);

                if(boolPm)
                {
                    strTime = strTime + " PM";
                }else {
                    strTime = strTime + " AM";
                }
                txtTimePicker.setText(strTime);

            }

            dataJsonReq.put("ReminderAt2",globalAchievementModel.getReminderAt2());
            if(globalAchievementModel.getReminderAt2()!=null)
            {
                //spAmPmDown.setSelection(Integer.parseInt(globalAchievementModel.getReminderAt2())-1);
                //txtAmPmDownwn.setText(lstAmPm.get(Integer.parseInt(globalAchievementModel.getReminderAt2())-1));

                Integer at2Hour = 0;
                Integer at2Minute = 0;
                Integer at2Second = 0;

                if(globalAchievementModel.getReminderAt2()!=null)
                {
                    at2Second = (globalAchievementModel.getReminderAt2() % 60);
                    Integer totalMinute = (globalAchievementModel.getReminderAt2() / 60);
                    at2Minute = totalMinute % 60;
                    at2Hour = totalMinute / 60;
                }

                String strTime ="";
                boolean boolPm = false;
                if(at2Hour == 12)
                {
                    at2Hour = 12;
                    boolPm = true;
                }else if(at2Hour>12)
                {
                    at2Hour = at2Hour - 12;
                    boolPm = true;
                }

                strTime = pad(at2Hour) + ":" + pad(at2Minute);

                if(boolPm)
                {
                    strTime = strTime + " PM";
                }else {
                    strTime = strTime + " AM";
                }
                txtTimePickerDown.setText(strTime);

            }

            dataJsonReq.put("Email",globalAchievementModel.getEmail());
            if(globalAchievementModel.getEmail()!=null && globalAchievementModel.getEmail())
            {
                chkEmailNotification.setChecked(true);
            }

            dataJsonReq.put("PushNotification",globalAchievementModel.getPushNotification());
            if(globalAchievementModel.getPushNotification()!=null && globalAchievementModel.getPushNotification())
            {
                chkPushNotification.setChecked(true);
            }

            dataJsonReq.put("Sunday",globalAchievementModel.getSunday());
            if(globalAchievementModel.getSunday()!=null && globalAchievementModel.getSunday())
            {
                boolS=true;
                buttonS.setBackground(null);
                buttonS.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("Monday",globalAchievementModel.getMonday());
            if(globalAchievementModel.getMonday()!=null && globalAchievementModel.getMonday())
            {
                boolM=true;
                buttonM.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("Tuesday",globalAchievementModel.getTuesday());
            if(globalAchievementModel.getTuesday()!=null && globalAchievementModel.getTuesday())
            {
                boolT=true;
                buttonT.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("Wednesday",globalAchievementModel.getWednesday());
            if(globalAchievementModel.getWednesday()!=null && globalAchievementModel.getWednesday())
            {
                boolW=true;
                buttonW.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("Thursday",globalAchievementModel.getThursday());
            if(globalAchievementModel.getThursday()!=null && globalAchievementModel.getThursday())
            {
                boolTH=true;
                buttonTH.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("Friday",globalAchievementModel.getFriday());
            if(globalAchievementModel.getFriday()!=null && globalAchievementModel.getFriday())
            {
                boolF=true;
                buttonF.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("Saturday",globalAchievementModel.getSaturday());
            if(globalAchievementModel.getSaturday()!=null && globalAchievementModel.getSaturday())
            {
                boolSa=true;
                buttonSa.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("January",globalAchievementModel.getJanuary());
            if(globalAchievementModel.getJanuary()!=null && globalAchievementModel.getJanuary())
            {
                boolJan=true;
                buttonJan.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("February",globalAchievementModel.getFebruary());
            if(globalAchievementModel.getFebruary()!=null && globalAchievementModel.getFebruary())
            {
                boolFeb=true;
                buttonFeb.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("March",globalAchievementModel.getMarch());
            if(globalAchievementModel.getMarch()!=null && globalAchievementModel.getMarch())
            {
                boolMar=true;
                buttonMar.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("April",globalAchievementModel.getApril());
            if(globalAchievementModel.getApril()!=null && globalAchievementModel.getApril())
            {
                boolApr=true;
                buttonApr.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("May",globalAchievementModel.getMay());
            if(globalAchievementModel.getMay()!=null && globalAchievementModel.getMay())
            {
                boolMay=true;
                buttonMay.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("June",globalAchievementModel.getJune());
            if(globalAchievementModel.getJune()!=null && globalAchievementModel.getJune())
            {
                boolJun=true;
                buttonJun.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("July",globalAchievementModel.getJuly());
            if(globalAchievementModel.getJuly()!=null && globalAchievementModel.getJuly())
            {
                boolJul=true;
                buttonJul.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("August",globalAchievementModel.getAugust());
            if(globalAchievementModel.getAugust()!=null && globalAchievementModel.getAugust())
            {
                boolAug=true;
                buttonAug.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("September",globalAchievementModel.getSeptember());
            if(globalAchievementModel.getSeptember()!=null && globalAchievementModel.getSeptember())
            {
                boolSep=true;
                buttonSep.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("October",globalAchievementModel.getOctober());
            if(globalAchievementModel.getOctober()!=null && globalAchievementModel.getOctober())
            {
                boolOct=true;
                buttonOct.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("November",globalAchievementModel.getNovember());
            if(globalAchievementModel.getNovember()!=null && globalAchievementModel.getNovember())
            {
                boolNov=true;
                buttonNov.setBackgroundResource(R.drawable.circle_blue_month);
            }

            dataJsonReq.put("December",globalAchievementModel.getDecember());
            if(globalAchievementModel.getDecember()!=null && globalAchievementModel.getDecember())
            {
                boolDec=true;
                buttonDec.setBackgroundResource(R.drawable.circle_blue_month);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView(Dialog dialog) {

        chkPushNotification=(CheckBox)dialog.findViewById(R.id.chkPushNotification);
        chkEmailNotification=(CheckBox)dialog.findViewById(R.id.chkEmailNotification);
        txtReminderFrequencyType=(TextView)dialog.findViewById(R.id.txtReminderFrequencyType);
        txtAmPmUp=(TextView)dialog.findViewById(R.id.txtAmPmUp);
        spReminderFrequency=(Spinner)dialog.findViewById(R.id.spReminderFrequency);
        vwLLReminder=(View)dialog.findViewById(R.id.vwLLReminder);
        vwDay=(View)dialog.findViewById(R.id.vwDay);
        vwDown=(View)dialog.findViewById(R.id.vwDown);
        llWk=(LinearLayout)dialog.findViewById(R.id.llWk);
        llDay=(LinearLayout)dialog.findViewById(R.id.llDay);
        llYear=(LinearLayout)dialog.findViewById(R.id.llYear);
        llDaily=(LinearLayout)dialog.findViewById(R.id.llDaily);
        llBtwnDown=(LinearLayout)dialog.findViewById(R.id.llBtwnDown);
        llAmPmUp=(LinearLayout)dialog.findViewById(R.id.llAmPmUp);
        llReminderFrequencySpinner=(LinearLayout)dialog.findViewById(R.id.llReminderFrequencySpinner);
        txtReminderDayFrequencyMonth=(TextView)dialog.findViewById(R.id.txtReminderDayFrequencyMonth);
        txtAmPmDownwn=(TextView)dialog.findViewById(R.id.txtAmPmDownwn);
        spMonth=(Spinner)dialog.findViewById(R.id.spMonth);
        spAmPmDown=(Spinner)dialog.findViewById(R.id.spAmPmDown);
        llAmPmDown=(LinearLayout)dialog.findViewById(R.id.llAmPmDown);
        vwYear=(View)dialog.findViewById(R.id.vwYear);
        vwWK=(View)dialog.findViewById(R.id.vwWK);
        spBtwn=(Spinner)dialog.findViewById(R.id.spBtwn);
        spAmPm=(Spinner)dialog.findViewById(R.id.spAmPm);
        buttonS=(Button) dialog.findViewById(R.id.buttonS);
        buttonM=(Button) dialog.findViewById(R.id.buttonM);
        buttonT=(Button) dialog.findViewById(R.id.buttonT);
        buttonW=(Button) dialog.findViewById(R.id.buttonW);
        buttonTH=(Button) dialog.findViewById(R.id.buttonTH);
        buttonF=(Button) dialog.findViewById(R.id.buttonF);
        buttonSa=(Button) dialog.findViewById(R.id.buttonSa);
        buttonJan=(Button) dialog.findViewById(R.id.buttonJan);
        buttonFeb=(Button) dialog.findViewById(R.id.buttonFeb);
        buttonMar=(Button) dialog.findViewById(R.id.buttonMar);
        buttonApr=(Button) dialog.findViewById(R.id.buttonApr);
        buttonMay=(Button) dialog.findViewById(R.id.buttonMay);
        buttonJun=(Button) dialog.findViewById(R.id.buttonJun);
        buttonJul=(Button) dialog.findViewById(R.id.buttonJul);
        buttonAug=(Button) dialog.findViewById(R.id.buttonAug);
        buttonSep=(Button) dialog.findViewById(R.id.buttonSep);
        buttonOct=(Button) dialog.findViewById(R.id.buttonOct);
        buttonNov=(Button) dialog.findViewById(R.id.buttonNov);
        buttonDec=(Button) dialog.findViewById(R.id.buttonDec);
        btnApply=(RelativeLayout) dialog.findViewById(R.id.btnApply);
        rlBack=(RelativeLayout)dialog.findViewById(R.id.rlBack);
        txtTimePicker = dialog.findViewById(R.id.txtTimePicker);
        txtTimePickerDown = dialog.findViewById(R.id.txtTimePickerDown);
        llBtwnUp = dialog.findViewById(R.id.llBtwnUp);

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        lstReminderMonth=new ArrayList<>();
        lstAmPm=new ArrayList<>();
        for(int p=1;p<=30;p++)
        {
            lstReminderMonth.add(p+" Day of Month");
        }
        for(int p=1;p<12;p++)
        {
            lstAmPm.add(p+" am");
        }
        lstAmPm.add(12+" pm");
        for(int p=1;p<12;p++)
        {
            lstAmPm.add(p+" pm");
        }
        lstAmPm.add(12+" am");
        List<String> lstBtwn= Arrays.asList(arrBtwn);

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

                if(reminderFrequencyCheck!=0)
                {
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
                twiceDailyBool=false;
                /*if(position==0)
                {
                    txtReminderFrequencyType.setText("Daily");
                    twiceDailyBool=false;
                    if(betweenTwiceBetween)
                    {
                        llBtwnDown.setVisibility(View.VISIBLE);
                        vwDown.setVisibility(View.VISIBLE);
                    }else
                    {
                        llBtwnDown.setVisibility(View.GONE);
                        vwDown.setVisibility(View.GONE);
                    }
                    try {
                        dataJsonReq.put("FrequencyId",1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else if(position==1)
                {
                    txtReminderFrequencyType.setText("Twice Daily");
                    twiceDailyBool=true;
                    llBtwnDown.setVisibility(View.VISIBLE);
                    vwDown.setVisibility(View.VISIBLE);
                    try {
                        dataJsonReq.put("FrequencyId",2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else*/ if(position==0)
                {
                    vwLLReminder.setVisibility(View.VISIBLE);
                    llWk.setVisibility(View.VISIBLE);
                    llBtwnUp.setVisibility(View.VISIBLE);
                    twiceDailyBool=false;
                    if(betweenTwiceBetween)
                    {
                        llBtwnDown.setVisibility(View.VISIBLE);
                        vwDown.setVisibility(View.VISIBLE);
                    }else
                    {
                        llBtwnDown.setVisibility(View.GONE);
                        vwDown.setVisibility(View.GONE);
                    }
                    txtReminderFrequencyType.setText("Weekly");
                    try {
                        dataJsonReq.put("FrequencyId",3);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position==1)
                {
                    vwLLReminder.setVisibility(View.VISIBLE);
                    llWk.setVisibility(View.VISIBLE);
                    llBtwnUp.setVisibility(View.VISIBLE);
                    twiceDailyBool=false;
                    if(betweenTwiceBetween)
                    {
                        llBtwnDown.setVisibility(View.VISIBLE);
                        vwDown.setVisibility(View.VISIBLE);
                    }else
                    {
                        llBtwnDown.setVisibility(View.GONE);
                        vwDown.setVisibility(View.GONE);
                    }
                    txtReminderFrequencyType.setText("Fortnightly");
                    try {
                        dataJsonReq.put("FrequencyId",4);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position==2)
                {
                    vwYear.setVisibility(View.VISIBLE);
                    vwDay.setVisibility(View.VISIBLE);
                    llBtwnUp.setVisibility(View.VISIBLE);
                    twiceDailyBool=false;
                    if(betweenTwiceBetween)
                    {
                        llBtwnDown.setVisibility(View.VISIBLE);
                        vwDown.setVisibility(View.VISIBLE);
                    }else
                    {
                        llBtwnDown.setVisibility(View.GONE);
                        vwDown.setVisibility(View.GONE);
                    }
                    llDay.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Monthly");
                    try {
                        dataJsonReq.put("FrequencyId",5);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position==3)
                {
                    vwWK.setVisibility(View.VISIBLE);
                    vwYear.setVisibility(View.VISIBLE);
                    llYear.setVisibility(View.VISIBLE);
                    llBtwnUp.setVisibility(View.VISIBLE);
                    llDay.setVisibility(View.VISIBLE);
                    twiceDailyBool=false;
                    if(betweenTwiceBetween)
                    {
                        llBtwnDown.setVisibility(View.VISIBLE);
                        vwDown.setVisibility(View.VISIBLE);
                    }else
                    {
                        llBtwnDown.setVisibility(View.GONE);
                        vwDown.setVisibility(View.GONE);
                    }
                    txtReminderFrequencyType.setText("Yearly");
                    try {
                        dataJsonReq.put("FrequencyId",6);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(position == 4)
                {
                    vwWK.setVisibility(View.GONE);
                    vwYear.setVisibility(View.GONE);
                    llYear.setVisibility(View.GONE);
                    llDay.setVisibility(View.GONE);
                    llBtwnDown.setVisibility(View.GONE);
                    vwDown.setVisibility(View.GONE);
                    llBtwnUp.setVisibility(View.GONE);

                    Calendar calendar = Calendar.getInstance();
                    int thisMonth = calendar.get(Calendar.MONTH);
                    if(thisMonth == 0)
                    {
                        try {
                            dataJsonReq.put("January",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(thisMonth == 1)
                    {
                        try {
                            dataJsonReq.put("February",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(thisMonth == 2)
                    {
                        try {
                            dataJsonReq.put("March",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(thisMonth == 3)
                    {
                        try {
                            dataJsonReq.put("April",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(thisMonth == 4)
                    {
                        try {
                            dataJsonReq.put("May",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(thisMonth == 5)
                    {
                        try {
                            dataJsonReq.put("June",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(thisMonth == 6)
                    {
                        try {
                            dataJsonReq.put("July",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(thisMonth == 7)
                    {
                        try {
                            dataJsonReq.put("August",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(thisMonth == 8)
                    {
                        try {
                            dataJsonReq.put("September",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(thisMonth == 9)
                    {
                        try {
                            dataJsonReq.put("October",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(thisMonth == 10)
                    {
                        try {
                            dataJsonReq.put("November",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(thisMonth == 11)
                    {
                        try {
                            dataJsonReq.put("December",true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                    if(currentDay >= 30)
                    {
                        try {
                            dataJsonReq.put("MonthReminder",30);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            dataJsonReq.put("MonthReminder",currentDay);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
                    String currentTime = dateFormat.format(calendar.getTime());
                    txtTimePicker.setText(currentTime.toUpperCase());

                    txtReminderFrequencyType.setText("Yearly");
                    try {
                        dataJsonReq.put("FrequencyId",6);
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
                    dataJsonReq.put("MonthReminder",position+1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                spMonth.setVisibility(View.VISIBLE);
                txtReminderDayFrequencyMonth.setVisibility(View.GONE);

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
                //spAmPm.setVisibility(View.VISIBLE);


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
                    dataJsonReq.put("ReminderOption",(position+1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(twiceDailyBool)
                {
                    llBtwnDown.setVisibility(View.VISIBLE);
                    vwDown.setVisibility(View.VISIBLE);
                }
                else
                {
                    if(position==0)
                    {
                        betweenTwiceBetween=false;
                        llBtwnDown.setVisibility(View.GONE);
                        vwDown.setVisibility(View.GONE);
                    }
                    else
                    {
                        betweenTwiceBetween=true;
                        llBtwnDown.setVisibility(View.VISIBLE);
                        vwDown.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        txtTimePicker.setOnClickListener(view -> {
            TimePickerControllerWithTextView pickerController = new TimePickerControllerWithTextView(getActivity(), txtTimePicker, "", "");

        });

        txtTimePickerDown.setOnClickListener(view -> {
            TimePickerControllerWithTextView pickerController = new TimePickerControllerWithTextView(getActivity(), txtTimePickerDown, "", "");
        });

        spAmPm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                txtAmPmUp.setVisibility(View.GONE);
                txtAmPmUp.setText(lstAmPm.get(position));
                //spAmPm.setVisibility(View.VISIBLE);
                try {
                    dataJsonReq.put("ReminderAt1",(position+1)+"");
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
                    dataJsonReq.put("ReminderAt2",(position+1)+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolS)
                {
                    boolS=true;
                    buttonS.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Sunday",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolS=false;
                    buttonS.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Sunday",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        buttonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolM)
                {
                    boolM=true;
                    buttonM.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Monday",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolM=false;
                    buttonM.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Monday",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        buttonT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolT)
                {
                    boolT=true;
                    buttonT.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Tuesday",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolT=false;
                    buttonT.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Tuesday",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolW)
                {
                    boolW=true;
                    buttonW.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Wednesday",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolW=false;
                    buttonW.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Wednesday",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolTH)
                {
                    boolTH=true;
                    buttonTH.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Thursday",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolTH=false;
                    buttonTH.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Thursday",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolF)
                {
                    boolF=true;
                    buttonF.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Friday",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolF=false;
                    buttonF.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Friday",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        buttonSa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolSa)
                {
                    boolSa=true;
                    buttonSa.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("Saturday",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolSa=false;
                    buttonSa.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("Saturday",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonJan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolJan)
                {
                    boolJan=true;
                    buttonJan.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("January",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolJan=false;
                    buttonJan.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("January",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonFeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolFeb)
                {
                    boolFeb=true;
                    buttonFeb.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("February",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolFeb=false;
                    buttonFeb.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("February",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonMar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolMar)
                {
                    boolMar=true;
                    buttonMar.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("March",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolMar=false;
                    buttonMar.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("March",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonApr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolApr)
                {
                    boolApr=true;
                    buttonApr.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("April",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolApr=false;
                    buttonApr.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("April",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonMay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolMay)
                {
                    boolMay=true;
                    buttonMay.setBackgroundResource(R.drawable.circle_blue_month);
                    buttonApr.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("May",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolMay=false;
                    buttonMay.setBackgroundResource(R.drawable.circle_gray_month);
                    buttonApr.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("May",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonJun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolJun)
                {
                    boolJun=true;
                    buttonJun.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("June",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolJun=false;
                    buttonJun.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("June",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonJul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolJul)
                {
                    boolJul=true;
                    buttonJul.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("July",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolJul=false;
                    buttonJul.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("July",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonAug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolAug)
                {
                    boolAug=true;
                    buttonAug.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("August",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolAug=false;
                    buttonAug.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("August",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonSep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolSep)
                {
                    boolSep=true;
                    buttonSep.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("September",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolSep=false;
                    buttonSep.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("September",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonOct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolOct)
                {
                    boolOct=true;
                    buttonOct.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("October",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolOct=false;
                    buttonOct.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("October",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonNov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolNov)
                {
                    boolNov=true;
                    buttonNov.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("November",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolNov=false;
                    buttonNov.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("November",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        buttonDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boolDec)
                {
                    boolDec=true;
                    buttonDec.setBackgroundResource(R.drawable.circle_blue_month);
                    try {
                        dataJsonReq.put("December",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    boolDec=false;
                    buttonDec.setBackgroundResource(R.drawable.circle_gray_month);
                    try {
                        dataJsonReq.put("December",false);
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
                        if(hour != 12)
                        {
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
                        if(hour == 12)
                        {
                            hour = hour + 12;
                        }
                        minute = Integer.parseInt(strMin);
                    }
                    Integer totatSecond = ((hour * 60) + minute) * 60;


                    Log.e("REMINDERAT1",totatSecond+">>>>>>>>");
                    try {
                        dataJsonReq.put("ReminderAt1",totatSecond);
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
                        if(hour != 12)
                        {
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
                        if(hour == 12)
                        {
                            hour = hour +12;
                        }
                        minute = Integer.parseInt(strMin);
                    }
                    Integer totatSecond = ((hour * 60) + minute) * 60;

                    try {
                        dataJsonReq.put("ReminderAt2",totatSecond);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                mListener.setOnSubmitListener(dataJsonReq);
                dismiss();


                if (NotificationManagerCompat.from(getActivity()).areNotificationsEnabled()) {

                } else {
                    // Notifications are not enabled, request the user to enable them
                    showNotificationPermissionDialog(getActivity());
                }
            }
        });
        chkEmailNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    try {
                        dataJsonReq.put("Email",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    try {
                        dataJsonReq.put("Email",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
        chkPushNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    try {
                        dataJsonReq.put("PushNotification",true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    try {
                        dataJsonReq.put("PushNotification",false);
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
