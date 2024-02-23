package com.kingseducation.app.activities.forms.model

import com.google.gson.annotations.SerializedName

class FormsModel (
    @SerializedName("status") val status: Int,
    @SerializedName("forms") val forms: ArrayList<FormList>
        )