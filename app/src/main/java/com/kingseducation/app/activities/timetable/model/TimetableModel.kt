package com.kingseducation.app.activities.timetable.model

import com.google.gson.annotations.SerializedName

class TimetableModel (
    @SerializedName("Monday") val Monday: ArrayList<MondayList>,
    @SerializedName("Tuesday") val Tuesday: ArrayList<MondayList>,
    @SerializedName("Wednesday") val Wednesday: ArrayList<MondayList>,
    @SerializedName("Thursday") val Thursday: ArrayList<MondayList>,
    @SerializedName("Friday") val Friday: ArrayList<MondayList>

)