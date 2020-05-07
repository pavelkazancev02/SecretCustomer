package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("id")
    val id: Int,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("name")
    val transactionName: String,
    @SerializedName("ownerId")
    val ownerId: Int,
    @SerializedName("address")
    val address: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("balance")
    val balance: Int
//    @SerializedName("role")
//    val role: Customer
)