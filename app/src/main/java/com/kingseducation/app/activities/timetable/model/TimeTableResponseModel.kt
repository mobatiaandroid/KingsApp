package com.kingseducation.app.activities.timetable.model

import com.google.gson.annotations.SerializedName

class TimeTableResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("timetable") val timetable: TimeTableApiListModel



)