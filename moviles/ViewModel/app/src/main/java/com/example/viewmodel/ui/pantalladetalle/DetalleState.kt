package com.example.viewmodel.ui.pantalladetalle

import com.example.viewmodel.domain.modelo.Persona
import com.example.viewmodel.ui.common.UiEvent

data class DetalleState(
    val persona: Persona = Persona(),
    val event: UiEvent? = null
)