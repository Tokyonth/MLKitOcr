package com.tokyonth.mlkitocr

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.google.mlkit.vision.text.Text
import com.tokyonth.mlkitocr.databinding.ActivityMainBinding
import com.tokyonth.mlkitocr.widget.TextSheetDialog
import com.tokyonth.module_core.AnalyzeResult
import com.tokyonth.module_core.BaseCameraScanActivity
import com.tokyonth.module_core.analyze.Analyzer
import com.tokyonth.module_ocr.analyze.TextRecognitionAnalyzer

class MainActivity : BaseCameraScanActivity<Text>() {

    private lateinit var vb : ActivityMainBinding

    private lateinit var dialog: TextSheetDialog

    override fun setVb() = vb

    override fun getPreviewView() = vb.previewView

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            isAppearanceLightStatusBars = false
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
        vb = ActivityMainBinding.inflate(layoutInflater)

        initView()

        super.onCreate(savedInstanceState)
        cameraScan.setAnalyzeImage(false)
    }

    override fun onScanResultCallback(result: AnalyzeResult<Text>) {
        dialog.apply {
            setOcrText(result.result.text)
        }.show()
        cameraScan.setAnalyzeImage(false)
    }

    override fun createAnalyzer(): Analyzer<Text> {
        return TextRecognitionAnalyzer()
    }

    private fun initView() {
        dialog = TextSheetDialog(this)
        vb.tvOcrStart.setOnClickListener {
            cameraScan.setAnalyzeImage(true)
        }
    }

}
