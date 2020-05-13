package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName

data class Shop(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("ownerId")
    val ownerId: Int,
    @SerializedName("address")
    val address: String,
    @SerializedName("balance")
    val balance: Float
)