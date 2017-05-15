package com.wmct.voteapp.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcz on 2017/5/10.
 */

public class VoteItem implements Serializable{
    private int titleseq;//题目序号
    private String title;//选项题目
    private int num;//选项数目
    private int maxnum;//最大可选
    private int minnum;//最小可选
    private List<String> option = new ArrayList<String>();//每个选项

    public int getTitleSeq() {
        return titleseq;
    }

    public void setTitleSeq(int titleSeq) {
        this.titleseq = titleSeq;
    }

    public int getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(int maxnum) {
        this.maxnum = maxnum;
    }

    public int getMinnum() {
        return minnum;
    }

    public void setMinnum(int minnum) {
        this.minnum = minnum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "题目序号: "+titleseq + "题目: "+title + "选项数: "+num + "多选: "+maxnum +"单选: " + minnum + "选项: " + option;
    }
}