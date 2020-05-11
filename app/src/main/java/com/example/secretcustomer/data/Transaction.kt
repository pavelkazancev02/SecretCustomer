package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("id")
    val id: String,
    @SerializedName("amount")
    val amount: Float
)