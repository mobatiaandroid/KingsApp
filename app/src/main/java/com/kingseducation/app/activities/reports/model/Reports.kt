package com.kingseducation.app.activities.reports.model

import com.google.gson.annotations.SerializedName

class Reports (
    @SerializedName("Acyear") val Acyear: String,
    @SerializedName("data") val data: ArrayList<ReportsList>
)