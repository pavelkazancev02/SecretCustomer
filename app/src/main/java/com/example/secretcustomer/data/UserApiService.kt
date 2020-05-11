package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.*


interface UserApiService {

    @GET("/users")
    fun getPaginatedUsersList(
        @Header("Authorization") auth: String,
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Single<Users>

    @GET("/users/me")
    fun getUserInfo(
        @Header("Authorization") auth: String
    ): Single<UserDetails>

    @POST("/users")
    fun createUser(
        @Header("Authorization") auth: String,
        @Body createUserPostData: CreateUserPostData
    ): Single<Unit>


    @POST("/users/login")
    fun logUser(
        @Header("Authorization") auth: String,
        @Body loginPostData: LoginPostData
    ): Single<Unit>

    @POST("/users/byEmail")
    fun findUserByEmail(
        @Header("Authorization") auth: String,
        @Body email: String
    ): Single<UserDetails>

    @PUT("/users/{id}")
    fun updateUser(
        @Header("Authorization") auth: String,
        @Path("id") id: Int,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("balance") balance: Float,
        @Field("role") role: String // Should be changed to Customer
    ): Single<UserDetails>

    @DELETE("/users/{id}")
    fun deleteUser(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): Single<Unit>
}

data class LoginPostData(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String
)

data class CreateUserPostData(
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("phone") var phone: String?,
    @SerializedName("role") var role: String // Should be changed to Customer
)