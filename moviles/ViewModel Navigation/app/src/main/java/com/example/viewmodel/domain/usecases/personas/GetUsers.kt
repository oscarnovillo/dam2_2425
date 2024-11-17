package com.example.viewmodel.domain.usecases.personas

import com.example.viewmodel.data.UsersRepository
import com.example.viewmodel.data.remote.NetworkResult
import com.example.viewmodel.data.remote.combine
import com.example.viewmodel.domain.modelo.validate
import javax.inject.Inject
import kotlin.random.Random


class GetUsers @Inject constructor(private val usersRepository: UsersRepository){

    suspend operator fun invoke() =
        validarLLamada()
            .then { usersRepository.fetchUsers()}
            .then { users -> users.map {it.validate() }.combine()}



    fun validarLLamada() =
        if (Random.nextBoolean())
            NetworkResult.Success(true)
        else
            NetworkResult.Success(true)

}