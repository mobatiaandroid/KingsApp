package com.example.kingsapp.activities.calender.model

import com.example.kingsapp.activities.forms.model.FormList
import com.google.gson.annotations.SerializedName

class CalendarResponseArray(
    @SerializedName("title") val title: String,
    @SerializedName("color") val color: String,
    @SerializedName("details") val details: ArrayList<CalendarList>
)