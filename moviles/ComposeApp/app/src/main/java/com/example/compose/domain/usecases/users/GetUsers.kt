package com.example.compose.domain.usecases.users

import com.example.compose.data.UsersRepository
import javax.inject.Inject

class GetUsers @Inject constructor(val userRepository: UsersRepository) {

    suspend operator fun invoke() = userRepository.fetchUsersConFlow()
}