package com.android.attrecto.emotiondemo.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.android.attrecto.emotiondemo.R;

/**
 * Created by Somogyi Bence on 2017.02.21..
 */

public class DialogHelper {

    public static void showAlertDialog(Context c, @StringRes int msg, DialogInterface.OnDismissListener l) {

        new AlertDialog.Builder(c)
                .setMessage(msg)
                .setPositiveButton(R.string.ok, null)
                .setOnDismissListener(l)
                .show();

    }

    public static void showAlertDialog(Context c, @StringRes int title, @StringRes int msg, DialogInterface.OnDismissListener l) {

        new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.ok, null)
                .setOnDismissListener(l)
                .show();

    }

    public static void showAppInfo(Context c) {

        new AlertDialog.Builder(c)
                .show();
    }

}
