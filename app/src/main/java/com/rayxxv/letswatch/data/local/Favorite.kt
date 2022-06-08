package com.rayxxv.letswatch.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite (
    @PrimaryKey
    @ColumnInfo(name = "id") var id: Int?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image") var image: String,

): Parcelable