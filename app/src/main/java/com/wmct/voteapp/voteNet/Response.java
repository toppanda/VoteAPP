package com.wmct.voteapp.voteNet;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by pcz on 2017/5/10.
 */

public class Response<T> extends BasicHttpResponse {

    private static final String TAG = "Response";
    private byte[] rawData = new byte[0];

    public Response(StatusLine statusline) {
        super(statusline);
    }

    public Response(StatusLine statusline, ReasonPhraseCatalog catalog, Locale locale) {
        super(statusline, catalog, locale);
    }

    public Response(ProtocolVersion ver, int code, String reason) {
        super(ver, code, reason);
    }

    @Override
    public void setEntity(HttpEntity entity) {
        super.setEntity(entity);
        rawData = entityToBytes(getEntity());
        Log.d(TAG, "rawData=" + new String(rawData));
    }

    public byte[] getRawData() {
        return rawData;
    }

    public int getStatusCode() {
        return getStatusLine().getStatusCode();
    }

    public String getMessage() {
        return getStatusLine().getReasonPhrase();
    }

    /**
     * Reads the contents of HttpEntity into a byte[].
     */
    private byte[] entityToBytes(HttpEntity entity) {
        try {
            InputStream is = entity.getContent();
            return streamToBytes(is);
        } catch (IOException e) {
            return new byte[0];
        }
    }

    private static byte[] streamToBytes(InputStream is, int length) throws IOException {
        byte[] bytes = new byte[length];
        int count = 0;
        int pos = 0;
        while (pos < length && ((count = is.read(bytes, pos, length - pos)) != -1)) {
            pos += count;
        }
        if (pos != length) {
            throw new IOException("Expected" + length + "bytes, read" + pos + "bytes");
        }
        return bytes;
    }
    private static byte[] streamToBytes(InputStream is) throws IOException {

        ByteArrayOutputStream byteous = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int count = 0;
        while ((count = is.read(buff))!=-1){
            byteous.write(buff,0,count);
        }
        return byteous.toByteArray();
    }
}