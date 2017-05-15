package com.wmct.voteapp.voteNet;

/**
 * Created by pcz on 2017/5/10.
 */

public class VoteNet {

    public static RequestQueue newRequestQueue() {
        return newRequestQueue(RequestQueue.DEFAULT_CORE_POOL_SIZE);
    }

    public static RequestQueue newRequestQueue(int coreNums) {
        return newRequestQueue(coreNums, null);
    }

    public static RequestQueue newRequestQueue(int coreNums, HttpStack httpStack) {
        RequestQueue requestQueue = new RequestQueue(coreNums, httpStack);
        requestQueue.start();
        return requestQueue;
    }
}
