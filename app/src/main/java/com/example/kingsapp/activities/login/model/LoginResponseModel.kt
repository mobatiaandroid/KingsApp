package com.example.kingsapp.activities.login.model

import com.google.gson.annotations.SerializedName

class LoginResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("token") val token: String
        )