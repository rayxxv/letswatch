package com.rayxxv.letswatch.data.local

class DatabaseHelper(private val favoriteDao: FavoriteDao, private val userDao: UserDao) {
    // Favorite
    fun getAllFav() = favoriteDao.getAllFav()
    suspend fun getFavorite(id: Int) =favoriteDao.getFavorite(id)
    suspend fun insert(entity: Favorite) = favoriteDao.insert(entity)
    suspend fun deleteFavorite(entity: Favorite) = favoriteDao.deleteFavorite(entity)

    suspend fun getALlUser() = userDao.getUser()
    suspend fun insertUser(user: User) = userDao.addUser(user)
    suspend fun getUserr(username: String): User = userDao.getUserr(username)
    suspend fun loginUser(username: String, password: String) = userDao.login(username, password)
    suspend fun updateUser(user: User):Int = userDao.updateUser(user)
}