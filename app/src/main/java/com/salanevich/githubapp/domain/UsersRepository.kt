package com.salanevich.githubapp.domain

import com.salanevich.githubapp.domain.model.UserDomain

interface UsersRepository {
    suspend fun getUsers(sinceUserId: Int, page: Int): List<UserDomain>
}