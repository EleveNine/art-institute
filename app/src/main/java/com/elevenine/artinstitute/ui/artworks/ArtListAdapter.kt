package com.elevenine.artinstitute.ui.artworks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.elevenine.artinstitute.R
import com.elevenine.artinstitute.databinding.ItemArtworkBinding
import com.elevenine.artinstitute.databinding.ItemArtworkLoadingBinding
import com.elevenine.artinstitute.ui.model.ArtworkListItem

/**
 * @author Sherzod Nosirov
 * @since 26.01.2022
 */

class ArtListAdapter : ListAdapter<ArtworkListItem, ArtworkListItemVH>(ArtworkDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtworkListItemVH {
        return when (viewType) {
            R.layout.item_artwork -> ArtworkVH(
                ItemArtworkBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ArtworkLoadingVH(
                ItemArtworkLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
    }

    override fun onBindViewHolder(holder: ArtworkListItemVH, position: Int) {
        holder.onBind(currentList[position])
    }
}

class ArtworkDiffUtils : DiffUtil.ItemCallback<ArtworkListItem>() {

    override fun areItemsTheSame(oldItem: ArtworkListItem, newItem: ArtworkListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArtworkListItem, newItem: ArtworkListItem): Boolean {
        return oldItem.callDataEquals(newItem)
    }

}