package com.android.attrecto.emotiondemo.logic;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.SurfaceView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.android.attrecto.emotiondemo.ApplicationObejct;
import com.android.attrecto.emotiondemo.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Somogyi Bence on 2017.02.22..
 */
public class CameraDetectorManager implements Detector.ImageListener, Detector.FaceListener, CameraDetector.CameraEventListener {

    private static final String PREFERENCES_CAMERA_BACK = "com.android.attrecto.emotiondemo.logic.CameraDetectorManager.preference_camera_back";

    private static CameraDetectorManager mCameraDetectorManagerInstance = null;
    private ArrayList<Detector.ImageListener> mImageListeners;
    private ArrayList<Detector.FaceListener> mFaceListeners;
    private ArrayList<CameraDetector.CameraEventListener> mCameraEventListeners;

    private CameraDetector mCameraDetectorInstance;

    private CameraDetector.CameraType mCurrentCameraType;

    public final boolean mIsFrontFacingCameraDetected;
    public final boolean mIsBackFacingCameraDetected;

    /**
     * @return The only instance of this class
     * @throws RuntimeException if init() has not been called.
     */
    public static CameraDetectorManager getInstance() {

        if (mCameraDetectorManagerInstance == null) {

            throw new RuntimeException("You have to call init() fist. CameraDetector is not attached to any view");
        }

        return mCameraDetectorManagerInstance;

    }

    public static void init(Context context, SurfaceView surfaceView) {

        if (mCameraDetectorManagerInstance == null) {

            mCameraDetectorManagerInstance = new CameraDetectorManager(context, surfaceView);
        }

    }

    private CameraDetectorManager(Context context, SurfaceView cameraPreviewView) {

        mIsFrontFacingCameraDetected = ApplicationObejct.appContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
        mIsBackFacingCameraDetected = ApplicationObejct.appContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

        if (mIsFrontFacingCameraDetected && mIsBackFacingCameraDetected) {

            mCurrentCameraType = PreferencesManager.getInstance().getBoolean(PREFERENCES_CAMERA_BACK, false) ? CameraDetector.CameraType.CAMERA_BACK : CameraDetector.CameraType.CAMERA_FRONT;
        }
        else {
            if (mIsBackFacingCameraDetected)
                mCurrentCameraType = CameraDetector.CameraType.CAMERA_BACK;

            if (mIsFrontFacingCameraDetected)
                mCurrentCameraType = CameraDetector.CameraType.CAMERA_FRONT;


        }

        mCameraDetectorInstance = new CameraDetector(context, mCurrentCameraType, cameraPreviewView, 1, Detector.FaceDetectorMode.LARGE_FACES);

        setupCameraDetector();

        mCameraDetectorInstance.setImageListener(this);
        mCameraDetectorInstance.setFaceListener(this);
        mCameraDetectorInstance.setOnCameraEventListener(this);

        mImageListeners = new ArrayList<>(3);
        mFaceListeners = new ArrayList<>(3);
        mCameraEventListeners = new ArrayList<>(3);

    }

    private void setupCameraDetector() {

        mCameraDetectorInstance.setMaxProcessRate(10);
        mCameraDetectorInstance.setSendUnprocessedFrames(true);
        mCameraDetectorInstance.setDetectAllEmotions(true);
        mCameraDetectorInstance.setDetectGender(true);
        mCameraDetectorInstance.setDetectAge(true);
        mCameraDetectorInstance.setDetectGlasses(true);
        mCameraDetectorInstance.setDetectAllEmojis(true);

    }

    public CameraDetector.CameraType getCurrentCameraType() {

        return mCurrentCameraType;
    }

    public void startDetecting() {

        mCameraDetectorInstance.start();
    }

    public void stopDetecting() {

        mCameraDetectorInstance.stop();
    }

    public void swapCamera() {

        if (!mIsFrontFacingCameraDetected)
            return;

        mCurrentCameraType = mCurrentCameraType == CameraDetector.CameraType.CAMERA_BACK ? CameraDetector.CameraType.CAMERA_FRONT : CameraDetector.CameraType.CAMERA_BACK;

        mCameraDetectorInstance.setCameraType(mCurrentCameraType);

        PreferencesManager.getInstance().putBoolean(PREFERENCES_CAMERA_BACK, mCurrentCameraType == CameraDetector.CameraType.CAMERA_BACK);

    }

    public boolean addImageListener(Detector.ImageListener l) {

        if (!mImageListeners.contains(l) && mImageListeners.size() < 3) {

            mImageListeners.add(l);
            return true;
        }

        return false;
    }

    public void removeImageListener(Detector.ImageListener l) {

        mImageListeners.remove(l);
    }

    public boolean addFaceListener(Detector.FaceListener l) {

        if (!mFaceListeners.contains(l) && mFaceListeners.size() < 3) {

            mFaceListeners.add(l);
            return true;
        }

        return false;
    }

    public void removeFaceListener(Detector.FaceListener l) {

        mFaceListeners.remove(l);
    }

    public boolean addCameraEventListener(CameraDetector.CameraEventListener l) {

        if (!mCameraEventListeners.contains(l) && mCameraEventListeners.size() < 3) {

            mCameraEventListeners.add(l);
            return true;
        }

        return false;
    }

    public void removeCameraEventListener(CameraDetector.CameraEventListener l) {

        mCameraEventListeners.remove(l);
    }

    private void sendImageResultsToAllListeners(List<Face> faces, Frame frame, float timestamp) {

        int i = 0;
        for (Detector.ImageListener l : mImageListeners) {

            l.onImageResults(faces, frame, timestamp);
        }
    }

    private void sendFaceDetectionToAllListeners(boolean detected) {

        if (detected) {
            for (Detector.FaceListener l : mFaceListeners) {

                l.onFaceDetectionStarted();
            }
        }
        else {

            for (Detector.FaceListener l : mFaceListeners) {

                l.onFaceDetectionStopped();
            }
        }

    }

    private void sendCameraEventsToAllListeners(int i, int i1, Frame.ROTATE rotate) {

        for (CameraDetector.CameraEventListener l : mCameraEventListeners) {

            l.onCameraSizeSelected(i, i1, rotate);
        }

    }

    /**
     * @param list  list of faces
     * @param frame
     * @param v     timestamp
     */
    @Override
    public void onImageResults(List<Face> list, Frame frame, float v) {

        // no frame processed or no faces found
        if (list == null || list.size() == 0)
            return;

        sendImageResultsToAllListeners(list, frame, v);
    }

    @Override
    public void onFaceDetectionStarted() {

        sendFaceDetectionToAllListeners(true);

    }

    @Override
    public void onFaceDetectionStopped() {

        sendFaceDetectionToAllListeners(false);

    }

    /**
     * @param i      cameraWidth
     * @param i1     cameraHeight
     * @param rotate
     */
    @Override
    public void onCameraSizeSelected(int i, int i1, Frame.ROTATE rotate) {

        sendCameraEventsToAllListeners(i, i1, rotate);

    }
}
