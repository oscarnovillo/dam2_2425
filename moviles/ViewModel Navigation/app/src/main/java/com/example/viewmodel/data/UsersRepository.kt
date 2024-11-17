package com.example.viewmodel.data

import com.example.primerxmlmvvm.di.IoDispatcher
import com.example.viewmodel.data.remote.NetworkResult
import com.example.viewmodel.data.remote.apiServices.UserService
import com.example.viewmodel.data.remote.datasource.UsersRemoteDataSource
import com.example.viewmodel.data.remote.modelo.toUser
import com.example.viewmodel.domain.modelo.User
import kotlinx.coroutines.CoroutineDispatcher

import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val userService: UserService,
    private val usersRemoteDataSource: UsersRemoteDataSource,

) {



    suspend fun fetchUsers(): NetworkResult<List<User>> {

        return usersRemoteDataSource.fetchUsers()

    }

    suspend fun fetchUser(id: Int): NetworkResult<User> {

        try {
            val response = userService.getUser(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return body.toUser()
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }

    }



    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}