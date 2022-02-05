package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.data.common.DataResult

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

interface FetchCategoriesAndCacheUseCase {
    suspend operator fun invoke(): DataResult<Unit>
}