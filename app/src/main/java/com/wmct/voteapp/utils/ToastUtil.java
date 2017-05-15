package com.wmct.voteapp.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by yimowunai on 2016/1/7.
 */
public class ToastUtil {
    private static Toast toast = null;
    private static int LENGTH_LONG = Toast.LENGTH_LONG;
    private static int LENGTH_SHORT = Toast.LENGTH_SHORT;

    /**
     * 普通文本消息提示
     *
     * @param context
     * @param text
     */
    public static void showToastShort(Context context, CharSequence text) {
        //创建一个Toast提示消息
        toast = Toast.makeText(context, text, LENGTH_SHORT);
        //设置Toast提示消息在屏幕上的位置
        toast.setGravity(Gravity.CENTER, 0, 0);
        //显示消息
        toast.show();
    }
    public static void showToastShort(Context context, int resourceId) {
        //创建一个Toast提示消息
        toast = Toast.makeText(context, resourceId, LENGTH_SHORT);
        //设置Toast提示消息在屏幕上的位置
        toast.setGravity(Gravity.CENTER, 0, 0);
        //显示消息
        toast.show();
    }
    public static void showToastLong(Context context, CharSequence text) {
        //创建一个Toast提示消息
        toast = Toast.makeText(context, text, LENGTH_SHORT);
        //设置Toast提示消息在屏幕上的位置
        toast.setGravity(Gravity.CENTER, 0, 0);
        //显示消息
        toast.show();
    }
    public static void showToastLong(Context context, int resourceId) {
        //创建一个Toast提示消息
        toast = Toast.makeText(context, resourceId, LENGTH_SHORT);
        //设置Toast提示消息在屏幕上的位置
        toast.setGravity(Gravity.CENTER, 0, 0);
        //显示消息
        toast.show();
    }
}
