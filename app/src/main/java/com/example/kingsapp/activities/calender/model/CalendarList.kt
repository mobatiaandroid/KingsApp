package com.example.kingsapp.activities.calender.model

import com.google.gson.annotations.SerializedName

class CalendarList (
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("start_date") val start_date: String,
        @SerializedName("end_date") val end_date: String,
        @SerializedName("venue") val venue: String,
        @SerializedName("description") val description: String,
        @SerializedName("monthNumber") var monthNumber: String,
        @SerializedName("dayOfTheWeekk") var dayOfTheWeekk: String,
        @SerializedName("dayss") var dayss: String,
        @SerializedName("monthString") var monthString: String,
        @SerializedName("yearr") var yearr: String

        )