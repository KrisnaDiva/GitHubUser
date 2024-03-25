package com.krisna.diva.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.krisna.diva.githubuser.data.local.entity.FavoriteUserEntity
import com.krisna.diva.githubuser.data.local.room.FavoriteUserDao
import com.krisna.diva.githubuser.data.local.room.GithubDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUsersDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GithubDatabase.getDatabase(application)
        mFavoriteUsersDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>> =
        mFavoriteUsersDao.getAllFavoriteUser()

    fun get(username: String): LiveData<FavoriteUserEntity> = mFavoriteUsersDao.get(username)

    fun insert(favoriteUser: FavoriteUserEntity) {
        executorService.execute { mFavoriteUsersDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUserEntity) {
        executorService.execute { mFavoriteUsersDao.delete(favoriteUser) }
    }

}