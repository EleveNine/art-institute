package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.data.common.EmptyDataError
import com.elevenine.artinstitute.domain.repository.ArtResourceRepository
import com.elevenine.artinstitute.ui.model.Artwork
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 12.01.2022
 */

class RequestNewArtworkPageUseCaseImpl @Inject constructor(private val artResourceRepository: ArtResourceRepository) :
    RequestNewArtworkPageUseCase {

    private var currentPage = 1

    override suspend fun invoke(): DataResult<List<Artwork>> {
        val apiResult = artResourceRepository.fetchArtworkListPage(currentPage)

        when (apiResult) {
            is DataResult.OnSuccess -> {

                if (apiResult.data.isEmpty()) return DataResult.OnError(EmptyDataError())

                val cacheResult = artResourceRepository.cacheArtworks(apiResult.data)
                return when (cacheResult) {
                    is DataResult.OnError -> cacheResult
                    is DataResult.OnSuccess -> {
                        currentPage++
                        artResourceRepository.getCachedArtworks()
                    }
                }
            }
            is DataResult.OnError -> return apiResult
        }
    }
}