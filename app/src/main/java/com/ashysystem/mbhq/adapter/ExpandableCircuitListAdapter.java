package com.ashysystem.mbhq.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.media.MediaPlayer;
import android.net.Uri;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
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

import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.SessionServiceImpl;
import com.ashysystem.mbhq.model.ExerciseRequestModel;
import com.ashysystem.mbhq.model.SessionOverViewModel;
import com.ashysystem.mbhq.model.ShowIndividualVideoModel;
import com.ashysystem.mbhq.model.SubTitle;

import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anandbose on 09/06/15.
 */
public class ExpandableCircuitListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public static Context context;
    private Item preItem=null;
    private boolean isAlreadyOPen=false;
    private ListHeaderViewHolder itemControllerPre=null;
    LayoutInflater layoutInflater;
    SharedPreference sharedPreference;
    VideoView videoGlobalView;
    List<ExerciseRequestModel.Exercise> lstExercisePicker=null;
    private String[] strCategory={"EQUIPMENT BASED ALTERNATIVES:","BODY WEIGHT ALTERNATIVES:","TARGET AREA: (Lower Body, Upper Body, Core)","STANDARD EXERCISES:","ALL EXERCISES"};
    List<SubTitle> _allExercise = new ArrayList<>();
    List<SubTitle> _allEquip = new ArrayList<>();
    List<SubTitle> _allAlt = new ArrayList<>();
    List<SubTitle> _allTarget = new ArrayList<>();
    List<SubTitle> _allStandard = new ArrayList<>();
    private List<Item> data;
    private boolean shouldClick=false;

    public ExpandableCircuitListAdapter(List<Item> data,Context context) {
        this.data = data;
        this.context=context;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sharedPreference=new SharedPreference(context);
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
              //  Log.e("head","123");
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.adapter_circuit_list, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
               // Log.e("tail","123");
                /*TextView itemTextView = new TextView(context);
                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
                itemTextView.setTextColor(0x88000000);
                itemTextView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                                return new RecyclerView.ViewHolder(itemTextView) {
                };*/
                LayoutInflater inflaterChild = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterChild.inflate(R.layout.layout_circuit_details, parent, false);
                ListItemViewHolder child = new ListItemViewHolder(view);
                return child;

        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.txtExerciseName.setText(item.exerciseName);
                itemController.txtDummy.setText(item.headerNo+"");
                if(item.lstImage.size()>0)
                {
                    Glide.with(context).load(item.lstImage.get(0))
                            .thumbnail(0.5f)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.ic_picture)
                            .error(R.drawable.ic_picture)
                            .into(itemController.imgExercise);
                }else
                {

                }
                /*if(item.RestUnitText!=null)
                {
                    itemController.txtReps.setText(item.RepGoal+" "+item.RestUnitText);
                }else {
                    itemController.txtReps.setText(item.RepGoal+" "+item.RepsUnitText);
                }*/

                if(item.flowId==5||item.flowId==6)
                {
                    itemController.txtReps.setText(item.RepGoal+" "+item.RepsUnitText);
                }
                else
                    itemController.txtReps.setText(item.RepGoal+" "+item.RepsUnitText+" X "+item.setCount+" sets");


              //  itemController.txtDummy.setText(item.headerNo+"");
                /*if (item.setCount > 0) {
                    itemController.txtReps.setText(item.setCount+"  Sets");
                }*//*
                if(item.restComment != null && !TextUtils.isEmpty(item.restComment)){
                    itemController.txtReps.setText(item.RestUnitText+"  "+item.restComment);
                }*/
               // itemController.txtIndex.setText(item.index);
                ////////Super Set////////////
                if(item.isSuperSet>=1)
                {
                    if(item.SuperSetPosition==-1){
                        itemController.imgBr.setImageResource(0);
                        itemController.imgBr.setImageResource(R.drawable.thired_first_half_pink);
                        itemController.txtSet.setVisibility(View.VISIBLE);
                       // itemController.viewTwo.setVisibility(View.GONE);
                        if(item.middle!=null && item.middle.equals("m"))
                        {
                            if(item.flowId==5||item.flowId==6)
                            {
                                Log.e("alpha set","11");
                                itemController.txtSet.setText("x "+context.getString(R.string.infinity)+" Sets");
                            }
                            else
                            {
                                Log.e("alpha not found","90");
                                itemController.txtSet.setText("x "+item.setCount+" Sets");
                            }

                        }
                         // itemController.txtSet.setText("x "+item.setCount+" Sets");
                    }
                    else if(item.SuperSetPosition==0)
                    {
                        itemController.imgBr.setImageResource(0);
                        itemController.imgBr.setImageResource(R.drawable.thired_middle_half_pink);
                        itemController.txtSet.setVisibility(View.VISIBLE);
                        //itemController.viewTwo.setVisibility(View.GONE);
                       /* if(item.middle!=null && item.middle.equals("m"))
                            itemController.txtSet.setText("x "+item.setCount+" Sets");*/
                        if(item.middle!=null && item.middle.equals("m"))
                        {
                            if(item.flowId==5||item.flowId==6)
                            {
                                Log.e("alpha set","11");
                                itemController.txtSet.setText("x "+context.getString(R.string.infinity)+" Sets");
                            }
                            else
                            {
                                Log.e("alpha not found","00");
                                itemController.txtSet.setText("x "+item.setCount+" Sets");
                            }

                        }
                           // itemController.txtSet.setText("x "+item.setCount+" Sets");
                    }
                    else if(item.SuperSetPosition==1)
                    {
                        itemController.imgBr.setImageResource(0);
                        itemController.imgBr.setImageResource(R.drawable.thired_last_half_pink);
                        itemController.txtSet.setVisibility(View.VISIBLE);
                       // itemController.viewTwo.setVisibility(View.GONE);
                       /* if(item.middle!=null && item.middle.equals("m"))
                            itemController.txtSet.setText("x "+item.setCount+" Sets");*/
                        if(item.middle!=null && item.middle.equals("m"))
                        {
                            if(item.flowId==5||item.flowId==6)
                            {
                                Log.e("alpha set","11");
                                itemController.txtSet.setText("x "+context.getString(R.string.infinity)+" Sets");
                            }
                            else {
                                Log.e("alpha not found","50");
                                itemController.txtSet.setText("x " + item.setCount + " Sets");
                            }
                        }
                    }else
                    {
                        itemController.imgBr.setVisibility(View.INVISIBLE);
                        itemController.txtSet.setVisibility(View.INVISIBLE);
                    }

                }else {
                    itemController.imgBr.setVisibility(View.INVISIBLE);
                    //itemController.txtSet.setVisibility(View.VISIBLE);
                    //itemController.txtSet.setText("x "+item.setCount+" Sets");
                }
                ////////Super Set////////////
               // Log.e("parent","123");
               /* itemController.llRoot.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
                        {
                            case MotionEvent.ACTION_DOWN:
                                shouldClick = false;
                                if(lstExercisePicker==null)
                                {
                                    getExerciseList(item.lstEquipment,item.lstSubstituteExercise,item.lstAltBodyWeightExercise);
                                }

                                else
                                    // showPicker(lstExercisePicker);
                                    openReplaceDialog();
                                break;
                            case MotionEvent.ACTION_UP:
                                if (shouldClick)
                                    view.performClick();
                                break;
                            case MotionEvent.ACTION_POINTER_DOWN:
                                break;
                            case MotionEvent.ACTION_POINTER_UP:
                                break;
                            case MotionEvent.ACTION_MOVE:
                                //Do your stuff
                                shouldClick = false;


                        }
                        itemController.llRoot.invalidate();
                        return true;
                    }
                });*/
                itemController.llRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("print video url change--",item.VideoPublicUrl+"--");
                        openDetailsDialog(item.VideoPublicUrl,item.lstEquipment,item.lstSubstituteExercise,item.lstAltBodyWeightExercise);
                      /*  if(videoGlobalView!=null)
                        {
                            videoGlobalView.stopPlayback();
                        }

                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                        }
                        else
                        {
                            if(preItem!=null)
                            {
                                closePreviousAccordian(preItem, itemControllerPre);
                            }
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren)
                            {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);

                            item.invisibleChildren = null;
                            /////////
                            isAlreadyOPen=true;
                            preItem=item;
                            itemControllerPre=itemController;
                            /////

                        }*/


                    }
                });
                /////////////////////////


                ////////////////////////
                break;
            case CHILD:
                /*TextView itemTextView = (TextView) holder.itemView;
                itemTextView.setText(data.get(position).text);
                break;*/
              //  Log.e("chld","123");
                final ListItemViewHolder childController = (ListItemViewHolder) holder;
                childController.refferalItem = item;
                videoGlobalView=childController.vidExercise;
              if(item.VideoPublicUrl==null||item.VideoPublicUrl.equals("")||!Util.chkIsUrl(item.VideoPublicUrl))
                    childController.rlRightBack.setVisibility(View.INVISIBLE);
                else
                    childController.rlRightBack.setVisibility(View.VISIBLE);

                try {
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
                 /*childController.txtEquipmentAns.setText((String)item.lstEquipment.get(0));
                if(item.lstEquipment.size()>1)
                 childController.txtEquipmentBasedAns.setText((String)item.lstEquipment.get(1));
                if(item.lstEquipment.size()>2)
                 childController.txtEquipmentWeightAns.setText((String)item.lstEquipment.get(2));*/
                /////////////////////
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
                        playVideo(item.VideoPublicUrl,childController.vidExercise);
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

                //loadAdapter(childController.rvCircuitList);
        }
    }

