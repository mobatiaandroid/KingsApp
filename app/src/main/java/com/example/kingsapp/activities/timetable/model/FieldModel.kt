package com.example.kingsapp.activities.timetable.model

import com.google.gson.annotations.SerializedName

data class FieldModel (
    @SerializedName("sortname") val sortname: String,
    @SerializedName("starttime") val starttime: String,
    @SerializedName("endtime") val endtime: String
)