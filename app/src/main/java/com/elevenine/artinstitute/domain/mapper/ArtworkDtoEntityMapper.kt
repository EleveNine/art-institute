package com.elevenine.artinstitute.domain.mapper

import com.elevenine.artinstitute.data.api.model.response.ArtworkDto
import com.elevenine.artinstitute.data.database.entity.ArtworkEntity
import com.elevenine.artinstitute.domain.mapper.base.Mapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * @author Sherzod Nosirov
 * @since 11.01.2022
 */

class ArtworkDtoEntityMapper @AssistedInject constructor(
    @Assisted(ASSISTED_INJECT_FIELD_IIIF_PREFIX) private val imageIiifPrefix: String
) : Mapper<ArtworkDto, ArtworkEntity> {

    companion object {
        const val IIIF_PARAMS_PATH = "/full/843,/0/default.jpg"
        const val ASSISTED_INJECT_FIELD_IIIF_PREFIX = "imageIiifPrefix"
    }

    override fun map(input: ArtworkDto): ArtworkEntity {
        return with(input) {
            ArtworkEntity(
                id ?: -1,
                imageId ?: "",
                "$imageIiifPrefix/$imageId$IIIF_PARAMS_PATH",
                title ?: "",
                mainReferenceNumber ?: "",
                dateDisplay ?: "",
                artistDisplay ?: "",
                artistId ?: -1,
                artworkTypeId ?: "",
                artworkTypeTitle ?: "",
            )
        }
    }

    /**
     * Due to the necessity to pass the imageIiifPrefix param to the constructor of
     * [ArtworkDtoEntityMapper] to properly create image urls, it is necessary to implement Dagger's
     * AssistedFactory.
     */
    @AssistedFactory
    interface Factory {
        fun create(@Assisted(ASSISTED_INJECT_FIELD_IIIF_PREFIX) imageIiifPrefix: String): ArtworkDtoEntityMapper
    }
}