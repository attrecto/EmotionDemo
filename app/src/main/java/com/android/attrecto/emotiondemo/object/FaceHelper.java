package com.android.attrecto.emotiondemo.object;

import android.support.annotation.StringRes;
import android.util.Log;

import com.affectiva.android.affdex.sdk.detector.Face;
import com.android.attrecto.emotiondemo.ApplicationObejct;
import com.android.attrecto.emotiondemo.R;

import java.util.Arrays;
import java.util.List;


/**
 * Created by Somogyi Bence on 2017.02.22..
 */

public class FaceHelper {

    private FaceHelper(){

    }

    /**
     * @return name of the dominant emotion
     */
    public static String getDominantEmotionName(Face face) {

        float max = 0;
        int maxi = 0;

        int i = 0;
        for (float temp : getEmotionPercentList(face)) {

            if (temp > max) {

                max = temp;
                maxi = i;
            }

            i++;
        }

        return ApplicationObejct.appContext.getResources().getStringArray(R.array.emotions)[maxi];
    }

    public static float getDominantEmotionPercentage(Face face) {

        float max = 0;

        for (float temp : getEmotionPercentList(face)) {

            if (temp > max) {

                max = temp;
            }
        }

        return max;

    }

    public static List<Float> getEmotionPercentList(Face face) {

        return Arrays.asList(
                face.emotions.getAnger(),
                face.emotions.getContempt(),
                face.emotions.getDisgust(),
                face.emotions.getFear(),
                face.emotions.getJoy(),
                face.emotions.getSadness(),
                face.emotions.getSurprise());
    }

}
