package com.wmct.voteapp.utils;

/**
 * Created by hp on 2017/5/12.
 */

import android.content.Context;
import android.widget.TextView;

import com.daasuu.bl.BubbleLayout;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.wmct.voteapp.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class XYMarkerView extends MarkerView {

    private BubbleLayout tvContent;
    private IAxisValueFormatter xAxisValueFormatter;
    private List<String> optons;
    private TextView mTextView;

    private DecimalFormat format;

    public XYMarkerView(Context context ,List<String> options) {
        super(context, R.layout.custom_maker_view);

        this.xAxisValueFormatter = xAxisValueFormatter;
        this.optons = options;
        tvContent = (BubbleLayout) findViewById(R.id.tvContent);
        mTextView = (TextView) findViewById(R.id.tvContentText);
        format = new DecimalFormat("#");
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        mTextView.setText(optons.get((int)e.getX())+ ", 票数: " + format.format(e.getY()));
        mTextView.setTextSize(20f);
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
