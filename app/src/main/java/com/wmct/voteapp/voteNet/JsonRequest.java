package com.wmct.voteapp.voteNet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pcz on 2017/5/10.
 */

public class JsonRequest extends com.wmct.voteapp.voteNet.Request<JSONObject> {
    /**
     * @param method
     * @param url
     * @param listener
     */
    public JsonRequest(com.wmct.voteapp.voteNet.Request.HttpMethod method, String url, RequestListener<JSONObject> listener) {
        super(method, url, listener);
    }

    @Override
    public JSONObject parseResponse(com.wmct.voteapp.voteNet.Response response) {
        String jsonString = new String(response.getRawData());

        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
