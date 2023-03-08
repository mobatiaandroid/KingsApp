package com.example.kingsapp.activities.timetable.model

import com.google.gson.annotations.SerializedName

data class TimeTableApiListModel (
    @SerializedName("id") val id: Int,
    @SerializedName("subject_name") val subject_name: String,
    @SerializedName("staff") val staff: String,
    @SerializedName("sortname") val sortname: String,
    @SerializedName("starttime") val starttime: String,

    )