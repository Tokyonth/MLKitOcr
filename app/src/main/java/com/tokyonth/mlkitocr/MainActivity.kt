package com.tokyonth.mlkitocr

import android.util.Log
import com.google.mlkit.vision.text.Text
import com.tokyonth.module_core.AnalyzeResult
import com.tokyonth.module_ocr.TextCameraScanActivity

class MainActivity : TextCameraScanActivity() {

    override fun onScanResultCallback(result: AnalyzeResult<Text>) {
        Log.e("dd->", result.result.text)
    }

}
