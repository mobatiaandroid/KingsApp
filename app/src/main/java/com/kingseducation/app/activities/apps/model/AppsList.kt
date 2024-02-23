package com.kingseducation.app.activities.apps.model

import com.google.gson.annotations.SerializedName

class AppsList (
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
        )