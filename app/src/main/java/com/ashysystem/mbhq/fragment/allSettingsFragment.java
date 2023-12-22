package com.ashysystem.mbhq.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashysystem.mbhq.R;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
//added jyotirmoy 22.09.22
public class allSettingsFragment extends Fragment {

    CheckBox reqPass,dailyPopup,journalPopUp;
    boolean checkedValue=false;
    boolean popCheckedValue=false;
    boolean journalPopChecked=false;
    public static final String SHARED_PREF_NAME_PASS ="PassReq";
    public static final String KEY_CHECKBOX ="CheckBox";

    public static final String DAILY_POP_UP= "DailyPopUp";
    public static final String KEY_DAILY_POPUP="PopUp";

    public static final String KEY_DAILY_POPUP_JOURNAL="JournalPopUp";


    public allSettingsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_settings, container, false);


        reqPass = (CheckBox) v.findViewById(R.id.requiredPassword);
        dailyPopup=(CheckBox)v.findViewById(R.id.dailyPopup);
        journalPopUp=(CheckBox)v.findViewById(R.id.journalPopUp);



        //gratitude daily popup setting
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor popEditor = mPreferences.edit();


        dailyPopup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isPopupChecked) {
                popCheckedValue=isPopupChecked;
                popEditor.putBoolean(KEY_DAILY_POPUP,isPopupChecked);
                popEditor.apply();
            }
        });

        SharedPreferences checkPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean popupChecked = checkPref.getBoolean(KEY_DAILY_POPUP,false);

        if(popupChecked || !checkPref.contains(KEY_DAILY_POPUP)){
            dailyPopup.setChecked(true);
        }else{
            dailyPopup.setChecked(false);
        }






        //journal daily popup settings
        SharedPreferences jPref= PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor jEditor = jPref.edit();
        journalPopUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isJournalChecked) {
                journalPopChecked=isJournalChecked;
                jEditor.putBoolean(KEY_DAILY_POPUP_JOURNAL,isJournalChecked);
                jEditor.apply();
            }
        });
        SharedPreferences checkJournalPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean JournalChecked = checkJournalPref.getBoolean(KEY_DAILY_POPUP_JOURNAL,false);
        if(JournalChecked || !checkJournalPref.contains(KEY_DAILY_POPUP_JOURNAL)){
            journalPopUp.setChecked(true);
        }else{
            journalPopUp.setChecked(false);
        }



        //required pass for journal tab
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();

        reqPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                checkedValue=isChecked;
                editor.putBoolean(KEY_CHECKBOX, isChecked);
                editor.apply();
            }
        });

        SharedPreferences checkPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean checked = checkPreferences.getBoolean(KEY_CHECKBOX,false);
        if(checked){
            reqPass.setChecked(true);
        }else {
            reqPass.setChecked(false);
        }



        return v;
    }
}