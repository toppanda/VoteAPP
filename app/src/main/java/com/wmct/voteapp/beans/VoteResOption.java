package com.wmct.voteapp.beans;

import java.io.Serializable;

/**
 * Created by pcz on 2017/5/11.
 */

public class VoteResOption implements Serializable{

    private int optionseq;//选项序号
    private String options;//选项内容
    private int res;//该选项得票数

    public String getOptions() {
        return options;
    }

    public int getOptionseq() {
        return optionseq;
    }

    public int getRes() {
        return res;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setOptionseq(int optionseq) {
        this.optionseq = optionseq;
    }

    public void setRes(int res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return " 选项序号: " + optionseq + " 选项名：" + options + "票数" + res;
    }
}
