package com.ashysystem.mbhq.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;

import com.ashysystem.mbhq.fragment.habit_hacker.HabbitDetailsCalendarFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerEditFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerListFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.MbhqTodayTwoFragment;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.ashysystem.mbhq.view.drag_drop.ItemMoveCallback;
import com.ashysystem.mbhq.view.drag_drop.StartDragListener;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitHackerListAdapter extends RecyclerView.Adapter<HabitHackerListAdapter.MyViewHolder> implements ItemMoveCallback.ItemTouchHelperContract {

    Context context;
    List<HabitSwap> habitSwaps;
    private final StartDragListener mStartDragListener;
    boolean showManual;
    HabitHackerListFragment hackerListFragment;

    public HabitHackerListAdapter(Context context, List<HabitSwap> habitSwaps, StartDragListener mStartDragListener, boolean showManual, HabitHackerListFragment hackerListFragment) {
        this.context = context;
        this.habitSwaps = habitSwaps;
        this.mStartDragListener = mStartDragListener;
        this.showManual = showManual;
        this.hackerListFragment = hackerListFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_habithacker_list, parent, false);
        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        Log.e("AllList","ListHabitAll"+habitSwaps);

        if (habitSwaps.get(position).getBreakShowing()) {
            holder.llBreakTotal.setVisibility(View.VISIBLE);
        } else {
            holder.llBreakTotal.setVisibility(View.GONE);
        }

        holder.txtHabitName1.setTextColor(Color.parseColor("#000000"));
        holder.txtHabitName2.setTextColor(Color.parseColor("#000000"));

        holder.txtHabitName1.setText(habitSwaps.get(position).getHabitName());
        holder.txtHabitName2.setText(habitSwaps.get(position).getSwapAction().getName());

        Log.e("ListPUSHH", "ListAll" + habitSwaps.get(position).getNewAction().getPushNotification()
                + "\n" + habitSwaps.get(position).getHabitName()); ///////

        if (habitSwaps.get(position).getNewAction().getPushNotification()) {
            holder.rlNotification.setVisibility(View.VISIBLE);
        } else {
            holder.rlNotification.setVisibility(View.GONE);
        }


        holder.rlNotification.setOnClickListener(view -> {
            habitSwaps.get(position).getNewAction().setPushNotification(false);
            hackerListFragment.saveNewBreakHabit(habitSwaps.get(position));
        });

        try {
//            holder.txtHabitPercentage.setText(habitSwaps.get(position).getNewAction().getOverallPerformance().intValue() + " %");
            holder.txtHabitPercentage.setText((int) Math.round(habitSwaps.get(position).getNewAction().getOverallPerformance()) + " %");

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
//            (int) Math.round(doubleValue)
//            holder.txtBreakPercentage.setText(habitSwaps.get(position).getSwapAction().getOverallPerformance().intValue() + " %");
            holder.txtBreakPercentage.setText((int) Math.round(habitSwaps.get(position).getSwapAction().getOverallPerformance()) + " %");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (habitSwaps.get(position).getNewAction().getCurrentDayTask() != null && habitSwaps.get(position).getNewAction().getCurrentDayTask2() != null) {
            if (showManual) {
                holder.imgHabit1.setEnabled(false);
                holder.imgHabit1.setAlpha(0.5f);
            } else {
                holder.imgHabit1.setEnabled(true);
                holder.imgHabit1.setAlpha(1.0f);
            }
            if (habitSwaps.get(position).getNewAction().getCurrentDayTask().getIsDone() && habitSwaps.get(position).getNewAction().getCurrentDayTask2().getIsDone()) {
                holder.imgHabit1.setImageResource(R.drawable.mbhq_green_check);
            } else if (habitSwaps.get(position).getNewAction().getCurrentDayTask().getIsDone() || habitSwaps.get(position).getNewAction().getCurrentDayTask2().getIsDone()) {
                holder.imgHabit1.setImageResource(R.drawable.mbhq_half_green);
            } else {
                holder.imgHabit1.setImageResource(R.drawable.mbhq_circle_green);
            }
        } else if (habitSwaps.get(position).getNewAction().getCurrentDayTask() != null) {
            if (showManual) {
                holder.imgHabit1.setEnabled(false);
                holder.imgHabit1.setAlpha(0.5f);
            } else {
                holder.imgHabit1.setEnabled(true);
                holder.imgHabit1.setAlpha(1.0f);
            }
            if (habitSwaps.get(position).getNewAction().getCurrentDayTask().getIsDone()) {
                holder.imgHabit1.setImageResource(R.drawable.mbhq_green_check);
            } else {
                holder.imgHabit1.setImageResource(R.drawable.mbhq_circle_green);
            }
        } else if (habitSwaps.get(position).getNewAction().getCurrentDayTask2() != null) {
            if (showManual) {
                holder.imgHabit1.setEnabled(false);
                holder.imgHabit1.setAlpha(0.5f);
            } else {
                holder.imgHabit1.setEnabled(true);
                holder.imgHabit1.setAlpha(1.0f);
            }
            if (habitSwaps.get(position).getNewAction().getCurrentDayTask2().getIsDone()) {
                holder.imgHabit1.setImageResource(R.drawable.mbhq_green_check);
            } else {
                holder.imgHabit1.setImageResource(R.drawable.mbhq_circle_green);
            }
        } else {
            holder.imgHabit1.setImageResource(R.drawable.mbhq_circle_green);
            holder.imgHabit1.setEnabled(false);
            holder.imgHabit1.setAlpha(0.5f);
        }

        if (habitSwaps.get(position).getSwapAction().getCurrentDayTask() != null && habitSwaps.get(position).getSwapAction().getCurrentDayTask2() != null) {
            if (showManual) {
                holder.imgHabit2.setEnabled(false);
                holder.imgHabit2.setAlpha(0.5f);
            } else {
                holder.imgHabit2.setEnabled(true);
                holder.imgHabit2.setAlpha(1.0f);
            }
            if (habitSwaps.get(position).getSwapAction().getCurrentDayTask().getIsDone() && habitSwaps.get(position).getSwapAction().getCurrentDayTask2().getIsDone()) {
                holder.imgHabit2.setImageResource(R.drawable.ic_close_gray);
            } else if (habitSwaps.get(position).getSwapAction().getCurrentDayTask().getIsDone() || habitSwaps.get(position).getSwapAction().getCurrentDayTask2().getIsDone()) {
                holder.imgHabit2.setImageResource(R.drawable.mbhq_half_red);
            } else {
                holder.imgHabit2.setImageResource(R.drawable.ic_grey_circle);
            }
        } else if (habitSwaps.get(position).getSwapAction().getCurrentDayTask() != null) {
            if (showManual) {
                holder.imgHabit2.setEnabled(false);
                holder.imgHabit2.setAlpha(0.5f);
            } else {
                holder.imgHabit2.setEnabled(true);
                holder.imgHabit2.setAlpha(1.0f);
            }
            if (habitSwaps.get(position).getSwapAction().getCurrentDayTask().getIsDone()) {
                holder.imgHabit2.setImageResource(R.drawable.ic_close_gray);
            } else {
                holder.imgHabit2.setImageResource(R.drawable.ic_grey_circle);
            }
        } else if (habitSwaps.get(position).getSwapAction().getCurrentDayTask2() != null) {
            if (showManual) {
                holder.imgHabit2.setEnabled(false);
                holder.imgHabit2.setAlpha(0.5f);
            } else {
                holder.imgHabit2.setEnabled(true);
                holder.imgHabit2.setAlpha(1.0f);
            }
            if (habitSwaps.get(position).getSwapAction().getCurrentDayTask2().getIsDone()) {
                holder.imgHabit2.setImageResource(R.drawable.ic_close_gray);
            } else {
                holder.imgHabit2.setImageResource(R.drawable.ic_grey_circle);
            }
        } else {
            holder.imgHabit2.setImageResource(R.drawable.ic_grey_circle);
            holder.imgHabit2.setEnabled(false);
            holder.imgHabit2.setAlpha(0.5f);
        }


        holder.llTop.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openDialogAfterLongPress(habitSwaps.get(position));
                return true;
            }
        });

        holder.llTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", habitSwaps.get(position).getHabitId());
                bundle.putString("habbit_name", habitSwaps.get(position).getHabitName());
                bundle.putString("break_name", habitSwaps.get(position).getSwapAction().getName());
                bundle.putBoolean("push_notification",habitSwaps.get(position).getNewAction().getPushNotification()); ////// added
                if (habitSwaps.get(position).getNewAction().getTaskFrequencyTypeId() != null) {
                    bundle.putInt("TASK_FREQUENCY", habitSwaps.get(position).getNewAction().getTaskFrequencyTypeId());
                }
                Log.e("print habbit id--", habitSwaps.get(position).getHabitId() + "?");
                ((MainActivity) context).clearCacheForParticularFragment(new HabbitDetailsCalendarFragment());
               HabbitDetailsCalendarFragment habbitDetailsCalendarFragment = new HabbitDetailsCalendarFragment();
                habbitDetailsCalendarFragment.setArguments(bundle);
                ((MainActivity) context).loadFragment(habbitDetailsCalendarFragment, "HabbitCalendara", bundle);
            }
        });
