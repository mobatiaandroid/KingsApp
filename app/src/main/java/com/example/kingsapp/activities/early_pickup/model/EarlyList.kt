package com.example.kingsapp.activities.early_pickup.model

import com.google.gson.annotations.SerializedName

class EarlyList (
    @SerializedName("id") val id: Int,
    @SerializedName("pickup_time") val pickup_time: String,
    @SerializedName("reason") val reason: String,
    @SerializedName("pickup_by_whom") val pickup_by_whom: String,
    @SerializedName("reason_for_rejection") val reason_for_rejection: String,
    @SerializedName("status") val status: Int ,
    @SerializedName("parent_name") val parent_name: String

    )