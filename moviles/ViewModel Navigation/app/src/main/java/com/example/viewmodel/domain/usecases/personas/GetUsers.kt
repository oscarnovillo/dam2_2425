package com.example.viewmodel.domain.usecases.personas

import com.example.viewmodel.data.Repository
import com.example.viewmodel.data.RespositoryDos
import com.example.viewmodel.data.UsersRepository
import javax.inject.Inject



class GetUsers @Inject constructor(private val usersRepository: UsersRepository){

    suspend operator fun invoke() = usersRepository.fetchUsers()
}