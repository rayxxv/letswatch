package com.rayxxv.letswatch.data.local

import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.*
import androidx.room.Query
import androidx.room.Update


@Dao
interface UserDao {
    @Query("SELECT * FROM user where username= :username AND password= :password ")
    suspend fun login(username: String, password: String): User

    @Query("SELECT * FROM User WHERE username like :username")
    suspend fun getUserr(username: String): User

    @Query("SELECT * FROM User")
    suspend fun getUser(): User

    @Insert(onConflict = REPLACE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User): Int

}