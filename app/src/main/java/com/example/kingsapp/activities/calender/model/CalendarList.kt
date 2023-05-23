package com.example.kingsapp.activities.calender.model

import com.google.gson.annotations.SerializedName

class CalendarList (
        @SerializedName("id") val id: Int,
        @SerializedName("DTSTART") val start_date: String,
        @SerializedName("DTEND") val end_date: String,
        @SerializedName("SUMMARY") val title: String,
        @SerializedName("LOCATION") val venue: String,
        @SerializedName("DESCRIPTION") val description: String,
       // @SerializedName("monthNumber") var monthNumber: String,
       // @SerializedName("dayOfTheWeekk") var dayOfTheWeekk: String,
       // @SerializedName("dayss") var dayss: String,
       // @SerializedName("monthString") var monthString: String,
       // @SerializedName("yearr") var yearr: String

        )