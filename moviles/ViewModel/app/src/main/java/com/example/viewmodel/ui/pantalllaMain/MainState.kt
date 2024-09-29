package com.example.viewmodel.ui.pantalllaMain

import com.example.viewmodel.domain.modelo.Persona

data class MainState(
    val persona: Persona = Persona(),
    val error: String? = null
)