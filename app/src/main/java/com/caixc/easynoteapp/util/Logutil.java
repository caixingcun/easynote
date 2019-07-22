package com.caixc.easynoteapp.util;

import android.util.Log;

/**
 * 日志工具类
 */
public class Logutil {
    public static final String TAG = "tag";
    public static final boolean IS_DEBUG = false;

    public static void debug(String msg) {
        if (IS_DEBUG) {
            Log.d(TAG, msg);
        }
    }
}
