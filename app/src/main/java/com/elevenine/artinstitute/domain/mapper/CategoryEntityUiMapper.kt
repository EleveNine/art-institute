package com.elevenine.artinstitute.domain.mapper

import com.elevenine.artinstitute.data.database.entity.CategoryEntity
import com.elevenine.artinstitute.domain.mapper.base.Mapper
import com.elevenine.artinstitute.ui.model.Category
import javax.inject.Inject

/**
 * @author Sherzod Nosirov
 * @since 05.02.2022
 */

class CategoryEntityUiMapper @Inject constructor() : Mapper<CategoryEntity, Category> {
    override fun map(input: CategoryEntity): Category {
        return with(input) {
            Category(id, apiModel, apiLink, title, lastUpdated)
        }
    }
}