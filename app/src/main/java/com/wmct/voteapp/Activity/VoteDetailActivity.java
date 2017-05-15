package com.wmct.voteapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wmct.voteapp.Adapter.VoteDetailAdapter;
import com.wmct.voteapp.R;
import com.wmct.voteapp.base.BaseActivity;
import com.wmct.voteapp.beans.VoteItem;
import com.wmct.voteapp.beans.VoteSubItem;
import com.wmct.voteapp.beans.VoteSubTheme;
import com.wmct.voteapp.beans.VoteTheme;
import com.wmct.voteapp.beans.VoteTicket;
import com.wmct.voteapp.utils.CheckUtil;
import com.wmct.voteapp.voteNet.Request;
import com.wmct.voteapp.utils.JsonToBean;
import com.wmct.voteapp.utils.ToastUtil;
import com.wmct.voteapp.voteNet.voteAPI.VoteNetApi;

import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class VoteDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "VoteDetailActivity";
    private String roomNum;
    private String voteName;

    private RecyclerView recyclerView;

    private Button btn_vote;

    private TextView tx_voteName;

    List<VoteItem> subjects;

    HashMap<String, Integer> map;
    HashMap<String, Set<Integer>> mapSet;

    VoteTicket vt;

    @Override
    protected void initView() {
        setContentView(R.layout.votepage);
        getSupportActionBar().setTitle("投票界面");

        recyclerView = (RecyclerView) findViewById(R.id.rv_vote_item);//recyclerview,votepage
        btn_vote = (Button) findViewById(R.id.btn_vote);
        tx_voteName = (TextView) findViewById(R.id.tv_subject);
        roomNum = getIntent().getStringExtra("roomNum");

        btn_vote.setOnClickListener(this);
        initVariable();
        showVotePage();

    }

    private void initVariable() {
        map = new HashMap<>();
        mapSet = new HashMap<>();
    }

    /**
     * 展示投票内容
     */
    private void showVotePage() {

        VoteNetApi.requestTheme(roomNum, new Request.RequestListener<JSONObject>() {
            @Override
            public void onComplete(int stCode, JSONObject response, String errMsg) {
                //voteName = JsonToBean.toVoteName(response);
                VoteTicket vt = JsonToBean.toVoteTicket(response.toString());
                VoteTheme voteTheme = vt.getVoteThemes();
                subjects = vt.getVoteThemes().getContent();
                voteName = voteTheme.getName();
                tx_voteName.setText(voteName);

                VoteDetailAdapter adapter = new VoteDetailAdapter(subjects, VoteDetailActivity.this, map, mapSet);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VoteDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_vote:
                submitResult();
                break;

        }
    }

    /**
     * 提交投票结果
     */
    private void submitResult() {

//        final VoteTheme[] votetheme = {new VoteTheme()};
        //模拟数据   需要从后台获取实际的数据。
        /*if(roomNum==-1){
            Intent intent = new Intent(this,InsertRoomActivity.class);
            startActivity(intent);
        }else{}*/
        boolean needBreakTwice = false;
        boolean canSendResult = true;
        Log.i(TAG, "ip------->>>" + getLocalIpAddress());
        VoteSubTheme voteSubTheme = new VoteSubTheme();
        voteSubTheme.setRoomnum(roomNum);
        voteSubTheme.setVoteip(getLocalIpAddress());
        List<VoteSubItem> content = new ArrayList<VoteSubItem>();
        if (subjects.size() != map.size() + mapSet.size()) {

            ToastUtil.showToastLong(VoteDetailActivity.this, "题目有未完成的哦！");
            canSendResult = false;
        }
        //处理单选结果   不需要做前端选项控制处理
        Set<String> set1 = map.keySet();
        for (String str : set1) {

               /*     Iterator<VoteItem> itemIterator = subjects.iterator();
                    while (itemIterator.hasNext()){
                        VoteItem item = itemIterator.next();
                        if( str.equals(item.getTitleSeq()+"")){
                            VoteSubItem voteSubItem = new VoteSubItem();
                            voteSubItem.setTitle(item.getTitle());
                            voteSubItem.setTitleseq(item.getTitleSeq()+"");
                            List<String> option = new ArrayList<String>();
                            option.add(map.get(str)+"");
                            voteSubItem.setOption(option);
                            content.add(voteSubItem);
                            itemIterator.remove();
                        }

                    }*/
            for (VoteItem item : subjects) {
                if (str.equals(item.getTitleSeq() + "")) {
                    VoteSubItem voteSubItem = new VoteSubItem();
                    voteSubItem.setTitle(item.getTitle());
                    voteSubItem.setTitleseq(item.getTitleSeq() + "");
                    List<String> option = new ArrayList<String>();
                    option.add(map.get(str) + "");
                    voteSubItem.setOption(option);
                    content.add(voteSubItem);
                }
            }
            Log.e(TAG + 1, "map key = " + str + "map value = " + map.get(str));
        }
              /*
                if(subjects.size()>0){
                    canSendResult = false;
                    List<Integer> list = new ArrayList<Integer>();
                    for(VoteItem item :subjects){
                        list.add(item.getTitleSeq());
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("第");
                    for(Integer integer : list){
                        stringBuilder.append(integer+"、");
                    }
                    stringBuilder.append("单选题未完成哦！");
                    ToastUtil.showToastLong(VoteDetailActivity.this,stringBuilder.toString());
                }*/
        //处理多选的投票结果    需要对选择结果进行判断

        Set<String> set2 = mapSet.keySet();
        for (String str : set2) {
            for (VoteItem item : subjects) {
                if (str.equals(item.getTitleSeq() + "")) {
                    VoteSubItem voteSubItem = new VoteSubItem();
                    voteSubItem.setTitle(item.getTitle());
                    voteSubItem.setTitleseq(item.getTitleSeq() + "");
                    List<String> option = new ArrayList<String>();
                    if (item.getMinnum() == item.getMaxnum() && item.getMinnum() > 1) {
                        if (mapSet.get(str).size() != item.getMinnum()) {
                            //提示处理
                            String tips = "第" + item.getTitleSeq() + "题限选" + item.getMinnum() + "个选项";
                            ToastUtil.showToastLong(VoteDetailActivity.this, tips);
                            recyclerView.scrollToPosition(item.getTitleSeq() - 1);
                            needBreakTwice = true;
                            canSendResult = false;
                            break;
                        }
                    } else {
                        if (mapSet.get(str).size() > item.getMaxnum() || mapSet.get(str).size() < item.getMinnum()) {
                            String tips = "第" + item.getTitleSeq() + "题最少选" + item.getMinnum() + "个选项，最多选" + item.getMaxnum() + "个选项";
                            ToastUtil.showToastLong(VoteDetailActivity.this, tips);
                            recyclerView.scrollToPosition(item.getTitleSeq() - 1);
                            needBreakTwice = true;
                            canSendResult = false;
                            break;
                        }
                    }
                    for (Integer integer : mapSet.get(str)) {
                        option.add(integer + "");
                    }
                    voteSubItem.setOption(option);
                    content.add(voteSubItem);
                }
            }
            if (needBreakTwice) {
                break;
            }
                  /*  Set<Integer> set = mapSet.get(str);
                    for(Integer integer : set){
                        Log.e(TAG+1,"map value = "+integer);
                    }*/
        }
        if (canSendResult) {

            voteSubTheme.setContent(content);
            Log.e(TAG, "voteSubTheme = " + voteSubTheme.toString());
            String voteResultString = JsonToBean.toVoteSubJson(voteSubTheme);

            if(CheckUtil.isNetworkConnected(this)){

                VoteNetApi.voteSubmit(voteResultString, new Request.RequestListener<String>() {
                    @Override
                    public void onComplete(int stCode, String response, String errMsg) {

                        Log.e(TAG, response);
                        if (response.contains("Success")) {
                            Log.i(TAG, "response.contains1");
                            AlertDialog.Builder dialog_submit = new AlertDialog.Builder(VoteDetailActivity.this);
                            dialog_submit.setTitle("系统信息");
                            dialog_submit.setMessage("您已投票成功!");
                            dialog_submit.setCancelable(false);//设置为false，按返回键不能退出
                            dialog_submit.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.putExtra("roomNum",roomNum);
                                    intent.setClass(VoteDetailActivity.this, ShowResultActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                            //ToastUtil.showToastLong(VoteDetailActivity.this,"投票成功！");
                        } else if (response.contains("IpPermissionError")) {
                            AlertDialog.Builder dialog_fail = new AlertDialog.Builder(VoteDetailActivity.this);
                            dialog_fail.setTitle("投票失败");
                            dialog_fail.setMessage("您现在使用的IP被禁止投票！");
                            dialog_fail.setCancelable(false);//设置为false，按返回键不能退出
                            dialog_fail.setPositiveButton("返回投票界面", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                            //   ToastUtil.showToastLong(VoteDetailActivity.this,"投票失败！");
                        } else if (response.contains("RoomHasNotOpenedError")) {

                            AlertDialog.Builder dialog_no_start = new AlertDialog.Builder(VoteDetailActivity.this);
                            dialog_no_start.setTitle("投票失败");
                            dialog_no_start.setMessage("本次投票尚未开始！");
                            dialog_no_start.setCancelable(false);//设置为false，按返回键不能退出
                            dialog_no_start.setPositiveButton("返回投票界面", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                        } else if (response.contains("RoomHasFinishedError")) {

                            AlertDialog.Builder dialog_no_start = new AlertDialog.Builder(VoteDetailActivity.this);
                            dialog_no_start.setTitle("投票失败");
                            dialog_no_start.setMessage("本次投票已经结束！");
                            dialog_no_start.setCancelable(false);//设置为false，按返回键不能退出
                            dialog_no_start.setPositiveButton("返回投票界面", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                        } else if (response.contains("DBUpdateError")) {

                            AlertDialog.Builder dialog_other_reason = new AlertDialog.Builder(VoteDetailActivity.this);
                            dialog_other_reason.setTitle("投票失败");
                            dialog_other_reason.setMessage("数据库更新失败！");
                            dialog_other_reason.setCancelable(false);//设置为false，按返回键不能退出
                            dialog_other_reason.setPositiveButton("返回投票界面", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                        }
                    }
                });

            }else {

                AlertDialog.Builder dialog = new AlertDialog.Builder(VoteDetailActivity.this);

                dialog.setTitle("系统消息");
                dialog.setMessage(R.string.network_not_connected);
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }


        }

               /* Set<Map.Entry<String,Integer>> entrySet = map.entrySet();
                for(Map.Entry<String,Integer> entry : entrySet){
                    Log.e(TAG+2,"map key = "+entry.getKey()+"map value = "+entry.getValue());
                }*/


     /*   showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"ip------->>>"+getLocalIpAddress());

            }
        });*/
    }



        /*int singleNum = new Random().nextInt(10)+1;
        int multiNum = new Random().nextInt(10)+1+singleNum;
        Log.e(TAG,"singleNum = "+singleNum+"......"+"multiNum = "+multiNum);
        //List<VoteItem> subjects = new ArrayList<>();
        for(int i=1;i<=singleNum;i++){
            VoteItem voteItem = new VoteItem();
            voteItem.setMinnum(1);
            voteItem.setMaxnum(1);
            voteItem.setTitleSeq(i);
            voteItem.setTitle("single test "+i);
//            voteItem.setNum(votetheme[0].getTitlenum()); //voteItem.setNum(new Random().nextInt(10)+1);
            List<String> list = new ArrayList<>();
            for(int j=0;j<voteItem.getNum();j++){
                list.add("single test "+j);
            }
            voteItem.setOption(list);
            subjects.add(voteItem);
        }
        for(int i=singleNum+1;i<=multiNum;i++){
            VoteItem voteItem = new VoteItem();
            voteItem.setMinnum(1);
            voteItem.setTitleSeq(i);
            voteItem.setTitle("multi test "+i);
            voteItem.setNum(new Random().nextInt(5)+2);
            voteItem.setMaxnum(2);
            List<String> list = new ArrayList<>();
            for(int j=0;j<voteItem.getNum();j++){
                list.add("multi test "+j);
            }
            voteItem.setOption(list);
            subjects.add(voteItem);
        }*/

       /* for(VoteItem item : subjects){
            Set<Integer> set = new HashSet<>();
            set.add(-1);
            mapSet.put(item.getTitleSeq()+"",set);
        }*/

    /**
     * 获取本地ip
     *
     * @return
     */
    private String getLocalIpAddress() {
        try {
            String ipv4 = null;
            List<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());//所有网络接口
            for (NetworkInterface ni : nilist) {
                List<InetAddress> ialist = Collections.list(ni.getInetAddresses());//接口下绑定的所有ip
                for (InetAddress address : ialist) {
                    ipv4 = address.getHostAddress();
                    if (!address.isLoopbackAddress() && (address instanceof Inet4Address))

                    {
                        return ipv4;
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return "0.0.0.0";
    }
}