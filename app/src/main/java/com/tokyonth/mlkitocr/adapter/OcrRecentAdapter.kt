package com.tokyonth.mlkitocr.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tokyonth.mlkitocr.databinding.ItemOcrRecentBinding
import com.tokyonth.module_common.db.OcrRecent

class OcrRecentAdapter : RecyclerView.Adapter<OcrRecentAdapter.OcrRecentViewHolder>() {

    private var dataArr: MutableList<OcrRecent>? = null

    private var click: ((Int, OcrRecent, Boolean) -> Unit?)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataArr: MutableList<OcrRecent>) {
        this.dataArr = dataArr
        notifyDataSetChanged()
    }

    fun removeData(position: Int) {
        dataArr?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun insertData(position: Int, ocrRecent: OcrRecent) {
        dataArr?.add(position, ocrRecent)
        notifyItemInserted(position)
    }

    fun setDataClick(click: ((Int, OcrRecent, Boolean) -> Unit)) {
        this.click = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OcrRecentViewHolder {
        return OcrRecentViewHolder(ItemOcrRecentBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: OcrRecentViewHolder, position: Int) {
        holder.bind(dataArr?.get(position)!!, click)
    }

    override fun getItemCount(): Int {
        return dataArr?.size ?: 0
    }

    inner class OcrRecentViewHolder(private val binding: ItemOcrRecentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            recent: OcrRecent,
            click: ((Int, OcrRecent, Boolean) -> Unit?)?
        ) {
            binding.root.backgroundTintList =
                ColorStateList.valueOf(recent.color)
            binding.tvOcrRecentContent.text = recent.content
            binding.root.setOnClickListener {
                click?.invoke(adapterPosition, recent, false)
            }
            binding.root.setOnLongClickListener {
                click?.invoke(adapterPosition, recent, true)
                true
            }
        }

    }

}
