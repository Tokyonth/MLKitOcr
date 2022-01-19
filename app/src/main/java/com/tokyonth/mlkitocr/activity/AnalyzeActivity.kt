package com.tokyonth.mlkitocr.activity

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.mlkit.vision.text.Text
import com.tokyonth.mlkitocr.databinding.ActivityAnalyzeBinding
import com.tokyonth.mlkitocr.viewmodel.RecentDataViewModel
import com.tokyonth.mlkitocr.widget.TextSheetDialog
import com.tokyonth.module_core.AnalyzeResult
import com.tokyonth.module_core.BaseCameraScanActivity
import com.tokyonth.module_core.analyze.Analyzer
import com.tokyonth.module_ocr.TextRecognitionAnalyzer
import com.tokyonth.module_ocr.factory.ChineseRecognizer

class AnalyzeActivity : BaseCameraScanActivity<ActivityAnalyzeBinding, Text>() {

    private val viewModel: RecentDataViewModel by viewModels()

    private lateinit var textSheetDialog: TextSheetDialog

    private lateinit var resultPhotoLauncher: ActivityResultLauncher<String>

    override fun setPreviewView() = vb.previewView

    override fun initData() {
        super.initData()
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
        val content = result.result.text
        textSheetDialog.apply {
            setOcrText(content)
        }.show()
        cameraScan.setAnalyzeImage(false)
        if (content.isNotEmpty()) {
            viewModel.insertData(content)
        }
    }

    override fun createAnalyzer(): Analyzer<Text?> {
        return TextRecognitionAnalyzer(contentResolver, ChineseRecognizer())
    }

    override fun onDestroy() {
        super.onDestroy()
        resultPhotoLauncher.unregister()
    }

}
