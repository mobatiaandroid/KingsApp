package com.kingseducation.app.activities.reports.model

import com.google.gson.annotations.SerializedName

class ReportsResponseModel (
        @SerializedName("status") val status: Int,
        @SerializedName("reports") val reports: ArrayList<Reports>
        )