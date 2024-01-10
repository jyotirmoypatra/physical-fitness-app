package com.ashysystem.mbhq.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.fragment.achievement.MyAchievementsFragment;
import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.SharedPreference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android-krishnendu on 3/16/17.
 */

public class MyAchievementsListAdapter extends RecyclerView.Adapter<MyAchievementsListAdapter.ViewHolder> {

    Context context;
    ViewHolder viewHolder=null;
    List<MyAchievementsListInnerModel> lstMyAchievementListModelInners = new ArrayList<MyAchievementsListInnerModel>();
    List<MyAchievementsListInnerModel> fullData = new ArrayList<MyAchievementsListInnerModel>();

    FinisherServiceImpl finisherService;
    SharedPreference sharedPreference;
    ProgressDialog progressDialog;
    MyAchievementsFragment myAchievementsFragment;

    String path = Environment.getExternalStorageDirectory()
            + "/Android/data/"
            + "com.ashysystem.mbhq"
            + "/Files/";

    private int __detail_previous_size = 0;

    public MyAchievementsListAdapter(Context context, MyAchievementsFragment myAchievementsFragment) {
        this.context = context;
        //this.lstMyAchievementListModelInners = details;
        this.myAchievementsFragment = myAchievementsFragment;
        finisherService = new FinisherServiceImpl(context);
        sharedPreference = new SharedPreference(context);
        setHasStableIds(true);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void loaddata(ArrayList<MyAchievementsListInnerModel> details) {
       /* List<MyAchievementsListInnerModel> tmp= new ArrayList<>();
        if ( details.size() < 50 ) {
            tmp = details;
        } else {
            tmp = details.subList(this.__detail_previous_size, details.size());
        }
        Log.e(TAG, "tmp: " + tmp.size());
        this.lstMyAchievementListModelInners.addAll(tmp);
        Log.e(TAG, "lstMyAchievementListModelInners: " + lstMyAchievementListModelInners.size());

        this.__detail_previous_size = details.size();*/
        this.lstMyAchievementListModelInners.addAll(details);
        this.fullData.addAll(lstMyAchievementListModelInners);

        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void search(String searchString) {
       // lstMyAchievementListModelInners.clear();
        //dynamicSize = 0;
        if (searchString.equals("")) {
            Log.i("textchange","8");
            sharedPreference.write("achivementfilter", "", "");
            MyAchievementsFragment.imgFilterAchieve.setImageResource(R.drawable.mbhq_filter);
            lstMyAchievementListModelInners.clear();
            lstMyAchievementListModelInners.addAll(fullData);
        } else {
            Log.i("textchange","9");
            lstMyAchievementListModelInners.clear();
            for (int x = 0; x < fullData.size(); x++) {

                if (fullData.get(x).getPrompt().toLowerCase().contains(searchString.toLowerCase())) {
                    Log.i("textchange","10");

                    lstMyAchievementListModelInners.add(fullData.get(x));
                    sharedPreference.write("achivementfilter", "", "yes");
                    MyAchievementsFragment.imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);
                }
            }
        }
      //  dynamicSize = lstData.size();
        Log.i("textchange","11");
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       /* if(viewHolder==null){
            viewHolder = new ViewHolder(itemLayoutView);
        }*/

        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_gratitudemylist, parent, false);
      //  return new MyAdapter.MyViewHolder(view);

       // View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gratitudemylist, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        try {

            holder.htmlTextView.setTextColor(Color.parseColor("#000000"));

            if (lstMyAchievementListModelInners.get(position).getAchievement() != null && !lstMyAchievementListModelInners.get(position).getAchievement().equals("") && lstMyAchievementListModelInners.get(position).getPrompt() != null) {
                holder.htmlTextView.setVisibility(View.VISIBLE);
                String sourceString = "";
                if(null==lstMyAchievementListModelInners.get(position).getPromptOfDay()){
                    sourceString = "<b>" + lstMyAchievementListModelInners.get(position).getPrompt() + "</b>" + " " + lstMyAchievementListModelInners.get(position).getAchievement();
                    holder.txtGratitudeDate.setText(context.getResources().getText(R.string.Today));

                }else{
                    holder.txtGratitudeDate.setText(lstMyAchievementListModelInners.get(position).getDateValue());
                    if(lstMyAchievementListModelInners.get(position).getPromptOfDay()){
                        sourceString = "<b>" + lstMyAchievementListModelInners.get(position).getPrompt() + "</b>" + " " + lstMyAchievementListModelInners.get(position).getAchievement();
                    }else{
                        sourceString = "<b>" + lstMyAchievementListModelInners.get(position).getPrompt() + "</b>" + " " + lstMyAchievementListModelInners.get(position).getAchievement();
                    }
                }


                holder.htmlTextView.setText(Html.fromHtml(sourceString));

            } else {
                holder.htmlTextView.setVisibility(View.GONE);
                holder.htmlTextView.setText("");
            }

           /* if(null!=lstMyAchievementListModelInners.get(position).getUserFolderId()){
                holder.id.setText(String.valueOf(lstMyAchievementListModelInners.get(position).getUserFolderId()));

            }*/


            if (lstMyAchievementListModelInners.get(position).getPicture() != null && !lstMyAchievementListModelInners.get(position).getPicture().isEmpty()) {
                holder.txtGratitudeDate.setTextColor(Color.parseColor("#474747"));
            } else {
                holder.txtGratitudeDate.setTextColor(Color.parseColor("#474747"));
            }

            if ((lstMyAchievementListModelInners.get(position).getPushNotification() != null && lstMyAchievementListModelInners.get(position).getPushNotification()) || (lstMyAchievementListModelInners.get(position).getEmail() != null && lstMyAchievementListModelInners.get(position).getEmail())) {
                holder.rlNotification.setVisibility(View.VISIBLE);
            } else {
                holder.rlNotification.setVisibility(View.GONE);
            }

            holder.imgNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean prevNotification = false;
                    if ((lstMyAchievementListModelInners.get(position).getPushNotification() != null && lstMyAchievementListModelInners.get(position).getPushNotification()) || (lstMyAchievementListModelInners.get(position).getEmail() != null && lstMyAchievementListModelInners.get(position).getEmail())) {
                        prevNotification = true;
                    } else {
                        prevNotification = false;
                    }
                    myAchievementsFragment.getSelectedAchievement(lstMyAchievementListModelInners.get(position).getId(), "TRUE", prevNotification);
                }
            });

            holder.rlNameAndDate.setOnClickListener(view -> {
                openDetails(position);
            });

            holder.journalImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDetails(position);
                }
            });
            holder.llClick.setOnClickListener(view -> {
                openDetails(position);
            });

            holder.llClick.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    openDialogAfterLongPress(lstMyAchievementListModelInners.get(position));
                    return true;
                }
            });


            holder.htmlTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    openDialogAfterLongPress(lstMyAchievementListModelInners.get(position));
                    return true;
                }
            });

            holder.journalImg.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    openDialogAfterLongPress(lstMyAchievementListModelInners.get(position));
                    return true;
                }
            });

            holder.htmlTextView.setOnClickListener(view -> {
                boolean prevNotification = false;
                if (lstMyAchievementListModelInners.get(position).getPushNotification() != null && lstMyAchievementListModelInners.get(position).getPushNotification() || lstMyAchievementListModelInners.get(position).getEmail() != null && lstMyAchievementListModelInners.get(position).getEmail()) {
                    prevNotification = true;
                } else {
                    prevNotification = false;
                }
                myAchievementsFragment.getSelectedAchievement(lstMyAchievementListModelInners.get(position).getId(), "", prevNotification);
            });


            ////////////////////////////////////////////////////////////////

            //added by jyotirmoy->j4
