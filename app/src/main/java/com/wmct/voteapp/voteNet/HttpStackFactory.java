package com.wmct.voteapp.voteNet;

import android.os.Build;

/**
 * Created by pcz on 2017/5/10.
 */
public class HttpStackFactory {
    private static final int ANDROID_NET_DEVIDE = 9;

    public static HttpStack createHttpStack(){
        int sdk_api = Build.VERSION.SDK_INT;
        if(sdk_api>ANDROID_NET_DEVIDE){
            return new HttpURLConnectionStack();
        }
        return new HttpClientStack();

    }
}
