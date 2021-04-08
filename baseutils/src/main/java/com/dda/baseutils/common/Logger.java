package com.dda.baseutils.common;

import android.text.TextUtils;

public class Logger {

    public static boolean logEnabled;
    public static String logTag = "LoggerDDA";

    public static void setLogEnabled(boolean enabled) {
        logEnabled = enabled;
    }

    public static void setLogTag(String tag) {
        logTag = tag;
    }

    /**     Info        **/
    public static void i(String string) {
        i(logTag, string);
    }

    public static void i(String tag, String string) {
        if (logEnabled) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(string))
                android.util.Log.i(tag, string);
        }
    }

    /**     Error       **/
    public static void e(String string) {
        e(logTag, string);
    }

    public static void e(String tag, String string) {
        if (logEnabled) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(string))
                android.util.Log.e(tag, string);
        }
    }

    /**     Debug       **/
    public static void d(String string) {
        d(logTag, string);
    }

    public static void d(String tag, String string) {
        if (logEnabled) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(string))
                android.util.Log.d(tag, string);
        }
    }

    /**     Verbose     **/
    public static void v(String string) {
        v(logTag, string);
    }

    public static void v(String tag, String string) {
        if (logEnabled) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(string))
                android.util.Log.v(tag, string);
        }
    }

    /**     Warning     **/
    public static void w(String string) {
        w(logTag, string);
    }

    public static void w(String tag, String string) {
        if (logEnabled) {
            if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(string))
                android.util.Log.w(tag, string);
        }
    }
}

