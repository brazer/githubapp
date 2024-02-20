package com.salanevich.githubapp.data.usecase

import com.salanevich.githubapp.domain.UsersRepository
import com.salanevich.githubapp.domain.model.UserDomain
import com.salanevich.githubapp.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class GetUsersUseCaseImpl(
    private val repo: UsersRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
): GetUsersUseCase {
    override suspend fun invoke(params: Unit): Flow<List<UserDomain>> = flow {
        emit(repo.getUsers(1, 20))
    }.stateIn(scope)
}