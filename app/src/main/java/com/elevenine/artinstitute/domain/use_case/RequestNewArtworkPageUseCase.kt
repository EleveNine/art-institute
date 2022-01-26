package com.elevenine.artinstitute.domain.use_case

import com.elevenine.artinstitute.data.common.DataResult
import com.elevenine.artinstitute.ui.model.Artwork

/**
 * @author Sherzod Nosirov
 * @since 12.01.2022
 */

interface RequestNewArtworkPageUseCase {
    suspend operator fun invoke(): DataResult<List<Artwork>>
}