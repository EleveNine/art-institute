package com.elevenine.artinstitute.data.api.model.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCategorizedArtworksRequest(
    val query: Query?
) {
    companion object {
        fun getInstance(artworkTypeId: Long): GetCategorizedArtworksRequest {
            return GetCategorizedArtworksRequest(
                Query(
                    Query.Term(
                        artworkTypeId
                    )
                )
            )
        }
    }

    @Serializable
    data class Query(
        val term: Term
    ) {
        @Serializable
        data class Term(
            @SerialName("artwork_type_id")
            val artworkTypeId: Long
        )
    }
}