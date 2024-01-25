package com.ashysystem.mbhq.util;

/**
 * Created by android-arindam on 23/2/17.
 */

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.fragment.course.CourseArticleDetailsNewFragment;
import com.ashysystem.mbhq.model.CourseDetailModel;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by anandbose on 09/06/15.
 */
public class ExpandableCourseListNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<Item> data;
    private Context context;
    int enrollCourseId;
    Bundle fromCourseBundle;
    boolean isfirstTime;
    private Bundle bundle;
    String courseType = "";
    String PLAY_EPISODE_ONE = "";
    String weeklyTheme="";
    String imageUrl = "";

    public ExpandableCourseListNew(List<Item> data, Context context, int enrollCourseId, Bundle fromCourseBundle, boolean isfirstTime, Bundle arguments, String PLAY_EPISODE_ONE, String imageUrl) {
        this.data = data;
        this.context = context;
        this.enrollCourseId = enrollCourseId;
        this.fromCourseBundle = fromCourseBundle;
        this.isfirstTime = isfirstTime;
        this.bundle = arguments;
        this.PLAY_EPISODE_ONE = PLAY_EPISODE_ONE;
        this.imageUrl = imageUrl;
        if (arguments.containsKey("type"))
            this.courseType = arguments.getString("type");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.adapter_expandel_article_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:

                LayoutInflater inflaterChild = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterChild.inflate(R.layout.adapter_expanble_article_child, parent, false);
                ListItemViewHolder child = new ListItemViewHolder(view);
                return child;
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                if (item != null) {
                    /*if (item.courseType.equals("Week Challenge")) {
                        if (position == 0)
                            itemController.txtHeader.setText("Getting Started: Prep Room");
                        else {
                            itemController.txtHeader.setText("Week " + item.text);
                        }
                    } else {
                        itemController.txtHeader.setText("Level " + item.text);
                    }*/
                   // Log.e("show me---",item.weeklyTheme+"???"+item.weekNumber);
                    if(item.weekNumber>0)
                    {
                        itemController.txtHeader.setText(item.runBy +" "+ item.weekNumber+" - "+item.weeklyTheme);
                    }
                    else
                    {
                        itemController.txtHeader.setText("Getting Started: Prep Room");
                    }
                    if (item.invisibleChildren == null) {
                       // itemController.imgOpenClose.setBackgroundResource(R.drawable.coll);
                    } else {
                      //  itemController.imgOpenClose.setBackgroundResource(R.drawable.coll_exp);
                    }
                } else {

                }

                break;
            case CHILD:
                final ListItemViewHolder childController = (ListItemViewHolder) holder;
                childController.refferalItem = item;
                if (item != null) {

                    if (item.isRead) {
                        //childController.header_title.setTypeface(null, Typeface.NORMAL);
                        childController.imgAction.setImageResource(R.drawable.mbhq_tick_vitamin);
                        childController.txtAction.setText("Done");
                    } else {


                        //childController.header_title.setTypeface(null, Typeface.BOLD);
                        if (item.relatedTask != null) {
                            childController.imgAction.setImageResource(0);
                            childController.imgAction.setImageResource(R.drawable.ic_play_program_grey);
                            childController.txtAction.setText("Task");
                        }

                    }
                    childController.header_title.setText(item.text);
                    childController.txtNumber.setText(item.index+"");
                    if(!item.SeminerTime.equals(""))
                    {
                        childController.txtSeminer.setText(item.SeminerTime);
                    }
                    else
                        childController.llSeminer.setVisibility(View.GONE);
                    if(item.isAvailable)
                    {
                        childController.llAction.setEnabled(true);
                        childController.rlText.setEnabled(true);
                        childController.llRoot.setEnabled(true);
                        childController.llUp.setVisibility(View.VISIBLE);
                        childController.imgAction.setVisibility(View.VISIBLE);
                       // childController.llAction.setAlpha(1f);
                      //  childController.header_title.setTextColor(Color.RED);
                        /*((ListItemViewHolder) holder).header_title.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
// Clear any previous
                        ((ListItemViewHolder) holder).header_title.getPaint().setMaskFilter(null);*/
                    }
                    else {
                        SimpleDateFormat reqFormat=new SimpleDateFormat("dd MMM yyyy");
                        SimpleDateFormat getFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        childController.llAction.setEnabled(false);
                        childController.rlText.setEnabled(false);
                        childController.llRoot.setEnabled(false);
                        //childController.llAction.setAlpha(.5f);
                        //childController.header_title.setTextColor(Color.GREEN);
                        String time=childController.txtSeminer.getText().toString();
                        String releaseDate=item.releaseDate;
                        Date dateObj=null;
                        try {
                            dateObj=getFormat.parse(releaseDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        releaseDate=reqFormat.format(dateObj);
                       // time=time+" Releases on "+item.releaseDate;
                        time=time+" Releases on "+releaseDate;
                        childController.txtSeminer.setText(time);
                        childController.llUp.setVisibility(View.GONE);
                        //childController.imgAction.setVisibility(View.INVISIBLE);
                        childController.imgAction.setVisibility(View.VISIBLE);
                       /* if (Build.VERSION.SDK_INT >= 11) {
                            ((ListItemViewHolder) holder).header_title.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        }
                        float radius = ((ListItemViewHolder) holder).header_title.getTextSize() / 3;
                        BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL);
                        ((ListItemViewHolder) holder).header_title.getPaint().setMaskFilter(filter);*/



                    }

                    childController.llAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            fromCourseBundle.putInt("COURSEID", enrollCourseId);
                            fromCourseBundle.putInt("ARTICLEID", item.articleId);
                            fromCourseBundle.putString("AUTHOR", item.author);
                            fromCourseBundle.putString("type", courseType);
                            fromCourseBundle.putString("IMAGE_URL", imageUrl);
                            Log.i("click_course_media","11");
                            Util.bundleProgramDetailsForBackground=null;
                            Util.bundleProgramDetailsForBackground=fromCourseBundle;
                            ((MainActivity) context).clearCacheForParticularFragment(new CourseArticleDetailsNewFragment());
                            ((MainActivity) context).loadFragment(new CourseArticleDetailsNewFragment(), "CourseArticleDetailsNew", fromCourseBundle);


                        }
                    });
                    childController.llRoot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                            fromCourseBundle.putInt("COURSEID", enrollCourseId);
                            fromCourseBundle.putInt("ARTICLEID", item.articleId);
                            fromCourseBundle.putString("type", courseType);
                            fromCourseBundle.putString("AUTHOR", item.author);
                            fromCourseBundle.putString("IMAGE_URL", imageUrl);
                            if (item.relatedTask != null) {
                                if (item.relatedTask.getTaskId() != null)
                                    fromCourseBundle.putInt("TASKID", item.relatedTask.getTaskId());
                            }
                            Log.i("click_course_media","10");
                            Util.bundleProgramDetailsForBackground=null;
                            Util.bundleProgramDetailsForBackground=fromCourseBundle;
                            ((MainActivity) context).clearCacheForParticularFragment(new CourseArticleDetailsNewFragment());
                            ((MainActivity) context).loadFragment(new CourseArticleDetailsNewFragment(), "CourseArticleDetailsNew", fromCourseBundle);
                        }
                    });

                    if (!PLAY_EPISODE_ONE.equals("") && item.articleId == 278) {
                        childController.rlText.performClick();
                    } else {
                        //Log.e("ELSEELSE", ">>>>>>>>>");
                    }

                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        //Log.e("print data",data.size()+"??");
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public ImageView imgOpenClose;
        public Item refferalItem;
        public LinearLayout llRoot;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            txtHeader = (TextView) itemView.findViewById(R.id.txtHeader);
            imgOpenClose = (ImageView) itemView.findViewById(R.id.imgOpenClose);
            llRoot = (LinearLayout) itemView.findViewById(R.id.llRoot);
        }
    }

    private static class ListItemViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title, txtAction, txtNumber;
        public ImageView imgAction;
        public Item refferalItem;
        public LinearLayout llRoot;
        public RelativeLayout rlText,llAction;
        public LinearLayout llUp;
        public LinearLayout llSeminer;
        public  TextView txtSeminer;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.txtCourseNmae);
            txtAction = (TextView) itemView.findViewById(R.id.txtAction);
            txtNumber = (TextView) itemView.findViewById(R.id.txtNumber);
            imgAction = (ImageView) itemView.findViewById(R.id.imgAction);
            llRoot = (LinearLayout) itemView.findViewById(R.id.llRoot);
            llAction = itemView.findViewById(R.id.llAction);
            rlText = (RelativeLayout) itemView.findViewById(R.id.rlText);
            llSeminer=(LinearLayout)itemView.findViewById(R.id.llSeminer);
            llUp=(LinearLayout)itemView.findViewById(R.id.llUp);
           txtSeminer=(TextView)itemView.findViewById(R.id.txtSeminer);
        }
    }

    public static class Item {
        public int type;
        public String text,author;
        public List<Item> invisibleChildren;
        public boolean isRead = false;
        public int articleId = 0;
        String courseType = "";
        public int weekNumber=0;
        CourseDetailModel.RelatedTask relatedTask;
        public String runBy="";
        public String SeminerTime="";
        int index=1;
        String releaseDate="";
        boolean isAvailable;
        String weeklyTheme="";


        public Item(int child, String articleTitle, String courseType, CourseDetailModel.ArticleFeedDetail articleFeedDetail, int weekNumber, String runBy, int index, boolean isAvailable, String rDate, String theme) {

            this.type = child;
            this.text = articleTitle;
            this.courseType = courseType;
            this.weekNumber=weekNumber;
            this.runBy=runBy;
            this.index=index;
            if(child==0)
              weeklyTheme=theme;
            if (articleFeedDetail != null) {
                this.isRead = articleFeedDetail.getIsRead();
                //Log.e("ARTICLEID--->", articleFeedDetail.getArticleId() + "");
                this.articleId = articleFeedDetail.getArticleId();
                relatedTask = articleFeedDetail.getRelatedTask();
                this.author=articleFeedDetail.getAuthorName();
                this.SeminerTime=articleFeedDetail.getSeminarTime();
                this.isAvailable=isAvailable;
                this.releaseDate=rDate;

                Log.e("print weekly theme--",weeklyTheme+"123"+"??"+SeminerTime+"???");
            }

        }

        /*public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }*/
    }
