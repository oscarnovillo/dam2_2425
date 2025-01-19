package com.example.compose.ui.users.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.remote.NetworkResult
import com.example.compose.di.IoDispatcher
import com.example.compose.domain.usecases.users.DelUser
import com.example.compose.domain.usecases.users.GetUser
import com.example.compose.ui.common.UiEvent
import com.example.compose.ui.users.detalle.DetalleUsersContract.DetalleUsersEvent
import com.example.compose.ui.users.detalle.DetalleUsersContract.DetalleUsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleUsersViewModel @Inject constructor(
    val getUser: GetUser,
    val delUser: DelUser,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _uiState: MutableStateFlow<DetalleUsersState> by lazy {
        MutableStateFlow(DetalleUsersState())
    }
    val uiState: StateFlow<DetalleUsersState> = _uiState.asStateFlow()


    fun handleEvent(event: DetalleUsersEvent) {
        when (event) {
            DetalleUsersEvent.UiEventDone -> {
                _uiState.update { it.copy(uiEvent = null) }
            }

            is DetalleUsersEvent.GetUser -> viewModelScope.launch(dispatcher) {
                getUser.invoke(event.id).apply {
                    when (this) {
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(user = this.data, isLoading = false)
                        }

                        is NetworkResult.Error -> tratarError(this.message)

                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }

                    }
                }
            }

            is DetalleUsersEvent.DelUser -> viewModelScope.launch(dispatcher) {
                _uiState.value.user?.let { user ->
                    delUser.invoke(user.id).collect { result ->
                        when (result) {
                            is NetworkResult.Success -> {
                                _uiState.update {
                                    it.copy(uiEvent = UiEvent.PopBackStack, isLoading = false)
                                }
                            }

                            is NetworkResult.Error -> tratarError(result.message)

                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        }
                    }
                }
            }
        }
    }

    private fun tratarError(message: String?) {
        _uiState.update {
            it.copy(
                uiEvent = message?.let { message -> UiEvent.ShowSnackbar(message) },
                isLoading = false
            )
        }
    }
}