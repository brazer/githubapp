package com.salanevich.githubapp.data.usecase

import com.salanevich.githubapp.domain.model.UserDomain
import com.salanevich.githubapp.domain.usecase.SearchParams
import com.salanevich.githubapp.domain.usecase.SearchUseCase

class SearchUseCaseImpl : SearchUseCase {
    override suspend fun invoke(params: SearchParams): List<UserDomain> {
        return params.users.filter { it.login.contains(params.query, ignoreCase = true) }
    }
}