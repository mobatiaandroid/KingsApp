package com.kingseducation.app.activities.calender.model

import com.google.gson.annotations.SerializedName

class CalendarDateModel {
    @SerializedName("startDate") var startDate: String=""
    @SerializedName("endDate") var endDate: String=""
    @SerializedName("detailList") var detailList= ArrayList<CalendarDetailModel>()
}