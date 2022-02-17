package com.elevenine.artinstitute.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json

/**
 * @author Sherzod Nosirov
 * @since 08.12.2021
 */

@Keep
class Base<T>(
    var pagination: Pagination,
    var data: T,
    var info: Info,
    var config: Config
)

@Keep
class Pagination(
    var total: Int?,
    var limit: Int?,
    var offset: Int?,
    @Json(name = "total_pages")
    var totalPages: Int?,
    @Json(name = "current_page")
    var currentPage: Int?,
    @Json(name = "next_url")
    var nextUrl: String?
)

@Keep
class Info(
    @Json(name = "license_text")
    var licenseText: String?,
    @Json(name = "license_links")
    var licenseLinks: List<String?>?,
    var version: String?
)

@Keep
class Config(
    @Json(name = "iiif_url")
    var iiifUrl: String?,
    @Json(name = "website_url")
    var websiteUrl: String?
)