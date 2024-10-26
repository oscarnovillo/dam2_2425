package com.example.viewmodel.domain.usecases.personas

import com.example.viewmodel.data.Repository
import com.example.viewmodel.data.RespositoryDos

class GetPersonas( private val repo : Repository){

    operator fun invoke() = RespositoryDos.getPersonas()
}