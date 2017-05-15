package com.wmct.voteapp.voteNet;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by pcz on 2017/5/10.
 * 请求队列，使用优先级队列，是的请求可以按照优先级进行处理
 */

public final class RequestQueue {
    private static final String TAG = "RequsetQueue";
    /**
     * 线程安全的请求队列
     */
    private BlockingQueue<Request<?>> mRequestQueue = new PriorityBlockingQueue<Request<?>>();
    /**
     * 请求的序列化生成器
     */
    private AtomicInteger mSequenceGenerator = new AtomicInteger();

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    public static int DEFAULT_CORE_POOL_SIZE = CPU_COUNT + 1;
    private int mDispatcherNums = DEFAULT_CORE_POOL_SIZE;
    private NetworkExecutor[] mNetworkExecutors;
    private HttpStack mHttpStack;

    RequestQueue() {
        this(DEFAULT_CORE_POOL_SIZE, null);
    }

    RequestQueue(int coreNums, HttpStack httpStack) {
        mDispatcherNums = coreNums;
        mHttpStack = httpStack != null ? httpStack : HttpStackFactory.createHttpStack();
    }

    public void start() {

        mNetworkExecutors = new NetworkExecutor[mDispatcherNums];
        Log.d(TAG, "DispatcherNums =" + mDispatcherNums);
        for (int i = 0; i < mDispatcherNums; i++) {
            mNetworkExecutors[i] = new NetworkExecutor(mRequestQueue, mHttpStack);
            mNetworkExecutors[i].start();
        }
    }

    public void stop() {
        if (mNetworkExecutors != null && mNetworkExecutors.length > 0) {
            for (int i = 0; i < mNetworkExecutors.length; i++) {
                mNetworkExecutors[i].quit();
            }
        }
    }

    //添加请求
    public void addRequest(Request<?> request) {
        if (!mRequestQueue.contains(request)) {
            request.setSerialNumber(this.generateSequenceNum());
            mRequestQueue.add(request);
        } else {
            Log.d(TAG, request.toString() + "请求队列中已经存在此请求");
        }
    }

    public void clear() {
        mRequestQueue.clear();
    }

    public int generateSequenceNum() {
        return mSequenceGenerator.incrementAndGet();
    }
}
