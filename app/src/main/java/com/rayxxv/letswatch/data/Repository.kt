package com.rayxxv.letswatch.data

import com.rayxxv.letswatch.data.local.DataStoreManager
import com.rayxxv.letswatch.data.local.DatabaseHelper
import com.rayxxv.letswatch.data.local.Favorite
import com.rayxxv.letswatch.data.local.User
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper,
    private val DataStore: DataStoreManager

    ) {
    suspend fun getPopularMovies() = apiHelper.getPopularMovies()
    suspend fun getMovieDetail(movieId: Int) = apiHelper.getMovieDetail(movieId)
    suspend fun getAllSeries() = apiHelper.getAllSeries()
    suspend fun getSeriesDetail(seriesId: Int) = apiHelper.getSeriesDetail(seriesId)

    // DataStore
    suspend fun setNama(user: User) =DataStore.setNama(user)
    suspend fun setUserPref(userEntity: User) = DataStore.setUser(userEntity)
    suspend fun getUserPref(): Flow<User> = DataStore.getUser()
    suspend fun deleteUserPref() = DataStore.deleteUser()

    // User
    suspend fun getAllUser() = databaseHelper.getALlUser()
    suspend fun insertUser(user: User) = databaseHelper.insertUser(user)
    suspend fun loginUser(username: String, password: String) = databaseHelper.loginUser(username, password)
    suspend fun updateUser(user: User): Int = databaseHelper.updateUser(user)
    suspend fun getUserr(username: String): User {
        return databaseHelper.getUserr(username)
    }
    //Favourite
    fun getAllFav() = databaseHelper.getAllFav()
    suspend fun getFavorite(id: Int) =databaseHelper.getFavorite(id)
    suspend fun insert(entity: Favorite) = databaseHelper.insert(entity)
    suspend fun delete(entity: Favorite) = databaseHelper.deleteFavorite(entity)

}