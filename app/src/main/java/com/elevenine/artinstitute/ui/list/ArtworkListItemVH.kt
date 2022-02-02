package com.elevenine.artinstitute.ui.list

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.elevenine.artinstitute.databinding.ItemArtworkBinding
import com.elevenine.artinstitute.databinding.ItemArtworkLoadingBinding
import com.elevenine.artinstitute.ui.model.Artwork
import com.elevenine.artinstitute.ui.model.ArtworkListItem

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

abstract class ArtworkListItemVH(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun onBind(artworkListItem: ArtworkListItem)
}

class ArtworkVH(private val binding: ItemArtworkBinding) : ArtworkListItemVH(binding) {

    private val context = binding.root.context

    override fun onBind(artworkListItem: ArtworkListItem) {
        if (artworkListItem !is Artwork) return

        with(artworkListItem) {
            Glide.with(context)
                .load(imageUrl)
                .into(binding.imgArt)

            binding.tvTitle.text = artworkListItem.title
        }
    }
}

class ArtworkLoadingVH(binding: ItemArtworkLoadingBinding) : ArtworkListItemVH(binding) {

    override fun onBind(artworkListItem: ArtworkListItem) {}
}