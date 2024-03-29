/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.example.android.gdgfinders.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.gdgfinders.network.GdgChapter
import com.example.android.gdgfinders.search.GdgListAdapter.GdgListViewHolder
import com.example.android.gdgfinders.databinding.ListItemBinding

class GdgListAdapter(val clickListener: GdgClickListener): ListAdapter<GdgChapter, GdgListViewHolder>(DiffCallback){
    companion object DiffCallback : DiffUtil.ItemCallback<GdgChapter>() {
        override fun areItemsTheSame(oldItem: GdgChapter, newItem: GdgChapter): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GdgChapter, newItem: GdgChapter): Boolean {
            return oldItem == newItem
        }
    }

    class GdgListViewHolder(private var binding: ListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: GdgClickListener, gdgChapter: GdgChapter) {
            binding.chapter = gdgChapter
            binding.clickListener = listener
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            // Это важно, потому что это заставляет привязку данных выполняться немедленно,
            // что позволяет RecyclerView производить правильные измерения размера вида
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GdgListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return GdgListViewHolder(binding)
            }
        }
    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs a new [ViewHolder].
     * Часть адаптера RecyclerView, вызываемого, когда RecyclerView нуждается в новом [ViewHolder].
     *
     * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
     * to the RecyclerView such as where on the screen it was last drawn during scrolling.
     * Держатель вида содержит представление для [RecyclerView], а также предоставляет дополнительную информацию
     * к RecyclerView, например, где на экране он был в последний раз нарисован во время прокрутки.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GdgListViewHolder {
        return GdgListViewHolder.from(parent)

    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs to show an item.
     * Часть адаптера RecyclerView, вызываемого, когда RecyclerView должен показать элемент.
     *
     * The ViewHolder passed may be recycled, so make sure that this sets any properties that
     * may have been set previously.
     * Переданный держатель Вида может быть переработан, поэтому убедитесь, что он задает любые свойства, которые
     * возможно, они были установлены ранее.
     */
    override fun onBindViewHolder(holder: GdgListViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }
}

class GdgClickListener(val clickListener: (chapter: GdgChapter) -> Unit) {
    fun onClick(chapter: GdgChapter) = clickListener(chapter)
}
