package com.wmct.voteapp.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by  on 2017/5/12.
 * 主持人界面，可以输入房间号、密码后进行开启投票、结束投票
 */

public class HostActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_roomNumber, et_password;
    private Button btn_startVoting, btn_endVoting;
    private String roomNumber, password;

    protected void initView() {
        setContentView(R.layout.activity_host);
        getSupportActionBar().setTitle("主持人界面");
        et_roomNumber = (EditText) findViewById(R.id.et_input_roomnumber);
        et_password = (EditText) findViewById(R.id.et_input_password);
        btn_startVoting = (Button) findViewById(R.id.btn_start_voting);
        btn_endVoting = (Button) findViewById(R.id.btn_end_voting);
        btn_startVoting.setOnClickListener(this);
        btn_endVoting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_voting:
                startVoting();
                break;
            case R.id.btn_end_voting:
                endVoting();
                break;
        }
    }
    /**
     * 开启投票
     */
    private void startVoting() {
        if (checkInput()) {
            VoteNetApi.hostLogin(roomNumber, password, new Request.RequestListener<JSONObject>() {
                @Override
                public void onComplete(int stCode, JSONObject response, String errMsg) {


                    if (JsonToBean.isOK(response)) {
                        Log.e(TAG,"response"+response.toString());
                        //  ToastUtil.showToastShort(HostActivity.this, R.string.start_success);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(HostActivity.this);
                        dialog.setTitle("系统消息");
                        dialog.setMessage(R.string.start_success);
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                    } else {
                        //ToastUtil.showToastShort(HostActivity.this, StringUtil.ENChangeToCH(JsonToBean.getMsg(response)));
                        AlertDialog.Builder dialog = new AlertDialog.Builder(HostActivity.this);
                        dialog.setTitle("系统消息");
                        dialog.setMessage(StringUtil.ENChangeToCH(JsonToBean.getMsg(response)));
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }
            });
        }
    }

    /**
     * 结束投票
     */
    private void endVoting() {
        if (checkInput()) {
            VoteNetApi.voteEnd(roomNumber, password, new Request.RequestListener<JSONObject>() {
                @Override
                public void onComplete(int stCode, JSONObject response, String errMsg) {
                    if (JsonToBean.isOK(response)) {
                        //  ToastUtil.showToastShort(HostActivity.this, R.string.end_success);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(HostActivity.this);
                        dialog.setTitle("系统消息");
                        dialog.setMessage(R.string.end_success);
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(HostActivity.this);

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
        }
    }

    /**
     * 检测输入格式
     * @return 输入不为空且网络完好时返回
     */
    private boolean checkInput() {
        roomNumber = et_roomNumber.getText().toString();
        Log.i(TAG, "roomNumber:" + roomNumber);
        if(TextUtils.isEmpty(roomNumber)){
            //ToastUtil.showToastShort(HostActivity.this, R.string.input_roomnumber);

            AlertDialog.Builder dialog = new AlertDialog.Builder(HostActivity.this);

            dialog.setTitle("系统消息");
            dialog.setMessage(R.string.input_roomnumber);
            dialog.setCancelable(false);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();

            return false;
        }
        password = et_password.getText().toString();
        Log.i(TAG, "password:" + password);
        if(TextUtils.isEmpty(password)||!CheckUtil.isNum(password)){


            AlertDialog.Builder dialog = new AlertDialog.Builder(HostActivity.this);

            dialog.setTitle("系统消息");
            dialog.setMessage(R.string.input_password);
            dialog.setCancelable(false);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            //  ToastUtil.showToastShort(HostActivity.this, R.string.input_password);
            return false;
        }
        if(!CheckUtil.isNetworkConnected(this)){

            AlertDialog.Builder dialog = new AlertDialog.Builder(HostActivity.this);

            dialog.setTitle("系统消息");
            dialog.setMessage(R.string.network_not_connected);
            dialog.setCancelable(false);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            // ToastUtil.showToastShort(HostActivity.this, R.string.network_not_connected);
            return false;
        }
        return true;
    }
}
