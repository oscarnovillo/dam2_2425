package com.example.compose.data.remote

import retrofit2.Response


sealed class NetworkResult<T>(
    var data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)

    class Loading<T> : NetworkResult<T>()


    fun <R> map( transform :(data: T?) -> R) : NetworkResult<R> =
        when(this){
            is Error -> Error(message!!,transform(data))
            is Loading -> Loading()
            is Success -> Success(transform(data))
        }





}

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
    try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                return NetworkResult.Success(body)
            }
        }
        return error("${response.code()} ${response.message()}")
    } catch (e: Exception) {
        return error(e.message ?: e.toString())
    }
}