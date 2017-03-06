package com.android.attrecto.emotiondemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by Somogyi Bence on 2017.02.21..
 */

public class ApplicationObejct extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {

        super.onCreate();

        appContext = getApplicationContext();

        final Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {

                Log.e("UNCAUGHT", null, throwable);

                handler.uncaughtException(thread, throwable);
            }
        });
    }
}
