package com.example.kingsapp.activities.calender.model

import com.example.kingsapp.activities.absence.model.AbsenceList
import com.google.gson.annotations.SerializedName

class CalendarListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("calendar") val calendar: ArrayList<CalendarList>
        )