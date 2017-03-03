package com.android.attrecto.emotiondemo.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.attrecto.emotiondemo.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    public static final int CAMERA_REQUEST_CODE = 0;

    private static final long SPLASH_DELAY_IN_MILLIS = 1000;

    private ImageView mSplashImage;


    @Override
    protected int getLayoutResId() {

        return R.layout.activity_splash;
    }

    @Override
    protected void acquireReferences() {

        mSplashImage = (ImageView) findViewById(R.id.splash_image);

    }

    @Override
    protected void onAfterAll() {

        requestPermissionsIfNeeded();

    }

    private void requestPermissionsIfNeeded() {

        if (!checkForCameraPermission())
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        else
            startCameraActivityWithDelay();

    }

    private boolean checkForPermission(String permission) {

        return ContextCompat.checkSelfPermission(SplashActivity.this, permission) == PackageManager.PERMISSION_GRANTED;

    }

    public boolean checkForCameraPermission() {

        return checkForPermission(Manifest.permission.CAMERA);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startCameraActivityWithDelay();
        }
        else {

            DialogHelper.showAlertDialog(SplashActivity.this, R.string.permissions_camera_explanation, new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialogInterface) {

                    finish();
                }
            });
        }

    }

    private void startCameraActivityWithDelay() {

        mSplashImage.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, android.R.anim.fade_in));
        mSplashImage.setVisibility(View.VISIBLE);

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, CameraActivity.class);

                startActivity(i);

                finish();

            }
        }, SPLASH_DELAY_IN_MILLIS);
    }

}
