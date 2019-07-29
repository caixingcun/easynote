package com.caixc.easynoteapp.util;

import android.util.Log;
import com.caixc.easynoteapp.global.App;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 日志工具类
 */
public class LogUtils {
    public static final String TAG = "tag";
    public static final boolean IS_DEBUG = App.debug;

    public static void debug(String msg) {
        if (IS_DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void error(String msg) {
        if (IS_DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public  static void printJson(String tag, String msg, String headString) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            LogUtils.debug("║ " + line);
        }
        printLine(tag, false);
    }
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            LogUtils.debug("╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            LogUtils.debug("╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }
}
