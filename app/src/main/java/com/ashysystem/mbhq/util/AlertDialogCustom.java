package com.ashysystem.mbhq.util;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by android-arindam on 11/12/15.
 */
public class AlertDialogCustom {
    private Context context;
    AlertResponse alertResponse;
    public AlertDialogCustom(Context context)
    {
        this.context=context;
    }
    public void ShowDialog(final String title, final String msg,boolean cancelFlag)
    {
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {

                        alertResponse.onDone(title);
                        //alertResponse.onCancel("");

                        dialog.cancel();
                    }
                });

        if(cancelFlag) {
            builder1.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //alertResponse.onDone("");
                            alertResponse.onCancel("Cancel");
                            dialog.cancel();
                        }
                    });
        }
        android.app.AlertDialog alert11 = builder1.create();
        //alert11.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert11.show();
    }
    public static interface AlertResponse
    {
        public void onDone(String title);
        public void onCancel(String title);
    }
    public void setAlertAction(AlertResponse alertResponse)
    {
        this.alertResponse=alertResponse;
    }
}
