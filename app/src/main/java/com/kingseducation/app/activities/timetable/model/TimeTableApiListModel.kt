package com.kingseducation.app.activities.timetable.model

import com.google.gson.annotations.SerializedName

data class TimeTableApiListModel (
    @SerializedName("range") val range: TimetableModel,
    @SerializedName("sort_fields") val field1List: ArrayList<FieldModel>,
    @SerializedName("all") val timeTableList: ArrayList<MondayList>,

    )