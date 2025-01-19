package com.example.compose.ui.users.listado

import com.example.compose.domain.modelo.User
import com.example.compose.ui.common.UiEvent

interface ListadoContract {

    sealed class ListadoEvent {
        object GetUsers : ListadoEvent()
        class DeleteUser(val user: User) : ListadoEvent()

        object UiEventDone: ListadoEvent()
    }

    data class ListadoState(
        val users: List<User> = emptyList(),
        val isLoading: Boolean = false,
        val uiEvent: UiEvent? = null
    )
}