package com.elevenine.artinstitute.ui.model

/**
 * @author Sherzod Nosirov
 * @since 26.01.2022
 */

data class Artwork(
    val id: Int,
    var imageId: String,
    var imageUrl: String,
    var title: String,
    var mainReferenceNumber: String,
    var dateDisplay: String,
    var artistDisplay: String
)