package com.wmct.voteapp.voteNet.voteAPI;



import com.wmct.voteapp.voteNet.JsonRequest;
import com.wmct.voteapp.voteNet.Request;
import com.wmct.voteapp.voteNet.RequestQueue;
import com.wmct.voteapp.voteNet.StringRquest;
import com.wmct.voteapp.voteNet.VoteNet;

import org.json.JSONObject;

/**
 * Created by pcz on 2017/5/10.
 */

public class VoteNetApi {

    private static RequestQueue mRequestQueue = VoteNet.newRequestQueue();
    private static final String TAG = "HealthNetAPI";



    private static final String WMCT_VOTE_HOST_IP = "http://58.194.170.16";
    private static final String WMCT_VOTE_HOST_PORT = ":80";
    private static final String WMCT_VOTE_HOST = WMCT_VOTE_HOST_IP + WMCT_VOTE_HOST_PORT;
    private static final String WMCT_VOTE_VISITERLOGIN = WMCT_VOTE_HOST + "/vote/controller/loginProcess.php";
    private static final String WMCT_VOTE_VOTEEND = WMCT_VOTE_HOST +"/vote/controller/finishProcess.php";
    private static final String WMCT_VOTE_VOTESTART = WMCT_VOTE_HOST +"/vote/controller/startProcess.php";
    private static final String WMCT_VOTE_REQTHEME = WMCT_VOTE_HOST +"/vote/controller/contentProcess.php";
    private static final String WMCT_VOTE_VOTESUBMIT = WMCT_VOTE_HOST +"/vote/controller/voteProcess.php";
    private static final String WMCT_VOTE_RESULTCHECK = WMCT_VOTE_HOST+"/vote/controller/displayProcess.php";



    /**
     * 网络测试接口
     *
     */
//    public static void testRequst( Request.RequestListener<JSONObject> listener) {
//        JsonRequest request = new JsonRequest(Request.HttpMethod.GET, TESPAI, listener);
//        request.addParams("id", "10");
//        request.addParams("rows", "10");
//        request.addParams("classify", "1");
//        mRequestQueue.addRequest(request);
//    }

    /**
     * #1
     * 参与者登录
     *
     */
    public static void visitorLogin( String roomNum, Request.RequestListener<JSONObject> listener) {

    JsonRequest request = new JsonRequest(Request.HttpMethod.POST, WMCT_VOTE_VISITERLOGIN, listener);
    request.addParams("roomnum", roomNum);
    mRequestQueue.addRequest(request);
    }

    /**
     * #2
     * 主持人登录 密码 房间号
     *
     */
    public static void hostLogin( String roomNum, String passWord, Request.RequestListener<JSONObject> listener) {

        JsonRequest request = new JsonRequest(Request.HttpMethod.POST, WMCT_VOTE_VOTESTART, listener);
        request.addParams("roomnum", roomNum);
        request.addParams("password", passWord);
        mRequestQueue.addRequest(request);

    }

    /**
     * #3
     * 发起投票
     *
     */
//    public static void voteStart( Request.RequestListener<JSONObject> listener) {
//
//        JsonRequest request  = new JsonRequest(Request.HttpMethod.POST, WMCT_VOTE_VOTESTART, listener);
//        request.addParams("");
//    }

    /**
     * #4
     * 结束投票
     *
     */
    public static void voteEnd( String roomNum, String passWord, Request.RequestListener<JSONObject> listener) {

        JsonRequest request  = new JsonRequest(Request.HttpMethod.POST, WMCT_VOTE_VOTEEND, listener);
        request.addParams("roomnum", roomNum);
        request.addParams("password", passWord);
        mRequestQueue.addRequest(request);
    }

    /**
     * #5
     * 请求题目
     *
     */
    public static void requestTheme(String roomNum, Request.RequestListener<JSONObject> listener) {

        JsonRequest request = new JsonRequest(Request.HttpMethod.POST, WMCT_VOTE_REQTHEME,listener);
        request.addParams("roomnum", roomNum);
        mRequestQueue.addRequest(request);
    }

    /**
     * #6
     *提交投票
     */
    public static void voteSubmit( String subTicket, Request.RequestListener<String> listener) {

        //JsonRequest request = new JsonRequest(Request.HttpMethod.POST, WMCT_VOTE_VOTESUBMIT,listener);

        StringRquest request = new StringRquest(Request.HttpMethod.POST, WMCT_VOTE_VOTESUBMIT,listener);
        request.addParams("votedata",subTicket );
        mRequestQueue.addRequest(request);
    }

    /**
     * #7
     *查看结果
     */
    public static void resultCheck( String roomNum,Request.RequestListener<JSONObject> listener) {
        JsonRequest request = new JsonRequest(Request.HttpMethod.POST, WMCT_VOTE_RESULTCHECK,listener);
        request.addParams("roomnum",roomNum );
        mRequestQueue.addRequest(request);
    }
}
