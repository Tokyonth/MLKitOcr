/*
 * Copyright (C) Jenly, MLKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tokyonth.module_core;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;

import com.tokyonth.module_core.analyze.Analyzer;
import com.tokyonth.module_core.config.CameraConfig;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 *
 * Modify 2021.12.30 by Tokyonth
 */
public abstract class CameraScan<T> implements ICamera, ICameraControl {

    public static String SCAN_RESULT = "SCAN_RESULT";

    /**
     * A camera on the device facing the same direction as the device's screen.
     */
    public static int LENS_FACING_FRONT = CameraSelector.LENS_FACING_FRONT;
    /**
     * A camera on the device facing the opposite direction as the device's screen.
     */
    public static int LENS_FACING_BACK = CameraSelector.LENS_FACING_BACK;

    /**
     * 是否需要支持触摸缩放
     */
    private boolean isNeedTouchZoom = true;

    /**
     * 是否需要支持触摸缩放
     */
    protected boolean isNeedTouchZoom() {
        return isNeedTouchZoom;
    }

    /**
     * 设置是否需要支持触摸缩放
     */
    public CameraScan<T> setNeedTouchZoom(boolean needTouchZoom) {
        isNeedTouchZoom = needTouchZoom;
        return this;
    }

    /**
     * 设置相机配置，请在{@link #startCamera()}之前调用
     */
    public abstract CameraScan<T> setCameraConfig(CameraConfig cameraConfig);

    /**
     * 设置是否分析图像，通过此方法可以动态控制是否分析图像，常用于中断扫码识别。如：连扫时，扫到结果，然后停止分析图像
     * <p>
     * 1. 因为分析图像默认为true，如果想支持连扫，在{@link OnScanResultCallback#onScanResultCallback(AnalyzeResult)}返回true拦截即可。
     * 当连扫的处理逻辑比较复杂时，请在处理逻辑前通过调用setAnalyzeImage(false)来停止分析图像，
     * 等逻辑处理完后再调用getCameraScan().setAnalyzeImage(true)来继续分析图像。
     * <p>
     * 2. 如果只是想拦截扫码结果回调自己处理逻辑，但并不想继续分析图像（即不想连扫），可通过
     * 调用getCameraScan().setAnalyzeImage(false)来停止分析图像。
     */
    public abstract CameraScan<T> setAnalyzeImage(boolean analyze);

    /**
     * 设置分析器，如果内置的一些分析器不满足您的需求，你也可以自定义{@link Analyzer}，
     * 自定义时，切记需在{@link #startCamera()}之前调用才有效。
     */
    public abstract CameraScan<T> setAnalyzer(Analyzer<T> analyzer);

    /**
     * 设置是否震动
     */
    public abstract CameraScan<T> setVibrate(boolean vibrate);

    /**
     * 设置是否播放提示音
     */
    public abstract CameraScan<T> setPlayBeep(boolean playBeep);

    /**
     * 设置扫码结果回调
     */
    public abstract CameraScan<T> setOnScanResultCallback(OnScanResultCallback<T> callback);

    /**
     * 绑定手电筒，绑定后可根据光线传感器，动态显示或隐藏手电筒
     */
    public abstract CameraScan<T> bindFlashlightView(@Nullable View v);

    /**
     * 设置光线足够暗的阈值（单位：lux），需要通过{@link #bindFlashlightView(View)}绑定手电筒才有效
     */
    public abstract CameraScan<T> setDarkLightLux(float lightLux);

    /**
     * 设置光线足够明亮的阈值（单位：lux），需要通过{@link #bindFlashlightView(View)}绑定手电筒才有效
     */
    public abstract CameraScan<T> setBrightLightLux(float lightLux);

    public interface OnScanResultCallback<T> {
        /**
         * 扫码结果回调
         */
        void onScanResultCallback(@NonNull AnalyzeResult<T> result);

        /**
         * 扫码结果识别失败时触发此回调方法
         */
        default void onScanResultFailure() {

        }

    }

    /**
     * 解析扫码结果
     */
    @Nullable
    public static String parseScanResult(Intent data) {
        if (data != null) {
            return data.getStringExtra(SCAN_RESULT);
        }
        return null;
    }

    /**
     * 分析图片
     */
    public abstract CameraScan<T> parseImage(Uri uri);

}
