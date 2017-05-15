package com.wmct.voteapp.beans;

/**
 * Created by pcz on 2017/5/11.
 */

public class VoteRes {
    String status;//请求状态码
    String msg;//请求状态描述
    VoteResTheme result;//投票主题与内容

    public String getMsg() {
        return msg;
    }

    public VoteResTheme getVoteThemes() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResult(VoteResTheme voteTheme) {
        result = voteTheme;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "状态码与状态描述" + status + ":" + msg + result;
    }
}
