package com.android.attrecto.emotiondemo.object;

import android.support.annotation.Px;
import android.util.TypedValue;

import com.android.attrecto.emotiondemo.ApplicationObejct;

/**
 * Created by Somogyi Bence on 2017.02.28..
 */

public class DimensionHelper {

    private DimensionHelper(){

    }

    public static int dpToPx(int dp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ApplicationObejct.appContext.getResources().getDisplayMetrics());
    }

    public static int pxToDp(@Px int px) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, ApplicationObejct.appContext.getResources().getDisplayMetrics());
    }


}
