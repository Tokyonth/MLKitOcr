package com.tokyonth.module_core;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;
import androidx.viewbinding.ViewBinding;

import com.tokyonth.module_common.base.BaseActivity;
import com.tokyonth.module_core.analyze.Analyzer;
import com.tokyonth.module_core.util.PermissionUtils;

public abstract class BaseCameraScanActivity<VB extends ViewBinding, T>
        extends BaseActivity<VB> implements CameraScan.OnScanResultCallback<T> {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 0x996;

    protected abstract PreviewView setPreviewView();

    private CameraScan<T> mCameraScan;

    @Override
    public boolean isLightStatusBar() {
        return false;
    }

    @Override
    public void initData() {
        initCameraScan();
        startCamera();
    }

    @Override
    public void initView() {

    }

    public void initCameraScan() {
        mCameraScan = createCameraScan(setPreviewView())
                .setAnalyzer(createAnalyzer())
                .setOnScanResultCallback(this);
    }

    public void startCamera() {
        if (mCameraScan != null) {
            if (PermissionUtils.checkPermission(this, Manifest.permission.CAMERA)) {
                mCameraScan.startCamera();
            } else {
                PermissionUtils.requestPermission(this, Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void releaseCamera() {
        if (mCameraScan != null) {
            mCameraScan.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            requestCameraPermissionResult(permissions, grantResults);
        }
    }

    public void requestCameraPermissionResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionUtils.requestPermissionsResult(Manifest.permission.CAMERA, permissions, grantResults)) {
            startCamera();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        releaseCamera();
        super.onDestroy();
    }

    public CameraScan<T> getCameraScan() {
        return mCameraScan;
    }

    public CameraScan<T> createCameraScan(PreviewView previewView) {
        return new BaseCameraScan<>(this, previewView);
    }

    @Nullable
    public abstract Analyzer<T> createAnalyzer();

}
