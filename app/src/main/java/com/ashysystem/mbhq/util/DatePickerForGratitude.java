package com.ashysystem.mbhq.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerForGratitude {

    private Context context;
    private TextView txtDate;
    private int year;
    private int month;
    private int day;
    private String minDate="";
    boolean timeDialog=false;
    String freindName;
    Integer freindId;

    public DatePickerForGratitude(Context context, TextView txtDate, String minDate, boolean timeDialog, String freindName, Integer freindId)
    {
        this.context=context;
        this.txtDate=txtDate;
        this.minDate=minDate;
        this.timeDialog=timeDialog;
        this.freindName=freindName;
        this.freindId=freindId;
        showDatePicker();
    }
    public Dialog showDatePicker()
    {
        DatePickerDialog dialog=null;
        if(minDate.equals(""))
        {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            dialog = new DatePickerDialog(context, datePickerListener,
                    year, month, day);

        }
        else
        {
            ////////////Set Minimum Date Range///////////////////////
            Calendar fromDateCal= Calendar.getInstance();
            SimpleDateFormat myDateFormat=new SimpleDateFormat("MM/dd/yyyy");
            Log.e("DATEDATE",minDate+">>>>>>>>");
            try {
                fromDateCal.setTime(myDateFormat.parse(minDate));
                year=fromDateCal.get(Calendar.YEAR);
                month=fromDateCal.get(Calendar.MONTH);
                day=fromDateCal.get(Calendar.DATE);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dialog = new DatePickerDialog(context, datePickerListener, year, month, day);
            dialog.getDatePicker().setMaxDate(fromDateCal.getTimeInMillis());
            //dialog.getDatePicker().setMinDate(fromDateCal.getTimeInMillis());

            ////////////Set Minimum Date Range End///////////////////////
        }
        dialog.show();
        return dialog;
    }
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            String strMonth="",strDay="",DATE="";
            if(month<10)
            {
                strMonth="0"+(month+1);
            }else {
                strMonth=(month+1)+"";
            }
            if(day<10)
            {
                strDay="0"+day;
            }else {
                strDay=day+"";
            }
            String date=year+"-"+strMonth+"-"+strDay;
            txtDate.setText(date);
        }
    };

}
