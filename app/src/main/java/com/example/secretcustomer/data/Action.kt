package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName

data class Action(
    @SerializedName("id") val id: Int,
    @SerializedName("shopId") val shopId: Int,
    @SerializedName("action") val action: String
)