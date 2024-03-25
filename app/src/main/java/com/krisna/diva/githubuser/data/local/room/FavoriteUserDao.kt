package com.krisna.diva.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krisna.diva.githubuser.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favorite_user")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM favorite_user WHERE username = :username")
    fun get(username: String): LiveData<FavoriteUserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUserEntity: FavoriteUserEntity)

    @Delete
    fun delete(favoriteUserEntity: FavoriteUserEntity)
}