package com.example.compose.ui.coches.listado

import com.example.compose.domain.modelo.Coche
import com.example.compose.ui.common.UiEvent

data class ListadoState(
    val coches: List<Coche> = emptyList(),
    val isLoading: Boolean = false,
    val cochesSeleccionados: List<Coche> = emptyList(),
    val selectMode: Boolean = false,
    val event: UiEvent? = null,
)
