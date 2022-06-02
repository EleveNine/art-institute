package com.elevenine.artinstitute.ui.model

import com.elevenine.artinstitute.R

/**
 * @author Sherzod Nosirov
 * @since 26.01.2022
 */

data class Artwork(
    override val id: Int,
    var imageId: String,
    var imageUrl: String,
    var title: String,
    var mainReferenceNumber: String,
    var dateDisplay: String,
    var artistDisplay: String,
    var artistId: Long,
    var artworkTypeId: String,
    var artworkTypeTitle: String
) : ArtworkListItem {

    override val viewType: Int
        get() = R.layout.item_artwork
}