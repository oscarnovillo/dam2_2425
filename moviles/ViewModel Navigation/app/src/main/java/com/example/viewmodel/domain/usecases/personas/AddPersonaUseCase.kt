package com.example.viewmodel.domain.usecases.personas

import com.example.viewmodel.data.RespositoryDos
import com.example.viewmodel.data.remote.NetworkResult
import com.example.viewmodel.domain.modelo.Persona
import javax.inject.Inject

class AddPersonaUseCase @Inject constructor(){


    operator fun invoke(persona: Persona): Boolean {


        return RespositoryDos.addPersona(persona)
    }





}