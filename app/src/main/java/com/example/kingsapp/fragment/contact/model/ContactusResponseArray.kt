package com.example.kingsapp.fragment.contact.model

import com.google.gson.annotations.SerializedName


class ContactusResponseArray (
    @SerializedName("school_name") val school_name: String,
    @SerializedName("description") val description: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("contacts") val contacts: ArrayList<ContactsListDetailModel>,
    @SerializedName("is_student_school") val is_student_school: Int
    )

