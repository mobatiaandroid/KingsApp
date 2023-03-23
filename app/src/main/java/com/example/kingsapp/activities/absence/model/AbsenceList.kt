package com.example.kingsapp.activities.absence.model

import com.google.gson.annotations.SerializedName

class AbsenceList (
    @SerializedName("id") val id: Int,
    @SerializedName("reason") val reason: String,
    @SerializedName("status") val status: Int,
    @SerializedName("parent_name") val parent_name: String,
    @SerializedName("from_date") val from_date: String,
    @SerializedName("to_date") val to_date: String = "",
        )