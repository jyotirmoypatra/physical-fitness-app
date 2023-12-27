package com.ashysystem.mbhq.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.net.Uri;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.SessionServiceImpl;

import com.ashysystem.mbhq.fragment.SessionOverviewFragment;
import com.ashysystem.mbhq.model.ExerciseRequestModel;
import com.ashysystem.mbhq.model.QuickEditCircuitResponseModel;
import com.ashysystem.mbhq.model.SessionOverViewModel;
import com.ashysystem.mbhq.model.ShowIndividualVideoModel;
import com.ashysystem.mbhq.model.SubTitle;
import com.ashysystem.mbhq.model.TargetExerciseModel;
import com.ashysystem.mbhq.model.Title;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import com.ashysystem.mbhq.util.Connection;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by anandbose on 09/06/15.
 */
public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public static Context context;
    List<SessionOverViewModel.Exercise> lstExercise;
    private RecyclerView rvCircuitList=null;
    private Item preItem=null;
    private boolean isAlreadyOPen=false;
    private ListHeaderViewHolder itemControllerPre=null;
    private List<Item> data;
    TextView txtSelectedText;
    ////////////
    ExpandableCircuitListAdapter.ListHeaderViewHolder preHolder=null;
    private float bitmapXPosition=0f,bitmapWidth=0f,bitmapYPosition=0f,bitmapHeight=0f;
    private RectF backgroundBkp=null;
    private Canvas canvasBkp=null;
    float width=0;
    private Paint paint = new Paint();
    private RecyclerView currentRecyclerview=null;
    private RecyclerView previousRecyclerview=null;
    private VideoView previousVideo=null;
    private VideoView currentVideo=null;
    SessionOverviewFragment parentFragment;
    LayoutInflater layoutInflater;
    //boolean[] arrShowInfo;
    SharedPreference sharedPreference;
    VideoView videoGlobalView;
    private int itemNo=0,headNo=0;
    List<ExerciseRequestModel.Exercise> lstExercisePicker=null;
    private String[] strCategory={"EQUIPMENT BASED ALTERNATIVES:","BODY WEIGHT ALTERNATIVES:","TARGET AREA: (Lower Body, Upper Body, Core)","STANDARD EXERCISES:","ALL EXERCISES"};
    List<SubTitle> _allExercise = new ArrayList<>();
    List<SubTitle> _allEquip = new ArrayList<>();
    List<SubTitle> _allAlt = new ArrayList<>();
    List<SubTitle> _allTarget = new ArrayList<>();
    List<SubTitle> _allTStandard = new ArrayList<>();
    List<SubTitle> _allTargetUpper = new ArrayList<>();
    List<SubTitle> _allTargetLower = new ArrayList<>();
    List<SubTitle> _allTargetCore = new ArrayList<>();
    private List<TargetExerciseModel.Obj> lstTargetExercise;
    //private List _allStandard;


    //////////


    public ExpandableListAdapter(List<Item> data, Context context, List<SessionOverViewModel.Exercise> lstExercise, SessionOverviewFragment sessionOverviewFragment) {
        this.data = data;
        this.context=context;
        this.lstExercise=lstExercise;
        this.parentFragment=sessionOverviewFragment;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sharedPreference=new SharedPreference(context);
      //  Log.e("print flow id--",parentFragment.getFlowId()+"???");

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type)
        {
            case HEADER:
                // Log.e("head","123");
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.adpter_main_circuit_list, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:

                LayoutInflater inflaterChild = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterChild.inflate(R.layout.layout_session_details, parent, false);
                ListItemViewHolder child = new ListItemViewHolder(view);
                return child;

        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        final Item item = data.get(position);

        switch (item.type)
        {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                // Toast.makeText(context,"HEADER_POSITION "+position,Toast.LENGTH_SHORT).show();
                itemController.refferalItem = item;
                itemController.txtExerciseName.setText(item.exerciseName);
                itemController.txtIndex.setText(item.index);
                if(!item.isCircuit)
                {
                    itemController.rlInformation.setVisibility(View.GONE);
                    itemController.txtReps.setText(item.exerciseRepGoal+" "+item.RepsUnitText);
                    if(item.isSuperSet >=1)
                    {

                        if(item.SuperSetPosition==-1){
                            itemController.imgBr.setImageResource(0);
                            itemController.imgBr.setImageResource(R.drawable.thired_first_half);
                            itemController.txtSet.setVisibility(View.VISIBLE);
                           /* if(item.middle!=null&&item.middle.equals("m"))
                                itemController.txtSet.setText("x "+String.valueOf(item.setCount)+" Sets");*/
                            if(item.middle!=null&&item.middle.equals("m"))
                            {
                                if(parentFragment.getFlowId()==5||parentFragment.getFlowId()==6)
                                {
                                    Log.e("alpha set","11");
                                    itemController.txtSet.setText("x "+context.getString(R.string.infinity)+" Sets");
                                }
                                else
                                {
                                    Log.e("alpha not set","22");
                                    itemController.txtSet.setText("x "+String.valueOf(item.setCount)+" Sets");
                                }
                            }



                        }
                        else if(item.SuperSetPosition==0)
                        {
                            itemController.imgBr.setImageResource(0);
                            itemController.imgBr.setImageResource(R.drawable.thired_mid_half);
                            itemController.txtSet.setVisibility(View.VISIBLE);
                           /* if(item.middle!=null&&item.middle.equals("m"))
                                itemController.txtSet.setText("x "+String.valueOf(item.setCount)+" Sets");*/
                            if(item.middle!=null&&item.middle.equals("m")) {
                                if(parentFragment.getFlowId()==5||parentFragment.getFlowId()==6)
                                {
                                    Log.e("alpha set","33");
                                    itemController.txtSet.setText("x "+context.getString(R.string.infinity)+" Sets");
                                }
                                else {
                                    Log.e("alpha not set","44");
                                    itemController.txtSet.setText("x " + String.valueOf(item.setCount) + " Sets");
                                }
                            }
                        }
                        else if(item.SuperSetPosition==1)
                        {
                            itemController.imgBr.setImageResource(0);
                            itemController.imgBr.setImageResource(R.drawable.thired_last_half);
                            itemController.txtSet.setVisibility(View.VISIBLE);
                            /*if(item.middle!=null&&item.middle.equals("m"))
                                itemController.txtSet.setText("x "+String.valueOf(item.setCount)+" Sets");*/
                            if(item.middle!=null&&item.middle.equals("m"))
                            {
                                if(parentFragment.getFlowId()==5||parentFragment.getFlowId()==6)
                                {
                                    Log.e("alpha set","55");
                                    itemController.txtSet.setText("x "+context.getString(R.string.infinity)+" Sets");
                                }
                                else {
                                    Log.e("alpha not set","66");
                                    itemController.txtSet.setText("x " + String.valueOf(item.setCount) + " Sets");
                                }
                            }

                        }/*else
                            {
                                itemController.imgBr.setVisibility(View.INVISIBLE);
                                itemController.txtSet.setVisibility(View.INVISIBLE);
                            }*/

                    }else
                    {
                        itemController.imgBr.setVisibility(View.INVISIBLE);
                        itemController.txtSet.setVisibility(View.INVISIBLE);
                    }

                }else
                {
                    if( item.lstTips!=null && item.lstTips.size()>0 || item.lstInstruction!=null && item.lstInstruction.size()>0)
                        itemController.rlInformation.setVisibility(View.VISIBLE);
                    else
                        itemController.rlInformation.setVisibility(View.INVISIBLE);

                    itemController.imgBr.setVisibility(View.INVISIBLE);
                    itemController.txtSet.setVisibility(View.INVISIBLE);
                }
                //  Log.e("parent","123");
                ////////Super Set////////////
                ////////Super Set////////////
                if (item.invisibleChildren == null) {
                    Log.e("hide", "12"+"---"+position);
                    //itemController.imgArrow.setImageResource(R.drawable.rt_arrow);
                    itemController.imgArrow.setImageResource(R.drawable.up_arrow_train);
                }
                else
                {
                    Log.e("open", "13"+"---"+position);
                    //itemController.imgArrow.setImageResource(R.drawable.rt_arrow);
                    // itemController.imgArrow.setImageResource(R.drawable.down_arrow);
                }
                itemController.rlInformation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(context,"IMAGEVIEW CLICKED",Toast.LENGTH_SHORT).show();
                        if(item.lstTips!=null && item.lstTips.size()>0 || item.lstInstruction!=null && item.lstInstruction.size()>0)
                        {
                                /*if(item.VideoUrl!=null)
                                {
                                    itemController.videoInstruction.setVisibility(View.VISIBLE);
                                    if(videoGlobalView!=null)
                                    {
                                        videoGlobalView.stopPlayback();
                                    }
                                    videoGlobalView=itemController.videoInstruction;
                                    playVideo(item.VideoUrl,itemController.videoInstruction);
                                }else {
                                    itemController.videoInstruction.setVisibility(View.GONE);
                                }*/

                            if(item.showInstruction)
                            {
                                item.showInstruction=false;
                                itemController.llInfoTips.setVisibility(View.GONE);
                            }else
                            {
                                itemController.llInfoTips.setVisibility(View.VISIBLE);
                                item.showInstruction=true;
                                if(item.lstTips!=null && item.lstTips.size()>0)
                                {
                                    itemController.llTips.setVisibility(View.VISIBLE);
                                    itemController.llDynamicTips.removeAllViews();
                                    for(int i=0;i<item.lstTips.size();i++)
                                    {
                                        View dynamicTipView=layoutInflater.inflate(R.layout.dynamic_tips_instruction_session,null);
                                        TextView txtTipsInstructions=(TextView)dynamicTipView.findViewById(R.id.txtTipsInstructions);
                                        txtTipsInstructions.setText(item.lstTips.get(i));
                                        itemController.llDynamicTips.addView(dynamicTipView);
                                    }
                                }else {
                                    itemController.llTips.setVisibility(View.GONE);
                                }
                                if(item.lstInstruction!=null && item.lstInstruction.size()>0)
                                {
                                    itemController.llInfoMain.setVisibility(View.VISIBLE);
                                    itemController.llDynamicInfo.removeAllViews();
                                    for(int i=0;i<item.lstInstruction.size();i++)
                                    {
                                        View dynamicTipView=layoutInflater.inflate(R.layout.dynamic_tips_instruction_session,null);
                                        TextView txtTipsInstructions=(TextView)dynamicTipView.findViewById(R.id.txtTipsInstructions);
                                        txtTipsInstructions.setText(item.lstInstruction.get(i));
                                        itemController.llDynamicInfo.addView(dynamicTipView);
                                    }
                                }else {
                                    itemController.llInfoMain.setVisibility(View.GONE);
                                }
                            }
                        }else
                        {
                            itemController.llInfoTips.setVisibility(View.GONE);
                        }
                    }
                });

                itemController.llRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(videoGlobalView!=null)
                        {
                            videoGlobalView.stopPlayback();
                        }
                        if (item.invisibleChildren == null)
                        {
                            Log.e("hide","12");
                            //itemController.imgArrow.setImageResource(R.drawable.rt_arrow);
                            //  itemController.imgArrow.setImageResource(R.drawable.down_arrow);
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD)
                            {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);

                        }
                        else
                        {
                                /*if(preItem!=null)
                                {
                                   closePreviousAccordian(preItem, itemControllerPre);
                                }
                                Log.e("show","13");
                               if(previousRecyclerview!=null)
                                {
                                    Log.e("INSIDESINDE","TRUEEEEEEEEEEEEEEE");
                                    previousRecyclerview.setAdapter(null);
                                }*/
                            previousRecyclerview=currentRecyclerview;
                            itemController.imgArrow.setImageResource(R.drawable.up_arrow_train);
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            item.invisibleChildren = null;
                            isAlreadyOPen=true;
                            preItem=item;
                            itemControllerPre=itemController;
                        }
                    }
                });

                break;
            case CHILD:
                //Toast.makeText(context,"CHILD_POSITION "+position,Toast.LENGTH_SHORT).show();
                final ListItemViewHolder childController = (ListItemViewHolder) holder;
                childController.refferalItem = item;
                childController.txtEquipmentAns.setText(item.exerciseName);
                /*if(parentFragment.customMode)
                    childController.rlEditSession.setVisibility(View.VISIBLE);
                else
                    childController.rlEditSession.setVisibility(View.GONE);*/

              /*  childController.rlEditSession.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("print edit","123");
                        if(parentFragment.customMode)
                        parentFragment.getSquadWorkoutSession();
                    }
                });*/
                if(item.VideoUrl==null||item.VideoUrl.equals("")||!Util.chkIsUrl(item.VideoUrl))
                    childController.rlRightBack.setVisibility(View.INVISIBLE);
                else
                    childController.rlRightBack.setVisibility(View.VISIBLE);
                if(item.isCircuit)
                {
                    childController.llInfo.setVisibility(View.GONE);
                    childController.rvCircuitList.setVisibility(View.VISIBLE);
                    if(null!=item.lstCircuitExercise)
                    {
                        currentRecyclerview=childController.rvCircuitList;
                        funForTouch(childController.rvCircuitList,item.lstCircuitExercise,Integer.parseInt(item.index),item.exerciseName);
                        // childController.rvCircuitList.setOnTouchListener(touchFun);
                        loadAdapter(childController.rvCircuitList, item.lstCircuitExercise,item,Integer.parseInt(item.index));

                    }
                    else
                    {
                        Log.e("print cir","null");
                    }
                }
                else
                {
                        /*childController.llInfo.setVisibility(View.VISIBLE);
                        childController.rvCircuitList.setVisibility(View.GONE);
                        playVideo(item.exerciseName,childController.vidExercise);*/
                    if(videoGlobalView!=null)
                    {
                        videoGlobalView.stopPlayback();
                    }
                    videoGlobalView=childController.vidExercise;
                    try {
                        childController.llInfo.setVisibility(View.VISIBLE);
                        childController.rvCircuitList.setVisibility(View.GONE);

                        if(item.lstEquipment!=null && item.lstEquipment.size()>0)
                        {
                            childController.llEquipment.setVisibility(View.VISIBLE);
                            childController.llDynamicEquipment.removeAllViews();
                            for (int i=0;i<item.lstEquipment.size();i++)
                            {
                                View dynamicEqpView=layoutInflater.inflate(R.layout.dynamic_tips_instruction_session,null);
                                TextView txtTipsInstructions=(TextView)dynamicEqpView.findViewById(R.id.txtTipsInstructions);
                                txtTipsInstructions.setText((String)item.lstEquipment.get(i));
                                childController.llDynamicEquipment.addView(dynamicEqpView);
                            }
                        }else
                        {
                            childController.llEquipment.setVisibility(View.GONE);
                        }

                        if(item.lstSubstituteExercise!=null && item.lstSubstituteExercise.size()>0)
                        {
                            childController.llEquipmentAlt.setVisibility(View.VISIBLE);
                            childController.llDynamicEquipmentAlt.removeAllViews();
                            for (int i=0;i<item.lstSubstituteExercise.size();i++)
                            {
                                View dynamicEqpView=layoutInflater.inflate(R.layout.dynamic_tips_instruction_session,null);
                                TextView txtTipsInstructions=(TextView)dynamicEqpView.findViewById(R.id.txtTipsInstructions);

                                if(item.lstSubstituteExercise.get(i).getSubstituteExerciseId()!=null)
                                {
                                    txtTipsInstructions.setTextColor(Color.parseColor("#0B86EA"));
                                    SpannableString content = new SpannableString(item.lstSubstituteExercise.get(i).getSubstituteExerciseName());
                                    content.setSpan(new UnderlineSpan(), 0, item.lstSubstituteExercise.get(i).getSubstituteExerciseName().length(), 0);
                                    txtTipsInstructions.setText(content);

                                    final int finalI = i;
                                    txtTipsInstructions.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showIndividualVideo(item.lstSubstituteExercise.get(finalI).getSubstituteExerciseId());
                                        }
                                    });

                                }else
                                {
                                    txtTipsInstructions.setText((String)item.lstSubstituteExercise.get(i).getSubstituteExerciseName());
                                }
                                childController.llDynamicEquipmentAlt.addView(dynamicEqpView);
                            }
                        }else
                        {
                            childController.llEquipmentAlt.setVisibility(View.GONE);
                        }
                        if(item.lstAltBodyWeightExercise!=null && item.lstAltBodyWeightExercise.size()>0)
                        {
                            childController.llBodyWeight.setVisibility(View.VISIBLE);
                            childController.llDynamicBodyWeight.removeAllViews();
                            for (int i=0;i<item.lstAltBodyWeightExercise.size();i++)
                            {
                                View dynamicEqpView=layoutInflater.inflate(R.layout.dynamic_tips_instruction_session,null);
                                TextView txtTipsInstructions=(TextView)dynamicEqpView.findViewById(R.id.txtTipsInstructions);

                                if(item.lstAltBodyWeightExercise.get(i).getBodyWeightAltExerciseId()!=null)
                                {
                                    txtTipsInstructions.setTextColor(Color.parseColor("#0B86EA"));
                                    SpannableString content = new SpannableString(item.lstAltBodyWeightExercise.get(i).getBodyWeightAltExerciseName());
                                    content.setSpan(new UnderlineSpan(), 0, item.lstAltBodyWeightExercise.get(i).getBodyWeightAltExerciseName().length(), 0);
                                    txtTipsInstructions.setText(content);
                                    final int finalI = i;
                                    txtTipsInstructions.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showIndividualVideo(item.lstAltBodyWeightExercise.get(finalI).getBodyWeightAltExerciseId());
                                        }
                                    });
                                }else
                                {
                                    txtTipsInstructions.setText(item.lstAltBodyWeightExercise.get(i).getBodyWeightAltExerciseName());
                                }
                                childController.llDynamicBodyWeight.addView(dynamicEqpView);
                            }
                        }else
                        {
                            childController.llBodyWeight.setVisibility(View.GONE);
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    if(item.lstImage.size()>0)
                    {
                        Glide.with(context).load(item.lstImage.get(0))
                                .thumbnail(0.5f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.ic_picture)
                                .error(R.drawable.ic_picture)
                                .into(childController.imgExercise);
                    }else
                    {

                    }
                    ///////////////////////

                    childController.rlRightBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            childController.rlRightBack.setVisibility(View.INVISIBLE);
                            childController.rlLeftBack.setVisibility(View.VISIBLE);
                            childController.imgExercise.setVisibility(View.GONE);
                            childController.rlVideo.setVisibility(View.VISIBLE);
                            playVideo(item.VideoUrl,childController.vidExercise);
                        }
                    });
                    childController.rlLeftBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            childController.rlLeftBack.setVisibility(View.INVISIBLE);
                            childController.rlRightBack.setVisibility(View.VISIBLE);
                            childController.rlVideo.setVisibility(View.GONE);
                            childController.imgExercise.setVisibility(View.VISIBLE);
                            if(videoGlobalView!=null)
                            {
                                videoGlobalView.stopPlayback();
                            }
                        }
                    });

                }
        }
    }

    private void funForTouch(RecyclerView rvCircuitList, List<SessionOverViewModel.Circuit> lstCircuitExercise, int i, String exerciseName) {

        rvCircuitList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        //Check if the x and y position of the touch is inside the bitmap

                        if(backgroundBkp!=null)
                            if( backgroundBkp.contains(x,y))
                            {
                                //Bitmap touched
                                Log.e("print touch","123");
                                if(lstExercisePicker==null)
                                {
                                    getExerciseList(lstCircuitExercise,i,exerciseName);
                                }

                                else {
                                    // showPicker(lstExercisePicker);
                                    openReplaceDialog(); //commented by jyoti
                                }
                            }
                            else
                            {
                                Log.e("out touch","123");
                            }
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        //  Log.e("print sizeeee",data.size()+"?");
        return data.size();
    }

    public static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIndex,txtExerciseName,txtReps,txtSet;
        public Item refferalItem;
        public RelativeLayout llRoot;
        //public ImageView imgBr;
        public ImageView imgInformation,imgArrow,imgBr;
        public LinearLayout llInfoTips,llInfoMain,llDynamicInfo,llTips,llDynamicTips;
        public RelativeLayout rlInformation;
        public VideoView videoInstruction;



        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            txtIndex = (TextView) itemView.findViewById(R.id.txtIndex);
            txtExerciseName = (TextView) itemView.findViewById(R.id.txtExerciseName);
            txtReps = (TextView) itemView.findViewById(R.id.txtReps);
            txtSet = (TextView) itemView.findViewById(R.id.txtSet);
            llRoot=(RelativeLayout)itemView.findViewById(R.id.llRoot);
            //imgBr=(ImageView) itemView.findViewById(R.id.imgBr);
            imgInformation=(ImageView) itemView.findViewById(R.id.imgInformation);
            rlInformation=(RelativeLayout)itemView.findViewById(R.id.rlInformation);
            imgArrow=(ImageView) itemView.findViewById(R.id.imgArrow);
            imgBr=(ImageView) itemView.findViewById(R.id.imgBr);
            llInfoTips=(LinearLayout)itemView.findViewById(R.id.llInfoTips);
            llInfoMain=(LinearLayout)itemView.findViewById(R.id.llInfoMain);
            llDynamicInfo=(LinearLayout)itemView.findViewById(R.id.llDynamicInfo);
            llTips=(LinearLayout)itemView.findViewById(R.id.llTips);
            llDynamicTips=(LinearLayout)itemView.findViewById(R.id.llDynamicTips);
            videoInstruction=(VideoView)itemView.findViewById(R.id.videoInstruction);

        }
    }
    public static class ListItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgExercise;
        public Item refferalItem;
        public RecyclerView rvCircuitList;
        public LinearLayout llInfo;
        public VideoView vidExercise;
        public View itemView;
        public LinearLayout llEquipment,llDynamicEquipment,llEquipmentAlt,llDynamicEquipmentAlt,llBodyWeight,llDynamicBodyWeight;
        public TextView txtEquipmentAns,txtEquipmentBasedAns,txtEquipmentWeightAns;
        public RelativeLayout rlLeftBack,rlRightBack,rlVideo,rlEditSession;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imgExercise = (ImageView) itemView.findViewById(R.id.imgExercise);
            txtEquipmentAns=(TextView)itemView.findViewById(R.id.txtEquipmentAns);
            txtEquipmentBasedAns=(TextView)itemView.findViewById(R.id.txtEquipmentBasedAns);
            txtEquipmentWeightAns=(TextView)itemView.findViewById(R.id.txtEquipmentWeightAns);
            llInfo=(LinearLayout)itemView.findViewById(R.id.llInfo);
            rvCircuitList=(RecyclerView)itemView.findViewById(R.id.rvCircuitList);
            vidExercise=(VideoView)itemView.findViewById(R.id.vidExercise);
            rvCircuitList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            llEquipment=(LinearLayout)itemView.findViewById(R.id.llEquipment);
            llDynamicEquipment=(LinearLayout)itemView.findViewById(R.id.llDynamicEquipment);
            llEquipmentAlt=(LinearLayout)itemView.findViewById(R.id.llEquipmentAlt);
            llDynamicEquipmentAlt=(LinearLayout)itemView.findViewById(R.id.llDynamicEquipmentAlt);
            llBodyWeight=(LinearLayout)itemView.findViewById(R.id.llBodyWeight);
            llDynamicBodyWeight=(LinearLayout)itemView.findViewById(R.id.llDynamicBodyWeight);
            rlLeftBack=(RelativeLayout)itemView.findViewById(R.id.rlLeftBack);
            rlRightBack=(RelativeLayout)itemView.findViewById(R.id.rlRightBack);
            rlVideo=(RelativeLayout)itemView.findViewById(R.id.rlVideo);
            rlEditSession=(RelativeLayout ) itemView.findViewById(R.id.rlEditSession);
        }
    }

    public static class Item {
        public int type;
        public String exerciseName;
        public String index,repsCount,VideoUrl,exerciseRepGoal,RepsUnitText;
        public boolean isCircuit;
        public Integer id,sequenceNo,setCount;
        public int isSuperSet,SuperSetPosition;
        public String middle="";
        public List<Item> invisibleChildren;
        public List<SessionOverViewModel.Exercise> lstExercise;
        List<SessionOverViewModel.Circuit> lstCircuitExercise;
        public List<String> lstTips;
        public List<String> lstInstruction;
        public boolean showInstruction;
        public List<String> lstEquipment;
        public List<String> lstAltEquipment;
        public List<SessionOverViewModel.AltBodyWeightExercise> lstAltBodyWeightExercise;
        public List<String> lstImage;
        public List<SessionOverViewModel.SubstituteExercise> lstSubstituteExercise;

        public Item(int type, String index, SessionOverViewModel.Exercise exercise) {
            this.type=type;
            this.exerciseName=exercise.getName();
            this.repsCount=exercise.getRepsUnit()+"";
            this.index=index;
            this.id=exercise.getId();
            this.sequenceNo=exercise.getSequenceNo();
            this.isCircuit=exercise.getIsCircuit();
            lstCircuitExercise = exercise.getCircuitExercises();
            this.isSuperSet=exercise.getIsSuperSet();
            this.SuperSetPosition=exercise.getSuperSetPosition();
            lstTips=exercise.getTips();
            lstInstruction=exercise.getInstructions();
            showInstruction=false;
            lstEquipment=exercise.getEquipments();
            lstAltEquipment=exercise.getSubstituteEquipments();
            lstAltBodyWeightExercise=exercise.getAltBodyWeightExercises();
            lstImage=exercise.getPhotoList();
            VideoUrl=(String)exercise.getVideoUrlPublic();
            setCount=exercise.getSetCount();
            exerciseRepGoal=exercise.getExerciseRepGoal();
            RepsUnitText=exercise.getRepsUnitText();
            lstSubstituteExercise=exercise.getSubstituteExercises();
            this.middle=exercise.getMiddle();
        }


    }
    private void loadAdapter(RecyclerView rvCircuitList, List<SessionOverViewModel.Circuit> circuitExercises, Item item, int adapterPosition)
    {
        List<ExpandableCircuitListAdapter.Item> data = new ArrayList<>();
        char alphabet = 'a';
        for(int p=0;p<circuitExercises.size();p++,alphabet++)
        {
            ExpandableCircuitListAdapter.Item places=new ExpandableCircuitListAdapter.Item(ExpandableListAdapter.HEADER,alphabet+".",circuitExercises.get(p),adapterPosition,parentFragment.getFlowId());
            places.invisibleChildren = new ArrayList<>();
            places.invisibleChildren.add(new ExpandableCircuitListAdapter.Item(ExpandableListAdapter.CHILD,alphabet+".",circuitExercises.get(p),adapterPosition, parentFragment.getFlowId()));
            data.add(places);


        }
        rvCircuitList.setAdapter(new ExpandableCircuitListAdapter(data,context));
        /*if(item.isCircuit) {
            initSwipe();
        }*/
        initSwipe(adapterPosition,circuitExercises);
    }

    /////////////////////One Accordian will open at a time Start//////////
    private void closePreviousAccordian(Item preItem, ListHeaderViewHolder itemControllerPre)
    {
        if (preItem.invisibleChildren == null)
        {
            Log.e("hide","00");
            preItem.invisibleChildren = new ArrayList<Item>();
            int count = 0;
            int pos = data.indexOf(itemControllerPre.refferalItem);
            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD)
            {
                preItem.invisibleChildren.add(data.remove(pos + 1));
                count++;
            }
            notifyItemRangeRemoved(pos + 1, count);

        }
    }
    ////////////////////////One Accordian will open at a time End//////////
    /////////////////////////Swipe Part Start/////////////////////////////////////////////
    ///////////////////////////Swipe RecyclerView////////////
    public void initSwipe(final int adapterPosition, List<SessionOverViewModel.Circuit> circuitExercises)
    {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT)
        {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                Log.e("swiped","123");

                setEquipExercise(circuitExercises,viewHolder.getAdapterPosition());
                setBodyWeightAlt(circuitExercises,viewHolder.getAdapterPosition());

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
                {
      /*  if(canvasBkp!=null&&preHolder!=null)

        {
            canvasBkp.drawColor(0, PorterDuff.Mode.CLEAR);
            super.onChildDraw(canvasBkp, recyclerView, preHolder, 0, dY, actionState, isCurrentlyActive);
            preHolder=null;
            canvasBkp=null;
        }*/

                    View itemView = viewHolder.itemView;

                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    width = height / 3;

                    if(dX > 0)
                    {
                        // Log.e("con 1","123");
                        paint.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,paint);
                        icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.s_t_ce_edit);
                        RectF icon_dest = new RectF(background.left-5 ,background.top-5,background.right-5,background.bottom-5);
                        c.drawBitmap(icon,null,icon_dest,paint);

                    }
                    else
                    {
                        //  Log.e("con 2","456");

                        paint.setColor(Color.parseColor("#01D0B9"));
                        RectF background = new RectF((float) itemView.getRight() + dX/5, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        backgroundBkp=background;
                        c.drawRect(background,paint);
                        icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.s_t_ce_edit);
                        RectF icon_dest = new RectF(background.left-5 ,background.top-5,background.right-5,background.bottom-5);
                        c.drawBitmap(icon,null,icon_dest,paint);
                        canvasBkp=c;
                        preHolder= (ExpandableCircuitListAdapter.ListHeaderViewHolder) viewHolder;
                        itemNo=preHolder.getAdapterPosition();
                        headNo=preHolder.refferalItem.headerNo;

                        /*  Log.e("print name-->",preHolder.refferalItem.headerNo+"?"+preHolder.refferalItem.exerciseName+"?"+preHolder.refferalItem.index+"?"+itemNo+"?"+preHolder.txtDummy.getText().toString());*/

            /*bitmapXPosition=(float) itemView.getRight() + dX/5;
            bitmapYPosition=(float) itemView.getTop();
            bitmapWidth=width;
            bitmapHeight=((float) itemView.getTop()-((float) itemView.getBottom()));*/



                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX/5, dY, actionState, isCurrentlyActive);

            }
            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                Log.e("print item index--",viewHolder.getAdapterPosition()+"?");
                if (viewHolder instanceof ExpandableCircuitListAdapter.ListItemViewHolder) return 0;
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(currentRecyclerview);
    }


    ///////////////////////Swipe Recyclerview ////////////////


