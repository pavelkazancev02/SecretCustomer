package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName

data class Feedback(
    @SerializedName("id")
    val id: Int,
    @SerializedName("shopName")
    val shopName: String,
    @SerializedName("customerEmail")
    val customerEmail: String,
    @SerializedName("pros")
    val pros: String,
    @SerializedName("cons")
    val cons: String,
    @SerializedName("additionalInfo")
    val additionalInfo: String?,
    @SerializedName("date")
    val date: String,
    @SerializedName("rating")
    val rating: Int
)