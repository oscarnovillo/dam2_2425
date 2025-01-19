package com.example.compose.ui.coches.detalle

import com.example.compose.domain.modelo.Coche
import com.example.compose.ui.common.UiEvent


interface DetalleContract {
    sealed class DetalleEvent {
        class GetCoche(val matricula: String) : DetalleEvent()
        object DelCoche : DetalleEvent()

        object UiEventDone : DetalleEvent()
    }

    data class DetalleState(
        val coche: Coche? = null,
        val isLoading: Boolean = false,
        val uiEvent: List<UiEvent?> = emptyList(),

        )
}