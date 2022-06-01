package com.elevenine.artinstitute.domain.model

/**
 * Wrapper class the represents a page of data. Can be used for cases when the source provides data
 * in the paginated manner.
 *
 * @param hasMore flag that indicates if there is more pages for the given data set.
 *
 * @param list the portion of the paged data.
 */
class DataListPage<T>(
    val hasMore: Boolean,
    val list: List<T>
)