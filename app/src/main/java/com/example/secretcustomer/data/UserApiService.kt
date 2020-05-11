package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.*


interface UserApiService {

    @GET("/users")
    fun getPaginatedUsersList(
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Single<Users>

    @GET("/users/me")
    fun getUserInfo(): Single<UserDetails>

    @POST("/users")
    fun createUser(
        @Body createUserPostData: CreateUserPostData
    )

    @Headers("Authorization")
    @POST("/users/login")
    fun logUser(
        @Body loginPostData: LoginPostData
    )

    @POST("/users/byEmail")
    fun findUserByEmail(
        @Body email: String
    ): Single<Users>

    @PUT("/users/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("balance") balance: Float,
        @Field("role") role: String // Should be changed to Customer
    ): Single<Users>

    @DELETE("/users/{id}")
    fun deleteUser(
        @Path("id") id: Int
    )
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