/*
    private void getExerciseList(List<String> lstEquipment, List<SessionOverViewModel.SubstituteExercise> lstSubstituteExercise, List<SessionOverViewModel.AltBodyWeightExercise> lstAltBodyWeightExercise) {
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
                    setEquipExercise(lstEquipment);
                    setBodyWeightAlt(lstAltBodyWeightExercise);
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
    }
*/

    private void setBodyWeightAlt(List<SessionOverViewModel.AltBodyWeightExercise> lstAltBodyWeightExercise) {
        for(int g=0;g<lstAltBodyWeightExercise.size();g++)
        {
            SubTitle allExerciseSubtitle=new SubTitle( lstAltBodyWeightExercise.get(g).getBodyWeightAltExerciseName(),lstAltBodyWeightExercise.get(g).getBodyWeightAltExerciseId(),"","","",null,null);
            _allAlt.add(allExerciseSubtitle);
        }
    }

    private void setEquipExercise(List<String> lstEquipment) {

        for(int g=0;g<lstEquipment.size();g++)
        {
            Log.e("Log","Loop here");
            SubTitle allExerciseSubtitle=new SubTitle(lstEquipment.get(g),g,"","","",null,null);
            _allEquip.add(allExerciseSubtitle);
        }
    }

    private void showIndividualVideo(Integer exerciseId) {

        final ProgressDialog progressDialog= ProgressDialog.show(context, "", "Please bbbbbb...");

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
                    final ImageView imgDemo=(ImageView)individualVideoDialog.findViewById(R.id.imgDemo);
                    final ImageView imgExercise=(ImageView)individualVideoDialog.findViewById(R.id.imgExercise);
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
                            vidExercise.pause();
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
                            //vidExercise.resume();
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


    public int getItemViewType(int position) {
        return data.get(position).type;
    }


    public int getItemCount() {
       // Log.e("print sizeeee",data.size()+"?");
        return data.size();
    }

    public static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIndex,txtExerciseName,txtReps,txtSet,txtDummy;
        public Item refferalItem;
        public RelativeLayout llRoot;
        public ImageView imgBr,imgExercise;
      //  public View viewOne,viewTwo;


        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            txtIndex = (TextView) itemView.findViewById(R.id.txtIndex);
            txtExerciseName = (TextView) itemView.findViewById(R.id.txtExerciseName);
            txtReps = (TextView) itemView.findViewById(R.id.txtReps);
            txtSet = (TextView) itemView.findViewById(R.id.txtSet);
            llRoot=(RelativeLayout)itemView.findViewById(R.id.llRoot);
            imgBr=(ImageView) itemView.findViewById(R.id.imgBr);
            //viewOne=(View)itemView.findViewById(R.id.viewTwo);
            //viewTwo=(View)itemView.findViewById(R.id.viewTwo);
           txtDummy=(TextView)itemView.findViewById(R.id.txtDummy);
            imgExercise=(ImageView)itemView.findViewById(R.id.imgExercise);
        }
    }
    public static class ListItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtEquipmentAns,txtEquipmentBasedAns,txtEquipmentWeightAns;
        public ImageView imgExercise;
        public Item refferalItem;
         public RecyclerView rvCircuitList;
        public VideoView vidExercise;
        public RelativeLayout rlLeftBack,rlRightBack,rlVideo;
        public LinearLayout llEquipment,llDynamicEquipment,llEquipmentAlt,llDynamicEquipmentAlt,llBodyWeight,llDynamicBodyWeight;

        public ListItemViewHolder(View itemView)
        {
            super(itemView);
            imgExercise = (ImageView) itemView.findViewById(R.id.imgExercise);
            txtEquipmentAns=(TextView)itemView.findViewById(R.id.txtEquipmentAns);
            txtEquipmentBasedAns=(TextView)itemView.findViewById(R.id.txtEquipmentBasedAns);
            txtEquipmentWeightAns=(TextView)itemView.findViewById(R.id.txtEquipmentWeightAns);
            vidExercise=(VideoView)itemView.findViewById(R.id.vidExercise);
            rlLeftBack=(RelativeLayout)itemView.findViewById(R.id.rlLeftBack);
            rlRightBack=(RelativeLayout)itemView.findViewById(R.id.rlRightBack);
            rlVideo=(RelativeLayout)itemView.findViewById(R.id.rlVideo);
            llEquipment=(LinearLayout)itemView.findViewById(R.id.llEquipment);
            llDynamicEquipment=(LinearLayout)itemView.findViewById(R.id.llDynamicEquipment);
            llEquipmentAlt=(LinearLayout)itemView.findViewById(R.id.llEquipmentAlt);
            llDynamicEquipmentAlt=(LinearLayout)itemView.findViewById(R.id.llDynamicEquipmentAlt);
            llBodyWeight=(LinearLayout)itemView.findViewById(R.id.llBodyWeight);
            llDynamicBodyWeight=(LinearLayout)itemView.findViewById(R.id.llDynamicBodyWeight);
        }
    }

    public static class Item {
        public int type;
        public String text,index,repsCount;
        public String exerciseName;
        public List<Item> invisibleChildren;
        public int id,sequenceNo;
        public int isSuperSet,SuperSetPosition,setCount;
        public String restComment="",circuitRepeat="";
        public List<String> lstEquipment;
        public List<Object> lstAltEquipment;
        public List<SessionOverViewModel.AltBodyWeightExercise> lstAltBodyWeightExercise;
        public List<String> lstImage;
        public String RepGoal,RepsUnitText,VideoUrl,VideoPublicUrl,RestUnitText;
        public String middle="";
        public List<SessionOverViewModel.SubstituteExercise> lstSubstituteExercise;
        public int headerNo=0;
        private int flowId=0;


        public Item(int header, String index, SessionOverViewModel.Circuit circuitExercises) {
            this.type = header;
            this.exerciseName = circuitExercises.getExerciseName();
            this.index=index;
           // this.headerNo=headerIndex;
            this.repsCount=circuitExercises.getRepsUnit()+"";
            this.isSuperSet=circuitExercises.getIsSuperSet();
            this.SuperSetPosition=circuitExercises.getSuperSetPosition();
            this.id=circuitExercises.getExerciseId();
            this.sequenceNo=circuitExercises.getSequenceNo();
            this.restComment=circuitExercises.getRestComment();
            lstEquipment=circuitExercises.getEquipments();
            lstImage=circuitExercises.getPhotoList();
            setCount=circuitExercises.getSetCount();
            lstAltEquipment=circuitExercises.getSubstituteEquipments();
            lstAltBodyWeightExercise=circuitExercises.getAltBodyWeightExercises();
            RepGoal=circuitExercises.getRepGoal();
            RepsUnitText=circuitExercises.getRepsUnitText();
            RestUnitText=circuitExercises.getRestUnitText();
            VideoUrl=(String)circuitExercises.getVideoUrl();
            VideoPublicUrl=circuitExercises.getVideoPublicUrl();
           // Log.e("print vid url",VideoPublicUrl+"?");
            lstSubstituteExercise=circuitExercises.getSubstituteExercises();
            this.middle=circuitExercises.getMiddle();
            this.flowId=flowId;
        }


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
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        if(fileName!=null)
        {
            vidExercise.setVisibility(View.VISIBLE);
            vidExercise.setVideoURI(Uri.parse(fileName));
            vidExercise.setMediaController(new MediaController(context));
            vidExercise.requestFocus();
            vidExercise.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //holder.progress.setVisibility(View.GONE);
                    //holder.imgFullScreen.setVisibility(View.VISIBLE);
                    vidExercise.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    //vidExercise.setBackgroundColor(Color.TRANSPARENT);
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
                    progressDialog.dismiss();
                    Toast.makeText(context,"Video cannot be played",Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }else {
            progressDialog.dismiss();
            Toast.makeText(context,"Video cannot be played",Toast.LENGTH_SHORT).show();
        }

        
        

    }

    private void playVideoForDialog(String fileName, final VideoView vidExercise, final ImageView imgDemo)
    {
// Do something else.
        if(fileName!=null)
        {
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


    private void openDetailsDialog(String videoPublicUrl, List<String> lstEquipment, List<SessionOverViewModel.SubstituteExercise> lstSubstituteExercise, List<SessionOverViewModel.AltBodyWeightExercise> lstAltBodyWeightExercise)
    {
        final Dialog dialog=new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_circuit_popup);
        ImageView imgCancel=(ImageView)dialog.findViewById(R.id.imgCancel);
        VideoView vidExercise=(VideoView )dialog.findViewById(R.id.vidExercise);
        LinearLayout llDynamicEquipment=(LinearLayout )dialog.findViewById(R.id.llDynamicEquipment);
        LinearLayout llDynamicEquipmentAlt=(LinearLayout )dialog.findViewById(R.id.llDynamicEquipmentAlt);
        LinearLayout llDynamicBodyWeight=(LinearLayout )dialog.findViewById(R.id.llDynamicBodyWeight);
        LinearLayout llEquipment=(LinearLayout )dialog.findViewById(R.id.llEquipment);
        LinearLayout llEquipmentAlt=(LinearLayout )dialog.findViewById(R.id.llEquipmentAlt);
        LinearLayout llBodyWeight=(LinearLayout )dialog.findViewById(R.id.llBodyWeight);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();




        ///////////////////////////
        playVideo(videoPublicUrl,vidExercise);
        try {
            if(lstEquipment!=null &&lstEquipment.size()>0)
            {
               llEquipment.setVisibility(View.VISIBLE);
               llDynamicEquipment.removeAllViews();
                for (int i=0;i<lstEquipment.size();i++)
                {
                    View dynamicEqpView=layoutInflater.inflate(R.layout.dynamic_tips_instruction_session,null);
                    TextView txtTipsInstructions=(TextView)dynamicEqpView.findViewById(R.id.txtTipsInstructions);
                    txtTipsInstructions.setTextColor(Color.parseColor("#FFFFFF"));
                    txtTipsInstructions.setText((String)lstEquipment.get(i));
                    llDynamicEquipment.addView(dynamicEqpView);
                }
            }else
            {
                llEquipment.setVisibility(View.GONE);
            }

            if(lstSubstituteExercise!=null && lstSubstituteExercise.size()>0)
            {
                llEquipmentAlt.setVisibility(View.VISIBLE);
                llDynamicEquipmentAlt.removeAllViews();
                for (int i=0;i<lstSubstituteExercise.size();i++)
                {
                    View dynamicEqpView=layoutInflater.inflate(R.layout.dynamic_tips_instruction_session,null);
                    TextView txtTipsInstructions=(TextView)dynamicEqpView.findViewById(R.id.txtTipsInstructions);

                    if(lstSubstituteExercise.get(i).getSubstituteExerciseId()!=null)
                    {
                        txtTipsInstructions.setTextColor(Color.parseColor("#FFFFFF"));
                        SpannableString content = new SpannableString(lstSubstituteExercise.get(i).getSubstituteExerciseName());
                        content.setSpan(new UnderlineSpan(), 0, lstSubstituteExercise.get(i).getSubstituteExerciseName().length(), 0);
                        //txtTipsInstructions.setText(content);
                        txtTipsInstructions.setText(lstSubstituteExercise.get(i).getSubstituteExerciseName());

                        final int finalI = i;
                        txtTipsInstructions.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showIndividualVideo(lstSubstituteExercise.get(finalI).getSubstituteExerciseId());
                            }
                        });

                    }else
                    {
                        txtTipsInstructions.setText((String)lstSubstituteExercise.get(i).getSubstituteExerciseName());
                    }
                    llDynamicEquipmentAlt.addView(dynamicEqpView);
                }
            }else
            {
                llEquipmentAlt.setVisibility(View.GONE);
            }
            if(lstAltBodyWeightExercise!=null && lstAltBodyWeightExercise.size()>0)
            {
                llBodyWeight.setVisibility(View.VISIBLE);
               llDynamicBodyWeight.removeAllViews();
                for (int i=0;i<lstAltBodyWeightExercise.size();i++)
                {
                    View dynamicEqpView=layoutInflater.inflate(R.layout.dynamic_tips_instruction_session,null);
                    TextView txtTipsInstructions=(TextView)dynamicEqpView.findViewById(R.id.txtTipsInstructions);

                    if(lstAltBodyWeightExercise.get(i).getBodyWeightAltExerciseId()!=null)
                    {
                        txtTipsInstructions.setTextColor(Color.parseColor("#FFFFFF"));
                        SpannableString content = new SpannableString(lstAltBodyWeightExercise.get(i).getBodyWeightAltExerciseName());
                        content.setSpan(new UnderlineSpan(), 0, lstAltBodyWeightExercise.get(i).getBodyWeightAltExerciseName().length(), 0);
                        //txtTipsInstructions.setText(content);
                        txtTipsInstructions.setText(lstAltBodyWeightExercise.get(i).getBodyWeightAltExerciseName());
                        final int finalI = i;
                        txtTipsInstructions.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showIndividualVideo(lstAltBodyWeightExercise.get(finalI).getBodyWeightAltExerciseId());
                            }
                        });
                    }else
                    {
                        txtTipsInstructions.setText(lstAltBodyWeightExercise.get(i).getBodyWeightAltExerciseName());
                    }
                    llDynamicBodyWeight.addView(dynamicEqpView);
                }
            }else
            {
                llBodyWeight.setVisibility(View.GONE);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        /////////////////////


    }
    //////////////////////////////////////////
    ///////////////////Replace Exercise////////////
