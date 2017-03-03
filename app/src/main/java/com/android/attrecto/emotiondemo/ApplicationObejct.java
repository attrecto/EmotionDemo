package com.android.attrecto.emotiondemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Somogyi Bence on 2017.02.21..
 */

public class ApplicationObejct extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {

        super.onCreate();

        appContext = getApplicationContext();


    }
}
