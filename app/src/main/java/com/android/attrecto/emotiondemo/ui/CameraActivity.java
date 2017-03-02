package com.android.attrecto.emotiondemo.ui;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.android.attrecto.emotiondemo.R;
import com.android.attrecto.emotiondemo.logic.CameraDetectorManager;
import com.android.attrecto.emotiondemo.object.DimensionHelper;
import com.android.attrecto.emotiondemo.object.FaceHelper;
import com.android.attrecto.emotiondemo.ui.widget.FaceDrawerView;

import java.util.List;
import java.util.Locale;

public class CameraActivity extends BaseActivity implements Detector.ImageListener, Detector.FaceListener {

    private TextView mDominantEmotion;
    private TextView mDominantEmotionPercent;
    private TextView mGender;
    private TextView mAge;
    private TextView mGlasses;
    private ImageView mCameraSwap;

    private View mPullUpContainer;
    private LinearLayout mMainInfoContainer;
    private FloatingActionButton mExpand;
    private FloatingActionButton mCollapse;
    private TextView mDetailDominantEmotion;
    private TextView mDetailDominantEmotionPercent;
    private View mDetailEmotionBackground;
    private TextView[] mDetailEmotionPercents;
    private View mCoverView;
    private BottomSheetBehavior mInfoContainerBehavior;

    private ContentLoadingProgressBar mGenderPb;
    private ContentLoadingProgressBar mAgePb;

    private SurfaceView mCameraView;
    private FaceDrawerView mFaceDrawerView;

    private boolean mCameraDetectorInitialized = false;

    private boolean mIsFaceDetected = false;


    @Override
    protected int getLayoutResId() {

        return R.layout.activity_camera;
    }

    @Override
    protected void acquireReferences() {

        mPullUpContainer = findViewById(R.id.pull_up_view);
        mInfoContainerBehavior = BottomSheetBehavior.from(mPullUpContainer);
        mMainInfoContainer = (LinearLayout) findViewById(R.id.info_container_main_info);
        mExpand = (FloatingActionButton) findViewById(R.id.expand_emotions_button);
        mCollapse = (FloatingActionButton) findViewById(R.id.close_dialog_btn);
        mCoverView = findViewById(R.id.cover_view);
        mDetailEmotionBackground = findViewById(R.id.detail_emotion_background);

        mDetailDominantEmotion = (TextView) findViewById(R.id.detail_dominant_emotion_name);
        mDetailDominantEmotionPercent = (TextView) findViewById(R.id.detail_dominant_emotion_percent);

        mDetailEmotionPercents = new TextView[7];

        mDetailEmotionPercents[0] = (TextView) findViewById(R.id.anger_percent);
        mDetailEmotionPercents[1] = (TextView) findViewById(R.id.contempt_percent);
        mDetailEmotionPercents[2] = (TextView) findViewById(R.id.disgust_percent);
        mDetailEmotionPercents[3] = (TextView) findViewById(R.id.fear_percent);
        mDetailEmotionPercents[4] = (TextView) findViewById(R.id.joy_percent);
        mDetailEmotionPercents[5] = (TextView) findViewById(R.id.sadness_percent);
        mDetailEmotionPercents[6] = (TextView) findViewById(R.id.surprise_percent);


        mDominantEmotion = (TextView) findViewById(R.id.dominant_emotion);
        mDominantEmotionPercent = (TextView) findViewById(R.id.dominant_emotion_percent);
        mGender = (TextView) findViewById(R.id.gender);
        mAge = (TextView) findViewById(R.id.age);
        mGlasses = (TextView) findViewById(R.id.glasses);
        mCameraSwap = (ImageView) findViewById(R.id.swap_camera);

        mGenderPb = (ContentLoadingProgressBar) findViewById(R.id.info_gender_pb);
        mAgePb = (ContentLoadingProgressBar) findViewById(R.id.info_age_pb);

        mCameraView = (SurfaceView) findViewById(R.id.camera_view);
        mFaceDrawerView = (FaceDrawerView) findViewById(R.id.face_drawer_view);

    }

