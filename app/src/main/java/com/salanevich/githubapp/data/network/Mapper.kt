package com.salanevich.githubapp.data.network

import com.salanevich.githubapp.data.network.response.UsersResponseItem
import com.salanevich.githubapp.domain.model.UserDomain

fun List<UsersResponseItem>.toModelList() = map {
    UserDomain(
        id = it.id,
        login = it.login,
        avatarUrl = it.avatarUrl,
        webSite = it.htmlUrl
    )
}