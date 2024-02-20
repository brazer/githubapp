package com.salanevich.githubapp.data.database

import com.salanevich.githubapp.data.database.entity.UserEntity
import com.salanevich.githubapp.domain.model.UserDomain

fun List<UserEntity>.toModelList() = map {
    UserDomain(
        id = it.id,
        login = it.login,
        avatarUrl = it.avatarUrl,
        webSite = it.webPage
    )
}