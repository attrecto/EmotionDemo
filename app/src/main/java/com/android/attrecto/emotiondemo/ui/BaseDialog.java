package com.android.attrecto.emotiondemo.ui;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Somogyi Bence on 2017.02.21..
 */

public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {

        super(context);

        setContentView(getLayoutResId());

        acquireReferences();

        setListeners();

        onAfterAll();

    }

    protected abstract int getLayoutResId();

    protected abstract void acquireReferences();

    private void setListeners() {

    }

    protected abstract void onAfterAll();


}
