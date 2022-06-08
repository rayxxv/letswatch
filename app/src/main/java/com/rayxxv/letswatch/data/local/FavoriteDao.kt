package com.rayxxv.letswatch.data.local

import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM Favorite")
    fun getAllFav(): List<Favorite>

    @Query("SELECT * FROM Favorite WHERE id= :id LIMIT 1")
    suspend fun getFavorite(id: Int): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Favorite)

    @Delete
    suspend fun deleteFavorite(entity: Favorite)
}