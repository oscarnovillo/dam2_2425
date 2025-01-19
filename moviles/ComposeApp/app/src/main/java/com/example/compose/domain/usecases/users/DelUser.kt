package com.example.compose.domain.usecases.users

import com.example.compose.data.UsersRepository
import javax.inject.Inject

class DelUser  @Inject constructor(val userRepository: UsersRepository) {

    suspend operator fun invoke(id : Int) = userRepository.delUserConFlow(id)
}