package com.android.attrecto.emotiondemo.object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.affectiva.android.affdex.sdk.detector.Face;
import com.android.attrecto.emotiondemo.ApplicationObejct;
import com.android.attrecto.emotiondemo.R;


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
    private static final int STUCK_OUT_TONGUE_WINKING_EYE = R.drawable.emoji_stuck_out_tongue_winking_eye;
    private static final int STUCK_OUT_TONGUE = R.drawable.emoji_stuck_out_tonge;
    private static final int WINK = R.drawable.emoji_wink;

    private static String DISAPPOINTED_NAME = "DISAPPOINTED";
    private static String FLUSHED_NAME = "FLUSHED";
    private static String KISSING_NAME = "KISSING";
    private static String LAUGHING_NAME = "LAUGHING ";
    private static String RAGE_NAME = "RAGE";
    private static String RELAXED_NAME = "RELAXED";
    private static String SCREAM_NAME = "SCREAM";
    private static String SMILEY_NAME = "SMILEY";
    private static String SMIRK_NAME = "SMIRK ";
    private static String STUCK_OUT_TOUNGUE_WINKING_EYE_NAME = "STUCK_OUT_TONGUE_WINKING_EYE";
    private static String STUCK_OUT_TOUNGUE_NAME = "STUCK_OUT_TONGUE";
    private static String WINK_NAME = "WINK";

    private static final String EXT = ".png";

    private EmojiHelper() {

    }

    public static Bitmap getDominantEmoji(Face face) {

        int emojiRes = -1;
        Bitmap re = null;

        switch (face.emojis.getDominantEmoji()) {

            case DISAPPOINTED:
                emojiRes = DISAPPOINTED;
                break;

            case RELAXED:
                emojiRes = RELAXED;
                break;

            case SMILEY:
                emojiRes = SMILEY;
                break;

            case LAUGHING:
                emojiRes = LAUGHING;
                break;

            case KISSING:
                emojiRes = KISSING;
                break;

            case RAGE:
                emojiRes = RAGE;
                break;

            case SMIRK:
                emojiRes = SMIRK;
                break;

            case WINK:
                emojiRes = WINK;
                break;

            case STUCK_OUT_TONGUE_WINKING_EYE:
                emojiRes = STUCK_OUT_TONGUE_WINKING_EYE;
                break;

            case STUCK_OUT_TONGUE:
                emojiRes = STUCK_OUT_TONGUE;
                break;

            case FLUSHED:
                emojiRes = FLUSHED;
                break;

            case SCREAM:
                emojiRes = SCREAM;
                break;

            case UNKNOWN:
                emojiRes = -1;
                break;

            default:
                throw new RuntimeException("Unhandled emoji value");

        }

        if (emojiRes == -1) {

            return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
        }

        return getBitmapFromEmojiRes(emojiRes);
    }

    public static Bitmap getBitmapFromEmojiRes(int emojiRes) {

        return BitmapFactory.decodeResource(ApplicationObejct.appContext.getResources(), emojiRes);
    }

    public static Bitmap[] getAllEmojis() {

        return new Bitmap[]{
                getBitmapFromEmojiRes(DISAPPOINTED),
                getBitmapFromEmojiRes(FLUSHED),
                getBitmapFromEmojiRes(KISSING),
                getBitmapFromEmojiRes(LAUGHING),
                getBitmapFromEmojiRes(RAGE),
                getBitmapFromEmojiRes(RELAXED),
                getBitmapFromEmojiRes(SCREAM),
                getBitmapFromEmojiRes(SMILEY),
                getBitmapFromEmojiRes(SMIRK),
                getBitmapFromEmojiRes(STUCK_OUT_TONGUE_WINKING_EYE),
                getBitmapFromEmojiRes(STUCK_OUT_TONGUE),
                getBitmapFromEmojiRes(WINK)
        };
    }

    public static int[] getAllEmojiRes() {

        return new int[]{
                DISAPPOINTED,
                FLUSHED,
                KISSING,
                LAUGHING,
                RAGE,
                RELAXED,
                SCREAM,
                SMILEY,
                SMIRK,
                STUCK_OUT_TONGUE_WINKING_EYE,
                STUCK_OUT_TONGUE,
                WINK
        };

    }

    public static String[] getAllEmojiNames() {

        return ApplicationObejct.appContext.getResources().getStringArray(R.array.emojis);
    }


    /*
    DISAPPOINTED
    FLUSHED
    KISSING
    LAUGHING
    RAGE
    RELAXED
    SCREAM
    SMILEY
    SMIRK
    STUCK_OUT_TONGUE_WINKING_EYE
    STUCK_OUT_TONGUE
    WINK
     */

}
