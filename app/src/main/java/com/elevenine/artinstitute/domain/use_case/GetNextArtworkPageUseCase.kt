package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.common.DomainResult
import com.elevenine.artinstitute.domain.model.DataListPage
import com.elevenine.artinstitute.ui.model.Artwork

/**
 * @author Sherzod Nosirov
 * @since 12.01.2022
 */

interface GetNextArtworkPageUseCase {
    suspend operator fun invoke(pageNumber: Int, pageSize: Int): DomainResult<DataListPage<Artwork>>
}