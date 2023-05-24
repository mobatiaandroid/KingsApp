package com.example.kingsapp.activities.timetable.model

import com.google.gson.annotations.SerializedName

data class FieldModel (
    @SerializedName("period_name") val sortname: String,
    @SerializedName("start_time") val starttime: String,
    @SerializedName("end_time") val endtime: String
)