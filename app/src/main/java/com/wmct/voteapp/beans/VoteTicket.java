package com.wmct.voteapp.beans;

/**
 * Created by pcz on 2017/5/11.
 */

public class VoteTicket {
    String status;//请求状态码
    String msg;//请求状态描述
    VoteTheme result;//投票主题与详细内容

    public VoteTheme getVoteThemes() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResult(VoteTheme voteThemes) {
        result = voteThemes;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "状态码与状态描述: " + status + ":" + msg + "主题与内容: " + result.toString();
    }
}