//           SharedPreferences prefPicture = context.getSharedPreferences("showpicture", MODE_PRIVATE);
//            Boolean picture = prefPicture.getBoolean("picshow", false);




            if (lstMyAchievementListModelInners.get(position).getHasImage()) {
              Log.e("block_b","0");
                if (lstMyAchievementListModelInners.get(position).getPictureDevicePath() != null && !lstMyAchievementListModelInners.get(position).getPictureDevicePath().equals("") && !lstMyAchievementListModelInners.get(position).getPicture().equals("")) {
                    Log.e("block_b","00");
                    holder.loadImageJournal.setVisibility(View.VISIBLE);

                   // new DownloadImageTask_(holder.journalImg, holder.imgCardView,holder.loadImageJournal).execute(lstMyAchievementListModelInners.get(position).getPictureDevicePath());
                  //  new DownloadImageTask(holder.journalImg, holder.imgCardView,holder.loadImageJournal).execute(lstMyAchievementListModelInners.get(position).getPicture());
/*
                    Glide.with(context)
                            .load(lstMyAchievementListModelInners.get(position).getPicture())
                            .placeholder(R.drawable.empty_image_old)
                            .error(R.drawable.notfound)
                            .fitCenter()
                            // .override(200, 200) // resize the image to 200x200 pixels
                            .thumbnail(0.5f)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    holder.imgCardView.setVisibility(View.GONE);
                                    holder.journalImg.setVisibility(View.GONE);


                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    holder.imgCardView.setVisibility(View.VISIBLE);
                                    holder.journalImg.setVisibility(View.VISIBLE);
                                    holder.loadImageJournal.setVisibility(View.GONE);

                                    return false;
                                }
                            })
                            .into( holder.journalImg);
*/


                } else {
                    Log.e("block_b","000");
if (!lstMyAchievementListModelInners.get(position).getPicture().equals("")) {
    Log.e("block_b","001");
    if(null==lstMyAchievementListModelInners.get(position).getPromptOfDay()){
        Log.e("block_b","002");
        Log.e("ADAPTER_GRATITUDEIMAGE5",lstMyAchievementListModelInners.get(position).getPicture());
        holder.imgCardView.setVisibility(View.VISIBLE);
        holder.journalImg.setVisibility(View.VISIBLE);
        holder.loadImageJournal.setVisibility(View.GONE);

        byte[] decodedString = Base64.decode(lstMyAchievementListModelInners.get(position).getPicture(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String base64String = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.e("ADAPTER_6666666",base64String);
        Log.d("MyApp", " " + lstMyAchievementListModelInners.get(position).getHasImage());

        holder.journalImg.setImageBitmap(bitmap);
    }else{
        Log.e("block_b","003");
        holder.loadImageJournal.setVisibility(View.VISIBLE);

        // new DownloadImageTask(holder.journalImg, holder.imgCardView,holder.loadImageJournal).execute(lstMyAchievementListModelInners.get(position).getPicture());
/*
        Glide.with(context)
                .load(lstMyAchievementListModelInners.get(position).getPicture())
                .placeholder(R.drawable.empty_image_old)
                .error(R.drawable.notfound)
                .fitCenter()
                // .override(200, 200) // resize the image to 200x200 pixels
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.imgCardView.setVisibility(View.GONE);
                        holder.journalImg.setVisibility(View.GONE);


                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.imgCardView.setVisibility(View.VISIBLE);
                        holder.journalImg.setVisibility(View.VISIBLE);
                        holder.loadImageJournal.setVisibility(View.GONE);

                        return false;
                    }
                })
                .into( holder.journalImg);
*/
    }

}else if(lstMyAchievementListModelInners.get(position).getPicture().equals("")){
    Log.e("block_b","00004");
    holder.imgCardView.setVisibility(View.GONE);

} else {
    Log.e("block_b","004");
    holder.loadImageJournal.setVisibility(View.VISIBLE);

                       // new DownloadImageTask_(holder.journalImg, holder.imgCardView,holder.loadImageJournal).execute(lstMyAchievementListModelInners.get(position).getPictureDevicePath());
                        String filePath = path + lstMyAchievementListModelInners.get(position).getPictureDevicePath();
                        File file = new File(filePath);
                        if (file.exists()) {

                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4; // This will reduce the image resolution by a factor of 4
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
                            Uri image= Uri.parse(path);
/*
                            Glide.with(context)
                                    .load(image)
                                    .placeholder(R.drawable.empty_image_old)
                                    .dontAnimate()
                                    .fitCenter()
                                    .thumbnail(0.5f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .listener(new RequestListener<Uri, GlideDrawable>() {
                                        @Override
                                        public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                                            holder.imgCardView.setVisibility(View.GONE);
                                            holder.journalImg.setVisibility(View.GONE);


                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                            holder.imgCardView.setVisibility(View.VISIBLE);
                                            holder.journalImg.setVisibility(View.VISIBLE);
                                            holder.loadImageJournal.setVisibility(View.GONE);

                                            return false;
                                        }
                                    })
                                    .into(holder.journalImg);
*/


                        }



                        //holder.imgCardView.setVisibility(View.GONE);
                    }
                }
            }
            else {
                Log.e("block_b","004");
                holder.imgCardView.setVisibility(View.GONE);
            }
            //ended by jyotirmoy
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void openDetails(int position) {
        boolean prevNotification = false;
        if (lstMyAchievementListModelInners.get(position).getPushNotification() != null && lstMyAchievementListModelInners.get(position).getPushNotification() || lstMyAchievementListModelInners.get(position).getEmail() != null && lstMyAchievementListModelInners.get(position).getEmail()) {
            prevNotification = true;
        } else {
            prevNotification = false;
        }
        myAchievementsFragment.getSelectedAchievement(lstMyAchievementListModelInners.get(position).getId(), "", prevNotification);
    }

    @Override
    public int getItemCount() {
        return lstMyAchievementListModelInners.size();
    }

    private void openDialogAfterLongPress(MyAchievementsListInnerModel listModelInner) {
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
            boolean prevNotification = false;
            if (listModelInner.getPushNotification() != null && listModelInner.getPushNotification() || listModelInner.getEmail() != null && listModelInner.getEmail()) {
                prevNotification = true;
            } else {
                prevNotification = false;
            }
            myAchievementsFragment.getSelectedAchievement(listModelInner.getId(), "", prevNotification);
            ;
        });

        rlDelete.setOnClickListener(view -> {
            dialog.dismiss();

            final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(context);
            alertDialogCustom.ShowDialog("Alert!", "Are you sure want to delete?", true);
            alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                @Override
                public void onDone(String title) {
                    myAchievementsFragment.deleteGratitude(listModelInner.getId());
                }

                @Override
                public void onCancel(String title) {

                }
            });

        });

        dialog.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtGratitudeDate,id;
        public ImageView imgNotification, journalImg;
        public TextView htmlTextView;
        public LinearLayout llClick;
        public RelativeLayout rlNotification, rlNotificationInner, llTotalContent, rlNameAndDate;
        public ProgressBar loadImageJournal;
        public CardView imgCardView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            id = itemLayoutView.findViewById(R.id.Id);
            txtGratitudeDate = itemLayoutView.findViewById(R.id.txtGratitudeDate);
            imgNotification = (ImageView) itemLayoutView.findViewById(R.id.imgNotification);
            htmlTextView = (TextView) itemLayoutView.findViewById(R.id.html_text);
            llTotalContent = itemLayoutView.findViewById(R.id.llTotalContent);
            llClick = itemLayoutView.findViewById(R.id.llClick);
            rlNotification = itemLayoutView.findViewById(R.id.rlNotification);
            rlNotificationInner = itemLayoutView.findViewById(R.id.rlNotificationInner);
            rlNameAndDate = itemLayoutView.findViewById(R.id.rlNameAndDate);
            journalImg = itemLayoutView.findViewById(R.id.journalImg);
            imgCardView = itemLayoutView.findViewById(R.id.imgCardView);
            loadImageJournal = itemLayoutView.findViewById(R.id.loadImageJournal);
        }
    }




