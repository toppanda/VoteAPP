package com.wmct.voteapp.beans;

import java.util.List;

/**
 * Created by pcz on 2017/5/10.
 */

public class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           VoteTheme {
    private String name;//投票主题
    private int titlenum;//题目数量
    private List<VoteItem> content;//题目列表

    public List<VoteItem> getContent() {
        return content;
    }

    public void setContent(List<VoteItem> content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTitlenum() {
        return titlenum;
    }

    public void setTitlenum(int titlenum) {
        this.titlenum = titlenum;
    }

    @Override
    public String toString() {
        return "主题"+name + "题目数" + titlenum + "详情" + content.toString();
    }
}


