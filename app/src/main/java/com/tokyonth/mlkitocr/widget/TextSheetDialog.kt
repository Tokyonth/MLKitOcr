package com.tokyonth.mlkitocr.widget

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tokyonth.mlkitocr.R
import com.tokyonth.mlkitocr.databinding.LayoutTextSheetDialogBinding

class TextSheetDialog(context: Context) : BottomSheetDialog(context, R.style.BottomSheetDialog) {

    private val vb = LayoutTextSheetDialogBinding.inflate(LayoutInflater.from(context))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        vb.tvOcrText.setTextIsSelectable(true)
    }

    fun setOcrText(text: String) {
        vb.tvOcrText.text = text
    }

}
