package com.ashysystem.mbhq.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.util.Util;

import java.util.Random;

/**
 * Created by android-arindam on 5/9/17.
 */

public class SongNotification extends Notification{
    private Context ctx;
    private NotificationManager mNotificationManager;
    private String trackName="";
    static Notification notification;
    Builder mNotifyBuilder;

    @SuppressLint("NewApi")
    public void trackName(String track)
    {
        this.trackName="Squad";
    }

    public SongNotification(Context ctx, String songName, String status){
        super();
        this.ctx=ctx;
        this.trackName=songName;
        String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) ctx.getSystemService(ns);
        long when = System.currentTimeMillis();
        @SuppressWarnings("deprecation")
        RemoteViews contentView=new RemoteViews(ctx.getPackageName(), R.layout.layout_notification_music);
        mNotifyBuilder = new Builder(ctx);
        Util.mNotifyBuilder=mNotifyBuilder;
        notification = mNotifyBuilder.setContentTitle("Audio Book")
                .setContentText("Song")
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(when)
                .setContentIntent(PendingIntent.getActivity(ctx, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE))
                .setOngoing(true)
                .build();
        notification.bigContentView = contentView;

        Log.e("SONGNAMENOTIFICATION",songName+">>>>>>>>>>>>");

        if(status.equals("PLAY"))
        {
            contentView.setImageViewResource(R.id.btnPlay,R.drawable.exo_controls_pause);
        }else if(status.equals("PAUSE"))
        {
            contentView.setImageViewResource(R.id.btnPlay,R.drawable.exo_controls_play);
        }

        //set the button listeners
        setListeners(contentView,songName,status);
       notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.bigContentView.setTextViewText(R.id.txtSongName,trackName);

        mNotificationManager.notify(Util.AUDIO_NOTIFICATION_ID, notification);
    }

    public void setListeners(RemoteViews view,String songName,String status){

        Intent playPauseIntent = new Intent(ctx, AudioService.class);
        Intent previousSongIntent = new Intent(ctx, AudioService.class);
        Intent nextSongIntent = new Intent(ctx, AudioService.class);

        Bundle bundle=new Bundle();
        if(status.equals("PLAY"))
        {
            bundle.putString("STATUS", "PAUSE");
        }else {
            bundle.putString("STATUS", "PLAY");
        }
        bundle.putString("SONGNAME", songName);
        playPauseIntent.putExtras(bundle);

        Bundle bundle1=new Bundle();
        bundle1.putString("STATUS","PREVIOUS");
        bundle1.putString("SONGNAME", songName);
        previousSongIntent.putExtras(bundle1);

        Bundle bundle2=new Bundle();
        bundle2.putString("STATUS","NEXT");
        bundle2.putString("SONGNAME", songName);
        nextSongIntent.putExtras(bundle2);

        Random rand = new Random();

        int  n = rand.nextInt(500000) + 1;
        int  n1 = rand.nextInt(500000) + 2;
        int  n2 = rand.nextInt(500000) + 3;

        PendingIntent playpause = PendingIntent.getService(ctx, n, playPauseIntent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent previous = PendingIntent.getService(ctx, n1, previousSongIntent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent next = PendingIntent.getService(ctx, n2, nextSongIntent, PendingIntent.FLAG_IMMUTABLE);

        view.setOnClickPendingIntent(R.id.btnPlay, playpause);
        //view.setOnClickPendingIntent(R.id.btnRewind, previous);
        //view.setOnClickPendingIntent(R.id.btnFwd, next);





        /*//rewind listener
        Intent rewind=new Intent(ctx,MainActivity.class);
        rewind.putExtra("Song", "rewind");
        PendingIntent pRadio = PendingIntent.getActivity(ctx, 0, rewind, 0);
        view.setOnClickPendingIntent(R.id.btnRewind, pRadio);

        ///Play Pause
        Intent playPause=new Intent(ctx, MainActivity.class);
        playPause.putExtra("Song", "playpause");
        PendingIntent pVolume = PendingIntent.getActivity(ctx, 1, playPause, 0);
        ((MainActivity)ctx).setNotificationUpdate(new MainActivity.NotificationUpdate() {
            @Override
            public void onClicked() {
                Log.e("NOTIFICATION","CLICKED>>>>>>>>");
                notification.bigContentView.setImageViewResource(R.id.btnPlay,0);
                notification.bigContentView.setImageViewResource(R.id.btnPlay,R.drawable.exo_controls_play);
            }
        });
        view.setOnClickPendingIntent(R.id.btnPlay, pVolume);


        //forward listener
        Intent forward=new Intent(ctx, MainActivity.class);
        forward.putExtra("Song", "forward");
        PendingIntent pforward = PendingIntent.getActivity(ctx, 2, forward, 0);
        view.setOnClickPendingIntent(R.id.btnFwd, pforward);*/

    }
}
