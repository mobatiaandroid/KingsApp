package com.example.kingsapp.activities.early_pickup.model

import com.example.kingsapp.activities.absence.model.AbsenceList
import com.google.gson.annotations.SerializedName

class EarlyPickupListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("early_pickups") val early_pickups: ArrayList<EarlyList>
        )