package com.example.compose.domain.usecases.users

import android.net.Network
import com.example.compose.data.UsersRepository
import com.example.compose.data.remote.NetworkResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DelUser  @Inject constructor(val userRepository: UsersRepository) {

    suspend operator fun invoke(id : Int)  =
        userRepository.delUserConFlow(id)




}