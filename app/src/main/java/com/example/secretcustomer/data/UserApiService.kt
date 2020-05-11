package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*


interface UserApiService {

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

    @POST("/users/login")
    fun createUser(
        @Body createUserPostData: CreateUserPostData
    ): Call<Users>

    @POST("/users/login")
    fun logUser(
        @Body loginPostData: LoginPostData
    ): Call<Users>

    @POST("/users/byEmail")
    fun findUserByEmail(
        @Body email: String
    ): Call<Users>

    @PUT("/users/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("balance") balance: Float,
        @Field("role") role: String // Should be changed to Customer
    ):Call<Users>

    @DELETE("/users/{id}")
    fun deleteUser(
        @Path("id") id: Int
    ):Call<Users>
}

data class LoginPostData(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String
)

data class CreateUserPostData(
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("email") var email: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("role") var role: String // Should be changed to Customer
)