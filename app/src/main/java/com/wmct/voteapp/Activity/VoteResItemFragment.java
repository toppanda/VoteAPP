package com.wmct.voteapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wmct.voteapp.R;
import com.wmct.voteapp.beans.VoteResItem;
import com.wmct.voteapp.beans.VoteResOption;
import com.wmct.voteapp.utils.VoteAxisValueFormatter;
import com.wmct.voteapp.utils.XVoteAxisValueFormatter;
import com.wmct.voteapp.utils.XYMarkerView;
import com.wmct.voteapp.utils.YVoteAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pcz on 2017/5/11.
 */

public class VoteResItemFragment extends Fragment {

    private static final String TAG = "VoteResItemFragment";
    private static final String ARG_VOTE_ITEM = "vote_item";
    /**
     * 每道题目的详情      @res @reslut @content
     */

    private VoteResItem mResItem;

    /**
     * 每道题目的详情      @content @titleseq
     */
    private String resSeq;//用来存放每道题号

    /**
     * 用来存选项类       @content @optoin
     */

    private List<VoteResOption> options;
    /**
     * 用来存选项名        @content @optoin @options
     */
    private List<String> resName;

    /**
     * 用来存选票数       @content @optoin @res
     */
    private List<Integer> resCount;

    /**
     * 用来存选项序号     @content @optoin @optionseq
     */
    private List<Integer> resOptionSeq;

    private ScrollView mScrollView;
    private TextView mResTileText;//用于显示题目
    private TextView mResOPtionsText;//用于显示题目选项
    private TextView mResCountText;//用于显示题目得票
    private BarChart mBarChart;//用于显示数据

    public static VoteResItemFragment newInstance(VoteResItem voteResItem) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_VOTE_ITEM, voteResItem);
        VoteResItemFragment voteResItemFragment = new VoteResItemFragment();
        voteResItemFragment.setArguments(args);
        return voteResItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResItem = (VoteResItem) getArguments().getSerializable(ARG_VOTE_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_voteres, container, false);

        //取得窗口高度
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int widthPixels = metric.widthPixels;


        mResTileText = (TextView) view.findViewById(R.id.text_resiem_title);
        mResOPtionsText = (TextView) view.findViewById(R.id.votefragment_text_resiem_options);
        mResCountText = (TextView) view.findViewById(R.id.votefragment_text_resiem_res);
        mBarChart = (BarChart) view.findViewById(R.id.barchart_voteitem);
        mScrollView = (ScrollView) view.findViewById(R.id.votefragment_scrollView);

        options = mResItem.getOption();
        resName = new ArrayList<String>();
        resCount = new ArrayList<Integer>();
        resOptionSeq = new ArrayList<Integer>();

        for (int i = 0; i < options.size(); i++) {

            //取出选项名并设置到界面
            String sN = options.get(i).getOptions();
            resName.add(options.get(i).getOptions());
            if (i == options.size()-1){
                mResOPtionsText.append(i+1 + ". " + sN);
            }else {
                mResOPtionsText.append(i+1 + ". " + sN + "\n");
            }
            Log.i(TAG, "resNAME" + resName);

            //取出选项票数
            String sR = Integer.toString(options.get(i).getRes());
            resCount.add(options.get(i).getRes());
            if (i == options.size()-1){
                mResCountText.append("得票：" + sR);
            }else {
                mResCountText.append("得票：" + sR + "\n");
            }
            Log.i(TAG, "resRes" + resCount);

        }

        //取出选项序号
        for (int i = 0; i < options.size(); i++) {
            resOptionSeq.add(options.get(i).getOptionseq());
        }

        mResTileText.setText(mResItem.getTitleseq()+ "." + mResItem.getTitle());

        ViewGroup.LayoutParams params = mScrollView.getLayoutParams();
        if (resCount.size() > 6){
            params.height = widthPixels/3;
        }else {
            params.height = ViewPager.LayoutParams.WRAP_CONTENT;
        }

        initBarChart();

        return view;
    }

    //初始化柱状图
    private void initBarChart() {

        mBarChart.setDrawBarShadow(false);//设置绘画阴影
        mBarChart.setDrawValueAboveBar(true);//true文字写在bar上
        mBarChart.getDescription().setEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);//false只能单轴缩放
        mBarChart.setDrawGridBackground(false);

        //x轴
        IAxisValueFormatter xAxisValueFormatter = new XVoteAxisValueFormatter(resOptionSeq);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisValueFormatter);

        //左侧y轴
        IAxisValueFormatter customAxis = new YVoteAxisValueFormatter();
        YAxis leftAxis = mBarChart.getAxisLeft();
        if (Collections.max(resCount) < 10){
            leftAxis.setLabelCount(Collections.max(resCount)+1, false);
        }else {
            leftAxis.setLabelCount(5,false);
        }
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setValueFormatter(customAxis);

        //右侧y轴
        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        if (Collections.max(resCount) < 10){
            rightAxis.setLabelCount(Collections.max(resCount)+1, false);
        }else {
            rightAxis.setLabelCount(5,false);
        }
        rightAxis.setValueFormatter(customAxis);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f);

        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);


        XYMarkerView mv = new XYMarkerView(this.getActivity(), resName);
        mv.setChartView(mBarChart); // For bounds control
        mBarChart.setMarker(mv); // Set the marker to the chart

        mBarChart.animateY(1000);
        setData(options.size());//        BarDataSet set1;
    }

    //设置柱状图参数
    private void setData(int count) {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(i, resCount.get(i)));
        }


        BarDataSet set1 = new BarDataSet(yVals1, "Vote Count");

        set1.setColors(ColorTemplate.MATERIAL_COLORS);
        VoteAxisValueFormatter f = new VoteAxisValueFormatter();
        set1.setValueFormatter(f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(25f);
        data.setBarWidth(0.75f);

        mBarChart.setData(data);
//        }
    }
}
