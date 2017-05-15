package com.wmct.voteapp.voteNet;

import android.os.Handler;
import android.os.Looper;

/**
 * ---------------------------------------
 * 版权 ：山大信息学院
 * <p/>
 * 作者 ：三月半
 * <p/>
 * 时间 ：2016/1/1 11:27
 * <p/>
 * --------------------------------------
 */
public class ResponseDelivery {
    private Handler mResponseHandler = new Handler(Looper.getMainLooper());

    public void deliveryResponse(final Request<?> request, final Response response) {
        Runnable responseRunnable = new Runnable() {
            @Override
            public void run() {
                request.deliveryResponse(response);
            }
        };
        mResponseHandler.post(responseRunnable);
    }
}
