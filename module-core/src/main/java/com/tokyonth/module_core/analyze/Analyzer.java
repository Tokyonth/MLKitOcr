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
package com.tokyonth.module_core.analyze;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageProxy;

import com.tokyonth.module_core.AnalyzeResult;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * Modify 2021.12.30 by Tokyonth
 */
public interface Analyzer<T> {
    /**
     * Analyzes an image to produce a result.
     *
     * @param imageProxy The image to analyze
     */
    void analyze(@NonNull ImageProxy imageProxy, @NonNull OnAnalyzeListener<AnalyzeResult<T>> listener);

    void analyze(@NonNull Uri uri, @NonNull OnAnalyzeListener<AnalyzeResult<T>> listener);

    interface OnAnalyzeListener<T> {
        void onSuccess(@NonNull T result);

        void onFailure();
    }

}