/*
    public void showPicker(final List<ExerciseRequestModel.Exercise> lstExercisePicker)
    {
        final Dialog pickerDialog=new Dialog(context,android.R.style.Theme_Light);
        pickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pickerDialog.setContentView(R.layout.dialog_picker);
        final WheelPicker pickerExercise=(WheelPicker)pickerDialog.findViewById(R.id.pickerExercise);
        ImageView imgBack=(ImageView)pickerDialog.findViewById(R.id.imgBack);
        ImageView imgFwd=(ImageView)pickerDialog.findViewById(R.id.imgFwd);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerDialog.dismiss();
            }
        });

        pickerExercise.setSelectedItemTextColor(0xCCFFFFFF);

        imgFwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pickerExercise.getCurrentItemPosition()
                // Log.e("pos->",pickerExercise.getCurrentItemPosition()+"?");
                // headerIndex--;
                Log.e("get hno",headNo+"?");
                Log.e("get item",itemNo+"?");
                int indexOut=Integer.parseInt(preHolder.txtDummy.getText().toString());
                if(indexOut<parentFragment.getLstExercise().size())
                {
                    if(itemNo<parentFragment.getLstExercise().get(indexOut).getCircuitExercises().size())
                    {
                        Log.e("before-->",indexOut+"?"+itemNo+"?"+"before"+itemNo+parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).getExerciseName()+"?"+parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).getExerciseId());
                        /////////////////

                        preHolder.txtExerciseName.setText(lstExercisePicker.get(pickerExercise.getCurrentItemPosition()).getExerciseName().toUpperCase());
                        parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).setExerciseName(lstExercisePicker.get(pickerExercise.getCurrentItemPosition()).getExerciseName());
                        parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).setExerciseId(lstExercisePicker.get(pickerExercise.getCurrentItemPosition()).getExerciseId());
                        Log.e("after-->",indexOut+"?"+itemNo+"?"+"after"+itemNo+parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).getExerciseName()+"?"+parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).getExerciseId());
                    }

                }



                */
