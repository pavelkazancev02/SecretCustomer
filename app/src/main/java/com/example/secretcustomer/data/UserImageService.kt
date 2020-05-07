package com.example.secretcustomer.data

import retrofit2.Call
import retrofit2.http.*

interface UserImageService {

    @GET("/users")
    fun getUsersListById(
        @Query("id") id: Int
    ): Call<Users>

    @GET("/users/me")
    fun getUserInfo(
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String,
        @Query("email") email: String,
        @Query("phone") phone: String,
        @Query("id") id: Int,
        @Query("balance") balance: Float,
        @Query("role") role: String //Should be changed
    ): Call<UserDetails>

    @POST("/users")
    fun postUser()

    @POST("/users/login")
    fun logUser()

    @POST("/users/byEmail")
    fun findUserByEmail()

    @PUT("/users/{id}")
    fun updateUser()

    @DELETE("/users/{id}")
    fun deleteUser()
}