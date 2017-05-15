package com.wmct.voteapp.voteNet;

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

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
public class HttpURLConnectionStack implements HttpStack {
    private static final String TAG = "HttpURLConnectionStack";
    HttpUrlConnConfig mConfig = HttpUrlConnConfig.getConfig();

    @Override
    public Response performRequest(Request<?> request) {

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = createUrlConnection(request);
            setRequestHeaders(urlConnection, request);
            setRequestParams(urlConnection, request);
            return fetchResponse(urlConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Response fetchResponse(HttpURLConnection urlConnection) throws IOException {
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        int responseCode = urlConnection.getResponseCode();
        if (responseCode == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection");
        }
        StatusLine responseStatus = new BasicStatusLine(protocolVersion,
                urlConnection.getResponseCode(), urlConnection.getResponseMessage());
        Response response = new Response(responseStatus);
        response.setEntity(entityFromConnection(urlConnection));
        setResponseHeader(urlConnection, response);
        return response;
    }

    private void setResponseHeader(HttpURLConnection urlConnection, BasicHttpResponse response) {
        Map<String, List<String>> map = urlConnection.getHeaderFields();
        if (map != null && map.size() > 0) {
            for (String headerName : map.keySet()) {
                if (headerName != null && map.get(headerName).size() > 0) {
                    Header header = new BasicHeader(headerName, map.get(headerName).get(0));
                    response.addHeader(header);
                }
            }
        }

    }

    private HttpEntity entityFromConnection(HttpURLConnection urlConnection) {
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream is;
        try {
            is = urlConnection.getInputStream();
            Log.d(TAG, "ResponseCode=" + urlConnection.getResponseCode());
           /* if (urlConnection.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(is);
                int i;
                String content = "";
                while ((i = reader.read()) != -1) {
                    content = content + (char) i;
                }
                Log.d(TAG, content);

            }*/
            entity.setContent(is);
           /* InputStream reader = entity.getContent();
            ByteArrayOutputStream byteous = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int i;
            while ((i = reader.read(buff)) != -1) {
                byteous.write(buff,0,i);
            }
            Log.d(TAG, "获取到"+new String(byteous.toByteArray(),"UTF-8"));*/
           /* InputStreamReader reader = new InputStreamReader(entity.getContent());
            int i;
            String content = "";
            while ((i = reader.read()) != -1) {
                content = content + (char) i;
            }
            Log.d(TAG, "获取到"+content);*/
            //Log.d(TAG, "ContentLength=" + urlConnection.getContentLength());
            entity.setContentLength(urlConnection.getContentLength());
            entity.setContentEncoding(urlConnection.getContentEncoding());
            entity.setContentType(urlConnection.getContentType());
        } catch (IOException e) {
            is = urlConnection.getErrorStream();
        }


        return entity;
    }

    private void setRequestParams(HttpURLConnection urlConnection, Request<?> request) {
        Request.HttpMethod method = request.getHttpMethod();
        try {
            urlConnection.setRequestMethod(method.toString());
            byte[] body = request.getBody();
            if (body != null) {
                urlConnection.setDoInput(true);
                urlConnection.addRequestProperty(Request.HEADER_CONTENT_TYPE, request.getBodyContentType());
                DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                os.write(body);
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setRequestHeaders(HttpURLConnection urlConnection, Request<?> request) {
        Map<String, String> map = request.getHeaders();
        if (map != null && map.size() > 0) {
            for (String headerName : map.keySet()) {
                urlConnection.addRequestProperty(headerName, map.get(headerName));
            }
        }

    }

    private HttpURLConnection createUrlConnection(Request<?> request) throws IOException {
        String url = request.getUrl();
        URL newUrl = new URL(url);
        URLConnection urlConnection = newUrl.openConnection();
        urlConnection.setConnectTimeout(mConfig.connTimeOut);
        urlConnection.setReadTimeout(mConfig.soTimeOut);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        if (request.isHttps()) {
            SSLSocketFactory sslFactory = mConfig.getSslSocketFactory();
            if (sslFactory != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslFactory);
                HttpsURLConnection.setDefaultHostnameVerifier(mConfig.getHostnameVerifier());
            }
        }
        return (HttpURLConnection) urlConnection;
    }

}