/*
    public void getExerciseList(List<SessionOverViewModel.Circuit> lstCircuitExercise, int i) {

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
                    setEquipExercise(lstCircuitExercise,i);
                    setBodyWeightAlt(lstCircuitExercise,i);
                    openReplaceDialog();
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
*/
    private void setBodyWeightAlt(List<SessionOverViewModel.Circuit> lstCircuitExercise, int i) {
        for(int g=0;g<lstCircuitExercise.get(i).getAltBodyWeightExercises().size();g++)
        {
            SubTitle allExerciseSubtitle=new SubTitle( lstExercisePicker.get(g).getExerciseName(),lstExercisePicker.get(g).getExerciseId(),"","","",null,null);
            _allAlt.add(allExerciseSubtitle);
        }
    }

    private void setEquipExercise(List<SessionOverViewModel.Circuit> lstCircuitExercise, int i) {
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
            SubTitle allExerciseSubtitle=new SubTitle( lstExercisePicker.get(g).getExerciseName(),lstExercisePicker.get(g).getExerciseId(),"","","",null,null);
            _allExercise.add(allExerciseSubtitle);
        }
    }

/*
    private void openReplaceDialog()
    {
        List list = getList();
        Dialog replaceDialog=new Dialog(context,R.style.DialogTheme);
        replaceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        replaceDialog.setContentView(R.layout.dialog_replace_exercise);
        RecyclerView rvExercise=(RecyclerView )replaceDialog.findViewById(R.id.rvExercise);
        RelativeLayout rlHeader=(RelativeLayout )replaceDialog.findViewById(R.id.rlHeader);
        ImageView imgBack=(ImageView )replaceDialog.findViewById(R.id.imgBack);
        rlHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceDialog.dismiss();
            }
        });
       */
/* imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceDialog.dismiss();
            }
        });*//*

        rvExercise.setLayoutManager(new LinearLayoutManager(context));
      */
/*  ReplaceExerciseListAdapter replaceExerciseListAdapter=new ReplaceExerciseListAdapter(list,context, _allTargetUpper, _allTargetLower, _allTargetCore);
        rvExercise.setAdapter(replaceExerciseListAdapter);
        replaceDialog.show();*//*




    }
*/
/*
    private List getList() {
        List <Title>list = new ArrayList<>();
        for (int i = 0; i < strCategory.length; i++) {
            List subTitles = new ArrayList<>();
            if(i==0)
            {
                */
/*if(_album!=null)
                for (int j = 0; j< _album.size(); j++){
                    SubTitle subTitle = new SubTitle(_album.get(j),0);
                    subTitles.add(subTitle);
                }*//*

                Log.e("equip size",_allEquip.size()+"?");
                subTitles=_allEquip;
            }
            else if(i==1)
            {
              */
/*  if(_artist!=null)
                for (int j = 0; j< _artist.size(); j++){
                    SubTitle subTitle = new SubTitle(_artist.get(j),0);
                    subTitles.add(subTitle);
                }*//*

                Log.e("alt size",_allEquip.size()+"?");
                subTitles=_allAlt;
            }
            else if(i==2)
            {
              */
/*  if(_genres!=null)
                for (int j = 0; j< _genres.size(); j++){
                    SubTitle subTitle = new SubTitle(_genres.get(j),0);
                    subTitles.add(subTitle);
                }*//*

                Log.e("sub size",_allExercise+"?");
                subTitles=_allExercise;
            }
            else if(i==3)
            {
                */
/*if(_playlist!=null)
                for (int j = 0; j< _playlist.size(); j++){
                    SubTitle subTitle = new SubTitle(_playlist.get(j),0);
                    subTitles.add(subTitle);
                }*//*

                subTitles=_allStandard;
            }
            else if(i==4)
            {
                */
/*if(_playlist!=null)
                for (int j = 0; j< _playlist.size(); j++){
                    SubTitle subTitle = new SubTitle(_playlist.get(j),0);
                    subTitles.add(subTitle);
                }*//*

                subTitles=_allExercise;
                Log.e("total size",_allExercise+"?");
            }


            CaseMap.Title model = new CaseMap.Title(strCategory[i],subTitles);
            list.add(model);
        }
        return list;
    }
*/
    ///////////////////Replace Exercise////////////
    /////////////////////////////////////////

}
