package com.salanevich.githubapp.domain.model

data class UserDomain(
    val id: Int,
    val login: String,
    val webSite: String,
    val avatarUrl: String
)
