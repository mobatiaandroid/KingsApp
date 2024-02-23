package com.kingseducation.app.activities.home.model

import com.google.gson.annotations.SerializedName

class HomeGuest (
    @SerializedName("android_version") val android_version: String,
    @SerializedName("ios_version") val ios_version: String
        )