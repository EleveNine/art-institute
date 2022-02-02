package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.data.common.DataResult

/**
 * @author Sherzod Nosirov
 * @since 12.01.2022
 */

interface RequestAndCacheNewArtworkPageUseCase {
    suspend operator fun invoke(pageNumber: Int): DataResult<Unit>
}