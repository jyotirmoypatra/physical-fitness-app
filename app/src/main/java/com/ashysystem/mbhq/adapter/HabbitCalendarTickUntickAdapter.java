package com.ashysystem.mbhq.adapter;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.fragment.habit_hacker.HabbitCalendarTickUntickFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabbitDetailsCalendarFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerListFragment;
import com.ashysystem.mbhq.model.habit_hacker.HabbitCalendarModel;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HabbitCalendarTickUntickAdapter extends RecyclerView.Adapter<HabbitCalendarTickUntickAdapter.VHItem> {
    private Context context;
    HabbitCalendarModel habbitCalendarModel;
    ProgressDialog progressDialog;
    SharedPreference sharedPreference;
    FinisherServiceImpl finisherService;
    int habbitId;
    boolean noteOpen;
    String details;
    int userId;
    HabbitCalendarTickUntickFragment tickUntickFragment;


    public HabbitCalendarTickUntickAdapter(Context context, HabbitCalendarModel habbitCalendarModel, boolean noteOpen, int habbitId, String strObj, HabbitCalendarTickUntickFragment tickUntickFragment) {
        this.context = context;
        this.habbitCalendarModel = habbitCalendarModel;
        sharedPreference = new SharedPreference(this.context);
        finisherService = new FinisherServiceImpl(this.context);
        this.noteOpen = noteOpen;
        this.habbitId = habbitId;
        this.details = strObj;
        this.tickUntickFragment = tickUntickFragment;
        userId = Integer.parseInt(sharedPreference.read("UserID", ""));
    }

    @NonNull
    @Override
    public VHItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_calendar_habbit_tick_untick, viewGroup, false);
        return new VHItem(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHItem vhItem, int i) {
        try {
            String dateRcvd = habbitCalendarModel.getHabitStats().get(i).getTaskDate();
            String[] spDate = dateRcvd.split("T");
            SimpleDateFormat sdfG = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfR = new SimpleDateFormat("EEE dd MMM");
            Date dateObj = null;
            try {
                dateObj = sdfG.parse(dateRcvd);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            vhItem.txtDate.setText(sdfR.format(dateObj));
            if (habbitCalendarModel.getHabitStats().get(i).getNote() != null)
                vhItem.txtNote.setText(habbitCalendarModel.getHabitStats().get(i).getNote());
            else
                vhItem.txtNote.setText("");
            if (noteOpen) {
                vhItem.txtNote.setVisibility(View.VISIBLE);
            } else
                vhItem.txtNote.setVisibility(View.GONE);
            if (habbitCalendarModel.getHabitStats().get(i).getIsTaskDone()) {
                vhItem.imgTickHabbit.setImageResource(R.drawable.mbhq_tick_green);
            } else {
                vhItem.imgTickHabbit.setImageResource(R.drawable.mbhq_circle_green);
            }

            if (habbitCalendarModel.getBreakHabitStats().get(i).getIsTaskDone()) {
                vhItem.imgTickBreak.setImageResource(R.drawable.ic_close_gray);
            } else {
                vhItem.imgTickBreak.setImageResource(R.drawable.ic_grey_circle);
            }
             /*   if(habbitCalendarModel.getBreakHabitStats().get(i).getNote()!=null)
                {
                    vhItem.imgEdit.setImageResource(R.drawable.mbhq_edit_active);
                }else
                {
                    vhItem.imgEdit.setImageResource(R.drawable.mbhq_edit_inactive);
                }*/


            if (!vhItem.txtNote.getText().equals("")) {
                vhItem.imgEdit.setImageResource(R.drawable.mbhq_edit_active);
            } else {
                vhItem.imgEdit.setImageResource(R.drawable.ic_edit_gray);
            }

            if(isMonday(habbitCalendarModel.getHabitStats().get(i).getTaskDate()))
            {
                vhItem.txtDate.setText(Html.fromHtml("<b>" + vhItem.txtDate.getText().toString() + "</b>"));
            }else {
                vhItem.txtDate.setText(vhItem.txtDate.getText().toString());
            }

            if(habbitCalendarModel.getHabitStats().get(i).isShowThickView())
            {
                vhItem.viewEndOfMonth.setVisibility(View.VISIBLE);
            }else {
                vhItem.viewEndOfMonth.setVisibility(View.GONE);
            }

            Date finalDateObj = dateObj;

            if (finalDateObj.after(new Date())) {
                vhItem.imgTickHabbit.setAlpha((float) 0.5);
                vhItem.rlTickBreak.setAlpha((float) 0.5);
                vhItem.imgEdit.setAlpha((float) 0.5);
            } else {
                vhItem.imgTickHabbit.setAlpha((float) 1.0);
                vhItem.rlTickBreak.setAlpha((float) 1.0);
                vhItem.imgEdit.setAlpha((float) 1.0);
            }

            vhItem.rlTickHabbit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*boolean future = finalDateObj.after(new Date());
                    if (!future)
                        UpdateTaskStatus(habbitCalendarModel.getHabitStats().get(i).getTaskMasterId(), !habbitCalendarModel.getHabitStats().get(i).getIsTaskDone(), i, vhItem.imgTickHabbit, "habbit");*/

                    boolean future = finalDateObj.after(new Date());
                    if(!future)
                    {
                        tickUntickFragment.rlSaveHabitCancel.setVisibility(View.VISIBLE);
                        if(habbitCalendarModel.getHabitStats().get(i).getIsTaskDone())
                        {
                            habbitCalendarModel.getHabitStats().get(i).setIsTaskDone(false);
                            tickUntickFragment.setHabitBreakTickUntick(habbitCalendarModel.getHabitStats().get(i).getTaskMasterId(),false);
                            //1,true
                        }else {
                            habbitCalendarModel.getHabitStats().get(i).setIsTaskDone(true);
                            tickUntickFragment.setHabitBreakTickUntick(habbitCalendarModel.getHabitStats().get(i).getTaskMasterId(),true);
                            //0,true
                        }
                        notifyItemChanged(i);
                    }
                }
            });
            vhItem.rlTickBreak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*boolean future = finalDateObj.after(new Date());
                    if (!future) {
                        UpdateTaskStatus(habbitCalendarModel.getBreakHabitStats().get(i).getTaskMasterId(), !habbitCalendarModel.getBreakHabitStats().get(i).getIsTaskDone(), i, vhItem.imgTickBreak, "break");
                    }*/

                    boolean future = finalDateObj.after(new Date());
                    if(!future)
                    {
                        tickUntickFragment.rlSaveHabitCancel.setVisibility(View.VISIBLE);
                        if(habbitCalendarModel.getBreakHabitStats().get(i).getIsTaskDone())
                        {
                            habbitCalendarModel.getBreakHabitStats().get(i).setIsTaskDone(false);
                            tickUntickFragment.setHabitBreakTickUntick(habbitCalendarModel.getBreakHabitStats().get(i).getTaskMasterId(),false);
                            //1,true
                        }else {
                            habbitCalendarModel.getBreakHabitStats().get(i).setIsTaskDone(true);
                            tickUntickFragment.setHabitBreakTickUntick(habbitCalendarModel.getBreakHabitStats().get(i).getTaskMasterId(),true);
                        }
                        notifyItemChanged(i);
                    }
                }
            });
            vhItem.rlEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean future = finalDateObj.after(new Date());
                    vhItem.txtNote.setVisibility(View.VISIBLE);
                    openNoteDialog(vhItem.txtNote, habbitCalendarModel.getHabitStats().get(i).getTaskMasterId(), habbitCalendarModel.getHabitStats().get(i), vhItem.imgEdit, future);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isMonday(String taskDate) {
        boolean isFirstDay = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dtTask = null;
        try {
            dtTask = dateFormat.parse(taskDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtTask);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if(dayOfWeek == 2)
        {
            isFirstDay = true;
        }else {
            isFirstDay = false;
        }

        return isFirstDay;
    }

    @Override
    public int getItemCount() {
        Log.e("row size--", habbitCalendarModel.getHabitStats().size() + "??");
        return habbitCalendarModel.getHabitStats().size();
    }

    static class VHItem extends RecyclerView.ViewHolder {
        private TextView txtDate, txtNote;
        private ImageView imgTickHabbit, imgTickBreak, imgEdit;
        private RelativeLayout rlTickHabbit, rlTickBreak, rlEdit, rlMain;
        private View viewEndOfMonth;

        public VHItem(View itemView) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtNote = (TextView) itemView.findViewById(R.id.txtNote);
            imgTickHabbit = (ImageView) itemView.findViewById(R.id.imgTickHabbit);
            imgTickBreak = (ImageView) itemView.findViewById(R.id.imgTickBreak);
            rlTickHabbit = (RelativeLayout) itemView.findViewById(R.id.rlTickHabbit);
            rlTickBreak = (RelativeLayout) itemView.findViewById(R.id.rlTickBreak);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            rlEdit = (RelativeLayout) itemView.findViewById(R.id.rlEdit);
            rlMain = (RelativeLayout) itemView.findViewById(R.id.rlMain);
            viewEndOfMonth = itemView.findViewById(R.id.viewEndOfMonth);
        }
    }

