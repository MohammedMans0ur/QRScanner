package com.ebe.qrscanner.model.data.source.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ebe.qrscanner.App;
import com.google.gson.Gson;


import java.lang.reflect.Type;
import java.util.ArrayList;


public class SharedManager {


    private static final String APP_PREFERENCE = "QR Scanner";
    private static SharedManager mSharedManager;
    private final SharedPreferences mPreference;
    private final Gson mGson;
    private final SharedPreferences.Editor mEditor;

    public SharedManager(Context mContext) {
        mPreference = mContext.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        mEditor = mPreference.edit();
        mGson = new Gson();
    }

    public static SharedManager newInstance() {
        if (mSharedManager == null) {
            return mSharedManager = new SharedManager(App.mContext);
        } else {
            return mSharedManager;
        }
    }

    public SharedPreferences getPreference() {
        return mPreference;
    }

    public <T> void saveObject(T object, String key) {
        String fromObject = mGson.toJson(object);
        Boolean status = mEditor.putString(key, fromObject).commit();
    }


    public <T> void saveTypedObject(T object, Type type, String key) {
        String fromObject = mGson.toJson(object, type);
        mEditor.putString(key, fromObject).commit();
    }

    public <T> T getObject(String key, Class<T> Clazz) {
        String fromString = mPreference.getString(key, null);
        T t;
        if (fromString != null) {
            t = mGson.fromJson(fromString, Clazz);
            return t;
        }
        return null;
    }

    public <T> ArrayList<T> getTypedObject(String key, Type type) {
        String fromString = mPreference.getString(key, null);
        ArrayList<T> list;
        if (fromString != null) {
            list = mGson.fromJson(fromString, type);
            return list;
        }
        return null;
    }

    public String getCashValue(String key) {
        return mPreference.getString(key, null);

    }

    public void CashValue(String key, String value) {
        mEditor.putString(key, value).commit();

    }

    public void cashBoolean(String key, Boolean value) {
        mEditor.putBoolean(key, value).commit();

    }

    public Boolean getCashBoolean(String key) {
        return mPreference.getBoolean(key, false);

    }


}
