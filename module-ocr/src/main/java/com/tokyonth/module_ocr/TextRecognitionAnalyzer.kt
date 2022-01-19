package com.tokyonth.module_ocr

import android.content.ContentResolver
import com.google.mlkit.vision.text.TextRecognizer
import androidx.camera.core.ImageProxy
import com.tokyonth.module_core.analyze.Analyzer.OnAnalyzeListener
import com.tokyonth.module_core.AnalyzeResult
import android.graphics.Bitmap
import com.tokyonth.module_core.util.BitmapUtils
import android.net.Uri
import java.io.IOException
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import java.lang.Exception
import com.google.mlkit.vision.text.TextRecognition
import com.tokyonth.module_core.analyze.Analyzer
import com.tokyonth.module_ocr.factory.RecognizerFactory

class TextRecognitionAnalyzer constructor(
    private val contentResolver: ContentResolver,
    options: RecognizerFactory
) : Analyzer<Text?> {

    private var mTextRecognizer: TextRecognizer? = null

    init {
        mTextRecognizer = TextRecognition.getClient(options.createClient())
    }

    override fun analyze(
        imageProxy: ImageProxy,
        listener: OnAnalyzeListener<AnalyzeResult<Text?>>
    ) {
        analyzeBitmap(BitmapUtils.getBitmap(imageProxy), listener)
    }

    override fun analyze(
        uri: Uri, listener:
        OnAnalyzeListener<AnalyzeResult<Text?>>
    ) {
        try {
            analyzeBitmap(BitmapUtils.getBitmapFromContentUri(contentResolver, uri), listener)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun analyzeBitmap(bitmap: Bitmap?, listener: OnAnalyzeListener<AnalyzeResult<Text?>>) {
        try {
            if (bitmap == null)
                return
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            mTextRecognizer!!.process(inputImage)
                .addOnSuccessListener { result: Text ->
                    listener.onSuccess(
                        AnalyzeResult(
                            bitmap,
                            result
                        )
                    )
                }
                .addOnFailureListener { listener.onFailure() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
