package com.ashysystem.mbhq.util;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerControllerWithTextView {

    private Context context;
    private TextView txtTime;
    private String minTime,dateAppend;
    private int hour;
    private int minute;
    static final int TIME_DIALOG_ID = 999;

    public TimePickerControllerWithTextView(Context context, TextView txtTime, String minTime, String dateAppend)
    {
        this.context=context;
        this.txtTime=txtTime;
        this.minTime=minTime;
        this.dateAppend=dateAppend;
        showTimePicker();
    }
    public Dialog showTimePicker()
    {
        TimePickerDialog timeDialog=  new TimePickerDialog(context,
                timePickerListener, hour, minute,false);
        timeDialog.show();
        return timeDialog;
    }
    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;
                    String strTime ="";
                    boolean boolPm = false;
                    if(hour == 12)
                    {
                        hour = 12;
                        boolPm = true;
                    }else if(hour>12)
                    {
                        hour = hour - 12;
                        boolPm = true;
                    }

                    if(dateAppend.equals(""))
                    {
                        strTime = pad(hour) + ":" + pad(minute);
                    }
                    else
                    {
                        strTime = dateAppend + pad(hour) + ":" + pad(minute) + ":" + "00";
                    }

                    if(boolPm)
                    {
                        strTime = strTime + " PM";
                    }else {
                        strTime = strTime + " AM";
                    }
                    txtTime.setText(strTime);
                }


            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}
