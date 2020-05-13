package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class Session(
    @SerializedName("id")
    val id: Int,
    @SerializedName("shopId")
    val shopId: Int,
    @SerializedName("customerId")
    val customerId: Int,
    @SerializedName("stage")
    val stage: Int,
    @SerializedName("started")
    val started: Date,
    @SerializedName("expiresAt")
    val expiresAt: Date
)