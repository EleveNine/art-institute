package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.domain.DomainResult

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

interface SyncCategoriesUseCase {
    suspend operator fun invoke(): DomainResult<Unit>
}