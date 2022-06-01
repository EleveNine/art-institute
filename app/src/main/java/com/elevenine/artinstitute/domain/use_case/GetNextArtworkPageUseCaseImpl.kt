package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.common.toDomainResult
import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.data.common.EmptyDataError
import com.elevenine.artinstitute.domain.DomainResult
import com.elevenine.artinstitute.domain.model.DataListPage
import com.elevenine.artinstitute.domain.repository.ArtResourceRepository
import com.elevenine.artinstitute.ui.model.Artwork
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 12.01.2022
 */

class GetNextArtworkPageUseCaseImpl @Inject constructor(private val artResourceRepository: ArtResourceRepository) :
    GetNextArtworkPageUseCase {

    override suspend fun invoke(
        pageNumber: Int,
        pageSize: Int
    ): DomainResult<DataListPage<Artwork>> {
        val apiResult =
            artResourceRepository.fetchArtworkListPage(pageNumber, pageSize).toDomainResult()

        when (apiResult) {
            is DomainResult.Success -> {

                if (apiResult.data.list.isEmpty()) return DomainResult.Error(EmptyDataError())

                return apiResult
            }
            is DomainResult.Error -> return apiResult
        }
    }
}