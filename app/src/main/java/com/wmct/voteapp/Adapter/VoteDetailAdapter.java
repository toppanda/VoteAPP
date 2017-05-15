package com.wmct.voteapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.wmct.voteapp.R;
import com.wmct.voteapp.beans.VoteItem;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wmct on 2017/5/10.
 */
public class VoteDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "VoteDetailAdapter";
    private static final int UNKNOWN_TYPE = -1;
    private static final int SINGLE_SELECT_TYPE = 1;
    private static final int MULTI_SELECT_TYPE = 2;//多选题目模式
    private static final int TEXT_SELECT_TYPE = 3;//选择题+文本
    private List<VoteItem> mDatas;//每个选择题的题目、选项等
    private Context mContext;
    private Map<String,Integer> mHashMap;
    private Map<String,Set<Integer>> mHashMapSet;


    public VoteDetailAdapter(List<VoteItem> subjectList, Context context){
        mContext = context;
        mDatas = subjectList;
    }
    public VoteDetailAdapter(List<VoteItem> subjectList, Context context,Map<String,Integer> map){
        mContext = context;
        mDatas = subjectList;
        mHashMap = map;
    }
    public VoteDetailAdapter(List<VoteItem> subjectList, Context context,Map<String,Integer> map,Map<String,Set<Integer>> mapSet){
        mContext = context;
        mDatas = subjectList;
        mHashMap = map;
        mHashMapSet = mapSet;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        //Log.e(TAG,viewType+"test");
        if (viewType == VoteDetailAdapter.SINGLE_SELECT_TYPE) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.recycler_single_select_item, parent, false);
            return new SingleSelectViewHolder(itemView);
        } else if (viewType == VoteDetailAdapter.MULTI_SELECT_TYPE) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.recycler_multi_select_item, parent, false);
            return new MultiSelectViewHolder(itemView);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final VoteItem voteItem = mDatas.get(position);
        if(voteItem == null){
            return;
        }
        if (holder instanceof SingleSelectViewHolder) {
            SingleSelectViewHolder singleSelectViewHolder = (SingleSelectViewHolder) holder;
            String title = voteItem.getTitle();
            String titleseq =voteItem.getTitleSeq()+"";
            singleSelectViewHolder.title.setText(titleseq+"."+title);

            int number = voteItem.getNum();
            List<String> options = voteItem.getOption();
            int tempNumber = voteItem.getOption().size();

            singleSelectViewHolder.radioGroup.removeAllViews();//
            if(number == tempNumber){
                int checkRadioButtonId = 0 ;
                if(mHashMap.containsKey(mDatas.get(position).getTitleSeq()+"")){
                    checkRadioButtonId = mHashMap.get(mDatas.get(position).getTitleSeq()+"");
                }
                for(int i = 0;i<number;i++){
                    //需要修改样式   字体大小、显示风格
                    RadioButton radioButton = new RadioButton(mContext);
                    radioButton.setText(options.get(i));
                    radioButton.setId(i);
                    radioButton.setTextSize(25.0f);
                    radioButton.setTextColor(Color.DKGRAY);
                    if(i==checkRadioButtonId-1){
                        radioButton.setChecked(true);
                    }
                    singleSelectViewHolder.radioGroup.addView(radioButton);
                }
                //singleSelectViewHolder.radioGroup.clearCheck();
                singleSelectViewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

                    public void onCheckedChanged(RadioGroup group, int checkedId){

                        mHashMap.put(mDatas.get(position).getTitleSeq()+"",checkedId+1);//保存选中状态
                    }
                });
            }

        }else if(holder instanceof MultiSelectViewHolder){

            MultiSelectViewHolder multiSelectViewHolder = (MultiSelectViewHolder)holder;
            String title = voteItem.getTitle();

            String titleseq =voteItem.getTitleSeq()+"";
            String tipsTemp = "";
            if(voteItem.getMinnum()==voteItem.getMaxnum()){
                tipsTemp = "(限选"+voteItem.getMinnum()+"项)";
            }else{
                tipsTemp = "(请至少选"+voteItem.getMinnum()+"项，"+"至多选"+voteItem.getMaxnum()+"项)";
            }

            multiSelectViewHolder.title.setText(titleseq+"."+title+"。"+tipsTemp);
            int number = voteItem.getNum();
            List<String> options = voteItem.getOption();
            int tempNumber = voteItem.getOption().size();
            multiSelectViewHolder.linearLayout.removeAllViews();

            final Set<Integer> set;
           // set = mHashMapSet.get(voteItem.getTitleSeq());
            Log.e(TAG,"检测containskey中"+voteItem.getTitleSeq());
            Log.e(TAG,"mHashMapSet的大小"+mHashMapSet.size());
           // Log.e(TAG,"voteItem.getTitleSeq()对应的选项set大小 = "+mHashMapSet.get(voteItem.getTitleSeq()).size());
            for(String strTemp : mHashMapSet.keySet()){
                Log.e(TAG,"mHashMapSet 中包含的key值 "+ strTemp);
            }
            if(mHashMapSet.containsKey(voteItem.getTitleSeq()+"")){

                Log.e(TAG,"存在。。。。。");
                set = mHashMapSet.get(voteItem.getTitleSeq()+"");
            }else {
                Log.e(TAG,"不存在。。。。。"+mHashMapSet.containsKey(voteItem.getTitleSeq()));
                set = new HashSet<>();
            }
           // final Set<Integer> set = set;
            // Log.e(TAG,"mHashMapSet的大小"+mHashMapSet.size());
          //  Log.e(TAG,"voteItem.getTitleSeq()对应的选项set = "+mHashMapSet.get(voteItem.getTitleSeq()));
            Log.e(TAG,"这个是多选选项set集合size= "+set.size());
            if(number == tempNumber){
                for(int i = 0;i<number;i++){
                    //需要修改样式   字体大小、显示风格  添加监听

                    CheckBox checkBox = new CheckBox(mContext);
                    checkBox.setText(options.get(i));
                    checkBox.setId(i+1);
                    checkBox.setTextSize(25.0f);//复选框文本大小
                    checkBox.setTextColor(Color.DKGRAY);


                    if(set.contains(i+1)){
                        checkBox.setChecked(true);
                    }
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                           if(isChecked){
                                set.add(buttonView.getId());//
                                Log.e(TAG,"isChecked的选择了哪个"+set.size());//可以显示选了哪个
                               for(Integer str : set){
                                   Log.e(TAG+1,"set = "+str);
                               }
                                mHashMapSet.put(voteItem.getTitleSeq()+"",set);
                            }else {
                                if(set.contains(buttonView.getId())){
                                    set.remove(buttonView.getId());
                                    mHashMapSet.put(voteItem.getTitleSeq()+"",set);
                                }else {
                                    mHashMapSet.put(voteItem.getTitleSeq()+"",set);
                                }
                            }
                        }
                    }
                    );
                    multiSelectViewHolder.linearLayout.addView(checkBox);
                }
            }
        }
    }
    @Override
    public int getItemCount() {
        //Log.e(TAG, "size = "+mDatas.size());
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas == null) return UNKNOWN_TYPE;
        VoteItem voteItem = mDatas.get(position);
        if (voteItem == null) return UNKNOWN_TYPE;
        if(voteItem.getMinnum()==1&& 1 == voteItem.getMaxnum()){
            return SINGLE_SELECT_TYPE;
        }else if(voteItem.getMinnum()>1&&voteItem.getMinnum() == voteItem.getMaxnum()||voteItem.getMinnum() < voteItem.getMaxnum()){
            return MULTI_SELECT_TYPE;
        }
        return UNKNOWN_TYPE;
    }

    // 与model数据关联，只读数据，不做增删
    public void setAllData(List<VoteItem> subjectList) {
        mDatas = subjectList;
        notifyDataSetChanged();
    }
    public void getChooseResult(){

    }

    static class SingleSelectViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final RadioGroup radioGroup;

        public SingleSelectViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.single_select_title);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);
        }
    }

    static class MultiSelectViewHolder extends RecyclerView.ViewHolder {

        final TextView title;
        final LinearLayout linearLayout;
        public MultiSelectViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.multi_select_title);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout);
        }
    }




}

