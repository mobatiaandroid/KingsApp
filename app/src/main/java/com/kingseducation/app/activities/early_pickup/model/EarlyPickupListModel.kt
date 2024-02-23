package com.kingseducation.app.activities.early_pickup.model

import com.google.gson.annotations.SerializedName

class EarlyPickupListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("early_pickups") val early_pickups: ArrayList<EarlyList>
        )