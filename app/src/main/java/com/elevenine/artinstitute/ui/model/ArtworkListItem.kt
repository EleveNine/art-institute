package com.elevenine.artinstitute.ui.model

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

/**
 * Marker interface for the UI models for the list of artworks
 */
interface ArtworkListItem {
    val id: Int

    val viewType: Int

    fun callDataEquals(item: ArtworkListItem): Boolean
}