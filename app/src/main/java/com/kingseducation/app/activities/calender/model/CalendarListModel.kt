package com.kingseducation.app.activities.calender.model

import com.google.gson.annotations.SerializedName

class CalendarListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("calendar") val calendar: ArrayList<CalendarResponseArray>
        )