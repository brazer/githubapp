package com.salanevich.githubapp.data

import com.salanevich.githubapp.data.database.entity.UserEntity
import com.salanevich.githubapp.data.network.response.UsersResponseItem

fun List<UsersResponseItem>.toEntityList() = map {
    UserEntity(
        id = it.id,
        login = it.login,
        webPage = it.htmlUrl,
        avatarUrl = it.avatarUrl
    )
}