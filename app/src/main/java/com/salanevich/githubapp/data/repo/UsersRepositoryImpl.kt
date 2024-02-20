package com.salanevich.githubapp.data.repo

import com.salanevich.githubapp.data.database.AppDatabase
import com.salanevich.githubapp.data.database.toModelList
import com.salanevich.githubapp.data.network.GitHubApi
import com.salanevich.githubapp.data.network.proceed
import com.salanevich.githubapp.data.network.toModelList
import com.salanevich.githubapp.data.toEntityList
import com.salanevich.githubapp.domain.UsersRepository
import com.salanevich.githubapp.domain.model.UserDomain

class UsersRepositoryImpl(
    private val webApi: GitHubApi,
    private val db: AppDatabase
) : UsersRepository {
    override suspend fun getUsers(sinceUserId: Int, page: Int): List<UserDomain> {
        val usersFromDb = db.usersDao().getUsers()
        return if (usersFromDb.isEmpty()) {
            val users = webApi.getUsers(sinceUserId, page).proceed()
            db.usersDao().putUsers(users.toEntityList())
            users.toModelList()
        } else usersFromDb.toModelList()
    }
}