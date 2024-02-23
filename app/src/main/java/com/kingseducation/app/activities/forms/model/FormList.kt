package com.kingseducation.app.activities.forms.model

import com.google.gson.annotations.SerializedName

class FormList (
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String
        )