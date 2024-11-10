package com.example.viewmodel.data.remote.datasource

import com.example.viewmodel.data.remote.NetworkResult
import com.example.viewmodel.data.remote.modelo.toUser
import com.example.primerxmlmvvm.di.IoDispatcher
import com.example.viewmodel.domain.modelo.User

import com.example.viewmodel.data.remote.apiServices.UserService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(
    private val userService: UserService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun fetchUsers(): NetworkResult<List<User>?> =
        safeApiCall { userService.getUsers("8") }.map { lista ->
            lista?.map { it.toUser() }
        }

    suspend fun delUser(id : Int): NetworkResult<Boolean> =
        safeApiCallNoBody { userService.delUser(id) }




}

