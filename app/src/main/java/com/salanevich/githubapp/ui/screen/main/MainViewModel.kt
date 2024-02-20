package com.salanevich.githubapp.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salanevich.githubapp.domain.model.UserDomain
import com.salanevich.githubapp.domain.usecase.GetUsersUseCase
import com.salanevich.githubapp.domain.usecase.SearchParams
import com.salanevich.githubapp.domain.usecase.SearchUseCase
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class MainViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val searchUseCase: SearchUseCase
) : ViewModel(), ContainerHost<MainState, MainSideEffect> {

    override val container = viewModelScope.container<MainState, MainSideEffect>(MainState())

    fun reduce(action: MainAction) {
        when (action) {
            MainAction.Load -> loadUsers()
            is MainAction.GoToDetail -> navigateToDetail(action.login)
            is MainAction.Search -> searchUsers(action.query)
        }
    }

    private fun loadUsers() = intent {
        reduce { state.copy(loading = true) }
        getUsersUseCase(Unit).collect {
            reduce { state.copy(users = it, filteredUsers = it, loading = false) }
        }
    }

    private fun navigateToDetail(login: String) = intent {
        postSideEffect(MainSideEffect.NavigateToDetail(login))
    }

    private fun searchUsers(query: String) = intent {
        val filteredUsers = searchUseCase(SearchParams(query, state.users))
        reduce { state.copy(filteredUsers = filteredUsers) }
    }

}

sealed class MainAction {
    data object Load: MainAction()
    data class GoToDetail(val login: String): MainAction()
    data class Search(val query: String): MainAction()
}

data class MainState(
    val users: List<UserDomain> = emptyList(),
    val filteredUsers: List<UserDomain> = emptyList(),
    val loading: Boolean = true
)

sealed interface MainSideEffect {
    data class NavigateToDetail(val login: String): MainSideEffect
}