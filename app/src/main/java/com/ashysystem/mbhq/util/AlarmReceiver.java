package com.ashysystem.mbhq.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.activity.MainActivity;

/**
 * Created by root on 25/6/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle=intent.getExtras();
        if(bundle.containsKey("NOTIFICATIONTYPE") && bundle.containsKey("NOTIFICATIONID"))
        {
            if(bundle.getString("NOTIFICATIONTYPE").equals("BUCKETLIST"))
            {
                /////////////////////////////////////////////////

                Log.e("NOTIFICATIONID",bundle.getInt("NOTIFICATIONID")+">>>>>>>>");
                Intent notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.putExtra("NOTIFICATIONTYPE","BUCKETLIST");
                notificationIntent.putExtra("NOTIFICATIONID",bundle.getInt("NOTIFICATIONID"));

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(notificationIntent);

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(bundle.getInt("PENDINGINTENTID"), PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(alarmSound);
                String notificationStr="How cool will it be when you tick this off. "+bundle.getString("NOTIFICATIONDATE");
                Notification notification = builder.setContentTitle(bundle.getString("NOTIFICATIONHEADING"))
                        .setContentText(notificationStr)
                        //.setTicker("New Message Alert!")
                        .setSmallIcon(R.drawable.app_icon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationStr))
                        .setContentIntent(pendingIntent).build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
              //  notificationManager.notify(bundle.getInt("NOTIFICATIONID"), notification);
                ///////////////For Oreo Change////////
                // mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "10001";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME_BUCKET", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert notificationManager != null;
                    builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    notificationManager.createNotificationChannel(notificationChannel);
                    //////////

                }
                assert notificationManager != null;
                notificationManager.notify(bundle.getInt("NOTIFICATIONID") , builder.build());
                //////////////////
                ///////////////For Oreo Change End////////

            }else if(bundle.getString("NOTIFICATIONTYPE").equals("GRATITUDELIST"))
            {
                /////////////////////////////////////////////////

                Intent notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.putExtra("NOTIFICATIONTYPE","GRATITUDELIST");
                notificationIntent.putExtra("NOTIFICATIONID",bundle.getInt("NOTIFICATIONID"));

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(notificationIntent);

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(bundle.getInt("NOTIFICATIONID"), PendingIntent.FLAG_UPDATE_CURRENT);
                String notificationStr="ahhhh Gratitude.. Doesn’t that feel good? Please make just one minute away from your busy, busy schedule to sit and appreciate how much this means to you ";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(alarmSound);
                Notification notification = builder.setContentTitle(bundle.getString("NOTIFICATIONHEADING"))
                        .setContentText(notificationStr)
                        //.setTicker("New Message Alert!")
                        .setSmallIcon(R.drawable.app_icon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationStr))
                        .setContentIntent(pendingIntent).build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
               // notificationManager.notify(bundle.getInt("NOTIFICATIONID"), notification);
                ///////////////For Oreo Change////////
                // mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "10002";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME_GRATITUDE", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert notificationManager != null;
                    builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    notificationManager.createNotificationChannel(notificationChannel);
                    //////////

                }
                assert notificationManager != null;
                notificationManager.notify(bundle.getInt("NOTIFICATIONID") , builder.build());
                //////////////////
                ///////////////For Oreo Change End////////

            }else if(bundle.getString("NOTIFICATIONTYPE").equals("ACHIEVEMENTLIST"))
            {
                //////////////////////////////////////////////
                Log.e("NOTIFICATIONID",bundle.getInt("PENDINGINTENTID")+">>>>>>>>");

                Intent notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.putExtra("NOTIFICATIONTYPE","ACHIEVEMENTLIST");
                notificationIntent.putExtra("NOTIFICATIONID",bundle.getInt("NOTIFICATIONID"));

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(notificationIntent);

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(bundle.getInt("PENDINGINTENTID"), PendingIntent.FLAG_UPDATE_CURRENT);
                String notificationStr="What an awesome achievement! Don’t dismiss it, don’t quickly ignore it.. Let yourself feel proud for a least a few seconds. You earned it";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(alarmSound);
                Notification notification = builder.setContentTitle(bundle.getString("NOTIFICATIONHEADING"))
                        .setContentText(notificationStr)
                        //.setTicker("New Message Alert!")
                        .setSmallIcon(R.drawable.app_icon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationStr))
                        .setContentIntent(pendingIntent).build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
              //  notificationManager.notify(bundle.getInt("NOTIFICATIONID"), notification);
                ///////////////For Oreo Change////////
                // mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "10003";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME_ACHIEVE", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert notificationManager != null;
                    builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    notificationManager.createNotificationChannel(notificationChannel);
                    //////////

                }
                assert notificationManager != null;
                notificationManager.notify(bundle.getInt("NOTIFICATIONID") , builder.build());
                //////////////////
                ///////////////For Oreo Change End////////

            }else if(bundle.getString("NOTIFICATIONTYPE").equals("VISIONBOARD"))
            {
                //////////////////////////////////////////////
                Log.e("NOTIFICATIONID",bundle.getInt("PENDINGINTENTID")+">>>>>>>>");

                Intent notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.putExtra("NOTIFICATIONTYPE","VISIONBOARD");
                notificationIntent.putExtra("NOTIFICATIONID",bundle.getInt("NOTIFICATIONID"));

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(notificationIntent);

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(bundle.getInt("PENDINGINTENTID"), PendingIntent.FLAG_UPDATE_CURRENT);
                String notificationStr="Click here to see your vision board, re-connect with your vision and set your intention. You are #unstoppable ";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(alarmSound);
                Notification notification = builder.setContentTitle("Ashy Bines Squad")
                        .setContentText(notificationStr)
                        //.setTicker("New Message Alert!")
                        .setSmallIcon(R.drawable.app_icon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationStr))
                        .setContentIntent(pendingIntent).build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
               // notificationManager.notify(bundle.getInt("NOTIFICATIONID"), notification);
                ///////////////For Oreo Change////////
                // mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "10004";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME_VISION", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert notificationManager != null;
                    builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    notificationManager.createNotificationChannel(notificationChannel);
                    //////////

                }
                assert notificationManager != null;
                notificationManager.notify(bundle.getInt("NOTIFICATIONID") , builder.build());
                //////////////////
                ///////////////For Oreo Change End////////

            }else if(bundle.getString("NOTIFICATIONTYPE").equals("ACTIONLIST"))
            {
                //////////////////////////////////////////////
                Log.e("NOTIFICATIONID",bundle.getInt("PENDINGINTENTID")+">>>>>>>>");


                Intent notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.putExtra("NOTIFICATIONTYPE","ACTIONLIST");
                notificationIntent.putExtra("NOTIFICATIONID",bundle.getInt("NOTIFICATIONID"));

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(notificationIntent);

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(bundle.getInt("PENDINGINTENTID"), PendingIntent.FLAG_UPDATE_CURRENT);
                String notificationStr="Chose your hard - You got this! ";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(alarmSound);
                Notification notification = builder.setContentTitle(bundle.getString("NOTIFICATIONHEADING"))
                        .setContentText(notificationStr)
                        //.setTicker("New Message Alert!")
                        .setSmallIcon(R.drawable.app_icon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationStr))
                        .setContentIntent(pendingIntent)

                                .build();

                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
               // notificationManager.notify(bundle.getInt("NOTIFICATIONID"), notification);
                ///////////////For Oreo Change////////
                // mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "10005";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME_ACTION", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert notificationManager != null;
                    builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    notificationManager.createNotificationChannel(notificationChannel);
                    //////////

                }
                assert notificationManager != null;
                notificationManager.notify(bundle.getInt("NOTIFICATIONID") , builder.build());
                //////////////////
                ///////////////For Oreo Change End////////


            }else if(bundle.getString("NOTIFICATIONTYPE").equals("GOALLIST"))
            {
                //////////////////////////////////////////////
                Log.e("NOTIFICATIONID",bundle.getInt("PENDINGINTENTID")+">>>>>>>>");

                Intent notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.putExtra("NOTIFICATIONTYPE","GOALLIST");
                notificationIntent.putExtra("NOTIFICATIONID",bundle.getInt("NOTIFICATIONID"));

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(notificationIntent);

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(bundle.getInt("PENDINGINTENTID"), PendingIntent.FLAG_UPDATE_CURRENT);
                String notificationStr=" #Squadgoals "+bundle.getString("NOTIFICATIONDATE");
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                //builder.setSound(alarmSound);
                Notification notification = builder.setContentTitle(bundle.getString("NOTIFICATIONHEADING"))
                        .setContentText(notificationStr)
                        //.setTicker("New Message Alert!")
                        .setSmallIcon(R.drawable.app_icon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationStr))
                        .setContentIntent(pendingIntent).build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
              //notificationManager.notify(bundle.getInt("NOTIFICATIONID"), notification);
               ///////////////For Oreo Change////////
               // mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
               String NOTIFICATION_CHANNEL_ID = "10006";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME_GOAL", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert notificationManager != null;
                    builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    notificationManager.createNotificationChannel(notificationChannel);
                    //////////

                }
                assert notificationManager != null;
                notificationManager.notify(bundle.getInt("NOTIFICATIONID") , builder.build());
                //////////////////
                ///////////////For Oreo Change End////////

            }else if(bundle.getString("NOTIFICATIONTYPE").equals("PERSONALCHALLENGELIST"))
            {
                //////////////////////////////////////////////
                Log.e("NOTIFICATIONID",bundle.getInt("PENDINGINTENTID")+">>>>>>>>");

                Intent notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.putExtra("NOTIFICATIONTYPE","PERSONALCHALLENGELIST");
                notificationIntent.putExtra("NOTIFICATIONID",bundle.getInt("NOTIFICATIONID"));
                notificationIntent.putExtra("NOTIFICATIONDATE",bundle.getString("NOTIFICATIONDATE"));
                notificationIntent.putExtra("NOTIFICATIONENDDATE",bundle.getString("NOTIFICATIONENDDATE"));

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(notificationIntent);

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(bundle.getInt("PENDINGINTENTID"), PendingIntent.FLAG_UPDATE_CURRENT);
                String notificationStr=" #PersonalChallenge ";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(alarmSound);
                Notification notification = builder.setContentTitle("PERSONAL CHALLENGE - " + bundle.getString("NOTIFICATIONHEADING"))
                        .setContentText(notificationStr)
                        //.setTicker("New Message Alert!")
                        .setSmallIcon(R.drawable.app_icon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationStr))
                        .setContentIntent(pendingIntent).build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                //notificationManager.notify(bundle.getInt("NOTIFICATIONID"), notification);
                ////////////////////////
                ///////////////For Oreo Change////////
                // mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "10007";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME_PERSONAL", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert notificationManager != null;
                    builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    notificationManager.createNotificationChannel(notificationChannel);
                    //////////
                }
                assert notificationManager != null;
                notificationManager.notify(bundle.getInt("NOTIFICATIONID") , builder.build());
                //////////////////
                ///////////////For Oreo Change End////////

            }else if(bundle.getString("NOTIFICATIONTYPE").equals("HABITLIST"))
            {
                /////////////////////////////////////////////////

                Intent notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.putExtra("NOTIFICATIONTYPE","HABITLIST");
                notificationIntent.putExtra("NOTIFICATIONID",bundle.getInt("NOTIFICATIONID"));

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(notificationIntent);

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(bundle.getInt("NOTIFICATIONID"), PendingIntent.FLAG_UPDATE_CURRENT);
                //String notificationStr="ahhhh Gratitude.. Doesn’t that feel good? Please make just one minute away from your busy, busy schedule to sit and appreciate how much this means to you ";
                String notificationStr="Habit Notification";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setAutoCancel(true);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(alarmSound);
                Notification notification = builder.setContentTitle(bundle.getString("NOTIFICATIONHEADING"))
                        .setContentText(notificationStr)
                        //.setTicker("New Message Alert!")
                        .setSmallIcon(R.drawable.app_icon)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationStr))
                        .setContentIntent(pendingIntent).build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                // notificationManager.notify(bundle.getInt("NOTIFICATIONID"), notification);
                ///////////////For Oreo Change////////
                // mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                String NOTIFICATION_CHANNEL_ID = "10008";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME_HABIT", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.RED);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    assert notificationManager != null;
                    builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    notificationManager.createNotificationChannel(notificationChannel);
                    //////////

                }
                assert notificationManager != null;
                notificationManager.notify(bundle.getInt("NOTIFICATIONID") , builder.build());
                //////////////////
                ///////////////For Oreo Change End////////

            }
        }
    }
}
