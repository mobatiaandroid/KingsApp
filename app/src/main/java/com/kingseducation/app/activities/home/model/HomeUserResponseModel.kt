package com.kingseducation.app.activities.home.model

import com.google.gson.annotations.SerializedName

class HomeUserResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("home") val home: Home
        )