package com.krisna.diva.githubuser.data.remote.retrofit

import com.krisna.diva.githubuser.data.remote.response.DetailUserResponse
import com.krisna.diva.githubuser.data.remote.response.GithubResponse
import com.krisna.diva.githubuser.data.remote.response.UsersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UsersItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UsersItem>>

}