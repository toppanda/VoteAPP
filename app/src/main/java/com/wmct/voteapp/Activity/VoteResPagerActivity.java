package com.wmct.voteapp.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wmct.voteapp.R;
import com.wmct.voteapp.base.BaseActivity;
import com.wmct.voteapp.beans.VoteRes;
import com.wmct.voteapp.beans.VoteResItem;
import com.wmct.voteapp.utils.CheckUtil;
import com.wmct.voteapp.utils.JsonToBean;
import com.wmct.voteapp.voteNet.Request;
import com.wmct.voteapp.voteNet.voteAPI.VoteNetApi;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcz on 2017/5/11.
 */

public class VoteResPagerActivity extends BaseActivity {

    private static final String TAG= "VoteResPagerActivity";

    private String roomnumber;
    private VoteRes mVoteRes;
    private Intent mIntent;

    private TextView mTitleText;
    private ViewPager mViewPager;
    private MagicIndicator mIndicator;
    CommonNavigator mCommonNavigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate(@Nullable Bundle savedInstanceState)");
        setContentView(R.layout.activity_voteres_pager);
        initView();

    }

    @Override
    protected void initView() {

        getSupportActionBar().setTitle(R.string.vote_activity_resName);

        mIntent = getIntent();
        roomnumber = mIntent.getStringExtra("roomNum");
        Log.i(TAG, "ROOM NUM" + roomnumber);

        mTitleText = (TextView) findViewById(R.id.activity_vote_pager_subject_text);
        mIndicator = (MagicIndicator) findViewById(R.id.activity_vote_pager_view_pager_indicator);
        mCommonNavigator = new CommonNavigator(VoteResPagerActivity.this);

        final List<VoteResItem>[] mVoteResItems = new List[]{new ArrayList<VoteResItem>()};




        if(CheckUtil.isNetworkConnected(this)){
            //请求数据
            VoteNetApi.resultCheck(roomnumber, new Request.RequestListener<JSONObject>() {
                @Override
                public void onComplete(int stCode, JSONObject response, String errMsg) {

                    Log.i(TAG, "jsonObject_result----------->" + response);
                    String resJsonString = response.toString();
//              resJsonString = "{\"status\":\"1\",\"msg\":\"Success\",\"result\":{\"name\":\"wmct\\u9009\\u4e3e\",\"content\":[{\"titleseq\":1,\"title\":\"\\u5927\\u5e08\\u5144\",\"option\":[{\"optionseq\":1,\"options\":\"\\u8881\\u4e1c\\u98ce\",\"res\":\"3\"},{\"optionseq\":2,\"options\":\"\\u67d0\\u4eba\",\"res\":\"1\"},{\"optionseq\":3,\"options\":\"\\u67d0\\u67d0\\u67d0\",\"res\":\"4\"}]},{\"titleseq\":2,\"title\":\"\\u9996\\u5e2d\\u79d1\\u5b66\\u5bb6\",\"option\":[{\"optionseq\":1,\"options\":\"\\u8881\\u4e1c\\u98ce\",\"res\":\"3\"},{\"optionseq\":2,\"options\":\"\\u67d0\\u4eba\",\"res\":\"1\"}]}]}}";

                    mVoteRes = JsonToBean.toVoteReslut(resJsonString);

                    mTitleText.setText("当前主题：" + mVoteRes.getVoteThemes().getName());

                    mVoteResItems[0] = mVoteRes.getVoteThemes().getResItems();

                    mViewPager = (ViewPager) findViewById(R.id.activity_vote_pager_view_pager);

                    mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
                        @Override
                        public int getCount() {
                            return mVoteResItems[0] == null ? 0 :mVoteResItems[0].size();
                        }

                        @Override
                        public IPagerTitleView getTitleView(Context context, final int i) {
                            final ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);

                            clipPagerTitleView.setText(i+1 + "." + mVoteResItems[0].get(i).getTitle());
//                        clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                            clipPagerTitleView.setTextColor(Color.parseColor("#616161"));
                            clipPagerTitleView.setClipColor(Color.parseColor("#212121"));
                            clipPagerTitleView.setTextSize(23f);
                            clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mViewPager.setCurrentItem(i);
                                }
                            });

                            return clipPagerTitleView;
                        }

                        @Override
                        public IPagerIndicator getIndicator(Context context) {


                            LinePagerIndicator indicator = new LinePagerIndicator(context);
                            indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                            indicator.setColors(Color.parseColor("#1976d2"));
//                        indicator.setMode();
                            return indicator;
                        }

                    });

                    mIndicator.setNavigator(mCommonNavigator);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentPagerAdapter adapter = new FragmentPagerAdapter(fragmentManager) {
                        @Override
                        public Fragment getItem(int position) {
                            VoteResItem voteResItem = mVoteResItems[0].get(position);
                            Log.i(TAG, voteResItem.toString());
                            return VoteResItemFragment.newInstance(voteResItem);
                        }

                        @Override
                        public int getCount() {
                            return mVoteResItems[0] == null ? 0 :mVoteResItems[0].size();
                        }


                    };

                    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            mIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
                        }

                        @Override
                        public void onPageSelected(int position) {
                            mIndicator.onPageSelected(position);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                            mIndicator.onPageScrollStateChanged(state);
                        }
                    });

                    mViewPager.setAdapter(adapter);
                }
            });
        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(VoteResPagerActivity.this);

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
    protected void onPause() {
        super.onPause();
    }
}
