package com.elevenine.artinstitute.data.repository

import com.elevenine.artinstitute.data.api.ArtApi
import com.elevenine.artinstitute.domain.repository.ArtResourceRepository
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 12.12.2021
 */

class ArtResourceRepositoryImpl @Inject constructor(private val artApi: ArtApi) : ArtResourceRepository {
}