package com.android.attrecto.emotiondemo.ui.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.android.attrecto.emotiondemo.ApplicationObejct;
import com.android.attrecto.emotiondemo.logic.CameraDetectorManager;
import com.android.attrecto.emotiondemo.object.EmojiHelper;

import java.util.List;

/**
 * Created by Somogyi Bence on 2017.02.22..
 */

public class FaceDrawerView extends View implements Detector.ImageListener, Detector.FaceListener, CameraDetector.CameraEventListener {

    private static final float STROKE_WIDTH = 3f;

    private Paint mPointsPaint;
    private Paint mRectanglePaint;

    private FaceAttributes mFaceAttributes;
    private ViewConfig mViewConfig;

    public FaceDrawerView(Context context) {

        super(context);

        init();

    }

    public FaceDrawerView(Context context, AttributeSet attrs) {

        super(context, attrs);

        init();

    }

    private void init() {

        setWillNotDraw(false);

        initPaints();

        mFaceAttributes = new FaceAttributes();
        mViewConfig = new ViewConfig();

    }

    private void initPaints() {

        mPointsPaint = new Paint();
        mPointsPaint.setColor(Color.WHITE);
        mPointsPaint.setStyle(Paint.Style.FILL);
        mPointsPaint.setStrokeWidth(STROKE_WIDTH);

        mRectanglePaint = new Paint();
        mRectanglePaint.setColor(Color.WHITE);
        mRectanglePaint.setStyle(Paint.Style.STROKE);
        mRectanglePaint.setStrokeWidth(STROKE_WIDTH);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if (!mFaceAttributes.shouldDraw)
            return;


        for (PointF point : mFaceAttributes.getFacePoints()) {

            canvas.drawCircle(point.x, point.y, STROKE_WIDTH, mPointsPaint);
        }

        Rect faceRect = mFaceAttributes.getFaceRect();

        canvas.drawRect(faceRect, mRectanglePaint);

        canvas.drawBitmap(mFaceAttributes.getDominantEmoji(), faceRect.right, faceRect.top, null);


    }

    public void startListening() {

        CameraDetectorManager.getInstance().addImageListener(this);
        CameraDetectorManager.getInstance().addFaceListener(this);
        CameraDetectorManager.getInstance().addCameraEventListener(this);

    }

    public void stopListening() {

        CameraDetectorManager.getInstance().removeImageListener(this);
        CameraDetectorManager.getInstance().removeFaceListener(this);
        CameraDetectorManager.getInstance().removeCameraEventListener(this);

    }

    private class FaceAttributes {

        private PointF[] facePoints;
        private Rect faceRect;
        boolean shouldDraw = false;
        private Bitmap dominantEmoji;

        void setFacePoints(PointF[] facePoints, boolean isMirrored) {

            this.facePoints = facePoints;

            Rect rect = new Rect(mViewConfig.imageWidth, mViewConfig.imageHeight, 0, 0);

            for (PointF point : this.facePoints) {

                if (isMirrored) {

                    point.x = mViewConfig.imageWidth - point.x;
                }

                point.x *= mViewConfig.screenToImageWidthRatio;

                point.y *= mViewConfig.screenToImageWidthRatio;

                if (isMirrored) {

                    point.y += ViewConfig.POINTS_MIRRORED_SHIFT_Y;
                }
                else {

                    point.y += ViewConfig.POINTS_SHIFT_Y;
                }

                rect.union(Math.round(point.x), Math.round(point.y));
                rect.union(Math.round(point.x), Math.round(point.y));

            }

            float diff = STROKE_WIDTH * 5f;

            rect.left -= diff;
            rect.top -= diff;
            rect.right += diff;
            rect.bottom += diff;

            faceRect = rect;

            shouldDraw = true;
        }

        void setDominantEmoji(Bitmap emoji) {

            dominantEmoji = emoji;
        }

        Bitmap getDominantEmoji() {

            return dominantEmoji;
        }

        PointF[] getFacePoints() {

            return facePoints;
        }

        Rect getFaceRect() {

            return faceRect;
        }

    }

    private class ViewConfig {

        public static final float POINTS_SHIFT_Y = -50f;
        public static final float POINTS_MIRRORED_SHIFT_Y = 20f;

        private int viewWidth = 1;
        private int viewHeight = 1;
        private int imageWidth = 1;
        private int imageHeight = 1;
        private float screenToImageWidthRatio = 1;
        private float screenToImageHeightRatio = 1;

        public ViewConfig() {

            DisplayMetrics displaymetrics = new DisplayMetrics();

            ((WindowManager) ApplicationObejct.appContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);

            viewWidth = displaymetrics.widthPixels;
            viewHeight = displaymetrics.heightPixels;
        }

        public void updateCameraDimensions(int imageWidth, int imageHeight) {

            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;

            mFaceAttributes.shouldDraw = false;

            calculateRatio();

        }

        private void calculateRatio() {

            screenToImageWidthRatio = (float) viewWidth / imageWidth;
            screenToImageHeightRatio = (float) viewHeight / imageHeight;
        }

    }


    @Override
    public void onImageResults(List<Face> list, Frame frame, float v) {

        Face face = list.get(0);

        mFaceAttributes.setFacePoints(face.getFacePoints(), CameraDetectorManager.getInstance().getCurrentCameraType() == CameraDetector.CameraType.CAMERA_FRONT);
        mFaceAttributes.setDominantEmoji(EmojiHelper.getDominantEmoji(face));

        post(new Runnable() {

            @Override
            public void run() {

                invalidate();
            }
        });

    }


    @Override
    public void onFaceDetectionStarted() {

    }

    @Override
    public void onFaceDetectionStopped() {

        mFaceAttributes.shouldDraw = false;

        post(new Runnable() {

            @Override
            public void run() {

                invalidate();
            }
        });

    }

    @Override
    public void onCameraSizeSelected(int i, int i1, Frame.ROTATE rotate) {

        if (rotate == Frame.ROTATE.BY_90_CCW || rotate == Frame.ROTATE.BY_90_CW) {

            mViewConfig.updateCameraDimensions(i1, i);
        }
        else {

            mViewConfig.updateCameraDimensions(i, i1);

        }

    }

}