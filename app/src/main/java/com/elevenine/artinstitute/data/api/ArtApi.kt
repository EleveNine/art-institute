package com.elevenine.artinstitute.data.api

import com.elevenine.artinstitute.data.api.model.response.ArtworkDto
import com.elevenine.artinstitute.data.api.model.response.Base
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

interface ArtApi {

    companion object {
        const val BASE_URL = "https://api.artic.edu/api/v1/"

        const val DEFAULT_ARTWORK_FIELDS =
            "id,image_id,title,artist_display,date_display,main_reference_number"
    }

    @GET("artworks")
    suspend fun getArtworksByPage(
        @Query("page") pageNumber: Int,
        @Query("fields") fields: String = DEFAULT_ARTWORK_FIELDS
    ): Base<List<ArtworkDto>>
}