package com.elevenine.artinstitute.data.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

@Serializable
data class Base<T>(
    val config: Config? = null,
    val `data`: T,
    val info: Info? = null,
    val pagination: Pagination? = null
)

@Serializable
data class Config(
    @SerialName("iiif_url")
    val iiifUrl: String? = null,
    @SerialName("website_url")
    val websiteUrl: String? = null
)

@Serializable
data class Info(
    @SerialName("license_links")
    val licenseLinks: List<String?>? = null,
    @SerialName("license_text")
    val licenseText: String? = null,
    val version: String? = null
)

@Serializable
data class Pagination(
    @SerialName("current_page")
    val currentPage: Int? = null,
    val limit: Int? = null,
    @SerialName("next_url")
    val nextUrl: String? = null,
    val offset: Int? = null,
    val total: Int? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null
)