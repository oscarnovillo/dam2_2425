package com.example.viewmodel.ui.pantalladetalle

import com.example.viewmodel.domain.modelo.Persona

data class DetalleState(
    val persona: Persona = Persona(),
    val error: String? = null
)