/*commented by sahenita unused in squad*/
/*
    public void getUnreadData(final int arrticleId, final String courseType) {
        if (Connection.checkConnection(context)) {

            final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("UserId", new SharedPreference(context).read("UserID", ""));
            hashMap.put("CourseId", enrollCourseId);
            hashMap.put("ArticleId", arrticleId);
            hashMap.put("Key", Util.KEY);
            hashMap.put("UserSessionID", new SharedPreference(context).read("UserSessionID", ""));
            FinisherServiceImpl finisherService = new FinisherServiceImpl(context);
            Call<ReadUnreadResponse> readUnreadResponseCall = finisherService.unreadArticle(hashMap);
            readUnreadResponseCall.enqueue(new Callback<ReadUnreadResponse>() {
                @Override
                public void onResponse(Call<ReadUnreadResponse> call, Response<ReadUnreadResponse> response) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().getSuccessFlag()) {
                            // ((MainActivity)context).loadFragment(new CourseArticleDetailsFragment(),"CourseArticleDetails",courseDetailBundle);


                           */
/* if(courseType.equals("Week Challenge"))
                            bundle.putString("type","Week Challenge");*//*



                            ((MainActivity) context).loadFragment(new CourseDetailsFragment(), "CourseDetails", bundle);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReadUnreadResponse> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(context, Util.networkMsg);
        }
    }
*/

}
