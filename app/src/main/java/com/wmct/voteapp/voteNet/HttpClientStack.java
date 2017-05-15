package com.wmct.voteapp.voteNet;

import android.net.http.AndroidHttpClient;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.Map;

/**
 * ---------------------------------------
 * 版权 ：山大信息学院
 * <p/>
 * 作者 ：三月半
 * <p/>
 * 时间 ：2016/1/1 11:02
 * <p/>
 * --------------------------------------
 */
public class HttpClientStack implements HttpStack {
    private static final String TAG = "HttpClientStack";
    HttpConfig mConfig = HttpClientConfig.getConfig();
    HttpClient mHttpClient = AndroidHttpClient.newInstance(mConfig.userAgent);

    @Override
    public Response performRequest(Request<?> request) {

        try {
            HttpUriRequest httpRequest = createHttpRequest(request);
            addHeaders(httpRequest, request.getHeaders());
            setConnParams(httpRequest, request);
            HttpResponse httpResponse = mHttpClient.execute(httpRequest);
            Response response = new Response(httpResponse.getStatusLine());
            response.setEntity(httpResponse.getEntity());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setConnParams(HttpUriRequest httpRequest, Request<?> request) {
        HttpParams httpParams = httpRequest.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, mConfig.connTimeOut);
        HttpConnectionParams.setSoTimeout(httpParams, mConfig.soTimeOut);
        SSLSocketFactory sslSocketFactory = ((HttpClientConfig) mConfig).getSocketFactory();
        if (request.isHttps() && sslSocketFactory != null) {
            Scheme scheme = new Scheme("https", sslSocketFactory, 443);
            mHttpClient.getConnectionManager().getSchemeRegistry().register(scheme);
        }
    }

    private void addHeaders(HttpUriRequest httpRequest, Map<String, String> header) {
        if (header != null && header.size() > 0) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                httpRequest.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private HttpUriRequest createHttpRequest(Request<?> request) {
        HttpUriRequest httpRequest = null;
        byte[] body = request.getBody();
        Log.d(TAG, "body=" + new String(body));
        if (body != null) {
            httpRequest = new HttpPost(request.getUrl());
            httpRequest.addHeader(request.HEADER_CONTENT_TYPE, request.getBodyContentType());
            HttpEntity entity;
            entity = new ByteArrayEntity(body);
            ((HttpPost) httpRequest).setEntity(entity);
        } else {
            httpRequest = new HttpGet(request.getUrl());
        }
        return httpRequest;
    }
}
