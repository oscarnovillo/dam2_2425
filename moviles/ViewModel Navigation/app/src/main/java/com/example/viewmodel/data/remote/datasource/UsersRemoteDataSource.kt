package com.example.viewmodel.data.remote.datasource

import com.example.viewmodel.data.remote.NetworkResult
import com.example.viewmodel.data.remote.modelo.toUser
import com.example.primerxmlmvvm.di.IoDispatcher
import com.example.viewmodel.domain.modelo.User

import com.example.viewmodel.data.remote.apiServices.UserService
import com.example.viewmodel.data.remote.combine
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(
    private val userService: UserService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun fetchUsers(): NetworkResult<List<User>> =
        safeApiCall { userService.getUsers("8") }.then { list ->
            if (list.isNotEmpty())
                list.map { it.toUser() }.combine()
            else
                NetworkResult.Error("lista vacia")
        }

    suspend fun delUser(id : Int): NetworkResult<Boolean> =
        safeApiCallNoBody { userService.delUser(id) }




}

