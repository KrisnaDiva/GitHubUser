package com.krisna.diva.githubuser.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krisna.diva.githubuser.data.remote.response.UsersItem
import com.krisna.diva.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _listFollowers = MutableLiveData<List<UsersItem>>()
    val listFollowers: LiveData<List<UsersItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<UsersItem>>()
    val listFollowing: LiveData<List<UsersItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun get(username: String, position: Int) {
        if (position == 1) {
            getFollowers(username)
        } else {
            getFollowing(username)
        }
    }

    private fun getFollowers(username: String) {
        if (_listFollowers.value == null) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getFollowers(username)
            client.enqueue(object : Callback<List<UsersItem>> {
                override fun onResponse(
                    call: Call<List<UsersItem>>,
                    response: Response<List<UsersItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _listFollowers.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
                    _isLoading.value = false
                    _toastMessage.value = t.message
                }
            })
        }
    }

    private fun getFollowing(username: String) {
        if (_listFollowing.value == null) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getFollowing(username)
            client.enqueue(object : Callback<List<UsersItem>> {
                override fun onResponse(
                    call: Call<List<UsersItem>>,
                    response: Response<List<UsersItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _listFollowing.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
                    _isLoading.value = false
                    _toastMessage.value = t.message
                }
            })
        }
    }
}