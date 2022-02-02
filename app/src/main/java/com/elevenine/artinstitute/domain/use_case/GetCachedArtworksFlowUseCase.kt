package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.ui.model.Artwork
import kotlinx.coroutines.flow.Flow

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

interface GetCachedArtworksFlowUseCase {
    suspend operator fun invoke(): DataResult<Flow<List<Artwork>>>
}