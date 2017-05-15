package com.wmct.voteapp.utils;

/**
 * Created by hp on 2017/5/12.
 */

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class YVoteAxisValueFormatter implements IAxisValueFormatter
{

    private DecimalFormat mFormat;

    public YVoteAxisValueFormatter() {
        mFormat = new DecimalFormat("##0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + " ";
    }
}
