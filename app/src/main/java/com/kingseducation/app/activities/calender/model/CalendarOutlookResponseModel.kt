package com.kingseducation.app.activities.calender.model

import com.google.gson.annotations.SerializedName


data class CalendarOutlookResponseModel(
    @SerializedName("status")
    val status: Int,
    @SerializedName("calendar")
    val calendar: ArrayList<CalendarEvent>,
    @SerializedName("calendar_categories")
    val calendarCategories: ArrayList<CalendarCategory>
) {
    data class CalendarEvent(

        @SerializedName("categories")
        val categories: ArrayList<String>,
        @SerializedName("subject")
        val subject: String,
        @SerializedName("start")
        val start: Start,
        @SerializedName("end")
        val end: End
    ) {
        data class Start(
            @SerializedName("dateTime")
            val dateTime: String,
            @SerializedName("timeZone")
            val timeZone: String
        )

        data class End(
            @SerializedName("dateTime")
            val dateTime: String,
            @SerializedName("timeZone")
            val timeZone: String
        )
    }

    data class CalendarCategory(
        @SerializedName("title")
        val title: String,
        @SerializedName("title_ar")
        val titleAr: String
    )
}

