package com.salanevich.githubapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class DetailViewModel: ViewModel(), ContainerHost<DetailState, DetailSideEffect> {

    override val container = viewModelScope.container<DetailState, DetailSideEffect>(DetailState())

    fun reduce(action: DetailAction) {
        when (action) {
            DetailAction.DataLoading -> onDataLoading()
            DetailAction.DataLoaded -> onDataLoaded()
        }
    }

    private fun onDataLoading() = intent {
        reduce { state.copy(loading = true) }
    }

    private fun onDataLoaded() = intent {
        reduce { state.copy(loading = false) }
    }

}

sealed class DetailAction {
    data object DataLoading: DetailAction()
    data object DataLoaded: DetailAction()
}

data class DetailState(
    val loading: Boolean = true
)

sealed class DetailSideEffect