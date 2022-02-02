package com.elevenine.artinstitute.domain.mapper

import com.elevenine.artinstitute.data.database.entity.ArtworkEntity
import com.elevenine.artinstitute.domain.mapper.base.Mapper
import com.elevenine.artinstitute.ui.model.Artwork
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 26.01.2022
 */

class ArtworkEntityUiMapper @Inject constructor() : Mapper<ArtworkEntity, Artwork> {
    override fun map(input: ArtworkEntity): Artwork {
        return with(input) {
            Artwork(
                id, imageId, imageUrl, title, mainReferenceNumber, dateDisplay,
                artistDisplay, artistId, artworkTypeId, artworkTypeTitle,
            )
        }
    }
}