package com.elevenine.artinstitute.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elevenine.artinstitute.databinding.ItemCategoryBinding
import com.elevenine.artinstitute.ui.model.Category

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

class CategoriesAdapter(private val onItemClickListener: (categoryId: Long) -> Unit) :
    ListAdapter<Category, CategoriesAdapter.CategoryVH>(CategoryDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        return CategoryVH(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.onBind(currentList[position])
    }

    class CategoryDiffUtils : DiffUtil.ItemCallback<Category>() {

        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }

    inner class CategoryVH(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(category: Category) {
            binding.run {
                root.setOnClickListener {
                    onItemClickListener(category.id.toLong())
                }

                tvName.text = category.title
            }
        }
    }
}