/*EditedCircuitList editedCircuitList=new EditedCircuitList();
                editedCircuitList.setSequenceNo(preHolder.refferalItem.sequenceNo);
                editedCircuitList.setId(preHolder.refferalItem.id);
                editedCircuitList.setRestComment(preHolder.refferalItem.restComment);
                editedCircuitList.setNewCircuitId(lstExercisePicker.get(pickerExercise.getCurrentItemPosition()).getExerciseId());
                ((SessionOverviewFragment) parentFragment).setArrEditedCircuitList(editedCircuitList);*//*
     ////Change 27/1/17 ////////////////

                */
/*QuickEditedCircuitList quickEditedCircuitList=new QuickEditedCircuitList();
                quickEditedCircuitList.setUserId(Integer.parseInt(sharedPreference.read("ABBBCOnlineUserId","")));
                quickEditedCircuitList.setExerciseSessionId(Integer.parseInt(sharedPreference.read("ABBBCOnlineUserSessionId","")));
                quickEditedCircuitList.setSessionTitle(((SessionOverviewFragment) parentFragment).getSessionTitle());
                quickEditedCircuitList.setOldExerciseId(preHolder.refferalItem.id);
                quickEditedCircuitList.setNewExerciseId(lstExercisePicker.get(pickerExercise.getCurrentItemPosition()).getExerciseId());
                quickEditedCircuitList.setPersonalised(((SessionOverviewFragment) parentFragment).isPersonalised());
                quickEditedCircuitList.setDate(simpleDateFormat.format(new Date()));
                quickEditedCircuitList.setSequenceNumber(preHolder.refferalItem.sequenceNo);
                quickEditedCircuitList.setKey(Util.KEY_ABBBC);*//*

                pickerDialog.dismiss();
                //SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
               */
/* SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                HashMap<String,Object> hashMap=new HashMap<String, Object>();
                hashMap.put("UserId",Integer.parseInt(sharedPreference.read("ABBBCOnlineUserId","")));
                hashMap.put("ExerciseSessionId",((SessionOverviewFragment) parentFragment).getExerciseSessionId());
                hashMap.put("SessionTitle",((SessionOverviewFragment) parentFragment).getSessionTitle());
                hashMap.put("OldExerciseId",preHolder.refferalItem.id);
                Log.e("OldExerciseId",preHolder.refferalItem.id+">>>>>>>>.");
                hashMap.put("NewExerciseId",lstExercisePicker.get(pickerExercise.getCurrentItemPosition()).getExerciseId());
                Log.e("NewExerciseId",lstExercisePicker.get(pickerExercise.getCurrentItemPosition()).getExerciseId()+">>>>>>>>.");
                //hashMap.put("Personalised",((SessionOverviewFragment) parentFragment).isPersonalised());
                hashMap.put("Personalised",false);
                hashMap.put("Date",simpleDateFormat.format(new Date()));
                hashMap.put("SequenceNumber",preHolder.refferalItem.sequenceNo);
                hashMap.put("Key",Util.KEY_ABBBC);
                hashMap.put("UserSessionID",Integer.parseInt(sharedPreference.read("ABBBCOnlineUserSessionId","")));*//*


                //circuitEditApi(hashMap);

                //((SessionOverviewFragment) parentFragment).setArrQuickEditedCircuitList(quickEditedCircuitList);


                ////////////////////
            }
        });
        List<String> lstData=new ArrayList<>();
        for(int i=0;i<lstExercisePicker.size();i++)
        {
            lstData.add(lstExercisePicker.get(i).getExerciseName());
        }

        pickerExercise.setData(lstData);
        pickerExercise.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position)
            {
                // Toast.makeText(getActivity(),   String.valueOf(data)+">>>>"+position, Toast.LENGTH_SHORT).show();
               */
