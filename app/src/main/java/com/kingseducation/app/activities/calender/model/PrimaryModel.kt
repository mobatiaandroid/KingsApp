package com.kingseducation.app.activities.calender.model

import com.google.gson.annotations.SerializedName

class PrimaryModel {
        @SerializedName("start_date") var DTSTART:String=""
        @SerializedName("end_date") var DTEND:String=""
        @SerializedName("title") var SUMMARY:String=""
        @SerializedName("description") var DESCRIPTION:String=""
        @SerializedName("venue") var LOCATION:String=""
        @SerializedName("type") var type:Int=0
        @SerializedName("color") var color:String=""
        @SerializedName("startTime") var startTime:String=""
        @SerializedName("endTime") var endTime:String=""
}