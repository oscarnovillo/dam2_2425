package com.example.compose.data.remote.datasource

import com.example.compose.data.remote.NetworkResult
import retrofit2.Response

abstract class BaseApiResponse {


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

    suspend fun <T> safeApiCallNoBody(apiCall: suspend () -> Response<T>): NetworkResult<Boolean> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
               return NetworkResult.Success(true)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }


    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

}