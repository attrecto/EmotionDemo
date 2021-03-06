package com.android.attrecto.emotiondemo.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;


/**
 * Created by Somogyi Bence on 2017.02.21..
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected View rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        rootView = getLayoutInflater().inflate(getLayoutResId(), null);

        // keeps screen turned on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
