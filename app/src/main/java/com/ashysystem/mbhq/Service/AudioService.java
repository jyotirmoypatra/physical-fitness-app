package com.ashysystem.mbhq.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.ashysystem.mbhq.model.SongInfoModel;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;

import java.util.ArrayList;

public class AudioService extends Service implements MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener{

	private static final String LOGCAT = null;
	public MediaPlayer objPlayer=null;
	private ArrayList<SongInfoModel>  arrSong=new ArrayList<>();
	private  int currentPosition=0;
	private final IBinder musicBind = new ServiceBinder();
	TrackChangeLIstener onTrackchangeListener;



	public void setCurrenentTrack(TrackChangeLIstener currenentTrack) {
		this.onTrackchangeListener = currenentTrack;
	}


	@Override
	public void onCreate(){
	    super.onCreate();
		Log.e("create","123");
	    Log.d(LOGCAT, "Service Started!");
	}
	public class ServiceBinder extends Binder {
		public AudioService getService()
		{
			return AudioService.this;
		}
	}
	@Override
	public IBinder onBind(Intent arg0){return musicBind;}
	public void setPlayList(ArrayList<SongInfoModel>  arrSong)
	{
		this.arrSong=arrSong;

	}
	public void playSong(int position)
	{
		this.currentPosition=position;
		stopMusic();
		playSong();
	}
	public int getCurrentPosition()
	{
		return currentPosition;
	}

	public int onStartCommand(Intent intent, int flags, int startId){
		Log.e("start cmd","123");
		Log.e("FLAGS",flags+">>>>>");
		Log.e("STARTID",startId+">>>>>");

		/*try {
			NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.cancel(Util.AUDIO_NOTIFICATION_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		if(intent!=null)
		{
			Bundle bundle=intent.getExtras();
			if(bundle!=null && bundle.containsKey("STATUS") && bundle.getString("STATUS").equals("PAUSE"))
			{
				SongNotification songNotification=new SongNotification(getApplicationContext(),bundle.getString("SONGNAME"),"PAUSE");
				stopMusic();
			}else if(bundle!=null && bundle.containsKey("STATUS") && bundle.getString("STATUS").equals("PLAY"))
			{
				SongNotification songNotification=new SongNotification(getApplicationContext(),bundle.getString("SONGNAME"),"PLAY");
				playSong();
			}else if(bundle!=null && bundle.containsKey("STATUS") && bundle.getString("STATUS").equals("PREVIOUS"))
			{
				playPrevious();

			}else if(bundle!=null && bundle.containsKey("STATUS") && bundle.getString("STATUS").equals("NEXT"))
			{
				playNext();
			}
		}


	    return START_STICKY;
	}

	public void playSong() {

		try {
			objPlayer = new MediaPlayer();
			objPlayer.setDataSource(AudioService.this, Uri.parse(arrSong.get(currentPosition).getPath()));
			objPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			objPlayer.prepare();
			objPlayer.start();
			objPlayer.setOnBufferingUpdateListener(this);
			objPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playSongSeekTo(int seekTo) {

		try {
			objPlayer = new MediaPlayer();
			objPlayer.setDataSource(AudioService.this, Uri.parse(arrSong.get(0).getPath()));
			objPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			objPlayer.prepare();
			objPlayer.start();
			objPlayer.seekTo(seekTo);
			objPlayer.setOnBufferingUpdateListener(this);
			objPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopMusic(){
		Log.e("stop","123");
		try {
			if(objPlayer!=null)
			{
				objPlayer.stop();
				objPlayer.release();
			}

		}catch (Exception e)
		{

		}
	}

	public void onPause(){
		Log.e("pause","123");
		try {
			if(objPlayer!=null)
			{
				objPlayer.stop();
				objPlayer.release();
			}

		}catch (Exception e)
		{

		}
	}
	
	public void onDestroy(){
		Log.e("destroy","123");
		try {
			if(objPlayer!=null)
			{
				objPlayer.stop();
				objPlayer.release();
				new SharedPreference(AudioService.this).clear("audiobook");
			}

		}catch (Exception e)
		{

		}


	}



	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.e("complete","123");
	    playNext();


	}

	private void playNext() {
		currentPosition++;
		if(currentPosition<arrSong.size())
		{
			objPlayer.reset();
			if(arrSong.get(currentPosition)!=null)
			{
				Log.e("con 1","123");
				playSong();
				onTrackchangeListener.onTrackChange(currentPosition);
			}else {
				Log.e("con 1","123");
				playNext();
				onTrackchangeListener.onTrackChange(currentPosition);
			}

			SongNotification songNotification=new SongNotification(getApplicationContext(),arrSong.get(currentPosition).getTitle(),"NEXT");

		}
		else
		{
			try {
				if(objPlayer!=null)
				{
					objPlayer.stop();
					objPlayer.release();
					NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.cancel(Util.AUDIO_NOTIFICATION_ID);
				}

			}catch (Exception e)
			{

			}

		}
	}

	private void playPrevious() {
		currentPosition--;
		if(currentPosition>=0)
		{
			objPlayer.reset();
			if(arrSong.get(currentPosition)!=null)
			{
				Log.e("con 1","123");
				playSong();
				onTrackchangeListener.onTrackChange(currentPosition);
			}else {
				Log.e("con 1","123");
				playPrevious();
				onTrackchangeListener.onTrackChange(currentPosition);
			}
			SongNotification songNotification=new SongNotification(getApplicationContext(),arrSong.get(currentPosition).getTitle(),"PREVIOUS");
		}
		else
		{
			try {
				if(objPlayer!=null)
				{
					objPlayer.stop();
					objPlayer.release();
					NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.cancel(Util.AUDIO_NOTIFICATION_ID);
				}

			}catch (Exception e)
			{

			}

		}
	}



	public interface TrackChangeLIstener
	{
		public void onTrackChange(int position);
	}

	public static class ExternalBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

		}
	}

}
