package com.ashysystem.mbhq.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import androidx.fragment.app.DialogFragment;

import com.ashysystem.mbhq.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HabitHackerReminderAddDialog extends DialogFragment {

    Button mButton;
    EditText mEditText;
    public onSubmitListener mListener;
    String text = "";
    //////////////////////
    private TextView txtReminderFrequencyType,txtReminderDayFrequencyMonth,txtAmPmUp,txtAmPmDownwn;
    private Spinner spReminderFrequency,spMonth,spAmPm,spBtwn,spAmPmDown;
    View vwLLReminder,vwDay,vwYear,vwWK,vwDown;
    private LinearLayout llBtwnDown1,llWk,llDay,llDaily,llYear,llAmPmUp,llAmPmDown,llBtwnDown,llReminderFrequencySpinner;
    private boolean twiceDailyBool=false,betweenTwiceBetween=false;
    private Button buttonS,buttonM,buttonT,buttonW,buttonTH,buttonF,buttonSa,buttonJan,buttonFeb,buttonMar,buttonApr,buttonMay,buttonJun,buttonJul,buttonAug,buttonSep,buttonOct,buttonNov,buttonDec;
    private boolean boolS=false,boolM=false,boolT=false,boolW=false,boolTH=false,boolF=false,boolSa=false,boolJan=false,boolFeb=false,boolMar=false,boolApr=false,boolMay=false,boolJun=false,boolJul=false,boolAug=false,boolSep=false,boolOct=false,boolNov=false,boolDec=false;
    private JSONObject dataJsonReq=new JSONObject();
    private CheckBox chkPushNotification,chkEmailNotification;
    private String jsonString="";
    RelativeLayout rlBack;
    int reminderFrequencyCheck=0;
    RelativeLayout btnApply;
    TextView txtReminderSettingsHeading;
    View vwPush,vwEmail,vwBtwnUp;
    LinearLayout llPush,llEmail,llBtwnUp;


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
        initJson();
        initView(dialog);
        return dialog;
    }

    private void initJson() {
        try {
            dataJsonReq.put("FrequencyId",1);
            dataJsonReq.put("MonthReminder",0);
            dataJsonReq.put("ReminderOption",1);
            dataJsonReq.put("ReminderAt1","12");
            dataJsonReq.put("ReminderAt2",0);
            dataJsonReq.put("Email",false);
            dataJsonReq.put("PushNotification",true);
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

    private void initView(Dialog dialog) {
        llBtwnDown1=(LinearLayout)dialog.findViewById(R.id.llBtwnDown1);
        llBtwnDown1.setVisibility(View.GONE);
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
        llReminderFrequencySpinner = (LinearLayout) dialog.findViewById(R.id.llReminderFrequencySpinner);
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
        txtReminderSettingsHeading = dialog.findViewById(R.id.txtReminderSettingsHeading);
        vwPush = dialog.findViewById(R.id.vwPush);
        vwEmail = dialog.findViewById(R.id.vwEmail);
        vwBtwnUp = dialog.findViewById(R.id.vwBtwnUp);
        llPush = dialog.findViewById(R.id.llPush);
        llEmail = dialog.findViewById(R.id.llEmail);
        llBtwnUp = dialog.findViewById(R.id.llBtwnUp);

        if(getArguments()!=null && getArguments().containsKey("HABIT_HACKER_TASK"))
        {
            txtReminderSettingsHeading.setVisibility(View.GONE);
            llPush.setVisibility(View.GONE);
            vwPush.setVisibility(View.GONE);
            llEmail.setVisibility(View.GONE);
            vwEmail.setVisibility(View.GONE);
            llBtwnUp.setVisibility(View.GONE);
            vwBtwnUp.setVisibility(View.GONE);
        }

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        chkPushNotification.setChecked(true);
        try {
            dataJsonReq.put("PushNotification",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] arrFreq={"Daily","Twice Daily","Weekly","Fortnightly","Monthly","Yearly"};
        String[] arrBtwn={"At","Between","Twice Between"};
        List<String> lstReminderMonth=new ArrayList<>();
        final List<String> lstAmPm=new ArrayList<>();
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
        //spReminderFrequency.setSelection(4);
        spReminderFrequency.setSelection(0);
        spMonth.setAdapter(adapterReminderFreqMonth);
        spBtwn.setAdapter(adapterBetween);
        spAmPm.setAdapter(adapterAmPm);
        spAmPmDown.setAdapter(adapterAmPm);
        spReminderFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*spReminderFrequency.setVisibility(View.GONE);
                txtReminderFrequencyType.setVisibility(View.VISIBLE);*/
                if(reminderFrequencyCheck!=0)
                {
                    llReminderFrequencySpinner.setVisibility(View.VISIBLE);
                    spReminderFrequency.setVisibility(View.VISIBLE);
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
                if(position==0)
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
                else if(position==2)
                {
                    vwLLReminder.setVisibility(View.VISIBLE);
                    llWk.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Weekly");
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
                        dataJsonReq.put("FrequencyId",3);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position==3)
                {
                    vwLLReminder.setVisibility(View.VISIBLE);
                    llWk.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Fortnightly");
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
                        dataJsonReq.put("FrequencyId",4);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position==4)
                {
                    vwYear.setVisibility(View.VISIBLE);
                    vwDay.setVisibility(View.VISIBLE);
                    llDay.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Monthly");
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
                        dataJsonReq.put("FrequencyId",5);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position==5)
                {
                    vwWK.setVisibility(View.VISIBLE);
                    vwYear.setVisibility(View.VISIBLE);
                    llYear.setVisibility(View.VISIBLE);
                    txtReminderFrequencyType.setText("Yearly");
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
                        dataJsonReq.put("FrequencyId",6);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(getArguments()!=null && getArguments().containsKey("HABIT_HACKER_TASK"))
                {
                    llBtwnUp.setVisibility(View.GONE);
                    vwBtwnUp.setVisibility(View.GONE);
                    llBtwnDown.setVisibility(View.GONE);
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
                spAmPmDown.setVisibility(View.VISIBLE);

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

        spAmPm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                txtAmPmUp.setVisibility(View.GONE);
                txtAmPmUp.setText(lstAmPm.get(position));
                spAmPm.setVisibility(View.VISIBLE);
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
                spAmPmDown.setVisibility(View.VISIBLE);
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
                mListener.setOnSubmitListener(dataJsonReq);
                dismiss();
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
                    dismiss();
                    try {
                        dataJsonReq.put("PushNotification",false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });



    }

}
