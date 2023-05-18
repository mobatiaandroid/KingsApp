package com.example.kingsapp.activities.reports.model

import com.google.gson.annotations.SerializedName

class Reports (
    @SerializedName("Acyear") val Acyear: Int,
    @SerializedName("data") val data: ArrayList<ReportsList>
        )