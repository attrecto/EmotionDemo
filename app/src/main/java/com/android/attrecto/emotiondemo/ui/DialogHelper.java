package com.android.attrecto.emotiondemo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.android.attrecto.emotiondemo.ApplicationObejct;
import com.android.attrecto.emotiondemo.R;

/**
 * Created by Somogyi Bence on 2017.02.21..
 */

public class DialogHelper {

    public static void showAlertDialog(Context c, String msg) {

        new AlertDialog.Builder(c)
                .setTitle(R.string.alert)
                .setMessage(msg)
                .setPositiveButton(R.string.ok, null)
                .show();

    }

    private static void showPermissionRequest(final Activity a, @StringRes int msg) {

        new AlertDialog.Builder(a)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();

                        a.finish();

                    }
                })
                .show();
    }

    public static void showCameraPermissionRequest(Activity a) {

        showPermissionRequest(a, R.string.permissions_camera_explanation);

    }

    public static void showExternalStoragePermissionRequest(Activity a) {

        showPermissionRequest(a, R.string.permissions_storage_explanation);

    }

    public static void showInternetAccessPermissionRequest(Activity a) {

        showPermissionRequest(a, R.string.permissions_internet_explanation);

    }

}
