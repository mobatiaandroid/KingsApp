package com.example.kingsapp.fragment.contact.model

import com.google.gson.annotations.SerializedName

 class ContactusModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: ContactusResponseArray
)