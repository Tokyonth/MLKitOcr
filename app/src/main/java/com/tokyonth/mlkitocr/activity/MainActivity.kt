package com.tokyonth.mlkitocr.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.WindowCompat
import com.google.mlkit.vision.text.Text
import com.tokyonth.mlkitocr.databinding.ActivityMainBinding
import com.tokyonth.mlkitocr.widget.TextSheetDialog
import com.tokyonth.module_core.AnalyzeResult
import com.tokyonth.module_core.BaseCameraScanActivity
import com.tokyonth.module_core.analyze.Analyzer
import com.tokyonth.module_ocr.TextRecognitionAnalyzer
import com.tokyonth.module_ocr.factory.ChineseRecognizer

class MainActivity : BaseCameraScanActivity<Text>() {

    private lateinit var vb: ActivityMainBinding

    private lateinit var textSheetDialog: TextSheetDialog

    private lateinit var resultPhotoLauncher: ActivityResultLauncher<String>

    override fun setViewBinding() = vb

    override fun setPreviewView() = vb.previewView

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            isAppearanceLightStatusBars = false
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
        vb = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        cameraScan.setAnalyzeImage(false)

        resultPhotoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            cameraScan.setAnalyzeImage(true)
            cameraScan.parseImage(it)
        }
    }

    override fun initView() {
        super.initView()
        textSheetDialog = TextSheetDialog(this)
        vb.tvOcrStart.setOnClickListener {
            cameraScan.setAnalyzeImage(true)
        }
        vb.ivFormImage.setOnClickListener {
            resultPhotoLauncher.launch("image/*")
        }
    }

    override fun onScanResultCallback(result: AnalyzeResult<Text>) {
        textSheetDialog.apply {
            setOcrText(result.result.text)
        }.show()
        cameraScan.setAnalyzeImage(false)
    }

    override fun createAnalyzer(): Analyzer<Text?> {
        return TextRecognitionAnalyzer(contentResolver, ChineseRecognizer())
    }

    override fun onDestroy() {
        super.onDestroy()
        resultPhotoLauncher.unregister()
    }

}
