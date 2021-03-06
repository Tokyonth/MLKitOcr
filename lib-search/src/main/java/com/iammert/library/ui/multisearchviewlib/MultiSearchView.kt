package com.iammert.library.ui.multisearchviewlib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.iammert.library.ui.multisearchviewlib.databinding.ViewMultiSearchBinding

class MultiSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val binding: ViewMultiSearchBinding =
        ViewMultiSearchBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MultiSearchView,
            defStyleAttr,
            defStyleAttr
        )
        val searchTextStyle =
            typedArray.getResourceId(R.styleable.MultiSearchView_searchTextStyle, 0)

        binding.searchViewContainer.apply {
            this.searchTextStyle = searchTextStyle
        }

        binding.imageViewSearch.setOnClickListener {
            if (binding.searchViewContainer.isInSearchMode().not()) {
                binding.searchViewContainer.search()
            } else {
                binding.searchViewContainer.completeSearch()
            }
        }
    }

    fun setSearchViewListener(multiSearchViewListener: MultiSearchViewListener) {
        binding.searchViewContainer.setSearchViewListener(multiSearchViewListener)
    }

}
