package com.ashysystem.mbhq.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
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

import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.fragment.bucket.BucketListFragment;
import com.ashysystem.mbhq.model.BucketListModelInner;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.view.drag_drop.ItemMoveCallbackBucket;
import com.ashysystem.mbhq.view.drag_drop.StartDragListener;
import com.bumptech.glide.Glide;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by android-krishnendu on 3/20/17.
 */

public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.ViewHolder> implements ItemMoveCallbackBucket.ItemTouchHelperContract{

    Context context;
    List<BucketListModelInner> lstGratitudeListModelInners;
    SharedPreference sharedPreference;
    ProgressDialog progressDialog;
    FinisherServiceImpl finisherService;
    SimpleDateFormat format;
    SimpleDateFormat changedFormat;
    BucketListFragment bucketListFragment;
    private final StartDragListener mStartDragListener;
    private boolean showManual;

    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/";

    public BucketListAdapter(Context context, List<BucketListModelInner> lstGratitudeListModelInners, StartDragListener mStartDragListener, boolean showManual, BucketListFragment bucketListFragment)
    {
        this.context=context;
        this.lstGratitudeListModelInners=lstGratitudeListModelInners;
        this.bucketListFragment=bucketListFragment;
        finisherService=new FinisherServiceImpl(context);
        sharedPreference=new SharedPreference(context);
        this.mStartDragListener = mStartDragListener;
        format = new SimpleDateFormat("yyyy-MM-dd");
        changedFormat = new SimpleDateFormat("MMM d yyyy");
        this.showManual=showManual;
        this.bucketListFragment.setLstManualDrag(lstGratitudeListModelInners);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_bucketlist, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //holder.txtGratitude.setText(lstGratitudeListModelInners.get(position).getName().toUpperCase());
        holder.txtGratitude.setText(lstGratitudeListModelInners.get(position).getName());
        holder.txtGratitude.setTextColor(Color.parseColor("#000000"));
        holder.txtDiaryDate.setTextColor(Color.parseColor("#000000"));
        if(lstGratitudeListModelInners.get(position).getPictureDevicePath()!=null && !lstGratitudeListModelInners.get(position).getPictureDevicePath().equals(""))
        {
           String imgPath=  /*path +*/ lstGratitudeListModelInners.get(position).getPictureDevicePath();
           Log.e("img path rcvd--",imgPath+"??");
            if (imgPath!=null&&!imgPath.equals(""))
           {
               File file = new File(imgPath);
               Uri imageUri = Uri.fromFile(file);
               if(file.exists())
               {
                   Glide.with(context)
                           .load(imageUri)
                           .placeholder(R.drawable.mbhq_bucket_list_placeholder)
                           .error(R.drawable.mbhq_bucket_list_placeholder)
                           .into(holder.imgGratitude);
               }else
               {
                   try {
                       Glide.with(context)
                               .load(lstGratitudeListModelInners.get(position).getPicture())
                               .error(R.drawable.mbhq_bucket_list_placeholder)
                               .into(holder.imgGratitude);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }


           }else {
                try {
                    Glide.with(context)
                            .load(lstGratitudeListModelInners.get(position).getPicture())
                            .error(R.drawable.mbhq_bucket_list_placeholder)
                            .into(holder.imgGratitude);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else
        {
            try {
                Glide.with(context)
                        .load(lstGratitudeListModelInners.get(position).getPicture())
                        .error(R.drawable.mbhq_bucket_list_placeholder)
                        .into(holder.imgGratitude);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            if(lstGratitudeListModelInners.get(position).getCompletionDate()!=null)
            {
                holder.txtDiaryDate.setText(changedFormat.format(format.parse(lstGratitudeListModelInners.get(position).getCompletionDate())).toUpperCase());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(lstGratitudeListModelInners.get(position).getPushNotification()!=null&&lstGratitudeListModelInners.get(position).getPushNotification())
        {
           holder.imgBell.setVisibility(View.VISIBLE);
        }else
        {
            holder.imgBell.setVisibility(View.INVISIBLE);
        }
        holder.imgBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bucketListFragment.getIndividualBucket(lstGratitudeListModelInners.get(position).getId(),true);
                /*if(lstGratitudeListModelInners.get(position).getPushNotification()!=null&&lstGratitudeListModelInners.get(position).getPushNotification())
                {
                    lstGratitudeListModelInners.get(position).setPushNotification(false);
                    HashMap<String, Object> requestHash = new HashMap<>();
                    requestHash.put("model", lstGratitudeListModelInners.get(position));
                    requestHash.put("Key", Util.KEY);
                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                    bucketListFragment.saveAddGratitudeDataForNotification(requestHash);
                }*/
            }
        });

        holder.rlTextPart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openDialogAfterLongPress(lstGratitudeListModelInners.get(position));
                return true;
            }
        });

        holder.rlTextPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bucketListFragment.getIndividualBucket(lstGratitudeListModelInners.get(position).getId(),false);

            }
        });

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

        if(showManual)
        {
            holder.imgBell.setVisibility(View.GONE);
            holder.imgManual.setVisibility(View.VISIBLE);
        }else {
            if(lstGratitudeListModelInners.get(position).getPushNotification()!=null&&lstGratitudeListModelInners.get(position).getPushNotification())
               holder.imgBell.setVisibility(View.VISIBLE);
            holder.imgManual.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {

        return lstGratitudeListModelInners.size();
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(lstGratitudeListModelInners, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(lstGratitudeListModelInners, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        bucketListFragment.setLstManualDrag(lstGratitudeListModelInners);

    }

    @Override
    public void onRowSelected(ViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
    }


    @Override
    public void onRowClear(ViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtGratitude,txtDiaryDate;
        public LinearLayout llRoot,rowView;
        public ImageView imgGratitude,imgBell,imgManual;
        public RelativeLayout rlTextPart;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtGratitude = (TextView) itemLayoutView.findViewById(R.id.txtGratitude);
            txtDiaryDate = (TextView) itemLayoutView.findViewById(R.id.txtDiaryDate);
            llRoot=(LinearLayout)itemLayoutView.findViewById(R.id.llRoot);
            imgGratitude=(ImageView)itemLayoutView.findViewById(R.id.imgGratitude);
            imgBell=(ImageView)itemLayoutView.findViewById(R.id.imgBell);
            this.imgManual =  itemLayoutView.findViewById(R.id.imgManual);
            this.rowView=itemLayoutView.findViewById(R.id.rowView);
            this.rlTextPart=itemLayoutView.findViewById(R.id.rlTextPart);
        }
    }

    private void openDialogAfterLongPress(BucketListModelInner listModelInner) {
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
            bucketListFragment.getIndividualBucket(listModelInner.getId(),false);
        });

        rlDelete.setOnClickListener(view -> {
            dialog.dismiss();

            final AlertDialogCustom alertDialogCustom=new AlertDialogCustom(context);
            alertDialogCustom.ShowDialog("Alert!","Are you sure want to delete?",true);
            alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                @Override
                public void onDone(String title) {
                    bucketListFragment.deleteGratitude(listModelInner.getId());
                }

                @Override
                public void onCancel(String title) {

                }
            });

        });

        dialog.show();
    }


}
