package com.wmct.voteapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.wmct.voteapp.Activity.ActivityCollector;

public abstract class BaseActivity extends AppCompatActivity{
     public final String TAG = this.getClass().getSimpleName();

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
         //设置全屏
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
         ActivityCollector.addActivity(this);
         initView();
     }
     protected abstract void initView();
     protected void openActivity(Class mClass){
         Intent intent = new Intent();
         intent.setClass(this,mClass);
         startActivity(intent);
     }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
