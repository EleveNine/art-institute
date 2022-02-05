package com.elevenine.artinstitute.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elevenine.artinstitute.data.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(
        entity = CategoryEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query(
        """
            SELECT * FROM categories
        """
    )
    fun getCategoriesFlow(): Flow<List<CategoryEntity>>
}
