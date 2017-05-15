package com.wmct.voteapp.voteNet;

import android.util.Log;

import java.util.concurrent.BlockingQueue;

/**
 * Created by pcz on 2017/5/10.
 *
 * 核心类+从网络请求队列中循环读取请求并执行
 */

public class NetworkExecutor extends Thread {

    private static final String TAG = "NetworkExecutor";

    /**
    * 网络请求队列
     */
    private BlockingQueue<Request<?>> mRequestQueue;

    /**
     * 网络请求栈
     */
    private HttpStack mHttpStack;

    /**
     * 请求缓存？*/
    //TODO
    /**
    *是否停止 */
    private boolean isStop = false;
    /**
     * 结果分发器，结果投递到主线程
     * */
    private static ResponseDelivery mResponseDelivery = new ResponseDelivery();

    public NetworkExecutor(BlockingQueue<Request<?>> queue, HttpStack httpStack){
        mRequestQueue = queue;
        mHttpStack = httpStack;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                Log.d(TAG,"NetworkDispatcher running");
                Request<?> request = mRequestQueue.take();
                if (request.isCanceled()) {
                    Log.i(TAG, request.toString() + "取消执行了");
                    continue;
                }
                Response response = null;
                response = mHttpStack.performRequest(request);
                mResponseDelivery.deliveryResponse(request, response);

        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        isStop = true;
        interrupt();
    }
}
