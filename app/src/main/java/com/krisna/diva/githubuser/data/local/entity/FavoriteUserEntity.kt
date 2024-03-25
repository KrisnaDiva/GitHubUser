package com.krisna.diva.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_user")
data class FavoriteUserEntity(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: String = ""
)