package com.wmct.voteapp.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by pcz on 2017/5/13.
 */

public class XVoteAxisValueFormatter implements IAxisValueFormatter {

    private static final String TAG  = "DayAxisValueFormatter";
    private List<Integer> labels;

    /**
     * @param labels 要显示的标签字符数组
     */
    public XVoteAxisValueFormatter(List<Integer> labels) {
        this.labels = labels;
    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return labels.get((int) value).toString();
    }
}
