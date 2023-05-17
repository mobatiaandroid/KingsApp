package com.example.kingsapp.activities.early_pickup.model

import com.google.gson.annotations.SerializedName

class RequestEarlyApiModel (
        @SerializedName("student_id") val student_id: String,
        @SerializedName("pickup_date_time") val pickup_date_time: String,
       // @SerializedName("to_date") val to_date: String,
        @SerializedName("pickup_reason") val pickup_reason: String,
        @SerializedName("pickup_by_whom") val pickup_by_whom: String,
        @SerializedName("device_type") val device_type: String,
        @SerializedName("device_name") val device_name: String,
        @SerializedName("app_version") val app_version: String
        )