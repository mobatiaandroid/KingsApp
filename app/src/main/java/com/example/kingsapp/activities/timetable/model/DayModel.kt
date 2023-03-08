package com.example.kingsapp.activities.timetable.model

import com.google.gson.annotations.SerializedName

data class DayModel (
    @SerializedName("id") var id: Int=0,
    @SerializedName("subject_name") var subject_name: String=""
    )