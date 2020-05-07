package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class SecretCustomer (
    @SerializedName("id")
    val id: Int,
    @SerializedName("shopId")
    val shopId: Int,
    @SerializedName("customerId")
    val customerId: Int,
    @SerializedName("action")
    val action: String,
    @SerializedName("stage")
    val stage: Int,
    @SerializedName("started")
    val started: Date,
    @SerializedName("expiresAt")
    val expiresAt: Date,
    @SerializedName("pros")
    val pros: String,
    @SerializedName("cons")
    val cons: String,
    @SerializedName("additionalInfo")
    val additionalInfo: String
)