/* preHolder.txtExerciseName.setText(String.valueOf(data));
                EditedCircuitList editedCircuitList=new EditedCircuitList();
                editedCircuitList.setSequenceNo(preHolder.refferalItem.sequenceNo);
                editedCircuitList.setId(preHolder.refferalItem.id);
                editedCircuitList.setRestComment(preHolder.refferalItem.restComment);
                editedCircuitList.setNewCircuitId(lstExercisePicker.get(position).getExerciseId());
                ((SessionOverviewFragment) parentFragment).setArrEditedCircuitList(editedCircuitList);*//*



            }
        });
        pickerDialog.show();

    }
*/

    private void circuitEditApi(HashMap<String,Object> hashMapReq) {

        if(Connection.checkConnection(context))
        {
            final ProgressDialog  progressDialog= ProgressDialog.show(context, "", "Please wait...");
            SessionServiceImpl serviceSessionMain=new SessionServiceImpl(context);
            Call<QuickEditCircuitResponseModel> sessionCall=serviceSessionMain.getQuickEditList(hashMapReq);
            sessionCall.enqueue(new Callback<QuickEditCircuitResponseModel>() {
                @Override
                public void onResponse(Call<QuickEditCircuitResponseModel> call, Response<QuickEditCircuitResponseModel> response) {
                    progressDialog.dismiss();
                    ((SessionOverviewFragment)parentFragment).getSessionListFromApi(true);
                }

                @Override
                public void onFailure(Call<QuickEditCircuitResponseModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }else
        {
            Util.showToast(context,Util.networkMsg);
        }
    }//commented by jyoti


    View.OnTouchListener touchFun=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    //Check if the x and y position of the touch is inside the bitmap

                    if(backgroundBkp!=null)
                        if( backgroundBkp.contains(x,y))
                        {
                            //Bitmap touched
                            Log.e("print touch","123");
                            if(lstExercisePicker==null)
                            {
                                //getExerciseList();
                                //getExerciseList(lstCircuitExercise, i);
                            }

                            else{

                            }
                            //   showPicker(lstExercisePicker);
                        }
                        else
                        {
                            Log.e("out touch","123");
                        }
                    return true;
            }
            return false;
        }
    };
    /////////////////////////////Swipe End//////////////////////////////////////////


    private void playVideo(String fileName, final VideoView vidExercise)
    {
        /*File file = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES), ".Squad"+File.separator+fileName+".mp4");
        Log.e("full",file.getAbsoluteFile()+"?");
        if(file.exists())
        {
            Log.e("exist true",fileName);
            String videoName = "vida";
            Uri video = Uri.parse("android.resource://com.ashysystem.mbhq/raw/" + videoName);
            vidExercise.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setLooping(true);
                }
            });

            //vidExercise.setVideoURI(video);
            //vidExercise.setVideoPath(Environment.getExternalStorageDirectory()+"/"+ex_name+".mp4");
            // vidExercise.setVideoPath(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES)+"/Squad/"+fileName+".mp4");
            //vidExercise.setVideoPath(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES)+File.separator+".Squad"+File.separator+fileName+".mp4");
            vidExercise.setVideoPath(file.getAbsolutePath());
            vidExercise.start();
        }
//Do something
        else
        {
            Log.e("exist false",fileName);

        }*/
// Do something else.

        vidExercise.setVideoURI(Uri.parse(fileName));
        vidExercise.setMediaController(new MediaController(context));
        vidExercise.requestFocus();
        vidExercise.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //holder.progress.setVisibility(View.GONE);
                //holder.imgFullScreen.setVisibility(View.VISIBLE);
                vidExercise.start();
            }
        });
        vidExercise.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                vidExercise.resume();
            }
        });
        vidExercise.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Toast.makeText(context,"Video cannot be played",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    private void showIndividualVideo(Integer exerciseId) {

        final ProgressDialog progressDialog= ProgressDialog.show(context, "", "Please video...");

        HashMap<String,Object> hashReq=new HashMap<>();
        hashReq.put("ExerciseId",exerciseId);
        hashReq.put("Key", Util.KEY_ABBBC);
        hashReq.put("UserSessionID",sharedPreference.read("ABBBCOnlineUserSessionId",""));
        SessionServiceImpl sessionService=new SessionServiceImpl(context);
        Call<ShowIndividualVideoModel> serverCall=sessionService.getIndividualVideo(hashReq);
        serverCall.enqueue(new Callback<ShowIndividualVideoModel>() {
            @Override
            public void onResponse(Call<ShowIndividualVideoModel> call, final Response<ShowIndividualVideoModel> response) {
                progressDialog.dismiss();
                if(response.body()!=null)
                {
                    final Dialog individualVideoDialog=new Dialog(context,R.style.DialogTheme);
                    individualVideoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    individualVideoDialog.setContentView(R.layout.dialog_showindividualvideo);
                    ImageView imgCancel=(ImageView)individualVideoDialog.findViewById(R.id.imgCancel);
                    final ImageView imgExercise=(ImageView)individualVideoDialog.findViewById(R.id.imgExercise);
                    final ImageView imgDemo=(ImageView)individualVideoDialog.findViewById(R.id.imgDemo);
                    TextView txtExerciseName=(TextView) individualVideoDialog.findViewById(R.id.txtExerciseName);
                    final RelativeLayout rlLeftBack=(RelativeLayout)individualVideoDialog.findViewById(R.id.rlLeftBack);
                    final RelativeLayout rlVideo=(RelativeLayout)individualVideoDialog.findViewById(R.id.rlVideo);
                    final RelativeLayout rlRightBack=(RelativeLayout)individualVideoDialog.findViewById(R.id.rlRightBack);
                    final VideoView vidExercise=(VideoView)individualVideoDialog.findViewById(R.id.vidExercise);
                    LinearLayout llEquipment=(LinearLayout)individualVideoDialog.findViewById(R.id.llEquipment);
                    LinearLayout llDynamicEquipment=(LinearLayout)individualVideoDialog.findViewById(R.id.llDynamicEquipment);
                    LinearLayout llEquipmentAlt=(LinearLayout)individualVideoDialog.findViewById(R.id.llEquipmentAlt);
                    LinearLayout llDynamicEquipmentAlt=(LinearLayout)individualVideoDialog.findViewById(R.id.llDynamicEquipmentAlt);

                    imgCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            individualVideoDialog.dismiss();
                        }
                    });
                    txtExerciseName.setText(response.body().getExerciseDetail().getExerciseName());
                    playVideoForDialog(response.body().getExerciseDetail().getPublicUrl(),vidExercise,imgDemo);
                    if(response.body().getExerciseDetail().getPhotos().size()>0)
                    {
                        Glide.with(context).load(response.body().getExerciseDetail().getPhotos().get(0))
                                .thumbnail(0.5f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.ic_picture)
                                .error(R.drawable.ic_picture)
                                .into(imgExercise);
                    }
                    rlRightBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rlRightBack.setVisibility(View.INVISIBLE);
                            rlLeftBack.setVisibility(View.VISIBLE);
                            vidExercise.stopPlayback();
                            rlVideo.setVisibility(View.GONE);
                            imgExercise.setVisibility(View.VISIBLE);
                        }
                    });
                    rlLeftBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rlLeftBack.setVisibility(View.INVISIBLE);
                            rlRightBack.setVisibility(View.VISIBLE);
                            rlVideo.setVisibility(View.VISIBLE);
                            imgExercise.setVisibility(View.GONE);
                            playVideoForDialog(response.body().getExerciseDetail().getPublicUrl(),vidExercise,imgDemo);
                        }
                    });

                    try {
                        if(response.body().getExerciseDetail().getEquipments()!=null && response.body().getExerciseDetail().getEquipments().size()>0)
                        {
                            llEquipment.setVisibility(View.VISIBLE);
                            llDynamicEquipment.removeAllViews();
                            for (int i=0;i<response.body().getExerciseDetail().getEquipments().size();i++)
                            {
                                View dynamicEqpView=layoutInflater.inflate(R.layout.dynamic_tips_instruction_session,null);
                                TextView txtTipsInstructions=(TextView)dynamicEqpView.findViewById(R.id.txtTipsInstructions);
                                txtTipsInstructions.setText((String)response.body().getExerciseDetail().getEquipments().get(i));
                                llDynamicEquipment.addView(dynamicEqpView);
                            }
                        }else
                        {
                            llEquipment.setVisibility(View.GONE);
                        }
                        if(response.body().getExerciseDetail().getSubstituteEquipments()!=null && response.body().getExerciseDetail().getSubstituteEquipments().size()>0)
                        {
                            llEquipmentAlt.setVisibility(View.VISIBLE);
                            llDynamicEquipmentAlt.removeAllViews();
                            for (int i=0;i<response.body().getExerciseDetail().getSubstituteEquipments().size();i++)
                            {
                                View dynamicEqpView=layoutInflater.inflate(R.layout.dynamic_tips_instruction_session,null);
                                TextView txtTipsInstructions=(TextView)dynamicEqpView.findViewById(R.id.txtTipsInstructions);
                                txtTipsInstructions.setText((String)response.body().getExerciseDetail().getSubstituteEquipments().get(i));
                                llDynamicEquipmentAlt.addView(dynamicEqpView);
                            }
                        }else
                        {
                            llEquipmentAlt.setVisibility(View.GONE);
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    individualVideoDialog.show();

                }

            }

            @Override
            public void onFailure(Call<ShowIndividualVideoModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    ///////////////////Replace Exercise////////////
    public void getExerciseList(List<SessionOverViewModel.Circuit> lstCircuitExercise, int i, String exerciseName) {

        if(Connection.checkConnection(context))
        {
            final ProgressDialog  progressDialog= ProgressDialog.show(context, "", "Please wait...");
            /////////////////Edit Session Api//////////////
            HashMap<String,Object> hmModel=new HashMap<String, Object>();

            hmModel.put("Key", Util.KEY_ABBBC);
            //hmModel.put("UserSessionID",108);
            hmModel.put("UserSessionID",sharedPreference.read("ABBBCOnlineUserSessionId",""));

            SessionServiceImpl serviceSessionMain=new SessionServiceImpl(context);
            Call<ExerciseRequestModel> sessionCall=serviceSessionMain.getExerciseList(hmModel);
            sessionCall.enqueue(new Callback<ExerciseRequestModel>()
            {
                @Override
                public void onResponse(Call<ExerciseRequestModel> call, Response<ExerciseRequestModel> response)
                {
                    Log.e("print response-->",response.toString()+"?");
                    lstExercisePicker = response.body().getExercises();
                    progressDialog.dismiss();
                    setAllExercise(lstExercisePicker);
                    // setEquipExercise(lstCircuitExercise,i);
                    // setBodyWeightAlt(lstCircuitExercise,i);
                    getTargetExerciseApi();
                    //openReplaceDialog();
                    // showPicker(lstExercisePicker);
                }

                @Override
                public void onFailure(Call<ExerciseRequestModel> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("print fail-->","14"+"?");
                    progressDialog.dismiss();

                }
            });
        }else
        {
            Util.showToast(context,Util.networkMsg);
        }
        ////////////////Edit Session Api//////////////
    }
    private void getTargetExerciseApi()
    {
        if(Connection.checkConnection(context))
        {
            final ProgressDialog  progressDialog= ProgressDialog.show(context, "", "Please wait...");
            /////////////////Edit Session Api//////////////

            String url= Util.ABBBC_BASE_URL+"api/values/GetTargetExercises/"+sharedPreference.read("ABBBCOnlineUserId", "");
            SessionServiceImpl serviceSessionMain=new SessionServiceImpl(context);
            Call<TargetExerciseModel> sessionCall=serviceSessionMain.getTargetExercise(url);
            sessionCall.enqueue(new Callback<TargetExerciseModel>()
            {
                @Override
                public void onResponse(Call<TargetExerciseModel> call, Response<TargetExerciseModel> response)
                {
                    Log.e("print response-->",response.toString()+"?");
                    lstTargetExercise = response.body().getObj();
                    progressDialog.dismiss();
                    setTargetExercise(lstTargetExercise);
                    // setEquipExercise(lstCircuitExercise,i);
                    // setBodyWeightAlt(lstCircuitExercise,i);
                  //  openReplaceDialog();
                    // showPicker(lstExercisePicker);
                }

                @Override
                public void onFailure(Call<TargetExerciseModel> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("print fail-->","14"+"?");
                    openReplaceDialog(); //commented by jyoti
                    progressDialog.dismiss();

                }
            });
        }else
        {
            Util.showToast(context,Util.networkMsg);
        }
    }
    private void setBodyWeightAlt(List<SessionOverViewModel.Circuit> lstCircuitExercise, int i) {
        if(lstCircuitExercise.get(i).getAltBodyWeightExercises()!=null)
            for(int g=0;g<lstCircuitExercise.get(i).getAltBodyWeightExercises().size();g++)
            {
                SubTitle allExerciseSubtitle=new SubTitle ( lstCircuitExercise.get(i).getAltBodyWeightExercises().get(g).getBodyWeightAltExerciseName(),g,"","","",null,null);
                _allAlt.add(allExerciseSubtitle);
            }
    }

    private void setEquipExercise(List<SessionOverViewModel.Circuit> lstCircuitExercise, int i) {
        if(lstCircuitExercise.get(i).getEquipments()!=null)
            for(int g=0;g<lstCircuitExercise.get(i).getEquipments().size();g++)
            {
                Log.e("Log","Loop here");
                SubTitle allExerciseSubtitle=new SubTitle( lstCircuitExercise.get(i).getEquipments().get(g),g,"","","",null,null);
                _allEquip.add(allExerciseSubtitle);
            }
    }

    private void setAllExercise(List<ExerciseRequestModel.Exercise> lstExercisePicker) {

        for(int g=0;g<lstExercisePicker.size();g++)
        {
            List<String> tmpPhoto = lstExercisePicker.get(g).getPhotos();
            String imgPath="",videoUrl="",equipment="",substitute="",alt="";
            if(tmpPhoto!=null&&tmpPhoto.size()>0) {
                imgPath = tmpPhoto.get(0);
            }
            videoUrl=lstExercisePicker.get(g).getPublicUrl();
            List<String> tmpEquip=lstExercisePicker.get(g).getEquipments();
            if(tmpEquip!=null)
            {

                for(int x=0;x<tmpEquip.size();x++)
                {
                    equipment+= tmpEquip.get(x);
                    equipment+=",";

                }

            }
            List<SessionOverViewModel.SubstituteExercise> tmpSub=lstExercisePicker.get(g).getSubstituteExercises();

            List<SessionOverViewModel.AltBodyWeightExercise> tmpAlt=lstExercisePicker.get(g).getAltBodyWeightExercises();

            Log.e("print image path-*",imgPath+"???");
            SubTitle allExerciseSubtitle=new SubTitle( lstExercisePicker.get(g).getExerciseName(),lstExercisePicker.get(g).getExerciseId(),imgPath,videoUrl,equipment,tmpAlt,tmpSub);
            _allExercise.add(allExerciseSubtitle);
        }
    }
    private void setTargetExercise(List<TargetExerciseModel.Obj> targetExerciseModels) {
        for(int g=0;g<lstTargetExercise.size();g++)
        //for(int g=0;g<50;g++)
        {
            String imgPath="",videoUrl="",equipment="",substitute="",alt="";
            List<SessionOverViewModel.SubstituteExercise> tmpSub=new ArrayList<>();
            List<SessionOverViewModel.AltBodyWeightExercise> tmpAlt=new ArrayList<>();
            ////////////Changes/////////

            for(int in=0;in<lstExercisePicker.size();in++)
            {
                if(lstExercisePicker.get(in).getExerciseId()==lstExercisePicker.get(g).getExerciseId())
                {
                    List<String> tmpPhoto = lstExercisePicker.get(g).getPhotos();

                    if(tmpPhoto!=null&&tmpPhoto.size()>0) {
                        imgPath = tmpPhoto.get(0);
                    }
                    videoUrl=lstExercisePicker.get(g).getPublicUrl();
                    List<String> tmpEquip=lstExercisePicker.get(g).getEquipments();
                    if(tmpEquip!=null)
                    {

                        for(int x=0;x<tmpEquip.size();x++)
                        {
                            equipment+= tmpEquip.get(x);
                            equipment+=",";

                        }

                    }
                    tmpSub=lstExercisePicker.get(g).getSubstituteExercises();

                    tmpAlt=lstExercisePicker.get(g).getAltBodyWeightExercises();

                    Log.e("print image path-*",imgPath+"???");

                    break;
                }

            }


            ////////////Changes///////////
            if(lstTargetExercise.get(g).getUpperBody())
            {
                SubTitle tagetUpper=new SubTitle(lstTargetExercise.get(g).getExerciseName(),lstExercisePicker.get(g).getExerciseId(),imgPath,videoUrl,equipment,tmpAlt,tmpSub);
                _allTargetUpper.add(tagetUpper);
            }
            else if(lstTargetExercise.get(g).getLowerBody())
            {
                SubTitle targetLower=new SubTitle( lstTargetExercise.get(g).getExerciseName(),lstExercisePicker.get(g).getExerciseId(),imgPath,videoUrl,equipment,tmpAlt,tmpSub);
                _allTargetLower.add(targetLower);
            }
            else if(lstTargetExercise.get(g).getCore())
            {
                SubTitle tagetCore=new SubTitle( lstTargetExercise.get(g).getExerciseName(),lstExercisePicker.get(g).getExerciseId(),imgPath,videoUrl,equipment,tmpAlt,tmpSub);
                _allTargetCore.add(tagetCore);
            }

        }
    }
    private void playVideoForDialog(String fileName, final VideoView vidExercise, final ImageView imgDemo)
    {
// Do something else.
        if(fileName!=null)
        {
            //vidExercise.setVisibility(View.VISIBLE);
            vidExercise.setVideoURI(Uri.parse(fileName));
            vidExercise.setMediaController(new MediaController(context));
            vidExercise.requestFocus();
            vidExercise.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //holder.progress.setVisibility(View.GONE);
                    //holder.imgFullScreen.setVisibility(View.VISIBLE);
                    imgDemo.setVisibility(View.GONE);
                    vidExercise.setVisibility(View.VISIBLE);
                    vidExercise.start();
                }
            });
            vidExercise.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    vidExercise.resume();
                }
            });
            vidExercise.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Toast.makeText(context,"Video cannot be played",Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }else {
            Toast.makeText(context,"Video cannot be played",Toast.LENGTH_SHORT).show();
        }




    }
    private void openReplaceDialog()
    {
        List list = getList();
        Dialog replaceDialog=new Dialog(context,R.style.DialogTheme);
        replaceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        replaceDialog.setContentView(R.layout.dialog_replace_exercise);
        LinearLayout llApply=(LinearLayout )replaceDialog.findViewById(R.id.llApply);
        RecyclerView rvExercise=(RecyclerView )replaceDialog.findViewById(R.id.rvExercise);
        rvExercise.setNestedScrollingEnabled(false);
        TextView txtHeader=(TextView )replaceDialog.findViewById(R.id.txtHeader);
        RelativeLayout rlHeader=(RelativeLayout )replaceDialog.findViewById(R.id.rlHeader);
        txtSelectedText=(TextView )replaceDialog.findViewById(R.id.txtSelectedText);
        llApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceDialog.dismiss();
            }
        });
        rlHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceDialog.dismiss();
            }
        });
        int indexOut=Integer.parseInt(preHolder.txtDummy.getText().toString());
        txtHeader.setText("REPLACE "+parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).getExerciseName().toUpperCase());
        txtSelectedText.setText(parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).getExerciseName().toUpperCase());
        // rvExercise.setNestedScrollingEnabled(true);
        rvExercise.setLayoutManager(new LinearLayoutManager(context));
        ReplaceExerciseListAdapter replaceExerciseListAdapter=new ReplaceExerciseListAdapter(list,context,_allTargetUpper,_allTargetLower,_allTargetCore);
        rvExercise.setAdapter(replaceExerciseListAdapter);

        replaceDialog.show();



    }
