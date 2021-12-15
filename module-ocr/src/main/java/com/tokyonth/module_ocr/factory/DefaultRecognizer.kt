package com.tokyonth.module_ocr.factory

import com.google.mlkit.vision.text.TextRecognizerOptionsInterface

class DefaultRecognizer(private val options: TextRecognizerOptionsInterface) : RecognizerFactory {

    override fun createClient(): TextRecognizerOptionsInterface {
        return options
    }

}
