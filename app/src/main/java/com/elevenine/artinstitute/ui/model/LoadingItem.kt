package com.elevenine.artinstitute.ui.model

import com.elevenine.artinstitute.R

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

data class LoadingItem(override val id: Int): ArtworkListItem {

    override val viewType: Int
        get() = R.layout.item_artwork_loading

    override fun callDataEquals(item: ArtworkListItem): Boolean {
        return item is LoadingItem && this == item
    }
}