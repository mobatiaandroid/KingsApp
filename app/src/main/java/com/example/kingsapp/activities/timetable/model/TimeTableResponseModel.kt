package com.example.kingsapp.activities.timetable.model

import com.example.kingsapp.activities.social_media.model.SocialMediaDetailModel
import com.google.gson.annotations.SerializedName

class TimeTableResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("timetable") val timetable: TimetableModel,
    @SerializedName("field") val field1List: List<FieldModel>,
    @SerializedName("Timetable") val timeTableList: List<MondayList>


)