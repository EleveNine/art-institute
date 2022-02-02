package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.domain.repository.ArtResourceRepository
import com.elevenine.artinstitute.ui.model.Artwork
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 02.02.2022
 */

class GetCachedArtworksFlowUseCaseImpl @Inject constructor(private val artworkRepository: ArtResourceRepository) :
    GetCachedArtworksFlowUseCase {
    override suspend fun invoke(): DataResult<Flow<List<Artwork>>> {
        return artworkRepository.getCachedArtworksFlow()
    }
}