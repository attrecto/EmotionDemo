package com.android.attrecto.emotiondemo.object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.affectiva.android.affdex.sdk.detector.Face;
import com.android.attrecto.emotiondemo.ApplicationObejct;
import com.android.attrecto.emotiondemo.R;

import java.util.HashMap;

/**
 * Created by Somogyi Bence on 2017.03.02..
 */

public class EmojiHelper {

    private static final int DISAPPOINTED = R.drawable.emoji_disappointed;
    private static final int FLUSHED = R.drawable.emoji_flushed;
    private static final int KISSING = R.drawable.emoji_kissing;
    private static final int LAUGHING = R.drawable.emoji_laughing;
    private static final int RAGE = R.drawable.emoji_rage;
    private static final int RELAXED = R.drawable.emoji_relaxed;
    private static final int SCREAM = R.drawable.emoji_scream;
    private static final int SMILEY = R.drawable.emoji_smiley;
    private static final int SMIRK = R.drawable.emoji_smirk;
    private static final int STUCK_OUT_TOUNGUE_WINKING_EYE = R.drawable.emoji_stuck_out_tongue_winking_eye;
    private static final int STUCK_OUT_TOUNGUE = R.drawable.emoji_stuck_out_tounge;
    private static final int WINK = R.drawable.emoji_wink;

    private static final String EXT = ".png";

    private EmojiHelper() {

    }

    public static Bitmap getDominantEmoji(Face face) {

        int emojiFileName = -1;
        Bitmap re = null;

        switch (face.emojis.getDominantEmoji()) {

            case DISAPPOINTED:
                emojiFileName = DISAPPOINTED;
                break;

            case RELAXED:
                emojiFileName = RELAXED;
                break;

            case SMILEY:
                emojiFileName = SMILEY;
                break;

            case LAUGHING:
                emojiFileName = LAUGHING;
                break;

            case KISSING:
                emojiFileName = KISSING;
                break;

            case RAGE:
                emojiFileName = RAGE;
                break;

            case SMIRK:
                emojiFileName = SMIRK;
                break;

            case WINK:
                emojiFileName = WINK;
                break;

            case STUCK_OUT_TONGUE_WINKING_EYE:
                emojiFileName = STUCK_OUT_TOUNGUE_WINKING_EYE;
                break;

            case STUCK_OUT_TONGUE:
                emojiFileName = STUCK_OUT_TOUNGUE;
                break;

            case FLUSHED:
                emojiFileName = FLUSHED;
                break;

            case SCREAM:
                emojiFileName = SCREAM;
                break;

            case UNKNOWN:
                emojiFileName = -1;
                break;

            default:
                throw new RuntimeException("Unhandled emoji value");

        }

        if (emojiFileName == -1) {

            return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
        }

        return BitmapFactory.decodeResource(ApplicationObejct.appContext.getResources(), emojiFileName);
    }

}