/*
    private void UpdateTaskStatus(int TaskId, boolean IsDone, int position, ImageView imgTick, String type) {

        if (Connection.checkConnection(context)) {

            progressDialog = ProgressDialog.show(context, "", "Please wait...");


            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("TaskId", TaskId);
            hashReq.put("IsDone", IsDone);

            Call<JsonObject> userHabitSwapsModelCall = finisherService.UpdateTaskStatus(hashReq);
            userHabitSwapsModelCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            ((MainActivity) context).clearCacheForParticularFragment(new HabbitDetailsCalendarFragment());
                            ((MainActivity) context).clearCacheForParticularFragment(new HabitHackerListFragment());
                            ((MainActivity) context).clearHashMapHabbit(habbitId);
                            Util.isReloadTodayMainPage = true;

                            Completable.fromAction(() -> {
                                tickUntickFragment.mViewModel.deleteHabitCalendarById(habbitId);
                            }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {

                                    }, throwable -> {

                                    });

                            Completable.fromAction(() -> {
                                tickUntickFragment.mViewModelEdit.deleteByHabitId(habbitId);
                            })
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        Log.e("DATABASE", "DELETE_SUCCESSFULL");
                                    }, throwable -> {
                                        Log.e("DATABASE", "DELETE_UN_SUCCESSFULL");
                                    });
                            if (type.equals("habbit")) {
                                if (!IsDone) {
                                    imgTick.setImageResource(R.drawable.mbhq_circle_green);
                                } else {
                                    imgTick.setImageResource(R.drawable.mbhq_tick_green);
                                }
                            } else {
                                if (!IsDone) {
                                    imgTick.setImageResource(R.drawable.ic_grey_circle);
                                } else {
                                    imgTick.setImageResource(R.drawable.ic_close_gray);
                                }
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
*/

    private void UpdateTaskNote(int TaskId, TextView textview, String desc, Dialog dialog, ImageView imgEdit, HabbitCalendarModel.HabitStat habitStat) {

        if (Connection.checkConnection(context)) {

            progressDialog = ProgressDialog.show(context, "", "Please wait...");


            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("TaskMasterId", TaskId);
            hashReq.put("Note", desc);


            Call<JsonObject> userHabitSwapsModelCall = finisherService.UpdateTaskNote(hashReq);
            userHabitSwapsModelCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            ((MainActivity) context).clearCacheForParticularFragment(new HabbitDetailsCalendarFragment());
                            ((MainActivity) context).clearCacheForParticularFragment(new HabitHackerListFragment());
                            habitStat.setNote(desc);
                            ((MainActivity) context).clearHashMapHabbit(habbitId);
                            Util.isReloadTodayMainPage = true;

                            Completable.fromAction(() -> {
                                tickUntickFragment.mViewModel.deleteHabitCalendarById(habbitId);
                            }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {

                                    }, throwable -> {

                                    });

                            Completable.fromAction(() -> {
                                tickUntickFragment.mViewModelEdit.deleteByHabitId(habbitId);
                            })
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        Log.e("DATABASE", "DELETE_SUCCESSFULL");
                                    }, throwable -> {
                                        Log.e("DATABASE", "DELETE_UN_SUCCESSFULL");
                                    });

                            textview.setText(desc);
                            imgEdit.setImageResource(R.drawable.mbhq_edit_active);
                            Util.showToast(context, "Note Saved Successfully");
                            dialog.dismiss();


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

    private void openNoteDialog(TextView textView, int noteId, HabbitCalendarModel.HabitStat habitStat, ImageView imgEdit, boolean future) {
        final Dialog dialog = new Dialog(context, R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_note_habbit);
        EditText edtDesc = dialog.findViewById(R.id.edtDesc);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        RelativeLayout rlSave = dialog.findViewById(R.id.rlSave);
        edtDesc.setText(textView.getText().toString());
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtDesc.getText().toString().equals("")) {
                    if (!future) {
                        UpdateTaskNote(noteId, textView, edtDesc.getText().toString(), dialog, imgEdit, habitStat);
                    }
                } else
                    Util.showDialog(context, "Alert", "Please enter some text", false);
            }
        });
        dialog.show();

    }

}