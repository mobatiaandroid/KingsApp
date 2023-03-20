package com.example.kingsapp.activities.home.model

import com.example.kingsapp.activities.student_info.model.StudentInfo
import com.google.gson.annotations.SerializedName

class HomeUserResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("home") val home: Home
        )