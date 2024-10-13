package com.example.viewmodel.ui.pantalllamain

import com.example.viewmodel.domain.modelo.Persona

data class MainState(
    val personas: List<Persona> = emptyList(),
    val isIrDetalle: Boolean = false,
)