//commented by jyoti
    private List getList() {
        List <Title>list = new ArrayList<>();
        for (int i = 0; i < strCategory.length; i++) {
            List subTitles = new ArrayList<>();
            if(i==0)
            {
                /*if(_album!=null)
                for (int j = 0; j< _album.size(); j++){
                    SubTitle subTitle = new SubTitle(_album.get(j),0);
                    subTitles.add(subTitle);
                }*/
                Log.e("equip size",_allEquip.size()+"?");
                subTitles=_allEquip;
            }
            else if(i==1)
            {
              /*  if(_artist!=null)
                for (int j = 0; j< _artist.size(); j++){
                    SubTitle subTitle = new SubTitle(_artist.get(j),0);
                    subTitles.add(subTitle);
                }*/
                Log.e("alt size",_allEquip.size()+"?");
                subTitles=_allAlt;
            }
            else if(i==2)
            {
              /*  if(_genres!=null)
                for (int j = 0; j< _genres.size(); j++){
                    SubTitle subTitle = new SubTitle(_genres.get(j),0);
                    subTitles.add(subTitle);
                }*/
                Log.e("sub size",_allEquip.size()+"?");
                subTitles=_allTarget;
                //subTitles=_allExercise;
            }
            else if(i==3)
            {
                /*if(_playlist!=null)
                for (int j = 0; j< _playlist.size(); j++){
                    SubTitle subTitle = new SubTitle(_playlist.get(j),0);
                    subTitles.add(subTitle);
                }*/
                Log.e("standard size",_allTStandard.size()+"?");
                subTitles=_allTStandard;
            }
            else if(i==4)
            {
                /*if(_playlist!=null)
                for (int j = 0; j< _playlist.size(); j++){
                    SubTitle subTitle = new SubTitle(_playlist.get(j),0);
                    subTitles.add(subTitle);
                }*/
                Log.e("all size",_allExercise.size()+"?");
                subTitles=_allExercise;
            }

            Log.e("Title list explist adapter",""+strCategory[i]);
          Title model = new Title(strCategory[i],subTitles);
          list.add(model); //commented by jyoti
        }
        return list;
    }
    ///////////////////Replace Exercise////////////
    //////////////////Outer Adapter Replace///////////////
    public class ReplaceExerciseListAdapter extends ExpandableRecyclerViewAdapter<ReplaceExerciseListAdapter.TitleViewHolder,ReplaceExerciseListAdapter.SubTitleViewHolder>
    {
        // private final RecyclerView.RecycledViewPool viewPool;
        private Context context;
        private LinearLayout preExLayout;
        private TextView preExText;
        /* String[] parents = new String[]{"Fruits",
                 "Nice Fruits", "Cool Fruits",
                 "Perfect Fruits", "Frozen Fruits",
                 "Warm Fruits"};*/
        String[] parents = new String[]{"LowerBody",
                "UpperBody", "Core"
        };
        private int counter=0;
        List<SubTitle> _allTargetUpper,_allTargetLower,_allTargetCore;
        ProgressDialog progressDialog=null;
        List listData=null;
        List listDataBkp=null;
        boolean isShow=false;

        public ReplaceExerciseListAdapter(List<?> groups, Context context, List<SubTitle> _allTargetUpper, List<SubTitle> _allTargetLower, List<SubTitle> _allTargetCore) {
            super(( List<? extends ExpandableGroup> ) groups);
            this.context=context;
            this.listData=groups;
            this.listDataBkp=groups;
            this._allTargetUpper=_allTargetUpper;
            this._allTargetLower=_allTargetLower;
            this._allTargetCore=_allTargetCore;
            // viewPool = new RecyclerView.RecycledViewPool();
        }
        @Override
        public void onGroupExpanded(int positionStart, int itemCount) {
            Log.e("Group expanded","1234"+">>"+itemCount+"?"+positionStart);
            if(positionStart==3)
            {
                Log.e("Group notify expand","1234"+">>"+itemCount+"?"+positionStart);
                isShow=true;
                ReplaceExerciseListAdapter.this.notifyDataSetChanged();
            } else if(itemCount==0)
            {
                Toast.makeText(context,"No exercise found",Toast.LENGTH_LONG).show();
            }

            // listData.addAll(listDataBkp);
            //ReplaceExerciseListAdapter.this.notifyItemChanged(positionStart);
            if(positionStart!=3)
                ReplaceExerciseListAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
        }
        @Override
        public void onGroupCollapsed(int positionStart, int itemCount) {
            Log.e("Group collapsed","1234"+">>"+itemCount+"?"+positionStart);
            if(positionStart==3)
            {
                Log.e("Group notify collapsed","1234"+">>"+itemCount+"?"+positionStart);
                isShow=false;
                ReplaceExerciseListAdapter.this.notifyDataSetChanged();
            } else if(itemCount==0)
            {
                Toast.makeText(context,"No exercise found",Toast.LENGTH_LONG).show();
            }
            if(positionStart!=3)
                ReplaceExerciseListAdapter.this.notifyItemRangeRemoved(positionStart,itemCount);
        }


        @Override
        public TitleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_header_replace_exercise, parent, false);
            return new TitleViewHolder(view);
        }

        @Override
        public SubTitleViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            Log.e("child oncreate view","123");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_child_replace_exercise, parent, false);
            return new SubTitleViewHolder(view);
        }

        @Override
        public void onBindChildViewHolder(SubTitleViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

            Log.e("child onbind view","123");
            final SubTitle subTitle = ( SubTitle ) group.getItems().get(childIndex);
            holder.tvPlayListName.setText(subTitle.getName());

            holder.llExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(preExLayout!=null)
                    {
                        preExLayout.setBackground(context.getResources().getDrawable(R.drawable.edittext_bg_exercise));
                        //preExLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
                        preExText.setTextColor(Color.parseColor("#58595b"));
                    }
                    preExLayout=holder.llExercise;
                    preExText=holder.tvPlayListName;
                    holder.llExercise.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    holder.tvPlayListName.setTextColor(Color.WHITE);
                    ///////////Value Set//////////
                    Log.e("set me--",1+"??");
                    setValue(subTitle);

                    //////////////
                }
            });
        }

        @Override
        public void onBindGroupViewHolder(TitleViewHolder holder, int flatPosition, ExpandableGroup group) {
            Log.e("parent onbind view","-"+flatPosition+"?"+strCategory[2]+">"+group.getTitle());
            holder.txtHeader.setText(group.getTitle());

            if(flatPosition==2)
            {
                if(isShow)
                {
                    Log.e("is show true","t");
                    holder.rvInner.setLayoutManager(new LinearLayoutManager(context));
                    //holder.rvInner.setLayoutManager(new MyLinearLayoutManager(context,1,false));
                    List list = getListForInner();
                    ReplaceInnerAdapter replaceExerciseListAdapter=new ReplaceInnerAdapter(list,context,_allTargetUpper,_allTargetLower,_allTargetCore);

                    holder.rvInner.setAdapter(replaceExerciseListAdapter);
                    Log.e("yes rv--",flatPosition+"?");
                    holder.rvInner.addOnItemTouchListener(mScrollTouchListener);
                    holder.rvInner.setVisibility(View.VISIBLE);
                }

                else
                {
                    Log.e("is show false","f");
                    holder.rvInner.setVisibility(View.GONE);
                    holder.rvInner.setAdapter(null);
                }

                // holder.rvInner.setVisibility(View.GONE);

                /////////
            }
            else {
                Log.e("dont show rv--",flatPosition+"?");
                holder.rvInner.setVisibility(View.GONE);
                holder.rvInner.setAdapter(null);
            }
        }
        RecyclerView.OnItemTouchListener mScrollTouchListener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        Log.e("finger move","123");
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;


                  /*  case MotionEvent.ACTION_UP:
                        Log.e("finger lift","123");
                        rv.getParent().requestDisallowInterceptTouchEvent(false);
                        break;*/

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };

        /////////////////////////////Header View Holder///////////
        public class TitleViewHolder extends GroupViewHolder {

            private TextView txtHeader;
            private ImageView arrow;
            private LinearLayout llExercise;
            //private ExpandableLayout exList;
            private RecyclerView rvInner;

            public TitleViewHolder(View itemView) {
                super(itemView);
                txtHeader = ( TextView ) itemView.findViewById(R.id.txtHeader);
                arrow = ( ImageView ) itemView.findViewById(R.id.imgOpenClose);
                llExercise=(LinearLayout)itemView.findViewById(R.id.llExercise);
                rvInner=(RecyclerView)itemView.findViewById(R.id.rvInner);
                //exList=(ExpandableLayout)itemView.findViewById(R.id.exList);
            }


            @Override
            public void expand() {
                arrow.setBackgroundResource(R.drawable.up_arrow_train);

                // animateExpand();
            }

            @Override
            public void collapse() {
                // arrow.setBackgroundResource(R.drawable.down_arrow);

                // animateCollapse();
            }

            private void animateExpand() {
                RotateAnimation rotate =
                        new RotateAnimation(360, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                rotate.setFillAfter(true);
                arrow.setAnimation(rotate);
                arrow.setBackgroundResource(R.drawable.up_arrow_train);
            }

            private void animateCollapse() {
                RotateAnimation rotate =
                        new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                rotate.setFillAfter(true);
                arrow.setAnimation(rotate);
                //  arrow.setBackgroundResource(R.drawable.down_arrow);
            }
        }
/////////////////////////Header View Holder End//////////

//////////Child View Holder//////////

        public class SubTitleViewHolder extends ChildViewHolder {

            private TextView tvPlayListName;
            private LinearLayout llExercise;


            public SubTitleViewHolder(View itemView) {
                super(itemView);
                tvPlayListName = ( TextView ) itemView.findViewById(R.id.tvPlayListName);
                llExercise = ( LinearLayout ) itemView.findViewById(R.id.llExercise);
            }


            public void setSubTitletName(String name) {
                tvPlayListName.setText(name);
            }
        }
//////////Child View Holder End//////////




        private List getListForInner() {
            List <Title>list = new ArrayList<>();
            for (int i = 0; i < parents.length; i++) {
                List subTitles = new ArrayList<>();
                if(i==0)
                {
                    Log.e("equip size",_allTargetLower.size()+"?");
                    subTitles=_allTargetLower;
                }
                else if(i==1)
                {
                    Log.e("alt size",_allTargetUpper.size()+"?");
                    subTitles=_allTargetUpper;
                }
                else if(i==2)
                {
                    Log.e("sub size",_allTargetCore.size()+"?");
                    subTitles=_allTargetCore;
                }

                Title model = new Title(parents[i],subTitles);
                list.add(model);
            }
            return list;
        }
    } //commented by jyoti

    private void setValue(SubTitle subTitle) {
        int indexOut=Integer.parseInt(preHolder.txtDummy.getText().toString());
        if(indexOut<parentFragment.getLstExercise().size())
        {
            if(itemNo<parentFragment.getLstExercise().get(indexOut).getCircuitExercises().size())
            {
                Log.e("image path show",subTitle.getImagePath()+"???");
                Log.e("video path show",subTitle.getVideoUrl()+"???");
                Log.e("before-->",indexOut+"?"+itemNo+"?"+"before"+itemNo+parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).getExerciseName()+"?"+parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).getExerciseId());
                /////////////////
                txtSelectedText.setText(subTitle.getName().toUpperCase());
                preHolder.txtExerciseName.setText(subTitle.getName().toUpperCase());
                Glide.with(context).load(subTitle.getImagePath())
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_picture)
                        .error(R.drawable.ic_picture)
                        .into(preHolder.imgExercise);
                List<String> lstEquip=new ArrayList<>();
                List<String> lstAlt=new ArrayList<>();
                List<String> lstSub=new ArrayList<>();
                if(subTitle.getEquipment()!=null&&!subTitle.getEquipment().equals(""))
                {
                    String tmpEquip=subTitle.getEquipment();
                    String[] arrEquip=tmpEquip.split(",");
                    if(arrEquip!=null&&arrEquip.length>0)
                        lstEquip = Arrays.asList(arrEquip);
                }

                parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).setEquipments(lstEquip);
                parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).setSubstituteExercises(subTitle.getSubstituteExercise());
                parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).setAltBodyWeightExercises(subTitle.getAltBodyWeightExercise());
                parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).setVideoPublicUrl(subTitle.getVideoUrl());
                parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).setExerciseName(subTitle.getName());
                parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).setExerciseId(subTitle.getId());
                Log.e("after-->",indexOut+"?"+itemNo+"?"+"after"+itemNo+parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).getExerciseName()+"?"+parentFragment.getLstExercise().get(indexOut).getCircuitExercises().get(itemNo).getExerciseId());
                notifyDataSetChanged();
            }

        }
    }

    ///////////////////Outer Adapter Replace//////////////////
    ///////////Inner Adapter Replace////////////
    public class ReplaceInnerAdapter extends ExpandableRecyclerViewAdapter<ReplaceInnerAdapter.TitleViewHolder,ReplaceInnerAdapter.SubTitleViewHolder>
    {
        private Context context;
        private LinearLayout preExLayout;
        private TextView preExText;
        /* String[] parents = new String[]{"Fruits",
                 "Nice Fruits", "Cool Fruits",
                 "Perfect Fruits", "Frozen Fruits",
                 "Warm Fruits"};*/

        private int counter=0;
        List<SubTitle> _allTargetUpper,_allTargetLower,_allTargetCore;
        ProgressDialog progressDialog=null;

        public ReplaceInnerAdapter(List<?> groups, Context context, List<SubTitle> _allTargetUpper, List<SubTitle> _allTargetLower, List<SubTitle> _allTargetCore) {
            super(( List<? extends ExpandableGroup> ) groups);
            this.context=context;
            this._allTargetUpper=_allTargetUpper;
            this._allTargetLower=_allTargetLower;
            this._allTargetCore=_allTargetCore;
        }
        @Override
        public void onGroupExpanded(int positionStart, int itemCount) {
            Log.e("Group  child expand","1234"+">>"+itemCount+"?"+positionStart);
            if(itemCount==0)
            {
                Toast.makeText(context,"No exercise found",Toast.LENGTH_LONG).show();
            }
            ReplaceInnerAdapter.this.notifyItemRangeInserted(positionStart,itemCount);

        }
        @Override
        public void onGroupCollapsed(int positionStart, int itemCount) {
            Log.e("Group child collapse","1234"+">>"+itemCount);
            if(itemCount==0)
            {
                // Toast.makeText(context,"No exercise found",Toast.LENGTH_LONG).show();
            }
            ReplaceInnerAdapter.this.notifyItemRangeRemoved(positionStart,itemCount);
        }


        @Override
        public TitleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_header_replace_exercise, parent, false);
            return new TitleViewHolder(view);
        }

        @Override
        public SubTitleViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_child_replace_exercise, parent, false);
            return new SubTitleViewHolder(view);
        }

        @Override
        public void onBindChildViewHolder(SubTitleViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

            final SubTitle subTitle = ( SubTitle ) group.getItems().get(childIndex);
            holder.tvPlayListName.setText(subTitle.getName());

            holder.llExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(preExLayout!=null)
                    {
                        preExLayout.setBackground(context.getResources().getDrawable(R.drawable.edittext_bg_exercise));
                        //preExLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
                        preExText.setTextColor(Color.parseColor("#58595b"));
                    }
                    preExLayout=holder.llExercise;
                    preExText=holder.tvPlayListName;
                    holder.llExercise.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    holder.tvPlayListName.setTextColor(Color.WHITE);
                    Log.e("set me--",2+"??");
                    setValue(subTitle);
                }
            });
        }

        @Override
        public void onBindGroupViewHolder(TitleViewHolder holder, int flatPosition, ExpandableGroup group) {
            Log.e("group child onbindvw--",flatPosition+"?");
            holder.txtHeader.setText(group.getTitle());

        }

        /////////////////////////////Header View Holder///////////
        public class TitleViewHolder extends GroupViewHolder {

            private TextView txtHeader;
            private ImageView arrow;
            private LinearLayout llExercise;
            //private ExpandableLayout exList;
            private RecyclerView rvInner;

            public TitleViewHolder(View itemView) {
                super(itemView);
                txtHeader = ( TextView ) itemView.findViewById(R.id.txtHeader);
                arrow = ( ImageView ) itemView.findViewById(R.id.imgOpenClose);
                llExercise=(LinearLayout)itemView.findViewById(R.id.llExercise);
                rvInner=(RecyclerView)itemView.findViewById(R.id.rvInner);
                //exList=(ExpandableLayout)itemView.findViewById(R.id.exList);
            }


            @Override
            public void expand() {
                arrow.setBackgroundResource(R.drawable.up_arrow_train);

                // animateExpand();
            }

            @Override
            public void collapse() {
                // arrow.setBackgroundResource(R.drawable.down_arrow);

                // animateCollapse();
            }

            private void animateExpand() {
                RotateAnimation rotate =
                        new RotateAnimation(360, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                rotate.setFillAfter(true);
                arrow.setAnimation(rotate);
                arrow.setBackgroundResource(R.drawable.up_arrow_train);
            }

            private void animateCollapse() {
                RotateAnimation rotate =
                        new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                rotate.setFillAfter(true);
                arrow.setAnimation(rotate);
                //arrow.setBackgroundResource(R.drawable.down_arrow);
            }
        }
/////////////////////////Header View Holder End//////////

//////////Child View Holder//////////

        public class SubTitleViewHolder extends ChildViewHolder {

            private TextView tvPlayListName;
            private LinearLayout llExercise;


            public SubTitleViewHolder(View itemView) {
                super(itemView);
                tvPlayListName = ( TextView ) itemView.findViewById(R.id.tvPlayListName);
                llExercise = ( LinearLayout ) itemView.findViewById(R.id.llExercise);
            }


            public void setSubTitletName(String name) {
                tvPlayListName.setText(name);
            }
        }
//////////Child View Holder End//////////


    }
    //commented by jyoti


    ////////////////Inner Adapter Replace/////////////


}