package com.kingseducation.app.fragment.contact.model

import com.google.gson.annotations.SerializedName

class SingleContactusResponseArray (
    @SerializedName("school_id") val school_id: Int,
    @SerializedName("school_name") val school_name: String,
    @SerializedName("description") val description: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("contacts") val contacts: ArrayList<ContactsListDetailModel>,
    @SerializedName("school_identifier") val schoolIdentifier: String,

    )