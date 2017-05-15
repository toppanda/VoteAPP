package com.wmct.voteapp.utils;

import android.util.Log;

import com.wmct.voteapp.R;

/**
 * Created by wmct on 2017/5/14.
 */

public class ParticipantStringUtil {

    private static final String TAG = "StringUtil";
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




}
