package com.example.kingsapp.activities.message.model

import com.google.gson.annotations.SerializedName

class MessageListModel (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("message") val message: String,
    @SerializedName("alert_type") val alert_type: Int,
    @SerializedName("url") val url: String,
    @SerializedName("created_at") val created_at: String


    )