///////////////////open ai example///////////////

    private static Bitmap compressBitmap(Bitmap bitmap) {
        // Compress the Bitmap and return the result:
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(stream.toByteArray()));
    }
    @SuppressLint("StaticFieldLeak")
    private class DownloadImageTask extends AsyncTask<String, Void, String> {
        ImageView bmImage;
        CardView cardView;
        ProgressBar progressBar;
        public DownloadImageTask(ImageView bmImage,CardView cardView,ProgressBar progressBar) {
            this.bmImage = bmImage;
            this.cardView = cardView;
            this.progressBar = progressBar;
        }

        protected String doInBackground(String... urls) {

            String url1 = urls[0];
            ByteArrayOutputStream outputStream=null;

            try {
             /*   URL url = new URL(url1);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                int bitmapSize = bitmap.getByteCount();
                Log.e("bitmap",String.valueOf(bitmapSize));
                 outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream);
                byte[] compressedImage = outputStream.toByteArray();
                int compressedSize = compressedImage.length;
                Log.e("compress_bitmap",String.valueOf(compressedSize));*/

            } catch (Exception e) {
              /*  Log.e("Error", e.getMessage());
                e.printStackTrace();*/
            }
            return url1;
        }

        protected void onPostExecute(String result) {


/*
                        Glide.with(context)
                                .load(result)
                                .placeholder(R.drawable.empty_image_old)
                                .error(R.drawable.notfound)
                                .fitCenter()
                               // .override(200, 200) // resize the image to 200x200 pixels
                                .thumbnail(0.5f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontAnimate()
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        cardView.setVisibility(View.GONE);
                                        bmImage.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        cardView.setVisibility(View.VISIBLE);
                                        bmImage.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);

                                        return false;
                                    }
                                })
                                .into(bmImage);
*/



        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageTask_ extends AsyncTask<String, Void, Uri> {
        ImageView bmImage;
        CardView cardView;
        ProgressBar progressBar;
        public DownloadImageTask_(ImageView bmImage,CardView cardView,ProgressBar progressBar) {
            this.bmImage = bmImage;
            this.cardView = cardView;
            this.progressBar = progressBar;
        }

        protected Uri doInBackground(String... urls) {

            String url = urls[0];
            // Bitmap image = null;
            Uri image = null;

            try {
                cardView.setVisibility(View.VISIBLE);

                String filePath = path + url;
                File file = new File(filePath);
                if (file.exists()) {

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4; // This will reduce the image resolution by a factor of 4
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
                    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
                    image= Uri.parse(path);


                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return image;
        }

        protected void onPostExecute(Uri result) {
/*
            Glide.with(context)
                    .load(result)
                    .placeholder(R.drawable.empty_image_old)
                    .dontAnimate()
                    .fitCenter()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<Uri, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                            cardView.setVisibility(View.GONE);
                            bmImage.setVisibility(View.GONE);


                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            cardView.setVisibility(View.VISIBLE);
                            bmImage.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                            return false;
                        }
                    })
                    .into(bmImage);
*/
        }
    }



}
