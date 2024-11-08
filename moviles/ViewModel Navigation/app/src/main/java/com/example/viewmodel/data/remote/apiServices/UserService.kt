package com.example.viewmodel.data.remote.apiServices

import com.example.viewmodel.data.remote.modelo.UserRemote
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Retrofit API Service
 */
interface UserService {

    @GET("/users")
    suspend fun getUsers(@Query("user") user: String) : Response<List<UserRemote>>


    @GET("/users/{id}")
    suspend fun getUser(@Path("id") id : Int) : Response<UserRemote>


    @DELETE("/users/{id}")
    suspend fun delUser(@Path("id") id : Int) : Response<Unit>




//
//    @GET("/3/movie/{movie_id}")
//    suspend fun getMovie(@Path("movie_id") id: Int) : Response<MovieDesc>
}