package com.example.compose.ui.users.detalle

import com.example.compose.domain.modelo.User
import com.example.compose.ui.common.UiEvent

interface DetalleUsersContract {
    sealed class DetalleUsersEvent {
        class GetUser(val id: Int) : DetalleUsersEvent()
        object DelUser : DetalleUsersEvent()
        object UiEventDone : DetalleUsersEvent()
    }

    data class DetalleUsersState(
        val user: User? = null,
        val isLoading: Boolean = false,
        val uiEvent: UiEvent? = null,
        )
}