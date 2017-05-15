package com.wmct.voteapp.voteNet;

import java.io.IOException;

/**
 * 接口，执行网络请求
 * Created by pcz on 2017/5/10.
 */
public interface HttpStack {
    /**
     * 执行Http请求
     *
     * @param request 待执行的请求
     * @return
     */
    public Response performRequest(Request<?> request) throws IOException;
}
