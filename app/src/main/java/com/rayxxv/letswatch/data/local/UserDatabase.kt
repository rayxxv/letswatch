package com.rayxxv.letswatch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class, Favorite::class], version = 2)
abstract class UserDatabase():RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun favoriteDao(): FavoriteDao

//    companion object{
//        private var INSTANCE : UserDatabase? = null
//
//        fun getInstance(context: Context): UserDatabase? {
//            if (INSTANCE == null){
//                synchronized(UserDatabase::class){
//                    INSTANCE = Room.databaseBuilder(
//                    context.applicationContext,
//                    UserDatabase::class.java,"Noted.db"
//                    ).build()
//                }
//            }
//            return INSTANCE
//        }
//        fun destroyInstance(){
//            INSTANCE = null
//        }
//    }
}