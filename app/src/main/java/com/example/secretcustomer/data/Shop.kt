package com.example.secretcustomer.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
    val balance: Int
) : Parcelable

data class ShopWithAvailability(
    @SerializedName("shop")
    val shop: Shop,
    @SerializedName("isAvailable")
    val isSessionAvailable: Boolean
)