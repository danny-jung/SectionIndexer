package com.dannyjung.sectionindexer.sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dannyjung.sectionindexer.sample.databinding.ViewItemBinding

class Adapter : ListAdapter<Brand, ViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffUtil : DiffUtil.ItemCallback<Brand>() {

    override fun areItemsTheSame(oldItem: Brand, newItem: Brand) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Brand, newItem: Brand) =
        oldItem == newItem
}
