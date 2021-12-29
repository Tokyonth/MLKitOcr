package com.tokyonth.mlkitocr.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.mlkit.vision.text.Text
import com.tokyonth.mlkitocr.adapter.OcrRecentAdapter
import com.tokyonth.mlkitocr.databinding.ActivityMainBinding
import com.tokyonth.mlkitocr.view.GridDividerItemDecoration
import com.tokyonth.mlkitocr.view.ProductItemDecoration
import com.tokyonth.mlkitocr.widget.TextSheetDialog
import com.tokyonth.module_core.AnalyzeResult
import com.tokyonth.module_core.BaseCameraScanActivity
import com.tokyonth.module_core.analyze.Analyzer
import com.tokyonth.module_ocr.TextRecognitionAnalyzer
import com.tokyonth.module_ocr.factory.ChineseRecognizer

class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            isAppearanceLightStatusBars = true
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
        initView()
    }

    private fun initView() {
        vb.rvOcrRecent.apply {
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            adapter = OcrRecentAdapter()
            addItemDecoration(ProductItemDecoration(10))
        }
    }

}
