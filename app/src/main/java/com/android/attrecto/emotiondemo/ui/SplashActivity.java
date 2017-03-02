package com.android.attrecto.emotiondemo.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import com.android.attrecto.emotiondemo.R;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    public static final int REQUEST_CODE = 0;
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_REQUEST_CODE = 2;
    public static final int INTERNET_REQUEST_CODE = 3;

    private static final long SPLASH_DELAY_IN_MILLIS = 0;


    @Override
    protected int getLayoutResId() {

        return R.layout.activity_splash;
    }

    @Override
    protected void acquireReferences() {

    }

    @Override
    protected void onAfterAll() {


        //requestPermissions();

        startCameraActivity();


    }

    private void requestPermissions() {

        ArrayList<String> permissions = new ArrayList<>();

        if (!checkForCameraPermission())
            permissions.add(Manifest.permission.CAMERA);

        if (!checkForExternalStoragePermission())
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (!checkForInternetAccessPermission())
            permissions.add(Manifest.permission.INTERNET);

        String[] p = new String[permissions.size()];

        for (int i = 0; i < permissions.size(); i++) {
            p[i] = permissions.get(i);

        }

        if (p != null)
            requestPermissions(p, REQUEST_CODE);

    }


    private boolean checkForPermission(String permission) {

        return ContextCompat.checkSelfPermission(SplashActivity.this, permission) == PackageManager.PERMISSION_GRANTED;

    }

    public boolean checkForCameraPermission() {

        return checkForPermission(Manifest.permission.CAMERA);

    }

    public boolean checkForExternalStoragePermission() {

        return checkForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    public boolean checkForInternetAccessPermission() {

        return checkForPermission(Manifest.permission.INTERNET);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        boolean camera = false;
        boolean storage = false;
        boolean internet = false;

        for (int i = 0; i < permissions.length; i++) {

            String permission = permissions[i];
            int grantResult = grantResults[i];

            if (permission.equals(Manifest.permission.CAMERA)) {

                if (camera = grantResult != PackageManager.PERMISSION_GRANTED) {

                    DialogHelper.showCameraPermissionRequest(SplashActivity.this);

                }

            }

            if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                if (storage = grantResult != PackageManager.PERMISSION_GRANTED) {

                    DialogHelper.showExternalStoragePermissionRequest(SplashActivity.this);

                }
            }

            if (permission.equals(Manifest.permission.INTERNET)) {

                if (internet = grantResult != PackageManager.PERMISSION_GRANTED) {

                    DialogHelper.showInternetAccessPermissionRequest(SplashActivity.this);

                }

            }

        }

        if (camera && storage && internet) {

            startCameraActivity();
        }

    }

    private void startCameraActivity() {

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
