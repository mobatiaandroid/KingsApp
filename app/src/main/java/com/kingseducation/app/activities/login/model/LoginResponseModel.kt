package com.kingseducation.app.activities.login.model

import com.google.gson.annotations.SerializedName

class LoginResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("token") val token: String
        )