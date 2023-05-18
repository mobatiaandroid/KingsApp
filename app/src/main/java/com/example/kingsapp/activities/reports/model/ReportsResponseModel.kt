package com.example.kingsapp.activities.reports.model

import com.example.kingsapp.activities.message.model.MessageListModel
import com.google.gson.annotations.SerializedName

class ReportsResponseModel (
        @SerializedName("status") val status: Int,
        @SerializedName("reports") val reports: ArrayList<Reports>
        )