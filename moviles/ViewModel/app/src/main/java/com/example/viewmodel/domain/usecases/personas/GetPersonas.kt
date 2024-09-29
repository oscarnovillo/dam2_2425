package com.example.viewmodel.domain.usecases.personas

import com.example.viewmodel.data.Repository

class GetPersonas( private val repo : Repository){

    operator fun invoke() = repo.getPersonas()
}