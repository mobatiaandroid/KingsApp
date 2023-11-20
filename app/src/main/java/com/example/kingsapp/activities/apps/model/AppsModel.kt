package com.example.kingsapp.activities.apps.model

import com.google.gson.annotations.SerializedName

class AppsModel (
        @SerializedName("status") val status: Int,
        @SerializedName("apps") val apps: ArrayList<AppsList>
        )