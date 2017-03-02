package com.android.attrecto.emotiondemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Somogyi Bence on 2017.02.24..
 */
public class PreferencesManager {

    private static final String SHARED_PREFERENCES_NAME = "com.android.attrecto.emotiondemo.PreferencesManager.shared_preferences_name";

    private static PreferencesManager mInstance;
    private SharedPreferences mSharedPreferences;

    public static PreferencesManager getInstance() {

        if (mInstance == null) {

            mInstance = new PreferencesManager();
        }

        return mInstance;
    }

    private PreferencesManager() {

        mSharedPreferences = ApplicationObejct.appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


    // SharedPreferences writing/reading methods

    public int getInt(String prefName, int defValue) {

        return mSharedPreferences.getInt(prefName, defValue);
    }

    public float getFloat(String prefName, float defValue) {

        return mSharedPreferences.getFloat(prefName, defValue);
    }

    public boolean getBoolean(String prefName, boolean defValue) {

        return mSharedPreferences.getBoolean(prefName, defValue);
    }

    public String getString(String prefName, String defValue) {

        return mSharedPreferences.getString(prefName, defValue);
    }

    public void putInt(String prefName, int value) {

        mSharedPreferences.edit().putInt(prefName, value).apply();
    }

    public void putFloat(String prefName, float value) {

        mSharedPreferences.edit().putFloat(prefName, value).apply();
    }

    public void putBoolean(String prefName, boolean value) {

        mSharedPreferences.edit().putBoolean(prefName, value).apply();
    }

    public void putString(String prefName, String value) {

        mSharedPreferences.edit().putString(prefName, value).apply();
    }


}
