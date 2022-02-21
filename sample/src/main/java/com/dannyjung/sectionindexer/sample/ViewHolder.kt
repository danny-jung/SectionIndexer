package com.dannyjung.sectionindexer.sample

import androidx.recyclerview.widget.RecyclerView
import com.dannyjung.sectionindexer.sample.databinding.ViewItemBinding

class ViewHolder(
    private val viewBinding: ViewItemBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: Brand) {
        viewBinding.item.text = item.name
    }
}
