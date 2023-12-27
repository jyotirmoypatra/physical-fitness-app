package com.ashysystem.mbhq.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.fragment.test.HappinessQuestionFragment;
import com.ashysystem.mbhq.model.ContentItemHappinessQuestion;
import com.ashysystem.mbhq.model.GetCohenModel;
import com.ashysystem.mbhq.model.HappinessModel;
import com.ashysystem.mbhq.model.HappinessQuestionModel;
import com.ashysystem.mbhq.model.HeaderHappinessQuestionModel;
import com.ashysystem.mbhq.model.TempModelHappinessSave;

import com.ashysystem.mbhq.util.SharedPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AQB Solutions PVT. LTD. on 24/9/18.
 */
public class HappinessQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //Header header;
    List<HappinessModel> list;
    private String result="";
    private Context context;
    private RelativeLayout previousLayout;
    private TextView previousText;
    private HashMap<Integer,Integer> hmArrVAl=new HashMap<>();
    HappinessQuestionFragment parentFragment;
    List<GetCohenModel.QuestionnaireHappinessAnswerList> lstResponseData;
    private ArrayList<Integer> arrSelectedQuestionId=new ArrayList<>();
    private ArrayList<Integer> arrSelectedAnswerId=new ArrayList<>();
    ArrayList<Integer> arrRowNo=new ArrayList<>();

    public HappinessQuestionAdapter(List<HappinessModel> lists, Context context, List<GetCohenModel.QuestionnaireHappinessAnswerList> lstResponseData, HappinessQuestionFragment parentFragment)
    {
        this.list = lists;
        this.context=context;
        this.parentFragment=parentFragment;
        this.lstResponseData=lstResponseData;
        if(lstResponseData!=null)
        {
            for(int x=0;x<lstResponseData.size();x++)
            {
                arrSelectedQuestionId.add(lstResponseData.get(x).getQuestionnaireHappinessMasterId());
                arrSelectedAnswerId.add(lstResponseData.get(x).getValue());
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_happiness_question, parent, false);
            return  new VHHeader(v);
        }
        else
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_happiness_question, parent, false);
            return new VHItem(v);
        }
        // return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)      {

        if(holder instanceof VHHeader)
        {
            // VHHeader VHheader = (VHHeader)holder;
            //Header currentItem = (Header) list.get(position);
            HappinessModel tmp = list.get(position);
            HeaderHappinessQuestionModel currentItem = (HeaderHappinessQuestionModel) list.get(position);
            VHHeader VHheader = (VHHeader)holder;
            VHheader.headerTxt.setText(currentItem.getHeader());


        }
        else if(holder instanceof VHItem) {
            //  ModelToDoList currentItem =  list.get(position);
            ;
            ContentItemHappinessQuestion currentItem = (ContentItemHappinessQuestion) list.get(position);
            VHItem VHitem = (VHItem) holder;

            if(currentItem.getHappinessQuestionModel().isSelected())
            {
                VHitem.rlText.setBackground(context.getResources().getDrawable(R.drawable.bg_green_question));
                VHitem.txtQuestion.setTextColor(Color.parseColor("#FFFFFF"));
                arrRowNo.add(position);
                hmArrVAl.put(currentItem.getHappinessQuestionModel().getId(),currentItem.getHappinessQuestionModel().getValue());
            }
            else
            {
                VHitem.rlText.setBackgroundColor(Color.parseColor("#FFFFFF"));
                //VHitem.txtQuestion.setTextColor(Color.parseColor("#f214a9"));
                VHitem.txtQuestion.setTextColor(Color.parseColor("#32cdb8"));
            }


            VHitem.txtRank.setText(currentItem.getHappinessQuestionModel().getQuestion().substring(0,1));
            VHitem.txtQuestion.setText(currentItem.getHappinessQuestionModel().getQuestion().substring(2).toUpperCase());
            VHitem.llBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hmArrVAl.put(currentItem.getHappinessQuestionModel().getId(),currentItem.getHappinessQuestionModel().getValue());
                    if(!arrRowNo.contains(position))
                      arrRowNo.add(position);

                    //clearSelection();
                    clearMyChoice();
                    //clearSelectedItem();
                    makeSelection();
                    notifyItemChanged(position);
                    funCalculate(currentItem.getHappinessQuestionModel());
                    //notifyDataSetChanged();
                }
            });

        }



        //////////////////////////


    }

    private void clearMyChoice() {
        for(int x=0;x<arrRowNo.size();x++)
        {

            ContentItemHappinessQuestion currentItem = (ContentItemHappinessQuestion) list.get(arrRowNo.get(x));
            currentItem.getHappinessQuestionModel().setSelected(false);
            notifyItemChanged(arrRowNo.get(x));
        }
    }

    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {

        return list.get(position) instanceof HeaderHappinessQuestionModel;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class VHHeader extends RecyclerView.ViewHolder{
        private TextView headerTxt;


        VHHeader(View itemView) {
            super(itemView);
            headerTxt = (TextView)itemView.findViewById(R.id.headerTxt);
        }
    }
    static class VHItem extends RecyclerView.ViewHolder{
        private TextView txtQuestion,txtRank;
        private LinearLayout llBlock;
        private RelativeLayout rlText;

        public VHItem(View itemView) {
            super(itemView);
            txtQuestion = (TextView)itemView.findViewById(R.id.txtQuestion);
            txtRank = (TextView)itemView.findViewById(R.id.txtRank);
            llBlock=(LinearLayout)itemView.findViewById(R.id.llBlock);
            rlText=(RelativeLayout)itemView.findViewById(R.id.rlText);
        }
    }

    private void funCalculate(HappinessQuestionModel happinessQuestionModel)
    {



        double sum=0.0000;
        // Log.e("size hashmap",hmArr.size()+"?");
        parentFragment.getArrRaheModel().clear();

        for(int key:hmArrVAl.keySet())
        {
            // Log.e("print key  val",key+"?"+hmArrVAl.get(key)+"?");
            sum+=hmArrVAl.get(key);

            TempModelHappinessSave tempModelHappinessSave=new TempModelHappinessSave();
            tempModelHappinessSave.setQuestionnaireAttemptId(parentFragment.attemptId);
            tempModelHappinessSave.setQuestionnaireHappinessMasterId(key);
            tempModelHappinessSave.setValue(hmArrVAl.get(key));
            SharedPreference sharedPreference=new SharedPreference(context);
            tempModelHappinessSave.setUserId(Integer.parseInt(sharedPreference.read("UserID", "")));
            //  happinessQuestionModel.setSelected(true);

            parentFragment.setArrRaheModel(tempModelHappinessSave);

        }
        // sum+=hmArr.size();
        // Log.e("print sum",sum+"?");
        double div=(sum/120.0000000);
        // Log.e("print div",div+"?");
        double percent=div*100;
        // Log.e("print percent",percent+"?");
        parentFragment.funCalculteScore(percent);
    }
    private void clearSelection()
    {
        for(int x=0;x<list.size();x++)
        {
            if(! (list.get(x) instanceof HeaderHappinessQuestionModel))
            {
                ContentItemHappinessQuestion currentItem = (ContentItemHappinessQuestion) list.get(x);
                currentItem.getHappinessQuestionModel().setSelected(false);

            }

        }
    }
    private void clearSelectedItem()
    {
        for(int x=0;x<list.size();x++)
        {
            if(! (list.get(x) instanceof HeaderHappinessQuestionModel))
            {
                ContentItemHappinessQuestion currentItem = (ContentItemHappinessQuestion) list.get(x);

                for(int key:hmArrVAl.keySet())
                {



                    if(currentItem.getHappinessQuestionModel().getId()==key&&currentItem.getHappinessQuestionModel().getValue()==hmArrVAl.get(key))
                    {
                        Log.e("print key  val",key+"?"+hmArrVAl.get(key)+"?");
                        currentItem.getHappinessQuestionModel().setSelected(false);
                        notifyItemChanged(hmArrVAl.get(key));
                    }



                }


            }

        }
    }
    private void makeSelection()
    {
        for(int x=0;x<list.size();x++)
        {
            if(! (list.get(x) instanceof HeaderHappinessQuestionModel))
            {
                ContentItemHappinessQuestion currentItem = (ContentItemHappinessQuestion) list.get(x);

                for(int key:hmArrVAl.keySet())
                {
                    //  Log.e("print key  val",key+"?"+hmArrVAl.get(key)+"?");


                    if(currentItem.getHappinessQuestionModel().getId()==key&&currentItem.getHappinessQuestionModel().getValue()==hmArrVAl.get(key))
                    {
                        currentItem.getHappinessQuestionModel().setSelected(true);
                       // notifyItemChanged(hmArrVAl.get(key));
                    }



                }


            }

        }
    }

    //////////////////////////

}