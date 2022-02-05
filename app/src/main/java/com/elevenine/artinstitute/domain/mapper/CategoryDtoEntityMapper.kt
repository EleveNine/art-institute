package com.elevenine.artinstitute.domain.mapper

import com.elevenine.artinstitute.data.api.model.response.CategoryDto
import com.elevenine.artinstitute.data.database.entity.CategoryEntity
import com.elevenine.artinstitute.domain.mapper.base.Mapper
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

class CategoryDtoEntityMapper @Inject constructor() : Mapper<CategoryDto, CategoryEntity> {
    override fun map(input: CategoryDto): CategoryEntity {
        return with(input) {
            CategoryEntity(id ?: -1, apiModel ?: "", apiLink ?: "", title ?: "", lastUpdated ?: "")
        }
    }
}