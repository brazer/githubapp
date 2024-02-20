package com.salanevich.githubapp.domain.usecase

import com.salanevich.githubapp.domain.model.UserDomain
import kotlinx.coroutines.flow.Flow

interface GetUsersUseCase : UseCase<Unit, Flow<List<UserDomain>>>