package com.kingseducation.app.activities.home.model

import com.google.gson.annotations.SerializedName

class UserDetails (
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
        )