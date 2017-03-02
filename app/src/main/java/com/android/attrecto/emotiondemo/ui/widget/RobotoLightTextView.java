package com.android.attrecto.emotiondemo.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Somogyi Bence on 2017.02.22..
 */

public class RobotoLightTextView extends TextView {

    private static final String FONT_ROBOTO_LIGHT = "fonts/roboto_light.ttf";

    public RobotoLightTextView(Context context) {

        super(context);

        setRLTf();
    }

    public RobotoLightTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

        setRLTf();
    }

    public RobotoLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        setRLTf();
    }

    public RobotoLightTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);

        setRLTf();
    }


    /**
     * sets the Robot Light Typeface
     */
    private void setRLTf() {

        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), FONT_ROBOTO_LIGHT);
        setTypeface(tf);

    }


}
