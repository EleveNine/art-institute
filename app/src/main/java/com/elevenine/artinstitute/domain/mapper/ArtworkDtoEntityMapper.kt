package com.elevenine.artinstitute.domain.mapper

import com.elevenine.artinstitute.data.api.model.response.ArtworkDto
import com.elevenine.artinstitute.data.database.entity.ArtworkEntity
import com.elevenine.artinstitute.domain.mapper.base.Mapper

/**
 * @author Sherzod Nosirov
 * @since 11.01.2022
 */

class ArtworkDtoEntityMapper(private val imageIiifPrefix: String) :
    Mapper<ArtworkDto, ArtworkEntity> {

    override fun map(input: ArtworkDto): ArtworkEntity {
        return with(input) {
            ArtworkEntity(
                id ?: 0,
                imageId ?: "",
                "$imageIiifPrefix/$imageId$IIIF_PARAMS_PATH",
                title ?: "",
                mainReferenceNumber ?: "",
                dateDisplay ?: "",
                artistDisplay ?: ""
            )
        }
    }

    companion object {
        const val IIIF_PARAMS_PATH = "/full/843,/0/default.jpg"
    }
}