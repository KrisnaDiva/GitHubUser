package com.krisna.diva.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.krisna.diva.githubuser.data.local.entity.FavoriteUserEntity

@Database(entities = [FavoriteUserEntity::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GithubDatabase {
            if (INSTANCE == null) {
                synchronized(GithubDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubDatabase::class.java, "github_database"
                    )
                        .build()
                }
            }
            return INSTANCE as GithubDatabase
        }
    }
}