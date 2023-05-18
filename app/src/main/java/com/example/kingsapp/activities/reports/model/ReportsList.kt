package com.example.kingsapp.activities.reports.model

import com.google.gson.annotations.SerializedName

class ReportsList (
    @SerializedName("id") val id: String,
    @SerializedName("report_cycle") val report_cycle: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("file") val file: String

    )