    @Override
    protected void setListeners() {

        mInfoContainerBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, final float slideOffset) {

                setPullUpContainerAlpha(1f - slideOffset);
            }
        });

        mExpand.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (mIsFaceDetected)
                    mInfoContainerBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        mCollapse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mInfoContainerBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


        mCameraSwap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                CameraDetectorManager.getInstance().swapCamera();

                setCameraSwapImage();

                removeInfo();

            }
        });

    }


    @Override
    protected void onAfterAll() {

        mInfoContainerBehavior.setHideable(true);
        mInfoContainerBehavior.setPeekHeight(DimensionHelper.dpToPx(106));
        mInfoContainerBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        mCoverView.setAlpha(0f);

        mGenderPb.hide();
        mAgePb.hide();

        initCameraDetector();

        addCameraDetectorListeners();

        removeInfo();

    }

    @Override
    protected void onResume() {

        super.onResume();

        removeInfo();

        if (mCameraDetectorInitialized) {

            initCameraDetector();
            addCameraDetectorListeners();
        }

        // mFaceDrawerView.invalidate();
    }

    @Override
    protected void onPause() {

        removeCameraDetectorListeners();

        CameraDetectorManager.getInstance().stopDetecting();

        //mFaceDrawerView.invalidate();

        super.onPause();
    }

    @Override
    protected void onStop() {

        mFaceDrawerView.stopListening();

        super.onStop();
    }

    private void setPullUpContainerAlpha(float alpha) {

        mDetailEmotionBackground.setAlpha(alpha);
        mMainInfoContainer.setAlpha(alpha);
        mExpand.setAlpha(alpha);
        mCoverView.setAlpha(1f - alpha);
        mCameraSwap.setAlpha(alpha);
    }

    private void setCameraSwapImage() {

        if (CameraDetectorManager.getInstance().mIsFrontFacingCameraDetected && CameraDetectorManager.getInstance().mIsBackFacingCameraDetected) {

            int r = CameraDetectorManager.getInstance().getCurrentCameraType() == CameraDetector.CameraType.CAMERA_BACK ? R.drawable.camera_rear_white_36 : R.drawable.camera_front_white_36;

            mCameraSwap.setImageResource(r);
        }
        else {

            mCameraSwap.setEnabled(false);
            mCameraSwap.setVisibility(View.GONE);
        }

    }

    private void initCameraDetector() {

        CameraDetectorManager.init(CameraActivity.this, mCameraView);
        CameraDetectorManager.getInstance().startDetecting();

        mCameraDetectorInitialized = true;

        setCameraSwapImage();

    }

    private void addCameraDetectorListeners() {

        CameraDetectorManager.getInstance().addImageListener(this);
        CameraDetectorManager.getInstance().addFaceListener(this);
        mFaceDrawerView.startListening();
    }

    private void removeCameraDetectorListeners() {

        CameraDetectorManager.getInstance().removeImageListener(this);
        CameraDetectorManager.getInstance().removeFaceListener(this);
    }

    private void updateInfo(Face face) {

        updateGender(face.appearance.getGender());
        updateAge(face.appearance.getAge());
        updateGlasses(face.appearance.getGlasses());

        mDominantEmotion.setText(FaceHelper.getDominantEmotionName(face));
        mDominantEmotionPercent.setText(String.format(Locale.getDefault(), "%.0f%%", FaceHelper.getDominantEmotionPercentage(face)));

        mDetailDominantEmotion.setText(FaceHelper.getDominantEmotionName(face));
        mDetailDominantEmotionPercent.setText(String.format(Locale.getDefault(), "%.0f%%", FaceHelper.getDominantEmotionPercentage(face)));

        List<Float> mPercents = FaceHelper.getEmotionPercentList(face);

        for (int i = 0; i < mPercents.size(); i++) {

            float percent = mPercents.get(i);

            mDetailEmotionPercents[i].setTextColor(percent < 1f ? getResources().getColor(R.color.zero_percent_text_color) : Color.WHITE);

            mDetailEmotionPercents[i].setText(String.format(Locale.getDefault(), "%.0f%%", percent));

        }

    }

    private void updateGender(Face.GENDER gender) {

        switch (gender) {

            case UNKNOWN:

                mGender.setText("");
                break;

            case FEMALE:

                mGenderPb.hide();
                mGender.setText(R.string.gender_female);
                break;

            case MALE:

                mGenderPb.hide();
                mGender.setText(R.string.gender_male);
                break;

            default:

                throw new RuntimeException("Unhandled gender");

        }

    }

    private void updateAge(Face.AGE age) {

        switch (age) {

            case AGE_UNKNOWN:

                mAge.setText("");
                break;

            case AGE_UNDER_18:

                mAgePb.hide();
                mAge.setText(R.string.age_under_18);
                break;

            case AGE_18_24:

                mAgePb.hide();
                mAge.setText(R.string.age_18_24);
                break;

            case AGE_25_34:

                mAgePb.hide();
                mAge.setText(R.string.age_25_34);
                break;

            case AGE_35_44:

                mAgePb.hide();
                mAge.setText(R.string.age_35_44);
                break;

            case AGE_45_54:

                mAgePb.hide();
                mAge.setText(R.string.age_45_54);
                break;

            case AGE_55_64:

                mAgePb.hide();
                mAge.setText(R.string.age_55_64);
                break;

            case AGE_65_PLUS:

                mAgePb.hide();
                mAge.setText(R.string.above_65);
                break;

            default:

                throw new RuntimeException("Unhandled age interval");

        }

    }

    private void updateGlasses(Face.GLASSES glasses) {

        switch (glasses) {

            case NO:

                mGlasses.setText(R.string.glasses_no);
                break;

            case YES:

                mGlasses.setText(R.string.glasses_yes);
                break;

        }

    }

    private void removeInfo() {

        mGender.setText("-");
        mAge.setText("-");
        mGlasses.setText("-");

        mDominantEmotion.setText(R.string.no_face); // R.string.emotion
        mDominantEmotionPercent.setText("");

        mDetailDominantEmotion.setText("");
        mDetailDominantEmotionPercent.setText("");

        for (TextView t : mDetailEmotionPercents) {

            t.setTextColor(getResources().getColor(R.color.zero_percent_text_color));
            t.setText("0%");
        }

        mGenderPb.hide();
        mAgePb.hide();

        mFaceDrawerView.invalidate();
    }

    @Override
    public void onImageResults(List<Face> list, Frame frame, float v) {

        final Face face = list.get(0);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                updateInfo(face);
            }
        });

    }

    @Override
    public void onFaceDetectionStarted() {

        mIsFaceDetected = true;
        mInfoContainerBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                mGenderPb.show();
                mAgePb.show();
            }
        });

    }

    @Override
    public void onFaceDetectionStopped() {

        mIsFaceDetected = false;
        mInfoContainerBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                removeInfo();
            }
        });


    }
}