/*
        holder.imgManual.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() ==
                        MotionEvent.ACTION_DOWN) {
                    mStartDragListener.requestDrag(holder);
                }
                return false;
            }
        });
*/

        holder.imgHabit1.setOnClickListener(view -> {
            if (habitSwaps.get(position).getNewAction().getCurrentDayTask() != null && habitSwaps.get(position).getNewAction().getCurrentDayTask2() != null) {
                if (habitSwaps.get(position).getNewAction().getCurrentDayTask().getIsDone() && habitSwaps.get(position).getNewAction().getCurrentDayTask2().getIsDone()) {
                    updateTask(habitSwaps.get(position).getNewAction().getCurrentDayTask().getTaskMasterId(), false, habitSwaps.get(position).getHabitId());
                } else if (habitSwaps.get(position).getNewAction().getCurrentDayTask().getIsDone() || habitSwaps.get(position).getNewAction().getCurrentDayTask2().getIsDone()) {
                    if (habitSwaps.get(position).getNewAction().getCurrentDayTask().getIsDone()) {
                        updateTask(habitSwaps.get(position).getNewAction().getCurrentDayTask2().getTaskMasterId(), true, habitSwaps.get(position).getHabitId());
                    } else {
                        updateTask(habitSwaps.get(position).getNewAction().getCurrentDayTask().getTaskMasterId(), true, habitSwaps.get(position).getHabitId());
                    }
                } else {
                    updateTask(habitSwaps.get(position).getNewAction().getCurrentDayTask().getTaskMasterId(), true, habitSwaps.get(position).getHabitId());
                }
            } else if (habitSwaps.get(position).getNewAction().getCurrentDayTask() != null) {
                if (habitSwaps.get(position).getNewAction().getCurrentDayTask().getIsDone()) {
                    updateTask(habitSwaps.get(position).getNewAction().getCurrentDayTask().getTaskMasterId(), false, habitSwaps.get(position).getHabitId());
                } else {
                    updateTask(habitSwaps.get(position).getNewAction().getCurrentDayTask().getTaskMasterId(), true, habitSwaps.get(position).getHabitId());
                }
            } else if (habitSwaps.get(position).getNewAction().getCurrentDayTask2() != null) {
                if (habitSwaps.get(position).getNewAction().getCurrentDayTask2().getIsDone()) {
                    updateTask(habitSwaps.get(position).getNewAction().getCurrentDayTask2().getTaskMasterId(), false, habitSwaps.get(position).getHabitId());
                } else {
                    updateTask(habitSwaps.get(position).getNewAction().getCurrentDayTask2().getTaskMasterId(), true, habitSwaps.get(position).getHabitId());
                }
            } else {

            }
        });

        holder.imgHabit2.setOnClickListener(view -> {
            if (habitSwaps.get(position).getSwapAction().getCurrentDayTask() != null && habitSwaps.get(position).getSwapAction().getCurrentDayTask2() != null) {
                if (habitSwaps.get(position).getSwapAction().getCurrentDayTask().getIsDone() && habitSwaps.get(position).getSwapAction().getCurrentDayTask2().getIsDone()) {
                    updateTask(habitSwaps.get(position).getSwapAction().getCurrentDayTask().getTaskMasterId(), false, habitSwaps.get(position).getHabitId());
                } else if (habitSwaps.get(position).getSwapAction().getCurrentDayTask().getIsDone() || habitSwaps.get(position).getSwapAction().getCurrentDayTask2().getIsDone()) {
                    if (habitSwaps.get(position).getSwapAction().getCurrentDayTask().getIsDone()) {
                        updateTask(habitSwaps.get(position).getSwapAction().getCurrentDayTask2().getTaskMasterId(), true, habitSwaps.get(position).getHabitId());
                    } else {
                        updateTask(habitSwaps.get(position).getSwapAction().getCurrentDayTask().getTaskMasterId(), true, habitSwaps.get(position).getHabitId());
                    }
                } else {
                    updateTask(habitSwaps.get(position).getSwapAction().getCurrentDayTask().getTaskMasterId(), true, habitSwaps.get(position).getHabitId());
                }
            } else if (habitSwaps.get(position).getSwapAction().getCurrentDayTask() != null) {
                if (habitSwaps.get(position).getSwapAction().getCurrentDayTask().getIsDone()) {
                    updateTask(habitSwaps.get(position).getSwapAction().getCurrentDayTask().getTaskMasterId(), false, habitSwaps.get(position).getHabitId());
                } else {
                    updateTask(habitSwaps.get(position).getSwapAction().getCurrentDayTask().getTaskMasterId(), true, habitSwaps.get(position).getHabitId());
                }
            } else if (habitSwaps.get(position).getSwapAction().getCurrentDayTask2() != null) {
                if (habitSwaps.get(position).getSwapAction().getCurrentDayTask2().getIsDone()) {
                    updateTask(habitSwaps.get(position).getSwapAction().getCurrentDayTask2().getTaskMasterId(), false, habitSwaps.get(position).getHabitId());
                } else {
                    updateTask(habitSwaps.get(position).getSwapAction().getCurrentDayTask2().getTaskMasterId(), true, habitSwaps.get(position).getHabitId());
                }
            } else {

            }
        });

        if (showManual) {
            holder.txtHabitPercentage.setVisibility(View.GONE);
            holder.txtBreakPercentage.setVisibility(View.GONE);
            holder.imgManual.setVisibility(View.VISIBLE);
        } else {
            holder.txtHabitPercentage.setVisibility(View.VISIBLE);
            holder.txtBreakPercentage.setVisibility(View.VISIBLE);
            holder.imgManual.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return habitSwaps.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtHabitName1, txtHabitName2, txtHabitPercentage, txtBreakPercentage;
        private ImageView imgHabit1, imgHabit2, imgNotification, imgManual;
        RelativeLayout rlNotification, rowView;
        LinearLayout llTop, llBreakTotal;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtHabitName1 = itemView.findViewById(R.id.txtHabitName1);
            this.txtHabitName2 = itemView.findViewById(R.id.txtHabitName2);
            this.imgHabit1 = itemView.findViewById(R.id.imgHabit1);
            this.imgHabit2 = itemView.findViewById(R.id.imgHabit2);
            this.imgNotification = itemView.findViewById(R.id.imgNotification);
            this.imgManual = itemView.findViewById(R.id.imgManual);
            this.rlNotification = itemView.findViewById(R.id.rlNotification);
            this.txtHabitPercentage = itemView.findViewById(R.id.txtHabitPercentage);
            this.txtBreakPercentage = itemView.findViewById(R.id.txtBreakPercentage);
            this.rowView = itemView.findViewById(R.id.rowView);
            this.llTop = itemView.findViewById(R.id.llTop);
            this.llBreakTotal = itemView.findViewById(R.id.llBreakTotal);

        }
    }


    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(habitSwaps, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(habitSwaps, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        hackerListFragment.setLstManualDrag(habitSwaps);
    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);

    }

  /*  @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);

    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);

    }*/

    public void updateTask(Integer id, Boolean status, Integer habitId) {
        if (Connection.checkConnection(context)) {

            ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(context);

            JsonObject object = new JsonObject();
            object.addProperty("TaskId", id);
            object.addProperty("IsDone", status);
            object.addProperty("Key", Util.KEY);
            object.addProperty("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Log.e("RequestSent", object.toString());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(context);
            Call<JsonObject> serverCall = finisherService.updateVitaminTask(object);

            serverCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            Util.isReloadTodayMainPage = true;
                            ((MainActivity) context).clearCacheForParticularFragment(new HabitHackerListFragment());
                            ((MainActivity) context).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                            ((MainActivity) context).clearCacheForParticularFragment(new HabbitDetailsCalendarFragment());
                            ((MainActivity) context).callTaskStatusForDateAPI("");

                            try {
                                Completable.fromAction(() -> {
                                    hackerListFragment.mViewModel.deleteHabitCalendarById(habitId);
                                }).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                            Completable.fromAction(() -> {
                                                hackerListFragment.mViewModelEdit.deleteByHabitId(habitId);
                                            })
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(() -> {
                                                        Log.e("DATABASE", "DELETE_SUCCESSFULL");
                                                        ((MainActivity) context).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                                                    }, throwable -> {
                                                        Log.e("DATABASE", "DELETE_UN_SUCCESSFULL");
                                                        ((MainActivity) context).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                                                    });
                                        }, throwable -> {
                                            ((MainActivity) context).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
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

    private void openDialogAfterLongPress(HabitSwap habitSwap) {
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

                bundle.putString("FROM_HABIT_LIST", "TRUE");

                Log.e("print habbit id--", habitSwap.getHabitId() + "?");
                Util.backtotodayhabit="";
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
                    hackerListFragment.deleteHabitSwapApiCall(habitSwap.getHabitId());
                }

                @Override
                public void onCancel(String title) {

                }
            });

        });

        dialog.show();
    }


}
