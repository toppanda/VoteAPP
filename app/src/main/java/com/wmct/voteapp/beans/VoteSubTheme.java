package com.wmct.voteapp.beans;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcz on 2017/5/11.
 */

public class VoteSubTheme {
    @JSONField(ordinal = 1)
    String roomnum;//房间号
    @JSONField(ordinal = 2)
    String voteip;//用户ip
    @JSONField(ordinal = 3)
    List<VoteSubItem> content = new ArrayList<VoteSubItem>();



    public String getRoomnum() {
        return roomnum;
    }

    public String getVoteip() {
        return voteip;
    }

    public List<VoteSubItem> getContent() {
        return content;
    }


    public void setRoomnum(String roomnum) {
        this.roomnum = roomnum;
    }

    public void setVoteip(String voteip) {
        this.voteip = voteip;
    }

    public void setContent(List<VoteSubItem> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "VoteSubTheme{" +
                "roomnum='" + roomnum + '\'' +
                ", voteip='" + voteip + '\'' +
                ", content=" + content +
                '}';
    }
}
