package com.wmct.voteapp.beans;

import java.util.List;

/**
 * Created by pcz on 2017/5/11.
 */

public class VoteResTheme {

    String name;//投票主题描述
    List<VoteResItem> content;//每条题目的结果

    public List<VoteResItem> getResItems() {
        return content;
    }

    public String getName() {
        return name;
    }

     //在这里命名的时候
    public void setContent(List<VoteResItem> resItems) {
        content = resItems;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "投票主题：" + name + "题目结果: " + content;
    }
}
