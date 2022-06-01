package com.elevenine.artinstitute.data.api

import com.elevenine.artinstitute.data.api.model.request.GetCategorizedArtworksRequest
import com.elevenine.artinstitute.data.api.model.response.ArtworkDto
import com.elevenine.artinstitute.data.api.model.response.Base
import com.elevenine.artinstitute.data.api.model.response.CategoryDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

interface ArtApi {

    companion object {
        const val BASE_URL = "https://api.artic.edu/api/v1/"

        const val DEFAULT_ARTWORK_FIELDS =
            "id,image_id,title,artist_id,artist_display,date_display,main_reference_number,artwork_type_id,artwork_type_title"
    }

    @POST("artworks/search")
    suspend fun getCategorizedArtworksByPage(
        @Body requestBody: GetCategorizedArtworksRequest,
        @Query("page") pageNumber: Int,
        @Query("limit") limit: Int,
        @Query("fields") fields: String = DEFAULT_ARTWORK_FIELDS,
    ): Base<List<ArtworkDto>>

    @GET("artwork-types")
    suspend fun getArtworkTypes(
        @Query("page") pageNumber: Int = 1,
        @Query("limit") limit: Int = 100
    ): Base<List<CategoryDto>>
}