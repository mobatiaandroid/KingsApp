package com.kingseducation.app.activities.payments.model

import com.google.gson.annotations.SerializedName

class AttributesModel (
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String,
)