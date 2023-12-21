package com.ashysystem.mbhq.util;

import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogWithCustomButton {

    private Context context;
    AlertDialogCustom.AlertResponse alertResponse;
    public AlertDialogWithCustomButton(Context context)
    {
        this.context=context;
    }
    public void ShowDialog(final String title, final String msg,boolean cancelFlag,String positiveButtonText,String negativeButtonText)
    {
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton(positiveButtonText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {

                        alertResponse.onDone(positiveButtonText);
                        //alertResponse.onCancel("");

                        dialog.cancel();
                    }
                });

        if(cancelFlag) {
            builder1.setNegativeButton(negativeButtonText,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //alertResponse.onDone("");
                            alertResponse.onCancel(negativeButtonText);
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
    public void setAlertAction(AlertDialogCustom.AlertResponse alertResponse)
    {
        this.alertResponse=alertResponse;
    }

}
