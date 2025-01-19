package com.example.compose.data.remote.datasource

import com.example.compose.data.remote.NetworkResult
import com.example.compose.data.remote.modelo.toUser
import com.example.compose.di.IoDispatcher
import com.example.compose.domain.modelo.User
import com.example.compose.data.remote.apiServices.UserService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(
    private val userService: UserService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun fetchUsers(): NetworkResult<List<User>?> =
        safeApiCall { userService.getUsers() }.map { lista ->
            lista?.map { it.toUser() }
        }

    suspend fun delUser(id : Int): NetworkResult<Boolean> =
        safeApiCallNoBody { userService.delUser(id) }




}

