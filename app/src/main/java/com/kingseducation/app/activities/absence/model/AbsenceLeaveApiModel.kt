package com.kingseducation.app.activities.absence.model

import com.google.gson.annotations.SerializedName

class AbsenceLeaveApiModel (
    @SerializedName("student_id") val student_id: String,
    @SerializedName("start") val start: Int,
    @SerializedName("limit") val limit: Int
        )