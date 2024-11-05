package com.example.viewmodel.ui.pantalllamain

import com.example.viewmodel.domain.modelo.Persona
import com.example.viewmodel.ui.common.UiEvent

data class MainState(
    val personas: List<Persona> = emptyList(),
    val isIrDetalle: Boolean = false,
    val event: UiEvent? = null,
)