package com.example.kingsapp.activities.timetable.model

import com.google.gson.annotations.SerializedName

class TimeTableList (
    @SerializedName("id") var id: Int,
    @SerializedName("period_order") var period_order: Int,
    @SerializedName("period_name") var period_name: String,
    @SerializedName("day_name") var day_name: String,
    @SerializedName("subject_name") var subject_name: String,
    @SerializedName("staff") var staff: String,
    @SerializedName("start_time") var start_time: String,
    @SerializedName("end_time") var end_time: String,
    @SerializedName("is_break") var is_break: Int,
    @SerializedName("Monday") val Monday: ArrayList<MondayList>,
    @SerializedName("Tuesday") val Tuesday: ArrayList<MondayList>,
    @SerializedName("Wednesday") val Wednesday: ArrayList<MondayList>,
    @SerializedName("Thursday") val Thursday: ArrayList<MondayList>,
    @SerializedName("Friday") val Friday: ArrayList<MondayList>,

        )