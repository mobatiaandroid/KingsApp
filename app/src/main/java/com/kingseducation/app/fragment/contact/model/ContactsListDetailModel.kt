package com.kingseducation.app.fragment.contact.model

import com.google.gson.annotations.SerializedName

 class ContactsListDetailModel(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String
    )
