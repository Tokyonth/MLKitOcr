package com.tokyonth.mlkitocr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tokyonth.mlkitocr.databinding.ItemOcrRecentBinding

class OcrRecentAdapter : RecyclerView.Adapter<OcrRecentAdapter.OcrRecentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OcrRecentViewHolder {
        return OcrRecentViewHolder(ItemOcrRecentBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: OcrRecentViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class OcrRecentViewHolder(binding: ItemOcrRecentBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}
