package com.elevenine.artinstitute.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elevenine.artinstitute.databinding.ItemArtworkBinding
import com.elevenine.artinstitute.ui.model.Artwork

/**
 * @author Sherzod Nosirov
 * @since 26.01.2022
 */

class ArtListAdapter : ListAdapter<Artwork, ArtworkVH>(ArtworkDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtworkVH {
        return ArtworkVH(
            ItemArtworkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArtworkVH, position: Int) {
        holder.onBind(currentList[position])
    }

}

class ArtworkDiffUtils : DiffUtil.ItemCallback<Artwork>() {

    override fun areItemsTheSame(oldItem: Artwork, newItem: Artwork): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Artwork, newItem: Artwork): Boolean {
        return oldItem == newItem
    }

}

class ArtworkVH(private val binding: ItemArtworkBinding) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    fun onBind(artwork: Artwork) {
        with(artwork) {
            Glide.with(context)
                .load(imageUrl)
                .into(binding.imgArt)

            binding.tvTitle.text = artwork.title
        }
    }
}