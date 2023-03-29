package com.example.kingsapp.activities.parentessentials.model

import com.example.kingsapp.activities.forms.model.FormList
import com.google.gson.annotations.SerializedName

class ParentModel (
    @SerializedName("status") val status: Int,
    @SerializedName("parent_essentials") val parent_essentials: ArrayList<FormList>
        )