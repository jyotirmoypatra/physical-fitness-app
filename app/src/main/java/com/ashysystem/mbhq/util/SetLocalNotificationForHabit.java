package com.ashysystem.mbhq.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.util.Log;

import androidx.annotation.RequiresApi;

import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.model.habit_hacker.NewAction;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SetLocalNotificationForHabit {

    public static void setNotificationForHabit(HabitSwap getGratitudeListModelInner, Context context) {


        if (getGratitudeListModelInner!=null && getGratitudeListModelInner.getNewAction()!=null && getGratitudeListModelInner.getNewAction().getFrequencyId() != null && getGratitudeListModelInner.getNewAction().getFrequencyId() != 0) {

            AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.setAction("ACTION_HABITLIST");
            Date now_ = new Date();
            int id_ = Integer.parseInt(new SimpleDateFormat("SSS", Locale.getDefault()).format(now_));
            //PendingIntent sender = PendingIntent.getBroadcast(context,getGratitudeListModelInner.getId(), intent, 0);
           // PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);
            PendingIntent sender = PendingIntent.getBroadcast(
                    context,
                    id_,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            try {
                am.cancel(sender);
            } catch (Exception e) {
                e.printStackTrace();
            }

            AlarmManager am1 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent1 = new Intent(context, AlarmReceiver.class);
            intent.setAction("ACTION_HABITLIST");
            Date now1_ = new Date();
            int id1_ = Integer.parseInt(new SimpleDateFormat("SSS", Locale.getDefault()).format(now1_));
            //PendingIntent sender = PendingIntent.getBroadcast(context,getGratitudeListModelInner.getId(), intent, 0);
          //  PendingIntent sender1 = PendingIntent.getBroadcast(context,0, intent1, PendingIntent.FLAG_NO_CREATE);
            PendingIntent sender1 = PendingIntent.getBroadcast(
                    context,
                    id1_,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            try {
                am1.cancel(sender1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Integer at1Hour = 0;
            Integer at1Minute = 0;
            Integer at1Second = 0;

            Integer at2Hour = 0;
            Integer at2Minute = 0;
            Integer at2Second = 0;

            if(getGratitudeListModelInner.getNewAction().getReminderAt1()!=null)
            {
                at1Second = (getGratitudeListModelInner.getNewAction().getReminderAt1() % 60);
                Integer totalMinute = (getGratitudeListModelInner.getNewAction().getReminderAt1() / 60);
                at1Minute = totalMinute % 60;
                at1Hour = totalMinute / 60;
            }
            if(getGratitudeListModelInner.getNewAction().getReminderAt2()!=null)
            {
                at2Second = (getGratitudeListModelInner.getNewAction().getReminderAt2() % 60);
                Integer totalMinute = (getGratitudeListModelInner.getNewAction().getReminderAt2() / 60);
                at2Minute = totalMinute % 60;
                at2Hour = totalMinute / 60;
            }

            if (getGratitudeListModelInner.getNewAction().getFrequencyId() - 1 == 0) {

                //For Daily
                Calendar globalCalender = Calendar.getInstance();
                Calendar globalCalender1 = Calendar.getInstance();

                Calendar dailyCalendar=Calendar.getInstance();
                Calendar dailyCalendar1=Calendar.getInstance();

                try {
                    dailyCalendar.set(Calendar.HOUR_OF_DAY,at1Hour);
                    dailyCalendar.set(Calendar.MINUTE,at1Minute);
                    dailyCalendar.set(Calendar.SECOND,at1Second);
                    Log.e("REMINDER1",getGratitudeListModelInner.getNewAction().getReminderAt1()+">>>>>>>");
                    dailyCalendar1.set(Calendar.HOUR_OF_DAY,at2Hour);
                    dailyCalendar1.set(Calendar.MINUTE,at2Minute);
                    dailyCalendar1.set(Calendar.SECOND,at2Second);
                    Log.e("REMINDER2",getGratitudeListModelInner.getNewAction().getReminderAt2()+">>>>>>>");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if(globalCalender.getTime().compareTo(dailyCalendar.getTime())>0)
                {
                    dailyCalendar.add(Calendar.DATE,1);
                }
                if(globalCalender1.getTime().compareTo(dailyCalendar1.getTime())>0)
                {
                    dailyCalendar1.add(Calendar.DATE,1);
                }

                try {
                    atBetweenTwiceBetweenCheck(dailyCalendar, dailyCalendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1,getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (getGratitudeListModelInner.getNewAction().getFrequencyId() - 1 == 1) {

                //For Twice Daily

                Calendar globalCalender = Calendar.getInstance();
                Calendar globalCalender1 = Calendar.getInstance();

                Calendar dailyCalendar=Calendar.getInstance();
                Calendar dailyCalendar1=Calendar.getInstance();
                try {
                    dailyCalendar.set(Calendar.HOUR_OF_DAY,at1Hour);
                    dailyCalendar.set(Calendar.MINUTE,at1Minute);
                    dailyCalendar.set(Calendar.SECOND,at1Second);
                    dailyCalendar1.set(Calendar.HOUR_OF_DAY,at2Hour);
                    dailyCalendar1.set(Calendar.MINUTE,at2Minute);
                    dailyCalendar1.set(Calendar.SECOND,at2Second);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                System.out.println("DATE>>>>>>"+dailyCalendar.getTime());
                System.out.println("DATE>>>>>>"+globalCalender.getTime());
                System.out.println("DATE>>>>>>"+dailyCalendar1.getTime());
                System.out.println("DATE>>>>>>"+(globalCalender.getTime().compareTo(dailyCalendar.getTime())));

                if(globalCalender.getTime().compareTo(dailyCalendar.getTime())>0)
                {
                    dailyCalendar.add(Calendar.DATE,1);
                }
                if(globalCalender1.getTime().compareTo(dailyCalendar1.getTime())>0)
                {
                    dailyCalendar1.add(Calendar.DATE,1);
                }
                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                alarmIntent.setAction("ACTION_HABITLIST");
                alarmIntent.putExtra("NOTIFICATIONTYPE","HABITLIST");
                alarmIntent.putExtra("NOTIFICATIONID",getGratitudeListModelInner.getHabitId());
                alarmIntent.putExtra("NOTIFICATIONHEADING",getGratitudeListModelInner.getHabitName());

                Date now = new Date();
                int id = Integer.parseInt(new SimpleDateFormat("SSS", Locale.getDefault()).format(now));
                alarmIntent.putExtra("PENDINGINTENTID",id);

             //   PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context,
                        id,
                        alarmIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

                Intent alarmIntent1 = new Intent(context, AlarmReceiver.class);
                alarmIntent1.setAction("ACTION_HABITLIST");
                alarmIntent1.putExtra("NOTIFICATIONTYPE","HABITLIST");
                alarmIntent1.putExtra("NOTIFICATIONID",getGratitudeListModelInner.getHabitId());
                alarmIntent1.putExtra("NOTIFICATIONHEADING",getGratitudeListModelInner.getHabitName());

                Date now1 = new Date();
                int id1 = Integer.parseInt(new SimpleDateFormat("SSS", Locale.getDefault()).format(now1));
                alarmIntent1.putExtra("PENDINGINTENTID",id1);

               // PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, id1, alarmIntent1, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent pendingIntent1 = PendingIntent.getBroadcast(
                        context,
                        id1,
                        alarmIntent1,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                AlarmManager alarmManager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
               // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dailyCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
               // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dailyCalendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dailyCalendar.getTimeInMillis(), pendingIntent);
                    alarmManager2.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dailyCalendar1.getTimeInMillis(), pendingIntent1);
                }else {
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, dailyCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    alarmManager2.setInexactRepeating(AlarmManager.RTC_WAKEUP, dailyCalendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);
                }

            } else if (getGratitudeListModelInner.getNewAction().getFrequencyId() - 1 == 2) {

                //For Weekly

                try {
                    if (getGratitudeListModelInner.getNewAction().getSunday()) {

                        //For Sunday
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarSunday = Calendar.getInstance();
                        calendarSunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

                        Calendar calendarSunday1 = Calendar.getInstance();
                        calendarSunday1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

                        if(globalCalendar.getTime().compareTo(calendarSunday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarSunday.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarSunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                            calendarSunday1.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarSunday1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarSunday, calendarSunday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"WEEKLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getMonday()) {

                        //For Monday
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarMonday = Calendar.getInstance();
                        calendarMonday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

                        Calendar calendarMonday1 = Calendar.getInstance();
                        calendarMonday1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

                        if(globalCalendar.getTime().compareTo(calendarMonday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarMonday.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarMonday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                            calendarMonday1.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarMonday1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        }

                        System.out.println("MONDAY>>>>>"+globalCalendar.getTime());
                        System.out.println("MONDAY>>>>>"+calendarMonday.getTime());

                        try {
                            atBetweenTwiceBetweenCheck(calendarMonday, calendarMonday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(),getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"WEEKLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getTuesday()) {

                        //For TuesDay
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarTuesday = Calendar.getInstance();
                        calendarTuesday.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);

                        Calendar calendarTuesday1 = Calendar.getInstance();
                        calendarTuesday1.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);

                        if(globalCalendar.getTime().compareTo(calendarTuesday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarTuesday.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarTuesday.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                            calendarTuesday1.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarTuesday1.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarTuesday, calendarTuesday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"WEEKLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getWednesday()) {

                        //For WednesDay
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarWednesday = Calendar.getInstance();
                        calendarWednesday.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);

                        Calendar calendarWednesday1 = Calendar.getInstance();
                        calendarWednesday1.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);

                        if(globalCalendar.getTime().compareTo(calendarWednesday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarWednesday.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarWednesday.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                            calendarWednesday1.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarWednesday1.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarWednesday, calendarWednesday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1,getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"WEEKLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getThursday()) {
                        //For ThursDay
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarThursday = Calendar.getInstance();
                        calendarThursday.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);

                        Calendar calendarThursday1 = Calendar.getInstance();
                        calendarThursday1.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);

                        if(globalCalendar.getTime().compareTo(calendarThursday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarThursday.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarThursday.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                            calendarThursday1.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarThursday1.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarThursday, calendarThursday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"WEEKLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getFriday()) {
                        //For Friday
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarFriday = Calendar.getInstance();
                        calendarFriday.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

                        Calendar calendarFriday1 = Calendar.getInstance();
                        calendarFriday1.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

                        if(globalCalendar.getTime().compareTo(calendarFriday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarFriday.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarFriday.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                            calendarFriday1.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarFriday1.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarFriday, calendarFriday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"WEEKLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getSaturday()) {
                        //For Saturday
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarSaturday = Calendar.getInstance();
                        calendarSaturday.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

                        Calendar calendarSaturday1 = Calendar.getInstance();
                        calendarSaturday1.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

                        if(globalCalendar.getTime().compareTo(calendarSaturday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarSaturday.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarSaturday.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                            calendarSaturday1.set(Calendar.WEEK_OF_MONTH,++i);
                            calendarSaturday1.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarSaturday, calendarSaturday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(),getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"WEEKLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (getGratitudeListModelInner.getNewAction().getFrequencyId() - 1 == 3) {

                //For Fortnightly

                try {
                    if (getGratitudeListModelInner.getNewAction().getSunday()) {

                        //For Sunday
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarSunday = Calendar.getInstance();
                        calendarSunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

                        Calendar calendarSunday1 = Calendar.getInstance();
                        calendarSunday1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

                        if(globalCalendar.getTime().compareTo(calendarSunday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarSunday.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarSunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                            calendarSunday1.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarSunday1.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarSunday, calendarSunday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"FORTNIGHTLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getMonday()) {

                        //For Monday
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarMonday = Calendar.getInstance();
                        calendarMonday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

                        Calendar calendarMonday1 = Calendar.getInstance();
                        calendarMonday1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

                        if(globalCalendar.getTime().compareTo(calendarMonday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarMonday.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarMonday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                            calendarMonday1.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarMonday1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarMonday, calendarMonday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(),getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"FORTNIGHTLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getTuesday()) {

                        //For TuesDay
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarTuesday = Calendar.getInstance();
                        calendarTuesday.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);

                        Calendar calendarTuesday1 = Calendar.getInstance();
                        calendarTuesday1.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);

                        if(globalCalendar.getTime().compareTo(calendarTuesday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarTuesday.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarTuesday.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                            calendarTuesday1.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarTuesday1.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarTuesday, calendarTuesday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"FORTNIGHTLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getWednesday()) {

                        //For WednesDay
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarWednesday = Calendar.getInstance();
                        calendarWednesday.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);

                        Calendar calendarWednesday1 = Calendar.getInstance();
                        calendarWednesday1.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);

                        if(globalCalendar.getTime().compareTo(calendarWednesday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarWednesday.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarWednesday.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                            calendarWednesday1.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarWednesday1.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarWednesday, calendarWednesday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1,getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"FORTNIGHTLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getThursday()) {
                        //For ThursDay
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarThursday = Calendar.getInstance();
                        calendarThursday.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);

                        Calendar calendarThursday1 = Calendar.getInstance();
                        calendarThursday1.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);

                        if(globalCalendar.getTime().compareTo(calendarThursday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarThursday.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarThursday.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                            calendarThursday1.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarThursday1.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarThursday, calendarThursday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"FORTNIGHTLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getFriday()) {
                        //For Friday
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarFriday = Calendar.getInstance();
                        calendarFriday.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

                        Calendar calendarFriday1 = Calendar.getInstance();
                        calendarFriday1.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

                        if(globalCalendar.getTime().compareTo(calendarFriday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarFriday.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarFriday.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                            calendarFriday1.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarFriday1.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarFriday, calendarFriday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"FORTNIGHTLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (getGratitudeListModelInner.getNewAction().getSaturday()) {
                        //For Saturday
                        Calendar globalCalendar=Calendar.getInstance();

                        Calendar calendarSaturday = Calendar.getInstance();
                        calendarSaturday.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

                        Calendar calendarSaturday1 = Calendar.getInstance();
                        calendarSaturday1.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

                        if(globalCalendar.getTime().compareTo(calendarSaturday.getTime())>0)
                        {
                            int i = globalCalendar.get(Calendar.WEEK_OF_MONTH);
                            calendarSaturday.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarSaturday.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                            calendarSaturday1.set(Calendar.WEEK_OF_MONTH,i+2);
                            calendarSaturday1.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        }

                        try {
                            atBetweenTwiceBetweenCheck(calendarSaturday, calendarSaturday1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(),getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"FORTNIGHTLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (getGratitudeListModelInner.getNewAction().getFrequencyId() - 1 == 4) {

                //For Monthly
                Calendar globalCalendar=Calendar.getInstance();

                Calendar calendar = Calendar.getInstance();   // this takes current date
                calendar.set(Calendar.DAY_OF_MONTH, getGratitudeListModelInner.getNewAction().getMonthReminder());

                Calendar calendar1 = Calendar.getInstance();   // this takes current date
                calendar1.set(Calendar.DAY_OF_MONTH, getGratitudeListModelInner.getNewAction().getMonthReminder());

                if(globalCalendar.getTimeInMillis()>calendar.getTimeInMillis())
                {
                    int i=globalCalendar.get(Calendar.MONTH);
                    calendar.set(Calendar.MONTH,++i);
                    calendar.set(Calendar.DAY_OF_MONTH, getGratitudeListModelInner.getNewAction().getMonthReminder());
                    calendar1.set(Calendar.MONTH,++i);
                    calendar1.set(Calendar.DAY_OF_MONTH, getGratitudeListModelInner.getNewAction().getMonthReminder());
                }

                try {
                    atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"MONTHLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (getGratitudeListModelInner.getNewAction().getFrequencyId() - 1 == 5) {
                //For Yearly

                try {
                    if(getGratitudeListModelInner.getNewAction().getJanuary())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.JANUARY);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.JANUARY);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(getGratitudeListModelInner.getNewAction().getFebruary())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.FEBRUARY);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(getGratitudeListModelInner.getNewAction().getMarch())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.MARCH);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.MARCH);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(getGratitudeListModelInner.getNewAction().getApril())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.APRIL);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.APRIL);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(getGratitudeListModelInner.getNewAction().getMay())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.MAY);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.MAY);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(getGratitudeListModelInner.getNewAction().getJune())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.JUNE);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.JUNE);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(),getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(getGratitudeListModelInner.getNewAction().getJuly())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.JULY);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.JULY);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(getGratitudeListModelInner.getNewAction().getAugust())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.AUGUST);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.AUGUST);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(getGratitudeListModelInner.getNewAction().getSeptember())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.SEPTEMBER);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(getGratitudeListModelInner.getNewAction().getOctober())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.OCTOBER);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(getGratitudeListModelInner.getNewAction().getNovember())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.NOVEMBER);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1, getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(getGratitudeListModelInner.getNewAction().getDecember())
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.MONTH, Calendar.DECEMBER);
                        calendar1.set(Calendar.DAY_OF_MONTH, 1);

                        atBetweenTwiceBetweenCheck(calendar, calendar1, getGratitudeListModelInner.getNewAction().getReminderOption() - 1,getGratitudeListModelInner.getNewAction().getReminderAt1(), getGratitudeListModelInner.getNewAction().getReminderAt2(), getGratitudeListModelInner.getHabitId(), context,"YEARLY",getGratitudeListModelInner.getNewAction(),getGratitudeListModelInner.getHabitName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }


    private static void atBetweenTwiceBetweenCheck(Calendar calender1, Calendar calender2,Integer reminderOption, Integer reminderAt1, Integer reminderAt2,Integer ID,Context context,String TYPE,NewAction getGratitudeListModelInner,String HEADING) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Integer at1Hour = 0;
        Integer at1Minute = 0;
        Integer at1Second = 0;

        Integer at2Hour = 0;
        Integer at2Minute = 0;
        Integer at2Second = 0;

        if(getGratitudeListModelInner.getReminderAt1()!=null)
        {
            at1Second = (getGratitudeListModelInner.getReminderAt1() % 60);
            Integer totalMinute = (getGratitudeListModelInner.getReminderAt1() / 60);
            at1Minute = totalMinute % 60;
            at1Hour = totalMinute / 60;
        }
        if(getGratitudeListModelInner.getReminderAt2()!=null)
        {
            at2Second = (getGratitudeListModelInner.getReminderAt2() % 60);
            Integer totalMinute = (getGratitudeListModelInner.getReminderAt2() / 60);
            at2Minute = totalMinute % 60;
            at2Hour = totalMinute / 60;
        }

        try {
            if (reminderOption == 0) {

                //For At
                calender1.set(Calendar.HOUR_OF_DAY,at1Hour);
                calender1.set(Calendar.MINUTE,at1Minute);
                calender1.set(Calendar.SECOND,at1Second);

                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                alarmIntent.setAction("ACTION_HABITLIST");
                alarmIntent.putExtra("NOTIFICATIONTYPE","HABITLIST");
                alarmIntent.putExtra("NOTIFICATIONID",ID);
                alarmIntent.putExtra("NOTIFICATIONHEADING",HEADING);

                Date now = new Date();
                int id = Integer.parseInt(new SimpleDateFormat("SSS", Locale.getDefault()).format(now));
                alarmIntent.putExtra("PENDINGINTENTID",id);

                //alarmIntent.putExtra("NOTIFICATIONDATE",simpleDateFormat.format(format.parse(getGratitudeListModelInner.getCompletionDate())));
               // PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context,
                        id,
                        alarmIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                switchMethodSet(alarmManager,pendingIntent,null,calender1,null,TYPE);

            } else if (reminderOption == 1) {

                //For Between
                Integer reminderOptionDiff = Math.abs(at2Hour-at1Hour);
                Integer reminderOptionDiffHour = reminderOptionDiff / 2;
                Integer reminderOptionDiffMinutes = (reminderOptionDiff % 2) * 30;
                calender1.set(Calendar.HOUR_OF_DAY, at1Hour + reminderOptionDiffHour);
                calender1.set(Calendar.MINUTE, reminderOptionDiffMinutes);
                calender1.set(Calendar.SECOND, 0);
                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                alarmIntent.setAction("ACTION_HABITLIST");
                alarmIntent.putExtra("NOTIFICATIONTYPE","HABITLIST");
                alarmIntent.putExtra("NOTIFICATIONID",ID);
                alarmIntent.putExtra("NOTIFICATIONHEADING",HEADING);

                Date now = new Date();
                int id = Integer.parseInt(new SimpleDateFormat("SSS", Locale.getDefault()).format(now));
                alarmIntent.putExtra("PENDINGINTENTID",id);

                //alarmIntent.putExtra("NOTIFICATIONDATE",simpleDateFormat.format(format.parse(getGratitudeListModelInner.getCompletionDate())));
              //  PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context,
                        id,
                        alarmIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                switchMethodSet(alarmManager,pendingIntent,null,calender1,null,TYPE);


            } else if (reminderOption == 2) {

                //For Twice Between
                calender1.set(Calendar.HOUR_OF_DAY, at1Hour);
                calender1.set(Calendar.MINUTE, at1Minute);
                calender1.set(Calendar.SECOND, at1Second);
                calender2.set(Calendar.HOUR_OF_DAY, at2Hour);
                calender2.set(Calendar.MINUTE, at2Minute);
                calender2.set(Calendar.SECOND, at2Second);
                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                alarmIntent.setAction("ACTION_HABITLIST");
                alarmIntent.putExtra("NOTIFICATIONTYPE","HABITLIST");
                alarmIntent.putExtra("NOTIFICATIONID",ID);
                alarmIntent.putExtra("NOTIFICATIONHEADING",HEADING);

                Date now = new Date();
                int id = Integer.parseInt(new SimpleDateFormat("SSS", Locale.getDefault()).format(now));
                alarmIntent.putExtra("PENDINGINTENTID",id);

                Intent alarmIntent1 = new Intent(context, AlarmReceiver.class);
                alarmIntent1.setAction("ACTION_HABITLIST");
                alarmIntent1.putExtra("NOTIFICATIONTYPE","HABITLIST");
                alarmIntent1.putExtra("NOTIFICATIONID",ID);
                alarmIntent1.putExtra("NOTIFICATIONHEADING",HEADING);

                Date now1 = new Date();
                int id1 = Integer.parseInt(new SimpleDateFormat("SSS", Locale.getDefault()).format(now1));
                alarmIntent1.putExtra("PENDINGINTENTID",id1);

              //  PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
               // PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, id1, alarmIntent1, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context,
                        id,
                        alarmIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                PendingIntent pendingIntent1 = PendingIntent.getBroadcast(
                        context,
                        id1,
                        alarmIntent1,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                switchMethodSet(alarmManager,pendingIntent,pendingIntent1,calender1,calender2,TYPE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void switchMethodSet(AlarmManager alarmManager, PendingIntent pendingIntent,PendingIntent pendingIntent1, Calendar calender1, Calendar calender2, String TYPE) {

        switch (TYPE) {
            case "":
                if(!funalarmTimeElapsed(calender1)) {
                   // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                  //  alarmManager.set(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), pendingIntent);
                   // alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                  alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), pendingIntent);
                    }else {
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    }


                }
                if(calender2!=null)
                {
                    if(!funalarmTimeElapsed(calender2))
                    {
                       // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(), pendingIntent);
                        }else {
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                        }
                    }
                }

                break;
            case "WEEKLY":
                if(!funalarmTimeElapsed(calender1)) {
                   // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), 7 * AlarmManager.INTERVAL_DAY, pendingIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), pendingIntent);
                    }else {
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(),7 * AlarmManager.INTERVAL_DAY, pendingIntent);
                    }
                }
                if(calender2!=null)
                {
                    if(!funalarmTimeElapsed(calender2)) {
                        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(), 7 * AlarmManager.INTERVAL_DAY, pendingIntent1);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(), pendingIntent);
                        }else {
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(),7 * AlarmManager.INTERVAL_DAY, pendingIntent);
                        }
                    }
                }

                break;
            case "MONTHLY":
                if(!funalarmTimeElapsed(calender1)) {
                  //  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), 30 * AlarmManager.INTERVAL_DAY, pendingIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), pendingIntent);
                    }else {
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(),30 * AlarmManager.INTERVAL_DAY, pendingIntent);
                    }
                }


                if(calender2!=null)
                {
                    if(!funalarmTimeElapsed(calender2)) {
                       // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(), 30 * AlarmManager.INTERVAL_DAY, pendingIntent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(), pendingIntent);
                        }else {
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(),30 * AlarmManager.INTERVAL_DAY, pendingIntent);
                        }
                    }
                }

                break;
            case "YEARLY":
                if(!funalarmTimeElapsed(calender1)) {
                  //  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), 365 * AlarmManager.INTERVAL_DAY, pendingIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), pendingIntent);
                    }else {
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(),365 * AlarmManager.INTERVAL_DAY, pendingIntent);
                    }

                }
                if(calender2!=null)
                {
                    if(!funalarmTimeElapsed(calender2)) {
                    //    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(), 365 * AlarmManager.INTERVAL_DAY, pendingIntent1);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(), pendingIntent);
                        }else {
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(),365 * AlarmManager.INTERVAL_DAY, pendingIntent);
                        }
                    }
                }

                break;
            case "FORTNIGHTLY":
                if(!funalarmTimeElapsed(calender1)) {
                   // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), 14 * AlarmManager.INTERVAL_DAY, pendingIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(), pendingIntent);
                    }else {
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender1.getTimeInMillis(),14 * AlarmManager.INTERVAL_DAY, pendingIntent);
                    }
                }
                if(calender2!=null)
                {
                    if(!funalarmTimeElapsed(calender2)) {
                      //  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(), 14 * AlarmManager.INTERVAL_DAY, pendingIntent1);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(), pendingIntent);
                        }else {
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender2.getTimeInMillis(),14 * AlarmManager.INTERVAL_DAY, pendingIntent);
                        }
                    }
                }

                break;
        }

    }
    private static boolean funalarmTimeElapsed(Calendar calNowCheck)
    {
        Calendar calNow = Calendar.getInstance();
        long current_time = calNow.getTimeInMillis();
        Timestamp alramTimeStamp = new Timestamp(calNowCheck.getTimeInMillis());
        Timestamp currentTimeStamp = new Timestamp(current_time);
        if (alramTimeStamp.before(currentTimeStamp)) {
            /*alarmManager.set(AlarmManager.RTC_WAKEUP, calNow.getTimeInMillis(),
                    operation_morn);*/
            return true;
        }
        else
            return false;
    }

}
