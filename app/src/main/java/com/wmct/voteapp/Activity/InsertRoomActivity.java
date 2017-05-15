package com.wmct.voteapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.wmct.voteapp.R;
import com.wmct.voteapp.base.BaseActivity;
import com.wmct.voteapp.utils.CheckUtil;
import com.wmct.voteapp.utils.StringUtil;
import com.wmct.voteapp.voteNet.Request;
import com.wmct.voteapp.utils.JsonToBean;
import com.wmct.voteapp.utils.ToastUtil;
import com.wmct.voteapp.voteNet.voteAPI.VoteNetApi;
import org.json.JSONObject;

/**
 * Created by wmct on 2017/5/10.
 */
public class InsertRoomActivity extends BaseActivity implements View.OnClickListener {

    EditText et_insertRoomNum;
    Button   bt_next_step;//确定输入房间号
    String   roomNum;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_insert_room);
        ActivityCollector.addActivity(this);
        getSupportActionBar().setTitle("即将进入投票界面");
        et_insertRoomNum = (EditText)findViewById(R.id.et_insert_num);
        bt_next_step = (Button)findViewById(R.id.btn_start_voting);
        bt_next_step.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_voting:
                next();
                break;
        }
    }
    private void next() {
        if(CheckUtil.isNetworkConnected(this)){
            roomNum = et_insertRoomNum.getText().toString().trim();
            if (roomNum.isEmpty()) {
                ToastUtil.showToastLong(InsertRoomActivity.this, "请您输入房间号");
            }else {
                VoteNetApi.visitorLogin(roomNum, new Request.RequestListener<JSONObject>() {
                    @Override
                    public void onComplete(int stCode, JSONObject response, String errMsg) {
                        if (JsonToBean.isOK(response)) {
                            Intent intent = new Intent(InsertRoomActivity.this, VoteDetailActivity.class);
                            intent.putExtra("roomNum",roomNum);
                            startActivity(intent);
                        } else {
                            ToastUtil.showToastShort(InsertRoomActivity.this, StringUtil.VoterENChangeToCH(JsonToBean.getMsg(response)));
                        }
                    }
                });
            }
        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(InsertRoomActivity.this);

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

    protected void onDestory(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /*private void initView(){
        et_insertRoomNum = (EditText)findViewById(R.id.insert_num);
        bt_next_step = (Button)findViewById(R.id.ok_button);
        bt_next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
                roomNum = et_insertRoomNum.getText().toString().trim();
                //将RoomNum发送给后台，后台返回给我一个isOK_code
                Log.i(TAG, "这里是输入房间号" + roomNum);
                if (roomNum == null) {
                    ToastUtil.showToastLong(InsertRoomActivity.this, "请您输入房间号");
                } else {
                    //跳转至投票界面
                    //需要判断是否能连接到

                    VoteNetApi.visitorLogin(roomNum, new Request.RequestListener<JSONObject>() {
                        @Override
                        public void onComplete(int stCode, JSONObject response, String errMsg) {
                            if (response != null) {

                                Log.i("TEST!", "Login_result----------->" + response);
                            } else {
                                //Log.i("TEST!", "Login_result----------->null");
                            }
//                            Log.e("TEST!", "Login_result----------->" + response);
                            if (JsontoBean.isOK(response)) {

                                Intent intent = new Intent(InsertRoomActivity.this, VoteDetailActivity.class);
                                startActivity(intent);
                            } else {

                                ToastUtil.showToastLong(InsertRoomActivity.this, "您输的房间号有误");
                            }
                        }
                    });
                }
            }
        });
        }*/

}
