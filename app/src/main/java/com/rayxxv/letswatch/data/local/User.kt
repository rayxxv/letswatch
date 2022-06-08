package com.rayxxv.letswatch.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User (
    @PrimaryKey(autoGenerate = true) var  id:Int?,
    @ColumnInfo(name = "username") var username : String,
    @ColumnInfo(name = "email") var email : String,
    @ColumnInfo(name = "password") var password : String,
    @ColumnInfo(name = "uri") var uri : String
): Parcelable