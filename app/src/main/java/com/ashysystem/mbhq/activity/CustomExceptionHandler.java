package com.ashysystem.mbhq.activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context context;
    public CustomExceptionHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        // Redirect to the launcher activity
       /* Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("FROM_LOGIN", "TRUE");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        // Terminate the app
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);*/
    }
}