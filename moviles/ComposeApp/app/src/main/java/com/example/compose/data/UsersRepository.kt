package com.example.compose.data

import com.example.compose.data.remote.NetworkResult
import com.example.compose.data.remote.datasource.UsersRemoteDataSource
import com.example.compose.data.remote.modelo.toUser
import com.example.compose.di.IoDispatcher
import com.example.compose.domain.modelo.User
import com.example.compose.data.remote.apiServices.UserService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val userService: UserService,
    private val usersRemoteDataSource: UsersRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {


    suspend fun fetchUsersConFlow(): Flow<NetworkResult<List<User>?>> {

        return flow {

            emit(NetworkResult.Loading())
            val result = usersRemoteDataSource.fetchUsers()
            emit(result)

        }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: "Error"))
            }
            .flowOn(dispatcher)
    }


    suspend fun fetchUsers(): NetworkResult<List<User>?> {

        return usersRemoteDataSource.fetchUsers()

    }

    suspend fun fetchUser(id: Int): NetworkResult<User> {

        try {
            val response = userService.getUser(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body.toUser())
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }

    }

    suspend fun delUser(id: Int): NetworkResult<Boolean> {

        try {
            val response = userService.delUser(id)
            if (response.isSuccessful) {
                return NetworkResult.Success(true)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }

    }


    suspend fun delUserConFlow(id: Int): Flow<NetworkResult<Boolean>> {

        return flow {

            emit(NetworkResult.Loading())
            val result = usersRemoteDataSource.delUser(id)
            emit(result)

        }
            .catch { e ->
                emit(NetworkResult.Error(e.message ?: "Error"))
            }
            .flowOn(dispatcher)
    }


    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}