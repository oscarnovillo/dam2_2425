package com.example.compose.ui.users.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.remote.NetworkResult
import com.example.compose.di.IoDispatcher
import com.example.compose.domain.modelo.User
import com.example.compose.domain.usecases.users.DelUser
import com.example.compose.domain.usecases.users.GetUsers
import com.example.compose.ui.common.UiEvent
import com.example.compose.ui.users.listado.ListadoContract.ListadoEvent
import com.example.compose.ui.users.listado.ListadoContract.ListadoState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListadoViewModel @Inject constructor(
    val getUsers: GetUsers,
    val delUser: DelUser,
    @IoDispatcher val dispatcher: CoroutineDispatcher

) : ViewModel() {
    private val _uiState: MutableStateFlow<ListadoState> by lazy {
        MutableStateFlow(ListadoState())
    }
    val uiState: StateFlow<ListadoState> = _uiState.asStateFlow()

    fun handleEvent(event: ListadoEvent) {
        when (event) {
            is ListadoEvent.UiEventDone -> {
                _uiState.value = _uiState.value.copy(uiEvent = null)
            }

            ListadoEvent.GetUsers ->
                getUsers()

            is ListadoEvent.DeleteUser -> delUser(event.user)
        }


    }

    private fun delUser(user: User) {
        viewModelScope.launch(dispatcher) {
            delUser.invoke(user.id).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                uiEvent = UiEvent.ShowSnackbar("ususario borrado"),
                                isLoading = false
                            )
                        }
                    }

                    is NetworkResult.Error -> _uiState.update {
                        it.copy(
                            uiEvent = UiEvent.ShowSnackbar(result.message ?: ""),
                            isLoading = false
                        )
                    }


                    is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun getUsers() {
        viewModelScope.launch(dispatcher) {
            getUsers.invoke().collect { result ->
                when (result) {
                    is NetworkResult.Error -> _uiState.update {
                        it.copy(
                            uiEvent = UiEvent.ShowSnackbar(result.message ?: ""),
                            isLoading = false
                        )
                    }

                    is NetworkResult.Loading -> _uiState.update {
                        it.copy(isLoading = true)
                    }

                    is NetworkResult.Success -> _uiState.update {
                        it.copy(users = result.data ?: emptyList(), isLoading = false)
                    }
                }

            }
        }
    }
}