package com.android.attrecto.emotiondemo.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Somogyi Bence on 2017.03.03..
 */

public abstract class BaseCustomDialog extends Dialog {

    protected View rootView;

    public BaseCustomDialog(Context context) {

        super(context);

        rootView = LayoutInflater.from(context).inflate(getLayoutResId(), null);
        setContentView(rootView);

        acquireReferences();

        setListeners();

        onAfterAll();

    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void acquireReferences();

    protected void setListeners() {

    }

    protected abstract void onAfterAll();


}
