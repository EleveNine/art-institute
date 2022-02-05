package com.elevenine.artinstitute.ui.model

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

data class Category(
    val id: Int,
    val apiModel: String,
    val apiLink: String,
    val title: String,
    val lastUpdated: String?
)