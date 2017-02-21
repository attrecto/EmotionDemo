package com.android.attrecto.emotiondemo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.attrecto.emotiondemo.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    private long SPLASH_DELAY_MILLIS = 0;


    @Override
    protected int getLayoutResId() {

        return R.layout.activity_splash;
    }

    @Override
    protected void acquireReferences() {

    }

    @Override
    protected void onAfterAll() {

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, CameraActivity.class);

                startActivity(i);

                finish();

            }
        }, SPLASH_DELAY_MILLIS);

    }
}
