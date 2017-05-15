package com.wmct.voteapp.voteNet;

/**
 * Created by pcz on 2017/5/10.
 */

public class StringRquest extends Request<String> {
    /**
     * @param method
     * @param url
     * @param listener
     */
    public StringRquest(HttpMethod method, String url, RequestListener<String> listener) {
        super(method, url, listener);
    }

    @Override
    public String parseResponse(Response response) {

        if (response != null){
            return new String(response.getRawData());
        }else {
            return null;
        }

    }
}
