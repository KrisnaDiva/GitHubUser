package com.krisna.diva.githubuser.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krisna.diva.githubuser.data.FavoriteUserRepository
import com.krisna.diva.githubuser.data.local.entity.FavoriteUserEntity
import com.krisna.diva.githubuser.data.remote.response.DetailUserResponse
import com.krisna.diva.githubuser.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _path = MutableStateFlow("")
    private val path: StateFlow<String> = _path

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    init {
        viewModelScope.launch {
            path.collect {
                getUser(it)
            }
        }
    }

    fun insert(favoriteUserEntity: FavoriteUserEntity) {
        mFavoriteUserRepository.insert(favoriteUserEntity)
    }

    fun delete(favoriteUserEntity: FavoriteUserEntity) {
        mFavoriteUserRepository.delete(favoriteUserEntity)
    }

    fun get(username: String): LiveData<FavoriteUserEntity> {
        return mFavoriteUserRepository.get(username)
    }


    fun updatePath(newPath: String) {
        _path.value = newPath
    }

    private fun getUser(username: String) {
        if (_user.value == null) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getDetailUser(username)
            client.enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _user.value = response.body()
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    _toastMessage.value = t.message
                }
            })
        }
    }
}
