package com.dda.baseutils.io;

import android.content.Context;
import android.content.SharedPreferences;

import com.dda.baseutils.common.Logger;

public class Preferences {

    private int _DEFAULT_INT_VALUE = -1;
    private String _DEFAULT_STRING_VALUE = "";

    private SharedPreferences sharedPreferences = null;
    private static Preferences sharedPreferencesInstance = null;

    private Preferences(Context context, String name, int mode) {
        sharedPreferences = context.getSharedPreferences(name, mode);
    }

    private synchronized static void createSharedPreferencesInstance(Context context, String name, int mode) {
        if (sharedPreferencesInstance == null)
            sharedPreferencesInstance = new Preferences(context, name, mode);
    }

    public static Preferences getInstance(Context context, String name) {
        if (sharedPreferencesInstance == null)
            createSharedPreferencesInstance(context, name, Context.MODE_PRIVATE);
        return sharedPreferencesInstance;
    }

    public static Preferences getInstance(Context context, String name, int mode) {
        if (sharedPreferencesInstance == null)
            createSharedPreferencesInstance(context, name, mode);
        return sharedPreferencesInstance;
    }

    public void setIntData(String key, int data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, data);
        editor.apply();
    }

    public int getIntData(String key) {
        return sharedPreferences.getInt(key, _DEFAULT_INT_VALUE);
    }

    public int getIntData(String key, int defaultInt) {
        return sharedPreferences.getInt(key, defaultInt);
    }

    public void removeIntData(String key) {
        setIntData(key, _DEFAULT_INT_VALUE);
    }

    public void setStringData(String key, String data) {
        if (data != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, data);
            editor.apply();
        }
        else {
            Logger.i("Preferences: data with key: " + key + " is null or empty");
        }
    }

    public String getStringData(String key) {
        return sharedPreferences.getString(key, _DEFAULT_STRING_VALUE);
    }

    public String getStringData(String key, String defaultString) {
        return sharedPreferences.getString(key, defaultString);
    }

    public void setLongData(String key, long data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, data);
        editor.apply();
    }

    public long getLongData(String key) {
        return sharedPreferences.getLong(key,_DEFAULT_INT_VALUE);
    }

    public long getLongData(String key, long defaultLong) {
        return sharedPreferences.getLong(key, defaultLong);
    }

    public void removeStringData(String key) {
        Logger.i("Removing key: " + key);
        setStringData(key, _DEFAULT_STRING_VALUE);
    }

    public void setDefaultIntValue(int defaultIntValue) {
        this._DEFAULT_INT_VALUE = defaultIntValue;
    }

    public void setDefaultStringValue(String defaultStringValue) {
        this._DEFAULT_STRING_VALUE = defaultStringValue;
    }
}


