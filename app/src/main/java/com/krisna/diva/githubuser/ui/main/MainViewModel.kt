package com.krisna.diva.githubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krisna.diva.githubuser.data.remote.response.GithubResponse
import com.krisna.diva.githubuser.data.remote.response.UsersItem
import com.krisna.diva.githubuser.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<UsersItem>>()
    val listUser: LiveData<List<UsersItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _query = MutableStateFlow("diva")
    private val query: StateFlow<String> = _query

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    init {
        viewModelScope.launch {
            query.collect {
                findUser(it)
            }
        }
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    private fun findUser(q: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(q)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _listUser.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                _toastMessage.value = t.message
            }
        })
    }
}