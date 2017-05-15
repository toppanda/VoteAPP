package com.wmct.voteapp.utils;

import android.util.Log;
import com.wmct.voteapp.R;

/**
 * Created by ZHYu on 2017/5/13.
 */

public class StringUtil {
    private static final String TAG = "StringUtil";

    /**
     * 针对主持人
     * @param msg
     * @return
     */
    public static int ENChangeToCH(String msg) {
        Log.i(TAG, "msg:------------------>" + msg);
        int resourceId = 0;
        switch (msg) {
            case "WrongRoomnumError":
                resourceId = R.string.WrongRoomnumError;
                break;
            case "WrongPasswordError":
                resourceId = R.string.WrongPasswordError;
                break;
            case "RoomHasOpenedError":
                resourceId = R.string.RoomHasOpenedError;
                break;
            case "DBUpdateError":
                resourceId = R.string.DBUpdateError;
                break;
            case "RoomHasNotOpenedError":
                resourceId = R.string.RoomHasNotOpenedError;
                break;
            case "RoomHasFinishedError":
                resourceId = R.string.RoomHasFinishedError;
                break;

        }
        return resourceId;
    }

    /**
     * 针对投票人
     * @param msg
     * @return
     */
    public static int VoterENChangeToCH(String msg) {
        Log.i(TAG, "msg:------------------>" + msg);
        int resourceId = 0;
        switch (msg) {
            case "WrongRoomnumError":
                resourceId = R.string.WrongRoomnumError;
                break;
            case "RoomHasNotOpenedError":
                resourceId = R.string.vRoomHasNotOpenedError;
                break;
            case "RoomHasFinishedError":
                resourceId = R.string.vRoomHasFinishedError;
                break;

        }
        return resourceId;
    }
    /**
     * 针对显示结果
     * @param msg
     * @return
     */
    public static int ResultENChangeToCH(String msg) {
        Log.i(TAG, "msg:------------------>" + msg);
        int resourceId = 0;
        switch (msg) {
            case "OnVoteError":
                resourceId = R.string.OnVoteError;
                break;
            case "WrongRoomnumError":
                resourceId = R.string.resultWrongRoomnumError;
                break;
            case "displayProcessError":
                resourceId = R.string.resultdisplayProcessError;
                break;
            case "InputError":
                resourceId = R.string.resultInputError;
                break;



        }
        return resourceId;
    }


}
