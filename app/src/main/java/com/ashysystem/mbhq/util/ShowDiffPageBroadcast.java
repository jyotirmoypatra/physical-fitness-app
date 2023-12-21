package com.ashysystem.mbhq.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class ShowDiffPageBroadcast extends BroadcastReceiver{

    SharedPreference sharedPreference;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent!=null && intent.getAction().equals(Util.INTENT_ACTION_BACKGROUND) && intent.getStringExtra("FROMACTIVITY").equals("MAINACTIVITY"))
        {
//            try {
//                context.unregisterReceiver(((MainActivity)context).getShowDiffPageBroadcast());
//                Log.e("INSIDEBROADCAST",">>>>>>>>>>>>");
//                sharedPreference=new SharedPreference(context);
//                if(sharedPreference.read("IsNonSubscribeUser", "").equals("true") && new SharedPreference(context).read("WELCOMECHECKONE","").equals("TRUE") && new SharedPreference(context).read("WELCOMECHECKTWO","").equals("TRUE") && new SharedPreference(context).read("WELCOMECHECKTHREE","").equals("TRUE") && new SharedPreference(context).read("WELCOMECHECKFOUR","").equals("TRUE"))
//                {
//                    if(Util.isSevenDayOver(context))
//                    {
//                        Log.e("ONRESUME","MAINACTIVITY");
//                        Intent intent1=new Intent(context,AccessSquadFreeActivity.class);
//                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent1.putExtra("FROMBROADCAST","TRUE");
//                        //context.startActivity(intent1);
//                    }else {
//                        Log.e("ONRESUME","MAINACTIVITY");
//                        Intent intent2=new Intent(context,ContinueSevenDayTrialActivity.class);
//                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent2.putExtra("FROMBROADCAST","TRUE");
//                        //context.startActivity(intent2);
//                    }
//                }else if(sharedPreference.read("IsNonSubscribeUser", "").equals("true") && !new SharedPreference(context).read("WELCOMECHECKONE","").equals("TRUE") && !new SharedPreference(context).read("WELCOMECHECKTWO","").equals("TRUE") && !new SharedPreference(context).read("WELCOMECHECKTHREE","").equals("TRUE") && !new SharedPreference(context).read("WELCOMECHECKFOUR","").equals("TRUE"))
//                {
//                    if(sharedPreference.read("SEVENDAY_TRIAL_START","").equals("TRUE"))
//                    {
//                        Log.e("ONRESUME","MAINACTIVITY");
//                        Intent intent2=new Intent(context,ContinueSevenDayTrialActivity.class);
//                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent2.putExtra("FROMBROADCAST","TRUE");
//                        //context.startActivity(intent2);
//                    }else
//                    {
//                        Log.e("ONRESUME","MAINACTIVITY");
//                        Intent intent1=new Intent(context,AccessSquadFreeActivity.class);
//                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent1.putExtra("FROMBROADCAST","TRUE");
//                        //context.startActivity(intent1);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
}
