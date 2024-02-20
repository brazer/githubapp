package com.salanevich.githubapp.domain.usecase

interface UseCase<INPUT: Any?, OUTPUT: Any?> {
    suspend operator fun invoke(params: INPUT): OUTPUT
}