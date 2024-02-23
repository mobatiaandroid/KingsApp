package com.kingseducation.app.activities.parentessentials.model

import com.google.gson.annotations.SerializedName
import com.kingseducation.app.activities.forms.model.FormList

class ParentModel (
    @SerializedName("status") val status: Int,
    @SerializedName("parent_essentials") val parent_essentials: ArrayList<FormList>
        )