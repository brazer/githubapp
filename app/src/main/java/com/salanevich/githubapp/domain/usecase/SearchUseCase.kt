package com.salanevich.githubapp.domain.usecase

import com.salanevich.githubapp.domain.model.UserDomain

interface SearchUseCase: UseCase<SearchParams, List<UserDomain>>

data class SearchParams(
    val query: String,
    val users: List<UserDomain>
)