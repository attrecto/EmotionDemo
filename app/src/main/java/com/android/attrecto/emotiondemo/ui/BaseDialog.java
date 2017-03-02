package com.android.attrecto.emotiondemo.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.android.attrecto.emotiondemo.R;

/**
 * Created by Somogyi Bence on 2017.02.21..
 */

public abstract class BaseDialog extends Dialog {

    protected View rootView;

    public BaseDialog(Context context) {

        super(context, R.style.full_screen_dialog);

        rootView = LayoutInflater.from(getContext()).inflate(getLayoutResId(), null);

        setContentView(rootView);

        setWindowFullScreenAndTransparent();

        acquireReferences();

        setListeners();

        onAfterAll();

    }

    private void setWindowFullScreenAndTransparent() {

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                              WindowManager.LayoutParams.MATCH_PARENT);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }

    public View getRootView() {

        return rootView;
    }

    protected abstract int getLayoutResId();

    protected abstract void acquireReferences();

    protected void setListeners() {

    }

    protected abstract void onAfterAll();


}
