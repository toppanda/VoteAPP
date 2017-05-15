package com.wmct.voteapp.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.wmct.voteapp.beans.VoteRes;
import com.wmct.voteapp.beans.VoteSubTheme;
import com.wmct.voteapp.beans.VoteTicket;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pcz on 2017/5/10.
 */

public class JsonToBean {

    private static final String TAG = "JsonToBean";

    /**
     * 检测状态码
     */
    public static boolean isOK(JSONObject jsonObject) {
        int status = 0;
        try {
            status = jsonObject.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status == 1;
    }

    /**
     * 获得连接
     */
    public static String getUrl(JSONObject jsonObject) {
        String url = "";
        try {
            url = jsonObject.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 获得状态信息
     */
    public static String getMsg(JSONObject jsonObject) {
        String msg = "";
        try {
            msg = jsonObject.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 解析投票主题
     *
     * @param jsonObject 返回的任何有投票主题的json
     */
    public static String toVoteName(JSONObject jsonObject) {

        try {
            JSONObject object = jsonObject.getJSONObject("result");
            String name = object.getString("name");

            Log.i(TAG, "当前投票主题: " + name);
            return name;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(TAG, "解析投票主题信息出错");
            return null;
        }


    }

    /**
     * 解析投票项目信息
     *
     * @param jsonString 返回的投票页面jsonstring
     */

    public static VoteTicket toVoteTicket(String jsonString) {
        VoteTicket voteTicket = JSON.parseObject(jsonString, VoteTicket.class);

        Log.i(TAG + " 选票信息", voteTicket.toString());


        return voteTicket;
    }
//    public static List<VoteItem> toVoteList(JSONObject jsonObject){
//
//        List<VoteItem> itemsList = new ArrayList<VoteItem>();
//        try {
//            JSONObject object = jsonObject.getJSONObject("result");
//            JSONArray array = object.getJSONArray("content");
//            for (int i = 0; i < array.length(); i++){
//
//                JSONObject itemobject = array.getJSONObject(i);
//                VoteItem voteItem = new VoteItem();
//                String title = itemobject.getString("title");
//                int num = itemobject.getInt("num");
//                int maxnum = itemobject.getInt("maxnum");
//                int minnum = itemobject.getInt("minnum");
//
//                JSONArray optionArray = itemobject.getJSONArray("option");
//                String[] option = new String[6];
//                for (int j = 0; j < optionArray.length(); j++){
//                   option[j] = optionArray.getString(j);
//                }
//
//                Log.i(TAG,"解析题目信息" + "当前条目:"+i+title +"  对应选项"+ Arrays.toString(option));
//
//                voteItem.setTitle(title);
//                voteItem.setNum(num);
//                voteItem.setMaxnum(maxnum);
//                voteItem.setMinnum(minnum);
//                voteItem.setOption(option);
//                itemsList.add(voteItem);
//
//            }
//
//            return itemsList;
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.i(TAG,"解析题目信息出错");
//            return null;
//        }
//    }

    /**
     * 解析投票结果项目信息
     *
     * @param jsonString 返回的结果JSONSTRING
     */
    public static VoteRes toVoteReslut(String jsonString) {
        VoteRes voteRes = JSON.parseObject(jsonString, VoteRes.class);

        Log.i(TAG + " 投票结果", voteRes.toString());

        return voteRes;
    }
//    public static List<VoteResItem> toVoteReslut(JSONObject jsonObject){
//
//        List<VoteResItem> reslutList = new ArrayList<VoteResItem>();
//        try {
//            VoteResItem reslut = new VoteResItem();
//            JSONObject object = jsonObject.getJSONObject("result");
//            JSONArray array = object.getJSONArray("content");
//            for (int i = 0; i < array.length(); i++){
//                JSONObject itemObject = array.getJSONObject(i);
//                String title = itemObject.getString("title");
//                String options = itemObject.getString("options");
//                int res = itemObject.getInt("res");
//
//                reslut.setTitle(title);
//                reslut.setOptopns(options);
//                reslut.setRes(res);
//
//                Log.i(TAG,"解析题目信息" + " 当前条目:"+title +" 对应选项：" + options +" 该选项票数：" + res);
//                reslutList.add(reslut);
//            }
//
//            return reslutList;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.i(TAG,"解析结果信息出错");
//            return null;
//        }
//    }

    /**
     * 打包选票内容 返回
     */
    public static String toVoteSubJson(VoteSubTheme voteSubTheme) {

        String jsonString = JSON.toJSONString(voteSubTheme);

        Log.i(TAG + "打包选票内容", jsonString);

        return jsonString;
    }

}
