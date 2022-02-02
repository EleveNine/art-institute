package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.data.common.EmptyDataError
import com.elevenine.artinstitute.domain.repository.ArtResourceRepository
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 12.01.2022
 */

class RequestAndCacheNewArtworkPageUseCaseImpl @Inject constructor(private val artResourceRepository: ArtResourceRepository) :
    RequestAndCacheNewArtworkPageUseCase {

    override suspend fun invoke(pageNumber: Int): DataResult<Unit> {
        val apiResult = artResourceRepository.fetchArtworkListPage(pageNumber)

        when (apiResult) {
            is DataResult.Success -> {

                if (apiResult.data.isEmpty()) return DataResult.Error(EmptyDataError())

                return artResourceRepository.cacheArtworks(apiResult.data)
            }
            is DataResult.Error -> return apiResult
        }
    }
}