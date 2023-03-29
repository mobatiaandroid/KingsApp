package com.example.kingsapp.activities.forms.model

import com.example.kingsapp.fragment.contact.model.ContactusResponseArray
import com.google.gson.annotations.SerializedName

class FormsModel (
    @SerializedName("status") val status: Int,
    @SerializedName("forms") val forms: ArrayList<FormList>
        )