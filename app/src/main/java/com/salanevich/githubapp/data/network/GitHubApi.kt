package com.salanevich.githubapp.data.network

import androidx.annotation.IntRange
import com.salanevich.githubapp.data.network.response.UsersResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    /**
     * Lists all users, in the order that they signed up on GitHub. This list includes personal user accounts and organization accounts.
     * @param sinceUserId A user ID. Only return users with an ID greater than this ID.
     * @param page The number of results per page (max 100). Default: 30.
     */
    @GET("/users")
    suspend fun getUsers(
        @Query("since ") sinceUserId: Int,
        @Query("per_page") @IntRange(1, 100) page: Int
    ): Response<List<UsersResponseItem>>

}