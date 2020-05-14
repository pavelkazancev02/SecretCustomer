package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName

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
    val started: String, //todo: разобраться с датой
    @SerializedName("expiresAt")
    val expiresAt: String //todo: разобраться с датой
)