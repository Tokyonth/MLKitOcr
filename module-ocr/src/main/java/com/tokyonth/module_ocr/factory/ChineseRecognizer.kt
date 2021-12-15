package com.tokyonth.module_ocr.factory

import com.google.mlkit.vision.text.TextRecognizerOptionsInterface
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions

class ChineseRecognizer : RecognizerFactory {

    override fun createClient(): TextRecognizerOptionsInterface {
        return ChineseTextRecognizerOptions.Builder().build()
    }

}
