package com.wmct.voteapp.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pcz on 2017/5/10.
 */

public class VoteResItem implements Serializable {
    private int titleseq;//题目序号
    private String title;//题目内容
    private List<VoteResOption> option;//投票结果

    public List<VoteResOption> getOption() {
        return option;
    }

    public String getTitle() {
        return title;
    }

    public int getTitleseq() {
        return titleseq;
    }

    public void setOption(List<VoteResOption> option) {
        this.option = option;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleseq(int titleseq) {
        this.titleseq = titleseq;
    }

    @Override
    public String toString() {
        return "题目序号" + titleseq + " 题目详情" + title + " 投票选项：" + option;
    }
}
