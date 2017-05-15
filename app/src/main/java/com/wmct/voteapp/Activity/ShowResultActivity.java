package com.wmct.voteapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.wmct.voteapp.R;
import com.wmct.voteapp.base.BaseActivity;
import com.wmct.voteapp.utils.CheckUtil;
import com.wmct.voteapp.utils.JsonToBean;
import com.wmct.voteapp.utils.StringUtil;
import com.wmct.voteapp.utils.ToastUtil;
import com.wmct.voteapp.voteNet.Request;
import com.wmct.voteapp.voteNet.voteAPI.VoteNetApi;

import org.json.JSONObject;


/**
 * Created by wmct on 2017/5/13.
 */

public class ShowResultActivity extends BaseActivity implements View.OnClickListener {

     private  Button bnt_exit_system;
     private  Button bnt_query_result;
     private  Button btn_closeroom_test;
     private  String roomNum;
    @Override
    protected void initView() {
        roomNum = getIntent().getStringExtra("roomNum");
        setContentView(R.layout.query_result);
        bnt_exit_system = (Button) findViewById(R.id.bnt_exit_system);
        bnt_query_result = (Button) findViewById(R.id.bnt_query_result);
        bnt_exit_system.setOnClickListener(this);
        bnt_query_result.setOnClickListener(this);
        btn_closeroom_test = (Button) findViewById(R.id.btn_test);
        btn_closeroom_test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bnt_exit_system:
                ActivityCollector.finishAll();
                break;
            case R.id.bnt_query_result:
                showResult();
                break;
            //房间关闭测试
            case R.id.btn_test:
                endVoting();
                break;
        }
    }

    /**
     * 展示评选结果
     */
    private void showResult() {

    if(CheckUtil.isNetworkConnected(this)){

          VoteNetApi.resultCheck(roomNum, new Request.RequestListener<JSONObject>() {
        @Override
        public void onComplete(int stCode, JSONObject response, String errMsg) {

            if (JsonToBean.isOK(response)) {
                Log.e(TAG,"response"+response.toString());

                jumpToResult();

            } else {
                Log.e(TAG,"r11111esponse"+response.toString());
                ToastUtil.showToastShort(ShowResultActivity.this, StringUtil.ResultENChangeToCH(JsonToBean.getMsg(response)));
            }

        }


    });
    }else {
         AlertDialog.Builder dialog = new AlertDialog.Builder(ShowResultActivity.this);

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

    /**
     * 跳转至投票结果显示界面
     */
    private void jumpToResult() {
        Intent intent = new Intent();
        intent.putExtra("roomNum", roomNum);
        intent.setClass(this, VoteResPagerActivity.class);
        startActivity(intent);
    }

    /**
     * 结束投票
     */
    private void endVoting() {

        if(CheckUtil.isNetworkConnected(this)){
            VoteNetApi.voteEnd(roomNum, roomNum, new Request.RequestListener<JSONObject>() {
                @Override
                public void onComplete(int stCode, JSONObject response, String errMsg) {
                    if (JsonToBean.isOK(response)) {
                        //  ToastUtil.showToastShort(HostActivity.this, R.string.end_success);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(ShowResultActivity.this);
                        dialog.setTitle("系统消息");
                        dialog.setMessage(R.string.end_success);
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ShowResultActivity.this);

                        dialog.setTitle("系统消息");
                        dialog.setMessage(StringUtil.ENChangeToCH(JsonToBean.getMsg(response)));
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                        // ToastUtil.showToastShort(HostActivity.this,StringUtil.ENChangeToCH(JsonToBean.getMsg(response)));
                    }
                }
            });
        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ShowResultActivity.this);

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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                System.out.println("按下了back键 onKeyDown()");
                return false;
            }else {
                return super.onKeyDown(keyCode, event);
            }
    }
}
