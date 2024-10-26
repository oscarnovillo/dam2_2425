package com.example.viewmodel.domain.usecases.personas

import com.example.viewmodel.data.RespositoryDos
import com.example.viewmodel.domain.modelo.Persona

class DeletePersonaUseCase {

    operator fun invoke(persona: Persona): Boolean {
        return RespositoryDos.delPersona(persona)
    }
}