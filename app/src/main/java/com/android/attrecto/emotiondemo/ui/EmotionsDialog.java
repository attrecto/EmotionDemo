package com.android.attrecto.emotiondemo.ui;

import android.app.Dialog;
import android.content.Context;

import com.android.attrecto.emotiondemo.R;

/**
 * Created by Somogyi Bence on 2017.02.21..
 */

public class EmotionsDialog extends BaseDialog {


    public EmotionsDialog(Context context) {

        super(context);
    }

    @Override
    protected int getLayoutResId() {

        return R.layout.dialog_emotions;
    }

    @Override
    protected void acquireReferences() {

    }

    @Override
    protected void onAfterAll() {

    }


}
