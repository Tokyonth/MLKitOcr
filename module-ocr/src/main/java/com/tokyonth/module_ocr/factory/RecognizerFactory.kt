package com.tokyonth.module_ocr.factory

import com.google.mlkit.vision.text.TextRecognizerOptionsInterface

interface RecognizerFactory {

    fun createClient(): TextRecognizerOptionsInterface

}
