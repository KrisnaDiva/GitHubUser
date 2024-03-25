package com.krisna.diva.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.krisna.diva.githubuser.data.FavoriteUserRepository
import com.krisna.diva.githubuser.data.local.entity.FavoriteUserEntity

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>> {
        return mFavoriteUserRepository.getAllFavoriteUsers()
    }
    fun delete(favoriteUserEntity: FavoriteUserEntity) {
        mFavoriteUserRepository.delete(favoriteUserEntity)
    }
}