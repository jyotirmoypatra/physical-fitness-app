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

/**
 * Created by android-krishnendu on 3/8/17.
 */

public class DatePickerControllerFromTextView {

    boolean timeDialog = false;
    private Context context;
    private TextView txtDate;
    private int year;
    private int month;
    private int day;
    private String minDate = "";
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            // set selected date into textview
            if (txtDate != null) {
                                txtDate.setText(new StringBuilder().append(day)
                        .append("/").append(month + 1).append("/").append(year));

                //added  jyotirmoy patra
               /* String nDay="";
                if(day<10){
                    nDay="0"+day;
                }else{
                    nDay=""+day;
                }
                String date=new DateFormatSymbols().getShortMonths()[month]+" "+nDay+" "+year;
                txtDate.setText(date);
*/
                Log.e("Time flag", timeDialog + "????");
                /*if(timeDialog){
                    TimePickerController timeDialog = new TimePickerController(context, txtDate, "", txtDate.getText().toString());
                }*/
            }
        }
    };

    public DatePickerControllerFromTextView(Context context, TextView txtDate, String minDate, boolean timeDialog) {
        this.context = context;
        this.txtDate = txtDate;
        this.minDate = minDate;
        this.timeDialog = timeDialog;
        showDatePicker();
    }

    public Dialog showDatePicker() {
        DatePickerDialog dialog = null;
        if (minDate.equals("")) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            dialog = new DatePickerDialog(context, datePickerListener, year, month, day);

        } else {
            ////////////Set Minimum Date Range///////////////////////
            Calendar fromDateCal = Calendar.getInstance();
            SimpleDateFormat myDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                fromDateCal.setTime(myDateFormat.parse(minDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dialog = new DatePickerDialog(context, datePickerListener, year, month, day);
            dialog.getDatePicker().setMinDate(fromDateCal.getTime().getTime());
            ////////////Set Minimum Date Range End///////////////////////
        }
        dialog.show();
        return dialog;
    }

}
