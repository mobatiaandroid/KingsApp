package com.example.kingsapp.activities.home.model

import com.google.gson.annotations.SerializedName

class HomeGuestrResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("home") val home: HomeGuest
        )