package com.ashysystem.mbhq.adapter.mbhq_today;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;

import com.ashysystem.mbhq.fragment.habit_hacker.HabbitDetailsCalendarFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddSecondPage;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerEditFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerListFragment;
import com.ashysystem.mbhq.fragment.MbhqTodayTwoFragment;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.model.habit_hacker.NewAction;
import com.ashysystem.mbhq.model.habit_hacker.SwapAction;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayHabitHackerAdpapter extends RecyclerView.Adapter<TodayHabitHackerAdpapter.MyViewHolder> {

    public enum HabitActionStatus {
        ACTION_STATUS_UNKNOWN,
        ACTION_STATUS_DONE,
        ACTION_STATUS_NOT_DONE
    }


    public interface OnMultipleHabitTaskListener {
        void onMultipleHabitTaskSelected(Integer taskId, boolean isDone, Integer habitId, Integer tickingMode);

        void onAllCheck(String tag);
    }

    private int checkCounter = 0;

    private String TAG = "TodayHabitHackerAdpapter";
    Context context;
    ArrayList<HabitSwap> lstHabitToday;
    boolean allowMultipleTicks = false;
    boolean direct = false;

    MbhqTodayTwoFragment mbhqTodayTwoFragment;
    OnMultipleHabitTaskListener onMultipleHabitTaskListener = null;

    public TodayHabitHackerAdpapter(Context context, ArrayList<HabitSwap> lstHabitToday, boolean allowMultipleTicks, boolean direct, OnMultipleHabitTaskListener onMultipleHabitTaskListener, MbhqTodayTwoFragment mbhqTodayTwoFragment) {
        this.context = context;
        this.lstHabitToday = lstHabitToday;
        this.mbhqTodayTwoFragment = mbhqTodayTwoFragment;
        this.onMultipleHabitTaskListener = onMultipleHabitTaskListener;
        this.allowMultipleTicks = allowMultipleTicks;
        this.direct = direct;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_habithacker_today, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.txtHabit.setTextColor(Color.parseColor("#000000"));

        holder.txtHabit.setText(lstHabitToday.get(position).getHabitName());
        Log.e("Todays Habit name", "Today Habit name---------------->" + lstHabitToday.get(position).getHabitName());

        HabitSwap data = lstHabitToday.get(position);

        if( data.getNewAction().getPushNotification() || data.getNewAction().getEmail()){
            holder.imgnotification.setVisibility(View.VISIBLE);
        }else{
            holder.imgnotification.setVisibility(View.GONE);
        }


//        if (direct){
//            holder.imgHabit.setImageResource(R.drawable.mbhq_circle_green);
//            holder.imgHabit.setEnabled(false);
//            holder.imgHabit.setTag(ACTION_STATUS_UNKNOWN);
//        }else{
            if (
                    data.getNewAction().getCurrentDayTask() == null
                            &&
                            data.getNewAction().getCurrentDayTask2() == null
                            &&
                            data.getSwapAction().getCurrentDayTask() == null
                            &&
                            data.getSwapAction().getCurrentDayTask2() == null
            ) {
                holder.imgHabit.setImageResource(R.drawable.mbhq_circle_green);
                holder.imgHabit.setEnabled(false);
                holder.imgHabit.setTag(HabitActionStatus.ACTION_STATUS_UNKNOWN);
            } else {

                SwapAction swapAction = data.getSwapAction();

                NewAction newAction = data.getNewAction();

                if (
                        (newAction.getCurrentDayTask() != null && newAction.getCurrentDayTask().getIsDone())
                                ||
                                (newAction.getCurrentDayTask2() != null && newAction.getCurrentDayTask2().getIsDone())
                ) {

                    holder.imgHabit.setImageResource(R.drawable.mbhq_green_check);
                    checkCounter++;
                    holder.imgHabit.setTag(HabitActionStatus.ACTION_STATUS_DONE);

                } else if (
                        (swapAction.getCurrentDayTask() != null && swapAction.getCurrentDayTask().getIsDone())
                                ||
                                (swapAction.getCurrentDayTask2() != null && swapAction.getCurrentDayTask2().getIsDone())

                ) {

                    holder.imgHabit.setImageResource(R.drawable.ic_close_gray);
                    holder.imgHabit.setTag(HabitActionStatus.ACTION_STATUS_NOT_DONE);

                } else {
                    holder.imgHabit.setImageResource(R.drawable.mbhq_circle_green);
                    holder.imgHabit.setTag(HabitActionStatus.ACTION_STATUS_UNKNOWN);
                }

            }
            Log.e("Checked", "totalChecked---------->" + checkCounter);
            Log.e("position", "position------------->" + position);
            Log.e("List Size", "List Size----------->" + lstHabitToday.size());
            if (position == lstHabitToday.size() - 1) {
                Log.e("Checked", "called");
                isAllChecked(lstHabitToday);
            }
//        }




        holder.imgHabit.setOnClickListener(view -> {
                switch ((HabitActionStatus) view.getTag()) {

                    case ACTION_STATUS_UNKNOWN:
                        Log.e("unknowTest", "unknown0");

                        if (data.getNewAction().getCurrentDayTask() != null) {

                            data.getNewAction().getCurrentDayTask().setIsDone(true);
                            Log.e("unknowTest", "unknown00");
                            updateTask(data.getNewAction().getCurrentDayTask().getTaskMasterId(), true, lstHabitToday.get(position).getHabitId(), 0);

                        } else if (data.getNewAction().getCurrentDayTask2() != null) {

                            data.getNewAction().getCurrentDayTask2().setIsDone(true);
                            Log.e("unknowTest", "unknown000");
                            updateTask(data.getNewAction().getCurrentDayTask2().getTaskMasterId(), true, lstHabitToday.get(position).getHabitId(), 0);

                        }

                        isAllChecked(lstHabitToday);
                        break;

                    case ACTION_STATUS_DONE:
                        Log.e("unknowTest", "unknown1");


                        if (data.getSwapAction().getCurrentDayTask() != null) {

                            data.getSwapAction().getCurrentDayTask().setIsDone(true);

                            if (data.getNewAction().getCurrentDayTask() != null) {
                                data.getNewAction().getCurrentDayTask().setIsDone(false);
                            } else if (data.getNewAction().getCurrentDayTask2() != null) {
                                data.getNewAction().getCurrentDayTask2().setIsDone(false);
                            }

                            updateTask(data.getSwapAction().getCurrentDayTask().getTaskMasterId(), true, lstHabitToday.get(position).getHabitId(), 1);

                        } else if (data.getSwapAction().getCurrentDayTask2() != null) {

                            data.getSwapAction().getCurrentDayTask2().setIsDone(true);

                            if (data.getNewAction().getCurrentDayTask() != null) {
                                data.getNewAction().getCurrentDayTask().setIsDone(false);
                            } else if (data.getNewAction().getCurrentDayTask2() != null) {
                                data.getNewAction().getCurrentDayTask2().setIsDone(false);
                            }

                            updateTask(data.getSwapAction().getCurrentDayTask2().getTaskMasterId(), true, lstHabitToday.get(position).getHabitId(), 1);

                        }

                        isAllChecked(lstHabitToday);
                        break;

                    case ACTION_STATUS_NOT_DONE:
                        Log.e("unknowTest", "unknown2");

                        if (data.getSwapAction().getCurrentDayTask() != null) {

                            data.getSwapAction().getCurrentDayTask().setIsDone(false);

                        } else if (data.getSwapAction().getCurrentDayTask2() != null) {

                            data.getSwapAction().getCurrentDayTask2().setIsDone(false);

                        }

                        if (data.getNewAction().getCurrentDayTask() != null) {

                            data.getNewAction().getCurrentDayTask().setIsDone(false);

                            updateTask(data.getNewAction().getCurrentDayTask().getTaskMasterId(), false, lstHabitToday.get(position).getHabitId(), 2);

                        } else if (data.getNewAction().getCurrentDayTask2() != null) {

                            data.getNewAction().getCurrentDayTask2().setIsDone(false);

                            updateTask(data.getNewAction().getCurrentDayTask2().getTaskMasterId(), false, lstHabitToday.get(position).getHabitId(), 2);

                        }

                        isAllChecked(lstHabitToday);
                        break;


                    default:
                        Log.i(TAG, "No tags associated with this image");
                }
                notifyItemChanged(position);




        });


        holder.rlHabitMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                openDialogAfterLongPress(lstHabitToday.get(position));
                return true;
            }
        });

        holder.rlHabitMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if (Util.checkConnection(context)) {
                        Log.i("lstHabitToday1111111111111111","1");
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", lstHabitToday.get(position).getHabitId());
                        bundle.putString("habbit_name", lstHabitToday.get(position).getHabitName());
                        bundle.putString("break_name", lstHabitToday.get(position).getSwapAction().getName());
                        bundle.putBoolean("push_notification", lstHabitToday.get(position).getNewAction().getPushNotification());
                        bundle.putString("break_name", lstHabitToday.get(position).getSwapAction().getName());
                        Log.i("lstHabitToday1111111111111111","2");
                        bundle.putBoolean("new_action1", lstHabitToday.get(position).getNewAction().getCurrentDayTask().getIsDone());
                        Log.i("lstHabitToday1111111111111111","3");
                        bundle.putBoolean("new_action2", lstHabitToday.get(position).getNewAction().getCurrentDayTask().getIsDone());
                        Log.i("lstHabitToday1111111111111111","4");
                        bundle.putBoolean("swap_action1", lstHabitToday.get(position).getSwapAction().getCurrentDayTask().getIsDone());
                        Log.i("lstHabitToday1111111111111111","5");
                        bundle.putBoolean("swap_action2", lstHabitToday.get(position).getSwapAction().getCurrentDayTask().getIsDone());
                        Log.i("lstHabitToday1111111111111111","6");
                        bundle.putString("FROM_TODAY_PAGE", "TRUE");
                        if (lstHabitToday.get(position).getNewAction().getTaskFrequencyTypeId() != null) {
                            bundle.putInt("TASK_FREQUENCY", lstHabitToday.get(position).getNewAction().getTaskFrequencyTypeId());
                        }
                         Util.backtotodayhabit="yes";
                        Log.e("print habbit id--", lstHabitToday.get(position).getHabitId() + "?");
                        ((MainActivity) context).clearCacheForParticularFragment(new HabbitDetailsCalendarFragment());
                        HabbitDetailsCalendarFragment habbitDetailsCalendarFragment = new HabbitDetailsCalendarFragment();
                        habbitDetailsCalendarFragment.setArguments(bundle);
                        ((MainActivity) context).loadFragment(habbitDetailsCalendarFragment, "HabbitCalendara", bundle);
                    } else {
                        Util.showToast(context, Util.networkMsg);
                    }


            }
        });

    }

    private void isAllChecked(List<HabitSwap> habitSwapsList) {
        Log.e("Checked", "called1");
        int checkCount = 0, crossCount = 0;

        for (int i = 0; i < habitSwapsList.size(); i++) {

            HabitSwap data = habitSwapsList.get(i);
            if (
                    (data.getNewAction().getCurrentDayTask() != null && data.getNewAction().getCurrentDayTask().getIsDone())
                            ||
                            (data.getNewAction().getCurrentDayTask2() != null && data.getNewAction().getCurrentDayTask2().getIsDone())
            ){

                checkCount++;
            }


            if (
                    (data.getSwapAction().getCurrentDayTask() != null && data.getSwapAction().getCurrentDayTask().getIsDone())
                            ||
                            (data.getSwapAction().getCurrentDayTask2() != null && data.getSwapAction().getCurrentDayTask2().getIsDone())

            ) {
                crossCount++;
            }


        }

        Log.e("Crosscounter", "crossCount" + crossCount);
        Log.e("checkCounter", "checkcounter" + checkCount);

        Log.e("count--------->", String.valueOf(checkCount));
        Log.e("size---------->", String.valueOf(habitSwapsList.size()));
        if (checkCount == habitSwapsList.size()){
            Log.i("called_habit_section","TAG_ALLCHECKED");

            onMultipleHabitTaskListener.onAllCheck(Util.TAG_ALLCHECKED);

        }

        else {

            if (crossCount == habitSwapsList.size()) {
                Log.i("called_habit_section","TAG_CROSS");

                onMultipleHabitTaskListener.onAllCheck(Util.TAG_CROSS);
            } else {
                Log.i("called_habit_section","TAG_UNCHECKED");
                onMultipleHabitTaskListener.onAllCheck(Util.TAG_UNCHECKED);
            }
        }


    }


    private void openDialogAfterLongPress(HabitSwap habitSwap) {
        if(!direct){
            final Dialog dialog = new Dialog(context, R.style.DialogTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_common_longpess_edit_delete);

            ImageView imgClose = dialog.findViewById(R.id.imgClose);
            RelativeLayout rlEdit = dialog.findViewById(R.id.rlEdit);
            RelativeLayout rlDelete = dialog.findViewById(R.id.rlDelete);
            RelativeLayout rlTopTransparent = dialog.findViewById(R.id.rlTopTransparent);
            RelativeLayout rlBottomTransparent = dialog.findViewById(R.id.rlBottomTransparent);

            imgClose.setOnClickListener(view -> {
                dialog.dismiss();
            });

            rlBottomTransparent.setOnClickListener(view -> {
                dialog.dismiss();
            });

            rlTopTransparent.setOnClickListener(view -> {
                dialog.dismiss();
            });

            rlEdit.setOnClickListener(view -> {
                dialog.dismiss();
                if (Util.checkConnection(context)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", habitSwap.getHabitId());
                    bundle.putString("habbit_name", habitSwap.getHabitName());
                    bundle.putString("break_name", habitSwap.getSwapAction().getName());

                    //bundle.putString("FROM_TODAY_PAGE","TRUE");
                    bundle.putString("FROM_SIMPLE_HABIT_LIST", "TRUE");

                    Log.e("print habbit id--", habitSwap.getHabitId() + "?");
                    Util.backtotodayhabit="yes";
                    ((MainActivity) context).clearCacheForParticularFragment(new HabitHackerEditFragment());
                    HabitHackerEditFragment habitHackerEditFragment = new HabitHackerEditFragment();
                    habitHackerEditFragment.setArguments(bundle);
                    ((MainActivity) context).loadFragment(habitHackerEditFragment, "HabitHackerEdit", bundle);
                } else {
                    Util.showToast(context, Util.networkMsg);
                }
            });

            rlDelete.setOnClickListener(view -> {
                dialog.dismiss();

                final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(context);
                alertDialogCustom.ShowDialog("Alert!", "Are you sure want to delete?", true);
                alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                    @Override
                    public void onDone(String title) {
                        mbhqTodayTwoFragment.deleteHabitSwapApiCall(habitSwap.getHabitId());
                    }

                    @Override
                    public void onCancel(String title) {

                    }
                });

            });

            dialog.show();
        }

    }


    @Override
    public int getItemCount() {

        return lstHabitToday.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtHabit;
        private ImageView imgHabit,imgnotification;
        RelativeLayout rlHabitMain;
        LinearLayout llTop;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtHabit = itemView.findViewById(R.id.txtHabit);
            this.imgHabit = itemView.findViewById(R.id.imgHabit);
            this.imgnotification = itemView.findViewById(R.id.imgnotification);
            this.rlHabitMain = itemView.findViewById(R.id.rlHabitMain);
            this.llTop = itemView.findViewById(R.id.llTop);
        }
    }

    public void updateTask(Integer id, Boolean status, Integer habitId, Integer tickingMode) {

        Log.e("unknowTest", "unknown0000");
            if (allowMultipleTicks && onMultipleHabitTaskListener != null) {
                Log.i("called_habit_section","unknown1111");

                onMultipleHabitTaskListener.onMultipleHabitTaskSelected(id, status, habitId, tickingMode);
                Log.e("updteLog", "updateLog+");

            } else if (Connection.checkConnection(context)) {
                Log.e("unknowTest", "unknown22222");
                ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...");
                SharedPreference sharedPreference = new SharedPreference(context);

                JsonObject object = new JsonObject();
                object.addProperty("TaskId", id);
                object.addProperty("IsDone", status);
                object.addProperty("Key", Util.KEY);
                object.addProperty("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

               // Log.e("RequestSent", object.toString());
                Log.e("called_habit_section", object.toString());
                FinisherServiceImpl finisherService = new FinisherServiceImpl(context);
                Call<JsonObject> serverCall = finisherService.updateVitaminTask(object);
                Log.e("updteLog", "updateLog+");
                serverCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        progressDialog.dismiss();
                        if (response.body() != null) {
                            Log.e("called_habit_section", "success");

                            if (response.body().get("SuccessFlag").getAsBoolean()) {
                                Log.e("called_habit_section", "success-----");
                                ((MainActivity) context).clearCacheForParticularFragment(new HabitHackerListFragment());
                                ((MainActivity) context).clearCacheForParticularFragment(new HabbitDetailsCalendarFragment());
                                ((MainActivity) context).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                              //  ((MainActivity) context).callTaskStatusForDateAPI();

                                try {
                                    Completable.fromAction(() -> {
                                        mbhqTodayTwoFragment.mViewModel.deleteHabitCalendarById(habitId);
                                    }).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(() -> {
                                                Completable.fromAction(() -> {
                                                    mbhqTodayTwoFragment.mViewModelEdit.deleteByHabitId(habitId);
                                                })
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(() -> {
                                                            Log.e("called_habit_section", "DELETE_SUCCESSFULL");
                                                            Log.e("DATABASE", "DELETE_SUCCESSFULL");

                                                        /*Util.isReloadTodayMainPage = true;
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("LOAD_SECOND_PAGE","TRUE");
                                                        ((MainActivity)context).loadFragment(new MbhqTodayMainFragment(),"MbhqTodayMain",bundle);*/
                                                            ((MainActivity) context).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);

                                                        }, throwable -> {
                                                            Log.e("called_habit_section", "DELETE_UN_SUCCESSFULL");

                                                            Log.e("DATABASE", "DELETE_UN_SUCCESSFULL");
                                                        /*Util.isReloadTodayMainPage = true;
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("LOAD_SECOND_PAGE","TRUE");
                                                        ((MainActivity)context).loadFragment(new MbhqTodayMainFragment(),"MbhqTodayMain",bundle);*/
                                                            ((MainActivity) context).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                                        });
                                            }, throwable -> {
                                                Log.e("called_habit_section", "DELETE_UN_SUCCESSFULL-------------");
                                            /*Util.isReloadTodayMainPage = true;
                                            Bundle bundle = new Bundle();
                                            bundle.putString("LOAD_SECOND_PAGE","TRUE");
                                            ((MainActivity)context).loadFragment(new MbhqTodayMainFragment(),"MbhqTodayMain",bundle);*/
                                                ((MainActivity) context).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                            });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });

            } else {
                Util.showToast(context, Util.networkMsg);
            }







    }


}