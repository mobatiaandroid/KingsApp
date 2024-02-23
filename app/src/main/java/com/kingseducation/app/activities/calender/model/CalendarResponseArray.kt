package com.kingseducation.app.activities.calender.model

import com.google.gson.annotations.SerializedName

class CalendarResponseArray(
    @SerializedName("title") val title: String,
    @SerializedName("color") val color: String,
    @SerializedName("details") val details: ArrayList<CalendarList>
)