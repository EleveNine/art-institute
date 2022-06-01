package com.elevenine.artinstitute.data.api.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

@Serializable
data class Base<T>(
    var pagination: Pagination,
    var data: T,
    var info: Info,
    var config: Config
)

@Serializable
data class Pagination(
    var total: Int? = null,
    var limit: Int? = null,
    var offset: Int? = null,
    @SerialName("total_pages")
    var totalPages: Int? = null,
    @SerialName("current_page")
    var currentPage: Int? = null,
    @SerialName("next_url")
    var nextUrl: String? = null
)

@Serializable
data class Info(
    @SerialName("license_text")
    var licenseText: String? = null,
    @SerialName("license_links")
    var licenseLinks: List<String?>? = null,
    var version: String? = null
)

@Serializable
data class Config(
    @SerialName("iiif_url")
    var iiifUrl: String? = null,
    @SerialName("website_url")
    var websiteUrl: String? = null
)