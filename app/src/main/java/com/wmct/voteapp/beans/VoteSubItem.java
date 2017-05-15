package com.wmct.voteapp.beans;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by pcz on 2017/5/11.
 */

public class VoteSubItem {
    @JSONField(ordinal = 1)
    String title;//题目
    @JSONField(ordinal = 2)
    String titleseq;//题目序号
    @JSONField(ordinal = 3)
    List<String> option;//投票结果


    public String getTitle() {
        return title;
    }

    public String getTitleseq() {
        return titleseq;
    }

    public List<String> getOption() {
        return option;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setTitleseq(String titleseq) {
        this.titleseq = titleseq;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "VoteSubItem{" +
                "title='" + title + '\'' +
                ", titleseq='" + titleseq + '\'' +
                ", option=" + option +
                '}';
    }
}
