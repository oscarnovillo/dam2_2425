package com.example.viewmodel.domain.usecases.personas

import com.example.viewmodel.data.Repository
import com.example.viewmodel.data.RespositoryDos
import javax.inject.Inject

class GetPersonas @Inject constructor(private val repo : Repository){

    operator fun invoke() = RespositoryDos.getPersonas()
}