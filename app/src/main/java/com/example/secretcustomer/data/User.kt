package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName

data class Users(
    val result: List<UserDetails>
)

data class UserDetails(
    @SerializedName("id")
    val id: Int,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("balance")
    val balance: Float,
    @SerializedName("role")
    val role: Role
)

enum class Role(name: String) {
    Customer("Customer"),
    ShopOwner("ShopOwner"),
    Admin("Admin")
}