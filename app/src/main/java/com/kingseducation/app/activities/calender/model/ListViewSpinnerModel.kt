package com.kingseducation.app.activities.calender.model

import com.google.gson.annotations.SerializedName

class ListViewSpinnerModel (
        @SerializedName("filename") val filename: String,
        @SerializedName("title") val title: String,
        @SerializedName("id") val id: String
        )