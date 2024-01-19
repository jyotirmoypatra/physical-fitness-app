package com.ashysystem.mbhq;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Your code to handle the alarm event
        Log.d("AlarmReceiver", "Alarm fired!");
    }
}
