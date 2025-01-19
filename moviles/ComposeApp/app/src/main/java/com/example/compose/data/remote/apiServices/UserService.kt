package com.example.compose.data.remote.apiServices

import com.example.compose.data.remote.modelo.UserRemote
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Retrofit API Service
 */
interface UserService {

    @GET("/users")
    suspend fun getUsers() : Response<List<UserRemote>>


    @GET("/users/{id}")
    suspend fun getUser(@Path("id") id : Int) : Response<UserRemote>


    @DELETE("/users/{id}")
    suspend fun delUser(@Path("id") id : Int) : Response<Unit>




//
//    @GET("/3/movie/{movie_id}")
//    suspend fun getMovie(@Path("movie_id") id: Int) : Response<MovieDesc>
}